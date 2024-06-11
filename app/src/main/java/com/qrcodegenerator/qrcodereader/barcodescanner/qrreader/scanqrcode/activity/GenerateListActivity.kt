package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.SystemClock
import androidx.recyclerview.widget.GridLayoutManager
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter.QrCodeTypeAdapter
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityGenerateListBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.QRCodeTypeModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav

class GenerateListActivity : BaseActivity<ActivityGenerateListBinding>() {
    private var codeList = ArrayList<QRCodeTypeModel>()
    private var mLastClickTime: Long = 0

    private lateinit var qrCodeTypeAdapter: QrCodeTypeAdapter


    override fun getViewBinding(): ActivityGenerateListBinding {
        return ActivityGenerateListBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {

            toolBarView.tvTitle.text = resources?.getString(R.string.generate_qr)
            toolBarView.ivBack.setOnClickListener { if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
                mLastClickTime = SystemClock.elapsedRealtime()
                InterMoneyAds.showBackInterMoney(this@GenerateListActivity, 800)
            }

            codeList.clear()
            codeList.add(QRCodeTypeModel("Text", R.drawable.ic_qr_text))
            codeList.add(QRCodeTypeModel("Phone", R.drawable.ic_qr_phone))
            codeList.add(QRCodeTypeModel("Website", R.drawable.ic_qr_website)) 

            codeList.add(QRCodeTypeModel("SMS", R.drawable.ic_qr_sms))
            codeList.add(QRCodeTypeModel("Contact", R.drawable.ic_qr_contact))
            codeList.add(QRCodeTypeModel("Instagram", R.drawable.ic_qr_instagram))

            codeList.add(QRCodeTypeModel("Wi-Fi", R.drawable.ic_qr_wifi))
            codeList.add(QRCodeTypeModel("WhatsApp", R.drawable.ic_qr_wh))
            codeList.add(QRCodeTypeModel("Email", R.drawable.ic_qr_email))

            codeList.add(QRCodeTypeModel("Spotify", R.drawable.ic_qr_spotify))
            codeList.add(QRCodeTypeModel("Calendar", R.drawable.ic_qr_calender))
            codeList.add(QRCodeTypeModel("YouTube", R.drawable.ic_qr_youtube))

            codeList.add(QRCodeTypeModel("X", R.drawable.ic_qr_x))
            codeList.add(QRCodeTypeModel("PayPal", R.drawable.ic_qr_paypal))
            codeList.add(QRCodeTypeModel("Meta", R.drawable.ic_qr_meta))

            codeList.add(QRCodeTypeModel("Pinterest", R.drawable.ic_qr_pinterest))
            codeList.add(QRCodeTypeModel("SnapChat", R.drawable.ic_qr_snap))
            codeList.add(QRCodeTypeModel("Linkedin", R.drawable.ic_qr_linkedin))

            codeList.add(QRCodeTypeModel("Skype", R.drawable.ic_qr_skype))

            rvCodeList.layoutManager = GridLayoutManager(this@GenerateListActivity, 3)
            qrCodeTypeAdapter = QrCodeTypeAdapter(
                this@GenerateListActivity,
                codeList,
                object : QrCodeTypeAdapter.Callback {
                    override fun callBack(model: QRCodeTypeModel, pos: Int) {
                        if (isOnline()) {
                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                                return
                            }
                            mLastClickTime = SystemClock.elapsedRealtime()
                            InterMoneyAds.showForwardCountInterMoney(this@GenerateListActivity, 101, object :
                                AagalJav {
                                override fun onNext() {
                                    nextActivity(model, pos)
                                }
                            })
                        }else{
                            showNoInternet {
                                nextActivity(model, pos)
                            }
                        }
                                       }
                })
            rvCodeList.adapter = qrCodeTypeAdapter
        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@GenerateListActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }


