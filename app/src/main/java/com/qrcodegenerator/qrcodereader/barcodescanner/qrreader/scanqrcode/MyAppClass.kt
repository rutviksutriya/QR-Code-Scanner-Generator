package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode

import android.app.Application
import android.content.Context
import com.facebook.ads.AudienceNetworkAds


class MyAppClass : Application() {
    companion object {
        lateinit var application: MyAppClass
        val appContext: Context?
            get() {
                if (application == null) {
                    application = MyAppClass()
                }
                return application
            }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        AudienceNetworkAds.initialize(application)
    }
}
