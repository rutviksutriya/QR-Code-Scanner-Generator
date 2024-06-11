package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.CustomDialog
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.OnCustomDialogListener
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.gone
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.visible
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.DialogCommonBinding


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        val mWindowBtc = window
        val mBackgroundBtc = ResourcesCompat.getDrawable(
            resources,
            R.drawable.statusbar_theme_background_drawable,
            theme
        )
        mWindowBtc.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        mWindowBtc.statusBarColor = ContextCompat.getColor(
            this,  android.R.color.transparent)
        mWindowBtc.navigationBarColor = ContextCompat.getColor(
            this, android.R.color.transparent)
        mWindowBtc.setBackgroundDrawable(mBackgroundBtc)
        initIntentData()
        initView()

    }


    abstract fun getViewBinding(): B

    abstract fun initView()
    abstract fun initIntentData()

    fun showNoInternet(callback: () -> Unit) {
        CustomDialog(this@BaseActivity, object : OnCustomDialogListener<DialogCommonBinding> {

            override fun getBinding(): DialogCommonBinding {
                return DialogCommonBinding.inflate(layoutInflater)
            }

            override fun onDialogBind(binding: DialogCommonBinding, dialog: Dialog) {
                binding.apply {
                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                    tvDialogOk.text = getString(R.string.try_again)
                    tvDialogDesc.text = getString(R.string.check_internet)

                    tvDialogOk.setOnClickListener {
                        if (isOnline()) {
                            dialog.dismiss()
                            callback.invoke()
                        } else {
                            Toast.makeText(this@BaseActivity,getString(R.string.check_internet), Toast.LENGTH_SHORT ).show()
                        }

                    }

                }
            }

        }).getDialog().show()
    }


    private var baseDialog: Dialog? = null

    fun showDialog(
        title: String = "",
        desc: String,
        isExtraLin: Boolean = false,
        doneCallback: () -> Unit
    ) {

        baseDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        baseDialog = CustomDialog(this, object : OnCustomDialogListener<DialogCommonBinding> {

            override fun getBinding(): DialogCommonBinding {
                return DialogCommonBinding.inflate(layoutInflater)
            }

            override fun onDialogBind(binding: DialogCommonBinding, dialog: Dialog) {
                dialog.setCancelable(false)
                dialog.setCanceledOnTouchOutside(false)
                binding.apply {
                    if (title.isEmpty()) {
                        tvDialogTitle.text = getString(R.string.app_name)
                    } else {
                        tvDialogTitle.text = title
                    }
                    if (isExtraLin) {
                        llTow.visible()
                        tvDialogOk.gone()
                    } else {
                        llTow.gone()
                        tvDialogOk.visible()
                    }
                    tvDialogDesc.text = desc
                    tvDialogCancelEx.setOnClickListener {
                        dialog.dismiss()
                    }
                    tvDialogOk.setOnClickListener {
                        dialog.dismiss()
                        doneCallback.invoke()

                    }
                    tvDialogOkEx.setOnClickListener {
                        dialog.dismiss()
                        doneCallback.invoke()

                    }
                }
            }

        }).getDialog()
        baseDialog?.show()

    }


}