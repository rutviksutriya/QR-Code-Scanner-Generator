package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.SystemClock
import android.util.Patterns
import android.widget.Toast
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.openUrl
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.database.HistoryDatabase
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityShareDataBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.ScannerHistoryModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ShareDataActivity : BaseActivity<ActivityShareDataBinding>() {

  private var resultData = ""
  private var isHistory = false
    private var mLastClickTime: Long = 0

    private var titleText = ""
  private var historyDatabase: HistoryDatabase?= null

    override fun getViewBinding(): ActivityShareDataBinding {
       return  ActivityShareDataBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {
            toolBarView.tvTitle.text = getString(R.string.share_data)
            toolBarView.ivBack.setOnClickListener {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return@setOnClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                InterMoneyAds.showBackInterMoney(this@ShareDataActivity, 800)
            }

            if (Patterns.PHONE.matcher(resultData).matches()){
                titleText = "NUMBER"
                tvTitleView.text = titleText
            }else  if (Patterns.EMAIL_ADDRESS.matcher(resultData).matches()){
                titleText = "EMAIL"
                tvTitleView.text = titleText
            }else  if (Patterns.WEB_URL.matcher(resultData).matches()){
                titleText = "WEBSITE"
                tvTitleView.text = titleText
            }else  {
                titleText = "TEXT"
                tvTitleView.text =titleText
            }

            if (!isHistory) {
                addData()
            }

            tvResult.text = resultData
            ivWebsite.setOnClickListener {
                openUrl(resultData)

            }
            ivCopy.setOnClickListener {
                val clipboard: ClipboardManager =
                    getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", resultData)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this@ShareDataActivity, "Copy successfully", Toast.LENGTH_SHORT).show()
            }

            ivShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, resultData)
                startActivity(Intent.createChooser(shareIntent, "Share..."))
            }

        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@ShareDataActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@ShareDataActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }

    private fun addData(){
        val date1: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"))
        val currentLocalTime: Date = cal.time
        val date: DateFormat = SimpleDateFormat("HH:mm a")
        date.timeZone = TimeZone.getTimeZone("GMT+5:30")
        val localTime: String = date.format(currentLocalTime)
        val user = ScannerHistoryModel(
            0,
            titleText,
            date1,
            resultData,
            localTime
        )
        historyDatabase?.getScanHistoryDao()?.addHistory(user)
    }


    override fun initIntentData() {
        historyDatabase =     HistoryDatabase(this@ShareDataActivity)
        if (intent.hasExtra("result")) {
            resultData = intent.getStringExtra("result").toString()
        }
        if (intent.hasExtra("isHistory")) {
            isHistory = intent.getBooleanExtra("isHistory", false)
        }
    }
}