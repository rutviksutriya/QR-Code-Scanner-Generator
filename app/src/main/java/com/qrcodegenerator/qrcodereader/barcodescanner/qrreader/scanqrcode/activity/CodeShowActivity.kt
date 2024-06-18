package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.saveBitmapToFile
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.database.HistoryDatabase
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityCodeShowBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.CodeHistoryModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.Data
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds.showBackInterMoney
import java.io.ByteArrayOutputStream
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class CodeShowActivity : BaseActivity<ActivityCodeShowBinding>() {
    private var mLastClickTime: Long = 0

    private var codeText = ""
    private var title = ""
    private var isHistory = false
    private var details: Data? = null
    private var bitmap: Bitmap? = null
    private var historyDatabase: HistoryDatabase? = null

    override fun getViewBinding(): ActivityCodeShowBinding {
        return ActivityCodeShowBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        binding.apply {
            toolBarView.tvTitle.text = resources?.getString(R.string.generate_qr)
            toolBarView.ivBack.setOnClickListener {   if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
                mLastClickTime = SystemClock.elapsedRealtime()
                showBackInterMoney(this@CodeShowActivity, 800)}

            historyDatabase =     HistoryDatabase(this@CodeShowActivity)


            if (isHistory) {
                bitmap = details?.imgBtmp?.size?.let {
                    BitmapFactory.decodeByteArray(
                        details?.imgBtmp, 0,
                        it
                    )
                }
                tvText.text = details?.value
                ivCode.setImageBitmap(bitmap)
            } else {
                try {
                    bitmap = getQrCodeBitmap(codeText)
                    ivCode.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e("123456", e.message.toString())
                }
                tvText.text = codeText
                addData()

            }


            ivShare.setOnClickListener {
                ivShare.isClickable = false
                Handler(Looper.getMainLooper()).postDelayed({
                    ivShare.isClickable = true
                }, 1000)

                bitmap?.also { bit ->
                    val imageUri = saveBitmapToFile(bit, this@CodeShowActivity)

                    val shareIntent = Intent()
                    shareIntent.setAction(Intent.ACTION_SEND)
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                    shareIntent.putExtra( Intent.EXTRA_TEXT, getString(R.string.app_name)+ "\n\n"+ Constant.tiny)
                    shareIntent.setType("image/*")
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(Intent.createChooser(shareIntent, "QR Code"))
                } ?: run {
                    Toast.makeText(
                        this@CodeShowActivity,
                        "Something want wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@CodeShowActivity,
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
                    this@CodeShowActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addData() {
        val date1: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

        var time = "00:00"
        try {
            val localTime = LocalTime.now()
            val dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
            time = localTime.format(dateTimeFormatter).toString()
            println(localTime.format(dateTimeFormatter))
        } catch (e: Exception) {

        }

        bitmap?.also { bit ->
            val user = CodeHistoryModel(
                0,
                title.uppercase(),
                date1,
                codeText,
                time,
                getBytes(bit)
            )
            historyDatabase =     HistoryDatabase(this@CodeShowActivity)
            historyDatabase?.getCodeHistoryDao()?.addHistory(user)
            Toast.makeText(this@CodeShowActivity, "QR code generated successfully", Toast.LENGTH_SHORT)
                .show()

        } ?: run {
            Toast.makeText(this@CodeShowActivity, "Something want wrong", Toast.LENGTH_SHORT).show()
        }


    }

    private fun getBytes(audioIcon: Bitmap): ByteArray? {
        return run {

            val outputStream = ByteArrayOutputStream()
            audioIcon.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.toByteArray()
        }
    }





    private fun getQrCodeBitmap(ssid: String): Bitmap {
        val size = 512 //pixels
        val bits = QRCodeWriter().encode(ssid, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    override fun initIntentData() {
        if (intent.hasExtra("codeText")) {
            codeText = intent.getStringExtra("codeText").toString()
        }
        if (intent.hasExtra("isHistory")) {
            isHistory = intent.getBooleanExtra("isHistory", false)
        }

        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title").toString()
        }
        if (intent.hasExtra("details")) {
            details = intent.serializable("details") as Data
        }
    }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
            key, T::class.java
        )!!

        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
    }
}