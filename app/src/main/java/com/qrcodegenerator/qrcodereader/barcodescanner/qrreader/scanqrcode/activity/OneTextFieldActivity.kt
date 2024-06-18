package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.SystemClock
import android.text.InputType
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter.DialCodeAdapter
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.gone
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.visible

import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityOneTextFieldBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.DialCodeModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav

class OneTextFieldActivity : BaseActivity<ActivityOneTextFieldBinding>() {
    private var countryList: ArrayList<DialCodeModel> = arrayListOf()

    private var title = ""
    private var hint = ""
    private var mLastClickTime: Long = 0
    var phoneCode = "+91"

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

            if (title.equals("Phone", true) || title.equals("WhatsApp", true)) {
                editText.gone()
                llSpinner.visible()
                editText2.visible()

            } else if (title.equals("Email", true)) {
                editText.visible()
                llSpinner.gone()
                editText2.gone()
                editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            } else {
                editText.visible()
                llSpinner.gone()
                editText2.gone()
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }

            editText.hint = hint
            editText2.hint = hint
            tvGeneratedCode.setOnClickListener {
                if (editText.visibility == View.VISIBLE &&   TextUtils.isEmpty(editText.text.toString())) {
                    Toast.makeText(this@OneTextFieldActivity, msg, Toast.LENGTH_SHORT).show()
                } else if (title == "Email" && !Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
                    Toast.makeText(
                        this@OneTextFieldActivity,
                        "Please enter valid email",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if (llSpinner.visibility == View.VISIBLE &&   TextUtils.isEmpty(editText2.text.toString())) {
                    Toast.makeText(this@OneTextFieldActivity, msg, Toast.LENGTH_SHORT).show()
                }

                else if (editText2.visibility == View.VISIBLE && (editText2.text.toString().length > 17 || editText2.text.toString().length <= 9)

                ) {
                    Toast.makeText(
                        this@OneTextFieldActivity,
                        "Please enter phone number length 10-16",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val text  = if (title.equals("Phone", true) || title.equals("WhatsApp", true)) {
                        title+":- "  +phoneCode+" "+editText2.text.toString()
                    }else{
                        title+":- " +editText.text.toString()
                    }


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
                                        .putExtra("codeText",text )
                                )

                            }
                        })
                    }else{
                        showNoInternet {
                            startActivity(
                                Intent(this@OneTextFieldActivity, CodeShowActivity::class.java)
                                    .putExtra("title", title)
                                    .putExtra("codeText", text)
                            )

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

        setDialCode()

    }
    private fun setDialCode() {
        binding.apply {
            countryList.clear()
            countryList = Constant.getCountryCodeList(this@OneTextFieldActivity)
            val adapter = DialCodeAdapter(this@OneTextFieldActivity, countryList)
            spCountry.adapter = adapter
            spCountry.setSelection(97)
            spCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    phoneCode = countryList[position].dial

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
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