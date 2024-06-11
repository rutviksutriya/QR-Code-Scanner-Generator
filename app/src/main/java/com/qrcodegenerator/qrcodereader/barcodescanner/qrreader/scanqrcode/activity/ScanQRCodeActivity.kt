package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityScanQrcodeBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds

class ScanQRCodeActivity : BaseActivity<ActivityScanQrcodeBinding>() {

    private lateinit var codeScanner: CodeScanner
    private var mLastClickTime: Long = 0

    override fun getViewBinding(): ActivityScanQrcodeBinding {
        return  ActivityScanQrcodeBinding.inflate(layoutInflater)
    }

    override fun initView() {
       binding.apply {
           toolBarView.tvTitle.text = getString(R.string.scan_qr_code)
           toolBarView.ivBack.setOnClickListener { if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
               return@setOnClickListener
           }
               mLastClickTime = SystemClock.elapsedRealtime()
               InterMoneyAds.showBackInterMoney(this@ScanQRCodeActivity, 800)
           }

           codeScanner = CodeScanner(this@ScanQRCodeActivity, scannerView)
           codeScanner.camera = CodeScanner.CAMERA_BACK
           codeScanner.formats = CodeScanner.ALL_FORMATS
           codeScanner.autoFocusMode = AutoFocusMode.SAFE
           codeScanner.scanMode = ScanMode.SINGLE
           codeScanner.isAutoFocusEnabled = true
           codeScanner.isFlashEnabled = false

           codeScanner.decodeCallback = DecodeCallback {
               runOnUiThread {
                   startActivity(Intent(this@ScanQRCodeActivity, ShareDataActivity::class.java).putExtra("result", it.text))
                   finish()
               }
           }
           codeScanner.errorCallback = ErrorCallback {
               runOnUiThread {
                   Toast.makeText(this@ScanQRCodeActivity, "Camera initialization error: ${it.message}",
                       Toast.LENGTH_LONG).show()
                   it.message?.let { it1 -> Log.e("123456", it1) }
               }
           }

           scannerView.setOnClickListener {
               codeScanner.startPreview()
           }

       }

        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadPattiAds(this@ScanQRCodeActivity, llPattiMoneyView.nativeAdContainer50, llPattiMoneyView.llBanner50)

            }
        }

    }


    override fun initIntentData() {

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadPattiAds(
                    this@ScanQRCodeActivity,
                    llPattiMoneyView.nativeAdContainer50,
                    llPattiMoneyView.llBanner50
                )
            }
        }
    }
    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


}