    private  fun nextActivity(model: QRCodeTypeModel, pos: Int) {
        if (model.name.equals("Text", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Text")
                    .putExtra("hint", "Enter text")
                    .putExtra("msg", "Please enter text")
            )
        } else if (model.name.equals("Phone", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Phone")
                    .putExtra("hint", "Enter phone number")
                    .putExtra("msg", "Please enter phone number")
            )
        } else if (model.name.equals("Instagram", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Instagram")
                    .putExtra("hint", "Enter username")
                    .putExtra("msg", "Please enter username")
            )
        } else if (model.name.equals("WhatsApp", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "WhatsApp")
                    .putExtra("hint", "Enter phone number")
                    .putExtra("msg", "Please enter number")
            )
        } else if (model.name.equals("Email", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Email").putExtra("hint", "Enter email")
                    .putExtra("msg", "Please enter email")
            )
        } else if (model.name.equals("YouTube", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "YouTube")
                    .putExtra("hint", "Enter channel id")
                    .putExtra("msg", "Please enter channel id")
            )
        } else if (model.name.equals("X", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "X").putExtra("hint", "Enter username")
                    .putExtra("msg", "Please enter username")
            )
        } else if (model.name.equals("PayPal", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "PayPal").putExtra("hint", "Enter username")
                    .putExtra("msg", "Please enter username")
            )
        } else if (model.name.equals("Meta", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Meta").putExtra("hint", "Enter meta id")
                    .putExtra("msg", "Please enter meta id")
            )
        } else if (model.name.equals("Pinterest", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Pinterest")
                    .putExtra("hint", "Enter username")
                    .putExtra("msg", "Please enter username")
            )
        } else if (model.name.equals("SnapChat", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "SnapChat")
                    .putExtra("hint", "Enter username")
                    .putExtra("msg", "Please enter username")
            )
        } else if (model.name.equals("Linkedin", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Linkedin")
                    .putExtra("hint", "Enter profile link")
                    .putExtra("msg", "Please enter profile link")
            )
        } else if (model.name.equals("Skype", true)) {
            startActivity(
                Intent(this@GenerateListActivity, OneTextFieldActivity::class.java)
                    .putExtra("title", "Skype")
                    .putExtra("hint", "Enter profile link")
                    .putExtra("msg", "Please enter profile link")
            )

        } else if (model.name.equals("Website", true)) {
            startActivity(
                Intent(this@GenerateListActivity, TwoTextFieldActivity::class.java)
                    .putExtra("title", "Website")
                    .putExtra("hint1", "Enter profile link")
                    .putExtra("msg1", "Please enter profile link")
                    .putExtra("hint2", "Enter url")
                    .putExtra("msg2", "Please enter url")
            )
        } else if (model.name.equals("SMS", true)) {
            startActivity(
                Intent(this@GenerateListActivity, TwoTextFieldActivity::class.java)
                    .putExtra("title", "SMS")
                    .putExtra("hint1", "Enter number")
                    .putExtra("msg1", "Please enter number")
                    .putExtra("hint2", "Enter message")
                    .putExtra("msg2", "Please enter message")
            )
        } else if (model.name.equals("Spotify", true)) {
            startActivity(
                Intent(this@GenerateListActivity, TwoTextFieldActivity::class.java)
                    .putExtra("title", "Spotify")
                    .putExtra("hint1", "Enter artist name")
                    .putExtra("msg1", "Please enter artist name")
                    .putExtra("hint2", "Enter artist song")
                    .putExtra("msg2", "Please enter artist song")
            )
        } else if (model.name.equals("Wi-Fi", true)) {
            startActivity(
                Intent(this@GenerateListActivity, TwoTextFieldActivity::class.java)
                    .putExtra("title", "Wi-Fi")
                    .putExtra("hint1", "Enter SSID")
                    .putExtra("msg1", "Please enter SSID")
                    .putExtra("hint2", "Enter password")
                    .putExtra("msg2", "Please enter password")
                    .putExtra("hint3", "Enter password")
            )
        } else if (model.name.equals("Contact", true)) {
            startActivity(Intent(this@GenerateListActivity, ContactActivity::class.java))
        } else if (model.name.equals("Calendar", true)) {
            startActivity(Intent(this@GenerateListActivity, CalendarActivity::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@GenerateListActivity,
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