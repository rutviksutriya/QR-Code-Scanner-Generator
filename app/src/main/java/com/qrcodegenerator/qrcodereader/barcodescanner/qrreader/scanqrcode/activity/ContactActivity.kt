package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter.DialCodeAdapter
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline

import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityContactBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.DialCodeModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds.showBackInterMoney
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav

class ContactActivity : BaseActivity<ActivityContactBinding>() {

    private var mLastClickTime: Long = 0
    var countryList: ArrayList<DialCodeModel> = arrayListOf()
    var phoneCode = "+91"

    override fun getViewBinding(): ActivityContactBinding {
        return ActivityContactBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {
            toolBarView.tvTitle.text = getString(R.string.contact)
            toolBarView.ivBack.setOnClickListener {    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
                mLastClickTime = SystemClock.elapsedRealtime()
                showBackInterMoney(this@ContactActivity, 800) }


            tvGeneratedCode.setOnClickListener {
                if (TextUtils.isEmpty(editText1.text.toString())) {
                    Toast.makeText(this@ContactActivity, "Please enter name", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(editText2.text.toString())) {
                    Toast.makeText(
                        this@ContactActivity, "Please enter phone number", Toast.LENGTH_SHORT
                    ).show()
                }else if (editText2.text.toString().length > 17 || editText2.text.toString().length <= 9) {
                    Toast.makeText(
                        this@ContactActivity,  "Please enter phone number length 10-16", Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(editText3.text.toString())) {
                    Toast.makeText(this@ContactActivity, "Please enter email", Toast.LENGTH_SHORT)
                        .show()
                } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editText3.text).matches()) {
                    Toast.makeText(
                        this@ContactActivity,
                        "Please enter valid email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(editText4.text.toString())) {
                    Toast.makeText(this@ContactActivity, "Please enter company", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(editText5.text.toString())) {
                    Toast.makeText(
                        this@ContactActivity, "Please enter job name", Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(editText6.text.toString())) {
                    Toast.makeText(this@ContactActivity, "Please enter address", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(editText7.text.toString())) {
                    Toast.makeText(this@ContactActivity, "Please enter website", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    val text =
                        "Name:- " + editText1.text.toString() + "\nPhone Number:- " +phoneCode+" "+ editText2.text.toString() + "\nEmail:- " + editText3.text.toString() + "\nCompany:- " + editText4.text.toString() + "\nJob Name:- " + editText5.text.toString() + "\nAddress:- " + editText6.text.toString() + "\nWebsite:- " + editText7.text.toString()



                    if (isOnline()) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return@setOnClickListener
                        }
                        mLastClickTime = SystemClock.elapsedRealtime()
                        InterMoneyAds.showForwardCountInterMoney(this@ContactActivity, 101, object :
                            AagalJav {
                            override fun onNext() {
                                startActivity(
                                    Intent(this@ContactActivity, CodeShowActivity::class.java).putExtra(
                                        "title", "Contact"
                                    ).putExtra("codeText", text)
                                )

                            }
                        })
                    }else{
                        showNoInternet {
                            startActivity(
                                Intent(this@ContactActivity, CodeShowActivity::class.java).putExtra(
                                    "title", "Contact"
                                ).putExtra("codeText", text)
                            )

                        }
                    }


                }
            }


        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@ContactActivity,
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
    private fun setDialCode() {
        binding.apply {
            countryList.clear()
            countryList = Constant.getCountryCodeList(this@ContactActivity)
            val adapter = DialCodeAdapter(this@ContactActivity, countryList)
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
                LessMoneyBannerAds.loadVachaliAds(
                    this@ContactActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }

    override fun initIntentData() {

    }
}