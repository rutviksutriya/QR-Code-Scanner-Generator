package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner


import com.facebook.ads.Ad
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.MyAppClass
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.appOpenAdsId
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.fbInterstitialAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gAppOpenAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.issplashshowed
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isCheckNotEmpty
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isNoAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.removeSpace
import java.util.Date

class SplashMoneyApp(private var myAppClass: MyAppClass?, var getMoneyActivity: Activity, var nextMoney: AagalJav) : Application.ActivityLifecycleCallbacks, LifecycleObserver {
    var moneyLoadTime: Long = 0
    var isLoadingMoney = false
    var failToMoney = false
    var isPauseMoney = false
    var isMoneyAd = false

    companion object {
        var splashMoneyAds: AppOpenAd? = null

    }

    init {
            myAppClass?.registerActivityLifecycleCallbacks(this)
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        if (!issplashshowed) {
            val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
            if (whichMoneyGet.removeSpace().equals("F", true) || whichMoneyGet.removeSpace().equals("F,G", true)) {
                showFBMoney(getMoneyActivity)
            } else if (whichMoneyGet.removeSpace().equals("G", true) || whichMoneyGet.removeSpace().equals("G,F", true)) {
                loadGoogle()
            }
        }
    }

    private fun loadGoogle() {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        SharedPrefData.newAdvertiseModel?.let { adsData ->
            val gMoney = adsData.ads.firstOrNull { it.key == gAppOpenAds }
            val fbMoney = adsData.ads.firstOrNull { it.key == fbInterstitialAd }

            if (whichMoneyGet.removeSpace().equals("G", true)) {
                if (gMoney?.acc_type?.isNoAd() == true) {
                    nextMoney.onNext()
                } else {
                    if (gMoney?.id?.isCheckNotEmpty() == true) {
                        available2(gMoney.id)
                    } else {
                        showExMoneyOpen()
                    }
                }
            } else if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                if (gMoney?.acc_type?.isNoAd() == true) {
                    if (fbMoney?.acc_type?.isNoAd() == true) {
                        nextMoney.onNext()
                    } else {
                        if (fbMoney?.id?.isCheckNotEmpty() == true) {
                            loadFBMoney(getMoneyActivity, fbMoney.id)
                        } else {
                            exMoneyInter()
                        }
                    }
                } else {
                    if (gMoney?.id?.isCheckNotEmpty() == true) {
                        available2(gMoney.id)
                    } else {
                        if (fbMoney?.acc_type?.isNoAd() == true) {
                            showExMoneyOpen()
                        } else {
                            if (fbMoney?.id?.isCheckNotEmpty() == true) {
                                loadFBMoney(getMoneyActivity, fbMoney.id)
                            } else {
                                exMoneyInter()
                            }
                        }
                    }
                }

            }
        }
    }

    private fun showExMoneyOpen() {
        if (!issplashshowed) {
            issplashshowed = true
            LessMoneyBannerAds.callExtraSplashOpen(getMoneyActivity, nextMoney)
        }
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {

    }

    override fun onActivityStarted(p0: Activity) {
        getMoneyActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        getMoneyActivity = p0
        isPauseMoney = false
    }

    override fun onActivityPaused(p0: Activity) {
        getMoneyActivity = p0
        isPauseMoney = false
    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }


    override fun onActivityDestroyed(p0: Activity) {


    }

    private fun timeMoney(numHours: Long): Boolean {
        val difference: Long = Date().time - moneyLoadTime
        val hour: Long = 3600000
        return difference < hour * numHours
    }


    private fun moneyAd(id: String?) {
        if (isLoadingMoney || isAdAvailable()) {
            return
        }
        isLoadingMoney = true

        val networkExtras = Bundle()
        networkExtras.putString("max_ad_content_rating", "G")
        val request = AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, networkExtras)
            .build()
        myAppClass?.let {
            AppOpenAd.load(it, id!!, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                object : AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: AppOpenAd) {
                        splashMoneyAds = ad
                        isLoadingMoney = false
                        failToMoney = false
                        moneyLoadTime = Date().time
                        if (isAdAvailable()) {
                            if (!isPauseMoney) {
                                showAdIfAvailable1(getMoneyActivity)
                            }
                        }
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        isLoadingMoney = false
                        failToMoney = true
                        checkGMoneyFail()

                    }
                })
        }

    }

    private fun checkFbMoneyFail() {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority) ?: ""

        SharedPrefData.newAdvertiseModel?.let { adsData ->
            if (whichMoneyGet.removeSpace().equals("F", true)) {
                exMoneyInter()
            } else if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                val gMoney = adsData.ads.firstOrNull { it.key == gAppOpenAds }
                if (gMoney?.acc_type?.isNoAd() == true) {
                    exMoneyInter()
                } else {
                    if (gMoney?.id?.isCheckNotEmpty() == true) {
                        available2(gMoney.id)
                    } else {
                        showExMoneyOpen()
                    }
                }
            } else if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                exMoneyInter()
            }

        } ?: nextMoney.onNext()
    }

    private fun checkGMoneyFail() {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority) ?: ""
        SharedPrefData.newAdvertiseModel?.let { adsData ->
            val fbMoney = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
            if (whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.removeSpace().equals("F,G", true)) {
                showExMoneyOpen()
            } else if (whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.removeSpace().equals("G,F", true)) {
                if (fbMoney?.acc_type?.isNoAd() == true) {
                    showExMoneyOpen()
                } else {
                    if (fbMoney?.id?.isCheckNotEmpty() == true) {
                        loadFBMoney(getMoneyActivity, fbMoney.id)
                    } else {
                        exMoneyInter()
                    }
                }
            } else if (whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.removeSpace().equals("G", true)) {
                showExMoneyOpen()
            } else {
                nextMoney.onNext()
            }
        } ?: nextMoney.onNext()


    }



    fun isAdAvailable(): Boolean {
        return splashMoneyAds != null && timeMoney(4)
    }

    fun showAdIfAvailable1(activity: Activity) {
        available2(activity, appOpenAdsId)
    }


    private fun available2(id: String) {

        available2(getMoneyActivity, id)

    }

    private fun available2(activity: Activity, id: String) {
        if (isMoneyAd) {

            return
        }
        if (!isAdAvailable()) {
            moneyAd(id)
            return
        }
        splashMoneyAds?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                splashMoneyAds = null
                isMoneyAd = true
                issplashshowed = true
                nextMoney.onNext()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                splashMoneyAds = null
                isMoneyAd = false
                if (!isPauseMoney) {
                    if (!issplashshowed) {
                        checkGMoneyFail()
                    }
                }
            }

            override fun onAdShowedFullScreenContent() {
                isMoneyAd = true
                issplashshowed = true

            }
        }
        splashMoneyAds?.show(activity)
    }

    var interMoney: InterstitialAd? = null
    private var interMoneyList: InterstitialAdListener? = null

    private fun showFBMoney(activity: Activity) {
        SharedPrefData.newAdvertiseModel?.let { adsData ->
            val fbMoney = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
            val gMoney = adsData.ads.firstOrNull { it.key == gAppOpenAds }
            val whichMoneyGet = SharedPrefData.getString(Constant.strPriority) ?: ""
            if (whichMoneyGet.removeSpace().equals("F", true)) {
                loadFBMoney(activity, fbMoney?.id ?: "")
            } else if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                if (fbMoney?.acc_type?.isNoAd() == true) {
                    if (gMoney?.acc_type?.isNoAd() == true) {
                        nextMoney.onNext()
                    } else {
                        if (gMoney?.id?.isCheckNotEmpty() == true) {
                            available2(gMoney.id)
                        } else {
                            showExMoneyOpen()
                        }
                    }
                } else {
                    if (fbMoney?.id?.isCheckNotEmpty() == true) {
                        loadFBMoney(activity, fbMoney.id)
                    } else {
                        if (gMoney?.acc_type?.isNoAd() == true) {
                            exMoneyInter()
                        } else {
                            if (gMoney?.id?.isCheckNotEmpty() == true) {
                                available2(Constant.appOpenAdsId)
                            } else {
                                showExMoneyOpen()
                            }
                        }
                    }

                }
            }
        }

    }

    private fun loadFBMoney(activity: Activity, id: String) {

        interMoney = InterstitialAd(activity, id)

        interMoneyList = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {

            }
            override fun onInterstitialDismissed(ad: Ad) {
                Constant.isInterShow = false
                if (interMoney != null) {
                    interMoney?.destroy()
                    interMoney = null
                }
                nextMoney.onNext()
            }

            override fun onError(ad: Ad, adError: com.facebook.ads.AdError) {
                Constant.isInterShow = false
                if (interMoney != null) {
                    interMoney?.destroy()
                }
                interMoney = null
                checkFbMoneyFail()
            }

            override fun onAdLoaded(ad: Ad) {
                interMoney?.let { inter ->
                    Constant.isInterShow = true
                    issplashshowed = true
                    inter.show()
                }
            }

            override fun onAdClicked(ad: Ad) {

            }

            override fun onLoggingImpression(ad: Ad) {

            }
        }

        interMoney?.let { interAds ->
            if (!interAds.isAdLoaded) {
                interAds.loadAd(interAds.buildLoadAdConfig().withAdListener(interMoneyList).build())
            }
        }
    }

    private fun exMoneyInter() {
        if (!issplashshowed) {
            issplashshowed = true
            InterMoneyAds.extraInterMoney(getMoneyActivity, object : AagalJav {
                override fun onNext() {
                    nextMoney.onNext()
                }
            })
        }
    }
}