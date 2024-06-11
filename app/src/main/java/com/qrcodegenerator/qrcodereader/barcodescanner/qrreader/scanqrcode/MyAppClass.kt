package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode

import android.app.Application
import android.content.Context
import com.facebook.ads.AudienceNetworkAds
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics

class MyAppClass : Application() {

    companion object {
        lateinit var application: MyAppClass
        lateinit var mFirebaseAnalytics: FirebaseAnalytics

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
        FirebaseApp.initializeApp(application)


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


    }


}
