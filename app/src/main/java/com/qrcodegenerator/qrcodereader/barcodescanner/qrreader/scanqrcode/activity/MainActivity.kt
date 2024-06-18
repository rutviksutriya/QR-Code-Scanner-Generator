package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Process
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.BuildConfig
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.CustomBottomDialog
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.CustomDialog
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.OnCustomBottomDialogListener
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.OnCustomDialogListener
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isEmptyText
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.openUrl

import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityMainBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.DialogExitFromAppBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.DialogUserBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AppOpenMoney
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.SplashMoneyApp
import kotlin.system.exitProcess

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val requestCodeNotification = 112
    private val requestCodeCamera = 113
    private var mLastClickTime: Long = 0


    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        showUpdateDialog()
        getPushPermission()
        binding.apply {
            ivSetting.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
            llGenerate.setOnClickListener {

                if (isOnline()) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return@setOnClickListener
                    }
                    mLastClickTime = SystemClock.elapsedRealtime()
                    InterMoneyAds.showForwardCountInterMoney(this@MainActivity, 101, object :
                        AagalJav {
                        override fun onNext() {
                            startActivity(Intent(this@MainActivity, GenerateListActivity::class.java))

                        }
                    })
                }else{
                    showNoInternet {
                        startActivity(Intent(this@MainActivity, GenerateListActivity::class.java))

                    }
                }
            }
            llScan.setOnClickListener {
                getCameraPermission()
            }
            llHis.setOnClickListener {


                if (isOnline()) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return@setOnClickListener
                    }
                    mLastClickTime = SystemClock.elapsedRealtime()
                    InterMoneyAds.showForwardCountInterMoney(this@MainActivity, 101, object :
                        AagalJav {
                        override fun onNext() {
                            startActivity(Intent(this@MainActivity, ScannerHistoryActivity::class.java))

                        }
                    })
                }else{
                    showNoInternet {
                        startActivity(Intent(this@MainActivity, ScannerHistoryActivity::class.java))

                    }
                }
            }



            onBackPressedDispatcher.addCallback(this@MainActivity,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (!this@MainActivity.isFinishing) {
                            dialog?.show()
                        }
                    }
                })

            showExitDialog()
        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadMotiAds(
                    this@MainActivity,
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
                    this@MainActivity,
                    llMotiMoneyView.root,
                    llMotiMoneyView.nativeCurtain.motiMoneyCurtain,
                    llMotiMoneyView.moneyContainer,
                    llMotiMoneyView.flPlaceView,
                    llMotiMoneyView.llMotoCurtain
                )
            }
        }
    }

    var dialog: Dialog? = null

    private fun showExitDialog() {
        dialog = CustomBottomDialog(this@MainActivity,
            object : OnCustomBottomDialogListener<DialogExitFromAppBinding> {
                override fun getBinding(): DialogExitFromAppBinding {
                    return DialogExitFromAppBinding.inflate(layoutInflater)
                }

                override fun onDialogBind(
                    bindingD: DialogExitFromAppBinding, dialog: BottomSheetDialog
                ) {
                    bindingD.apply {
                        dialog.setCancelable(false)

                        dialog.behavior.isDraggable = false
                        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                        dialog.behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

                        LessMoneyBannerAds.loadVachaliAds(
                            this@MainActivity,
                            llVachaliMoneyView.root,
                            llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                            llVachaliMoneyView.moneyContainer,
                            llVachaliMoneyView.flPlaceView,
                            llVachaliMoneyView.llNaniCurtain
                        )

                        tvRate.setOnClickListener {
                            dialog.dismiss()
                            openUrl("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)

                        }

                        tvYes.setOnClickListener {
                            val startMain = Intent(Intent.ACTION_MAIN)
                            startMain.addCategory(Intent.CATEGORY_HOME)
                            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(startMain)
                            Constant.isNotificationClicked = true
                            AppOpenMoney.appOpenMoney = null
                            AppOpenMoney.isShowingMoney = true
                            SplashMoneyApp.splashMoneyAds = null
                            Constant.appOpenAdsId = ""
                            finish()
                            finishAffinity()
                            Process.killProcess(Process.myPid())
                            exitProcess(0)
                        }
                        ivClose.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }

            }).getDialog()
    }


    private fun showUpdateDialog() {
        if (Constant.dialogTrue) {
            Constant.dialogTrue = false
            val versionCode = SharedPrefData.getString(Constant.App_VERSION_CODE)
            val updateText = SharedPrefData.getString(Constant.USER_DIALOG_UPDATE_TEXT)


            if (versionCode.isNotEmpty()) {
                if (versionCode.toInt() > BuildConfig.VERSION_CODE) {
                    if (updateText.isNotEmpty()) {

                        val dialog = CustomDialog(this, object :
                            OnCustomDialogListener<DialogUserBinding> {

                            override fun getBinding(): DialogUserBinding {
                                return DialogUserBinding.inflate(layoutInflater)
                            }

                            override fun onDialogBind(binding: DialogUserBinding, dialog: Dialog) {
                                dialog.setCancelable(false)
                                binding.apply {
                                    binding.tViewDescription.text = updateText
                                    binding.tViewDownload.text = getString(R.string.update)

                                    binding.tvCancel.setOnClickListener {
                                        dialog.dismiss()

                                    }
                                    binding.tViewDownload.setOnClickListener {
                                        dialog.dismiss()
                                        this@MainActivity.openUrl("http://play.google.com/store/apps/details?id=$packageName")

                                    }
                                }
                            }

                        })
                        dialog.getDialog().show()

                    }
                }
            }
        }
    }

    private fun getPushPermission() {
        if (!isPermissionGranted()) {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    requestCodeNotification
                )
            }
        }
    }


    private fun getCameraPermission() {
        if (!isCameraGranted()) {

            startActivity(Intent(this@MainActivity, PermissionActivity::class.java))

        } else {

            if (isOnline()) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                InterMoneyAds.showForwardCountInterMoney(this@MainActivity, 101, object :
                    AagalJav {
                    override fun onNext() {
                        startActivity(Intent(this@MainActivity, ScanQRCodeActivity::class.java))

                    }
                })
            }else{
                showNoInternet {
                    startActivity(Intent(this@MainActivity, ScanQRCodeActivity::class.java))

                }
            }

        }

    }



    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@MainActivity, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isCameraGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@MainActivity, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == this.requestCodeNotification) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("code", grantResults.toString())
            } else {
                getPushNotificationPermission()
            }
        }

    }

    private fun getPushNotificationPermission() {
        val isNotifyAllow =
            NotificationManagerCompat.from(this@MainActivity).areNotificationsEnabled()
        if (!isNotifyAllow) {

            showDialog(
                "Alert Notification",
                "Allow " + this@MainActivity.resources.getString(R.string.app_name) + " to send you notification?",
                true
            ) {
                val intent = Intent()
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("app_package", this@MainActivity.packageName)
                intent.putExtra("app_uid", this@MainActivity.applicationInfo.uid)
                intent.putExtra(
                    "android.provider.extra.APP_PACKAGE", this@MainActivity.packageName
                )
                this@MainActivity.startActivity(intent)
            }
        }
    }


    override fun initIntentData() {

    }

}