package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.SystemClock
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter.DialCodeAdapter
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivitySmsBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.DialCodeModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds

class SMSActivity : BaseActivity<ActivitySmsBinding>() {
    private var title = ""
    private var hint1 = ""
    private var msg1 = ""
    private var mLastClickTime: Long = 0
    var countryList: ArrayList<DialCodeModel> = arrayListOf()
    var phoneCode = "+91"

    private var hint2 = ""
    private var msg2 = ""

    override fun getViewBinding(): ActivitySmsBinding {
        return ActivitySmsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {
            toolBarView.tvTitle.text = title
            toolBarView.ivBack.setOnClickListener {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return@setOnClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                InterMoneyAds.showBackInterMoney(this@SMSActivity, 800)


            }
            editText1.hint = hint1
            editText2.hint = hint2


            tvGeneratedCode.setOnClickListener {
                if (TextUtils.isEmpty(editText1.text.toString())) {
                    Toast.makeText(applicationContext, msg1, Toast.LENGTH_SHORT).show()
                } else if (editText1.text.toString().length > 17 || editText1.text.toString().length <= 9) {
                    Toast.makeText(
                        this@SMSActivity,
                        "Please enter phone number length 10-16",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(editText2.text.toString())) {
                    Toast.makeText(applicationContext, msg2, Toast.LENGTH_SHORT).show()
                } else {


                    if (isOnline()) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return@setOnClickListener
                        }
                        mLastClickTime = SystemClock.elapsedRealtime()
                        InterMoneyAds.showForwardCountInterMoney(this@SMSActivity,
                            101,
                            object : AagalJav {
                                override fun onNext() {
                                    startActivity(
                                        Intent(
                                            this@SMSActivity, CodeShowActivity::class.java
                                        ).putExtra(
                                            "title", title
                                        ).putExtra(
                                            "codeText",
                                            "Number:- " + phoneCode + " " + editText1.text.toString() + "\nMessage:- " + editText2.text.toString()

                                        )
                                    )

                                }
                            })
                    } else {
                        showNoInternet {
                            startActivity(
                                Intent(
                                    this@SMSActivity, CodeShowActivity::class.java
                                ).putExtra(
                                    "title", title
                                ).putExtra(
                                    "codeText",
                                    "Number:- " + phoneCode + " " + editText1.text.toString() + "\nMessage:- " + editText2.text.toString()

                                )
                            )

                        }
                    }
                }
            }


        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@SMSActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
        setDialCode()

    }

    override fun onResume() {
        super.onResume()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@SMSActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }

    private fun setDialCode() {
        binding.apply {
            countryList.clear()
            countryList = Constant.getCountryCodeList(this@SMSActivity)
            val adapter = DialCodeAdapter(this@SMSActivity, countryList)
            spCountry.adapter = adapter
            spCountry.setSelection(97)
            spCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                    phoneCode = countryList[position].dial

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    override fun initIntentData() {
        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title")?:""
        }

        if (intent.hasExtra("hint1")) {
            hint1 = intent.getStringExtra("hint1")?:""
        }

        if (intent.hasExtra("msg1")) {
            msg1 = intent.getStringExtra("msg1")?:""
        }


        if (intent.hasExtra("hint2")) {
            hint2 = intent.getStringExtra("hint2")?:""
        }

        if (intent.hasExtra("msg2")) {
            msg2 = intent.getStringExtra("msg2")?:""
        }

    }
}