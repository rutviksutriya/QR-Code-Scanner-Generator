package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.CustomDialog
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.OnCustomDialogListener
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityPermissionBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.DialogPermissionBinding

class PermissionActivity :  BaseActivity<ActivityPermissionBinding>() {

    private val requestCodeCamera = 113

    override fun getViewBinding(): ActivityPermissionBinding {
        return  ActivityPermissionBinding.inflate(layoutInflater)
    }

    override fun initView() {
        enableEdgeToEdge()
        binding.apply {
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }


            tvAllow.setOnClickListener {

                getCameraPermission()
            }

        }

    }
    private fun isCameraGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@PermissionActivity, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun getCameraPermission() {
        if (!isCameraGranted()) {

            ActivityCompat.requestPermissions(
                this@PermissionActivity,
                arrayOf(Manifest.permission.CAMERA),
                requestCodeCamera
            )
        } else {


        }

    }

    override fun onResume() {
        super.onResume()
        if (isCameraGranted()) {
            startActivity(Intent(this@PermissionActivity, ScanQRCodeActivity::class.java))
            finish()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         if (requestCode == this.requestCodeCamera) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isOnline()) {
                    startActivity(Intent(this@PermissionActivity, ScanQRCodeActivity::class.java))
                    finish()
                }else{
                    showNoInternet {
                        startActivity(Intent(this@PermissionActivity, ScanQRCodeActivity::class.java))
                        finish()

                    }
                }
            } else {

                showPermissionDialog()

            }
        }

    }


    private fun showPermissionDialog() {

        val dialog = CustomDialog(this, object :
            OnCustomDialogListener<DialogPermissionBinding> {

            override fun getBinding(): DialogPermissionBinding {
                return DialogPermissionBinding.inflate(layoutInflater)
            }

            override fun onDialogBind(binding: DialogPermissionBinding, dialog: Dialog) {
                dialog.setCancelable(false)
                binding.apply {
                    tvCancel.setOnClickListener {
                        dialog.dismiss()
                    }

                    tvAllow.setOnClickListener {
                        dialog.dismiss()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }

                }
            }

        })
        dialog.getDialog().show()
    }
    override fun initIntentData() {

    }

}