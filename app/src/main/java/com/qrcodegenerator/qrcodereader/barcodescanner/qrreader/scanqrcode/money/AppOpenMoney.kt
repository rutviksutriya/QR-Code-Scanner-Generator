package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money
import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.MyAppClass
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity.SplashActivity
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gAppOpenAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isCheckNotEmpty
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isNoAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.removeSpace
import java.util.Date

class AppOpenMoney(private val myAppClass: MyAppClass?) : LifecycleObserver, ActivityLifecycleCallbacks {
    private var isFirstTimeMoney = true
    private var isPauseMoney = false


    init {
        if (!Constant.isInitialize) {
            Constant.isInitialize = true
            this.myAppClass?.registerActivityLifecycleCallbacks(this)
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        }
    }

    fun removeListener() {
        this.myAppClass?.unregisterActivityLifecycleCallbacks(this)
    }


    companion object {
        var isShowingMoney = true
        var appOpenMoney: AppOpenAd? = null

    }

    private var loadTimeMoney: Long = 0
    private var showTimeMoney: Long = 0
    private var loadCallbackMoney: AppOpenAdLoadCallback? = null

    private var currentMoney: Activity? = null


    override fun onActivityCreated(p0: Activity, p1: Bundle?) {

    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {



    }

    override fun onActivityStopped(activity: Activity) {
        if (activity !is AdActivity) {
            currentMoney = activity
        }
        isPauseMoney = true

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {


    }

    override fun onActivityDestroyed(p0: Activity) {

    }

    private fun getAdRequest(): AdRequest {

        val networkExtras = Bundle()
        networkExtras.putString("max_ad_content_rating", "G")
        return AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, networkExtras)
            .build()
    }

    private fun isMoney(): Boolean {
        return appOpenMoney != null && timeMoney(4)
    }

    private fun timeMoney(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTimeMoney
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    private fun showAfter(numHours: Long): Boolean {
        if (showTimeMoney == 0L) {
            return true
        } else {
            val dateDifference = Date().time - showTimeMoney
            val numMilliSecondsPerHour: Long = 3600000
            return dateDifference < numMilliSecondsPerHour * numHours
        }

    }

    fun fetchMoney(strKey: String) {

        if (strKey.isNotEmpty()) {
            if (isMoney()) {
                return
            }
            loadCallbackMoney = object : AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenMoney = ad
                    loadTimeMoney = Date().time
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    showQureka()
                }
            }
            val request = getAdRequest()
            myAppClass?.let {
                AppOpenAd.load(it, strKey, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallbackMoney!!)
            }
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        val adsPriority = SharedPrefData.getString(Constant.strPriority)
        if (adsPriority.isCheckNotEmpty() && adsPriority.removeSpace().equals("G", true) || adsPriority.removeSpace().equals("F,G", true) || adsPriority.removeSpace().equals("G,F", true)) {
            if (!Constant.isNotificationClicked && !Constant.isNotRedirect) {
                if (currentMoney !is SplashActivity) {
                    if (isPauseMoney && !Constant.isInterShow) {
                        isFirstTimeMoney = false
                        isPauseMoney = false
                        val isMoneyHave = SharedPrefData.getString(Constant.isAdAvailable)
                        if (isMoneyHave.isCheckNotEmpty()) {
                            if (isMoneyHave.equals("1", true)) {
                                SharedPrefData.newAdvertiseModel?.let { adsData ->
                                    val openAds = adsData.ads.firstOrNull { it.key == gAppOpenAds }
                                    if (openAds?.acc_type?.isNoAd() == false) {
                                        if (openAds.id.isCheckNotEmpty()) {
                                            showMoneyIfHave()
                                        } else {
                                            showQureka()
                                        }
                                    }
                                }
                            } else if (isMoneyHave.equals("2", true)) {
                                SharedPrefData.newAdvertiseModel?.let { adsData ->
                                    val openAds = adsData.ads.firstOrNull { it.key == gAppOpenAds }
                                    if (openAds?.acc_type?.isNoAd() == false) {
                                        showQureka()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private fun showQureka() {
        if (LessMoneyBannerAds?.dialog == null) {
            if (!isShowingMoney) {
                currentMoney?.let {
                    isShowingMoney = true
                    LessMoneyBannerAds.callExtraOpenAds(it, object : AagalJav {
                        override fun onNext() {
                            isShowingMoney = false
                        }
                    })
                }
            }
        } else {
            LessMoneyBannerAds.dialog?.let {
                it.dismiss()
                if (!it.isShowing) {
                    currentMoney?.let {
                        isShowingMoney = true
                        LessMoneyBannerAds.callExtraOpenAds(it, object : AagalJav {
                            override fun onNext() {
                                isShowingMoney = false
                            }
                        })
                    }
                }
            }
        }

    }

    private fun showMoneyIfHave() {
        if (!isShowingMoney && isMoney()) {
            val fullScreenContentCallback: FullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenMoney = null
                    isShowingMoney = false
                    fetchMoney(Constant.appOpenAdsId)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    showQureka()
                }

                override fun onAdShowedFullScreenContent() {
                    isShowingMoney = true
                }
            }

            currentMoney?.let {
                if (!isShowingMoney) {
                    appOpenMoney?.show(it)
                }
            }
            appOpenMoney?.fullScreenContentCallback = fullScreenContentCallback
        } else {
            fetchMoney(Constant.appOpenAdsId)
        }
    }
}