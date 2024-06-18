package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.OnCustomDialogListener


class CustomDialog<B : ViewBinding>(
    private var cn: Context,
    private var listener: OnCustomDialogListener<B>,
) {

    private lateinit var dialogView: Dialog
    private lateinit var bindingView: B

    init {
        init()
    }

    private fun init() {

        dialogView = Dialog(cn)
        bindingView = listener.getBinding()

        dialogView.apply {
            setContentView(bindingView.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setGravity(Gravity.CENTER)
        }

        listener.onDialogBind(bindingView, dialogView)


    }

    fun getDialogView(): Dialog {
        return dialogView
    }

    fun getBinding(): B {
        return bindingView
    }

}