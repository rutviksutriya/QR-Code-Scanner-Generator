package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.BuildConfig
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.openUrl
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivitySettingsBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {
    override fun getViewBinding(): ActivitySettingsBinding {
        return  ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {
            toolBarView.tvTitle.text = getString(R.string.setting)
            toolBarView.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

            llRate.setOnClickListener {
                openUrl("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)

            }
            llShare.setOnClickListener {
                val myapp = Intent(Intent.ACTION_SEND)
                myapp.setType("text/plain")
                myapp.putExtra(
                    Intent.EXTRA_TEXT, """${getString(R.string.app_name)}

                    https://play.google.com/store/apps/details?id=$packageName""" + " \n"
                )
                startActivity(myapp)
            }
            llPP.setOnClickListener {
                openUrl(Constant.PRIVACY_LINK)

            }
            llMore.setOnClickListener {
                openUrl("https://play.google.com/store/search?q=pub:" + Constant.DEVELOPER_NAME)

            }
        }

        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadMotiAds(
                    this@SettingsActivity,
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
                    this@SettingsActivity,
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

    }

}