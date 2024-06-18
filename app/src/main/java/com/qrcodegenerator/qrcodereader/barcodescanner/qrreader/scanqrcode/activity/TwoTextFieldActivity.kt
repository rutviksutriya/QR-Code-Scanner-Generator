package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.SystemClock
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityTwoTextFieldBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav

class TwoTextFieldActivity : BaseActivity<ActivityTwoTextFieldBinding>() {
    private var title = ""
    private var hint1 = ""
    private var msg1 = ""
    private var mLastClickTime: Long = 0

    private var hint2 = ""
    private var msg2 = ""
    private var spinnerText = ""

    override fun getViewBinding(): ActivityTwoTextFieldBinding {
        return ActivityTwoTextFieldBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {

            toolBarView.tvTitle.text = title
            toolBarView.ivBack.setOnClickListener { if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
                mLastClickTime = SystemClock.elapsedRealtime()
                InterMoneyAds.showBackInterMoney(this@TwoTextFieldActivity, 800)
            }
            if (hint1.equals("Enter number", true)) {
                editText1.inputType = InputType.TYPE_CLASS_PHONE
            } else {
                editText1.inputType = InputType.TYPE_CLASS_TEXT
            }

            when (title) {
                "Website" -> {
                    editText3.hint = hint2
                    editText1.visibility = View.GONE
                    editText2.visibility = View.VISIBLE
                    editText3.visibility = View.VISIBLE
                    setupSpinner("Website")
                }


                "Spotify" -> {
                    editText1.hint = hint1
                    editText3.hint = hint2
                    editText1.visibility = View.VISIBLE
                    editText2.visibility = View.GONE
                    editText3.visibility = View.VISIBLE
                }

                "Wi-Fi" -> {
                    editText1.hint = hint1
                    editText3.hint = hint2
                    editText1.visibility = View.VISIBLE
                    editText2.visibility = View.VISIBLE
                    editText3.visibility = View.VISIBLE
                    setupSpinner("Wi-Fi")
                }
            }


            tvGeneratedCode.setOnClickListener {
                if (TextUtils.isEmpty(editText1.text.toString()) && title != "Website") {
                    Toast.makeText(applicationContext, msg1, Toast.LENGTH_SHORT).show()
                } else if (editText3.visibility == View.VISIBLE && TextUtils.isEmpty(editText3.text.toString())) {
                    Toast.makeText(applicationContext, msg2, Toast.LENGTH_SHORT).show()
                } else {


                    if (isOnline()) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return@setOnClickListener
                        }
                        mLastClickTime = SystemClock.elapsedRealtime()
                        InterMoneyAds.showForwardCountInterMoney(this@TwoTextFieldActivity, 101, object :
                            AagalJav {
                            override fun onNext() {
                                nextActivity()
                            }
                        })
                    }else{
                        showNoInternet {
                            nextActivity()
                        }
                    }
                }
            }
        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@TwoTextFieldActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }


    private fun nextActivity(){
        binding.apply {       when (title) {
            "Website" -> {
                startActivity(
                    Intent(
                        this@TwoTextFieldActivity, CodeShowActivity::class.java
                    ).putExtra(
                        "title", title
                    ).putExtra("codeText", "Website:- "+spinnerText + editText3.text.toString())
                )

            }


            "Spotify" -> {
                startActivity(
                    Intent(
                        this@TwoTextFieldActivity, CodeShowActivity::class.java
                    ).putExtra(
                        "title", title
                    ).putExtra(
                        "codeText",
                        "Artist Name:- " + editText1.text.toString() + "\nArtist Song:- " + editText3.text.toString()
                    )
                )

            }
            "Wi-Fi" -> {
                startActivity(
                    Intent(
                        this@TwoTextFieldActivity, CodeShowActivity::class.java
                    ).putExtra(
                        "title", title
                    ).putExtra(
                        "codeText",
                        "SSID:- " + editText1.text.toString() + "\nEncryption:- " + spinnerText + "\nPassword:- " + editText3.text.toString()
                    )
                )

            }
        } }
    }


    override fun onResume() {
        super.onResume()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@TwoTextFieldActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }


    private fun setupSpinner(s: String) {
        val personNames: Array<String> = if (s == "Website") {
            arrayOf("http://", "https://", "ffp://", "file://")
        } else {
            arrayOf("WPA/WPA2", "WEP", "None")

        }

        val spinner = binding.spinner
        val arrayAdapter = ArrayAdapter(this, R.layout.spinner, personNames)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                spinnerText = personNames[position]
                if (s == "Website") {
                    //   binding.tvTitle3.text = personNames[position]
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinnerText = personNames[0]
                // Code to perform some action when nothing is selected
            }
        }
    }

    override fun initIntentData() {
        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title").toString()
        }

        if (intent.hasExtra("hint1")) {
            hint1 = intent.getStringExtra("hint1").toString()
        }

        if (intent.hasExtra("msg1")) {
            msg1 = intent.getStringExtra("msg1").toString()
        }


        if (intent.hasExtra("hint2")) {
            hint2 = intent.getStringExtra("hint2").toString()
        }

        if (intent.hasExtra("msg2")) {
            msg2 = intent.getStringExtra("msg2").toString()
        }


    }
}