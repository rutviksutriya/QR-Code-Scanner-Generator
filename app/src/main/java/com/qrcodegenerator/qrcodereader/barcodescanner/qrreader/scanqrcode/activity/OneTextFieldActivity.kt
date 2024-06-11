package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.SystemClock
import android.text.InputType
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.util.Patterns
import android.widget.Toast
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline

import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityOneTextFieldBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav

class OneTextFieldActivity : BaseActivity<ActivityOneTextFieldBinding>() {

    private var title = ""
    private var hint = ""
    private var mLastClickTime: Long = 0

    private var msg = ""
    override fun getViewBinding(): ActivityOneTextFieldBinding {
        return ActivityOneTextFieldBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {

            toolBarView.tvTitle.text = title
            toolBarView.ivBack.setOnClickListener {if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
                mLastClickTime = SystemClock.elapsedRealtime()
                InterMoneyAds.showBackInterMoney(this@OneTextFieldActivity, 800)
            }

            if (title.equals("Phone", true)) {
                editText.setKeyListener(DigitsKeyListener.getInstance("0123456789+"))

            } else if (title.equals("Email", true)) {
                editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            } else {
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }

            editText.hint = hint
            tvGeneratedCode.setOnClickListener {
                if (TextUtils.isEmpty(editText.text.toString())) {
                    Toast.makeText(this@OneTextFieldActivity, msg, Toast.LENGTH_SHORT).show()
                } else if (title == "Email" && !Patterns.EMAIL_ADDRESS.matcher(editText.text.toString())
                        .matches()
                ) {
                    Toast.makeText(
                        this@OneTextFieldActivity,
                        "Please enter valid email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {


                    if (isOnline()) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return@setOnClickListener
                        }
                        mLastClickTime = SystemClock.elapsedRealtime()
                        InterMoneyAds.showForwardCountInterMoney(this@OneTextFieldActivity, 101, object :
                            AagalJav {
                            override fun onNext() {
                                startActivity(
                                    Intent(this@OneTextFieldActivity, CodeShowActivity::class.java)
                                        .putExtra("title", title)
                                        .putExtra("codeText", editText.text.toString())
                                )
                                finish()
                            }
                        })
                    }else{
                        showNoInternet {
                            startActivity(
                                Intent(this@OneTextFieldActivity, CodeShowActivity::class.java)
                                    .putExtra("title", title)
                                    .putExtra("codeText", editText.text.toString())
                            )
                            finish()
                        }
                    }
                }
            }
        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadMotiAds(
                    this@OneTextFieldActivity,
                    llMotiMoneyView.root,
                    llMotiMoneyView.nativeCurtain.motiMoneyCurtain,
                    llMotiMoneyView.moneyContainer,
                    llMotiMoneyView.flPlaceView,
                    llMotiMoneyView.llMotoCurtain
                )


            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadMotiAds(
                    this@OneTextFieldActivity,
                    llMotiMoneyView.root,
                    llMotiMoneyView.nativeCurtain.motiMoneyCurtain,
                    llMotiMoneyView.moneyContainer,
                    llMotiMoneyView.flPlaceView,
                    llMotiMoneyView.llMotoCurtain
                )
            }
        }
    }

    override fun initIntentData() {
        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title").toString()
        }
        if (intent.hasExtra("hint")) {
            hint = intent.getStringExtra("hint").toString()
        }

        if (intent.hasExtra("msg")) {
            msg = intent.getStringExtra("msg").toString()
        }
    }

}