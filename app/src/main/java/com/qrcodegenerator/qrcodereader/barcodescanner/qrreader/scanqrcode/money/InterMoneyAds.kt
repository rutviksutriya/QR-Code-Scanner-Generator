package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.ExtraAdsModel

import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.fbInterstitialAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gInterstitialAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.CustomDialog
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.OnCustomDialogListener
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.checkIsAdAvailable
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.checkIsQurekaAvailable
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isCheckNotEmpty
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isNoAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.removeSpace
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.shareQurekaUrl
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.DialogLoadingBinding
import pl.droidsonroids.gif.GifImageView

object InterMoneyAds {
    private var progressDialog: Dialog? = null
    var interMoneyGoogle: com.google.android.gms.ads.interstitial.InterstitialAd? = null
    var interMoney: InterstitialAd? = null
    private var googleCountMoney = 0
    private var interMoneyListener: InterstitialAdListener? = null
    private var fbInterMoney = 0
    private var aagalJav: AagalJav? = null


    fun loadInterMoney(getMoneyActivity: Activity) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        val isMoneyGet = SharedPrefData.getString(Constant.isAdAvailable)
        fbInterMoney = 0
        googleCountMoney = 0
        initTimerMoney()
        if (whichMoneyGet.isCheckNotEmpty() && isMoneyGet.checkIsAdAvailable()) {
            if (whichMoneyGet.removeSpace().equals("F", true) || whichMoneyGet.removeSpace().equals("F,G", true)) {
                loadFBMoneyInter(getMoneyActivity)
            } else if (whichMoneyGet.removeSpace().equals("G", true) || whichMoneyGet.removeSpace().equals("G,F", true)) {
                loadGMoneyInter(getMoneyActivity)
            }
        }
    }

    private fun loadFBMoneyInter(getMoneyActivity: Activity) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        SharedPrefData.newAdvertiseModel?.let { adsData ->
            val fbMoneyAds = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
            val gMoneyAds = adsData.ads.firstOrNull { it.key == gInterstitialAd }
            if (whichMoneyGet.removeSpace().equals("F", true)) {
                if (fbMoneyAds?.acc_type?.isNoAd() == false) {
                    if (fbMoneyAds.id.isCheckNotEmpty()) {
                        callFbMoney(getMoneyActivity, fbMoneyAds.id)
                    }
                }
            } else if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                if (fbMoneyAds?.acc_type?.isNoAd() == true) {
                    if (gMoneyAds?.acc_type?.isNoAd() == false && gMoneyAds.id.isCheckNotEmpty()) {
                        callGoogleMoney(getMoneyActivity, gMoneyAds.id)
                    }
                } else {
                    if (fbMoneyAds?.id?.isCheckNotEmpty() == true) {
                        callFbMoney(getMoneyActivity, fbMoneyAds.id)
                    } else {
                        if (gMoneyAds?.acc_type?.isNoAd() == false && gMoneyAds.id.isCheckNotEmpty()) {
                            callGoogleMoney(getMoneyActivity, gMoneyAds.id)
                        }
                    }
                }
            }
        }
    }

    private fun callFbMoney(getMoneyActivity: Activity, moneyId: String) {
        if (interMoney == null) {
            interMoney = InterstitialAd(getMoneyActivity, moneyId)

            interMoneyListener = object : InterstitialAdListener {
                override fun onInterstitialDisplayed(ad: Ad) {

                }

                override fun onInterstitialDismissed(ad: Ad) {
                    Constant.isInterShow = false
                    startTimer()
                    if (interMoney != null) {
                        interMoney?.destroy()
                        interMoney = null
                    }
                    loadInterMoney(getMoneyActivity)
                    nextAction()
                }

                override fun onError(ad: Ad, adError: AdError) {
                    Constant.isInterShow = false
                    if (interMoney != null) {
                        interMoney?.destroy()
                    }
                    interMoney = null
                    loadMoneyOnFbError(getMoneyActivity)

                }

                override fun onAdLoaded(ad: Ad) {

                }

                override fun onAdClicked(ad: Ad) {

                }

                override fun onLoggingImpression(ad: Ad) {

                }
            }
            interMoney?.let { interAds ->
                if (!interAds.isAdLoaded) {
                    interAds.loadAd(interAds.buildLoadAdConfig().withAdListener(interMoneyListener).build())
                }
            }
        }
    }


    private fun loadMoneyOnFbError(getMoneyActivity: Activity) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        if (whichMoneyGet.removeSpace().equals("F,G", true)) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val gMoneyAds = adsData.ads.firstOrNull { it.key == gInterstitialAd }
                if (gMoneyAds?.acc_type?.isNoAd() == false && gMoneyAds.id.isCheckNotEmpty()) {
                    callGoogleMoney(getMoneyActivity, gMoneyAds.id)
                }
            }
        }
    }

    private fun loadGMoneyInter(getMoneyActivity: Activity) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        SharedPrefData.newAdvertiseModel?.let { adsData ->
            val gMoneyAds = adsData.ads.firstOrNull { it.key == gInterstitialAd }
            if (whichMoneyGet.removeSpace().equals("G", true)) {
                if (gMoneyAds?.acc_type?.isNoAd() == false && gMoneyAds.id.isCheckNotEmpty()) {
                    callGoogleMoney(getMoneyActivity, gMoneyAds.id)
                }
            } else if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                val fbInter = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
                if (gMoneyAds?.acc_type?.isNoAd() == true) {
                    if (fbInter?.acc_type?.isNoAd() == false && fbInter.id.isCheckNotEmpty()) {
                        callFbMoney(getMoneyActivity, fbInter.id)
                    }
                } else {
                    if (gMoneyAds?.id?.isCheckNotEmpty() == true) {
                        callGoogleMoney(getMoneyActivity, gMoneyAds.id)
                    } else {
                        if (fbInter?.acc_type?.isNoAd() == false && fbInter.id.isCheckNotEmpty()) {
                            callFbMoney(getMoneyActivity, fbInter.id)
                        }

                    }
                }
            }
        }

    }

    private fun callGoogleMoney(getMoneyActivity: Activity, adsId: String) {
        if (interMoneyGoogle == null) {
            val requestMoney = AdRequest.Builder().build()
            com.google.android.gms.ads.interstitial.InterstitialAd.load(getMoneyActivity, adsId, requestMoney,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd) {
                        interMoneyGoogle = interstitialAd
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Constant.isInterShow = false
                        loadMoneyGoogleError(getMoneyActivity)
                    }
                })
        }
    }

    private fun loadMoneyGoogleError(getMoneyActivity: Activity) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        if (whichMoneyGet.removeSpace().equals("G,F", true)) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val fbInter = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
                if (fbInter?.acc_type?.isNoAd() == false && fbInter.id.isCheckNotEmpty()) {
                    callFbMoney(getMoneyActivity, fbInter.id)
                }
            }
        }
    }

    private fun showExtraMoney(getMoneyActivity: Activity) {
        if (Constant.predChampURL.isCheckNotEmpty() || Constant.qurekaURL.isCheckNotEmpty() || Constant.gameURL.isCheckNotEmpty()) {
            val dialogMoney = Dialog(getMoneyActivity)
            var tvHeaderMoney: TextView
            var titleMoney: TextView
            var title: TextView
            var tvBottomMoney: TextView
            var imgMoney: ImageView
            val btnMoney: Button
            var llMain: LinearLayout
            var gifMoney: GifImageView
            dialogMoney.apply {
                Constant.isInterShow = true
                setContentView(GetMoneyUtils.intertialLayout())
                setCancelable(true)
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setGravity(Gravity.CENTER)
                setCancelable(false)
                setCanceledOnTouchOutside(false)

                tvHeaderMoney = findViewById(R.id.tvHeader)
                tvBottomMoney = findViewById(R.id.tvBottomText)

                tvHeaderMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)

                titleMoney = findViewById(R.id.tital1)
                if (titleMoney != null) {
                    titleMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                }

                title = findViewById(R.id.tital2)
                if (title != null) {
                    title.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                }

                if (tvBottomMoney != null) {
                    tvBottomMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                }
                val advertiseModel: ExtraAdsModel? = GetMoneyUtils.descriptionList(getMoneyActivity)
                advertiseModel?.let { model ->
                    imgMoney = findViewById(R.id.imgBanner)
                    if (imgMoney != null) {
                        imgMoney.setImageResource(model.banner)
                        imgMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top2)
                    }
                    llMain = findViewById(R.id.llMain)
                    gifMoney = findViewById(R.id.gifView)

                    tvHeaderMoney.text = model.header
                    titleMoney.text = model.title
                    tvBottomMoney.text = model.body
                    gifMoney.setImageResource(GetMoneyUtils.champOnlyGIFImage())

                    btnMoney = findViewById(R.id.btnPlayNow)
                    if (btnMoney != null) {
                        btnMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top3)
                        btnMoney.text = GetMoneyUtils.buttonText(getMoneyActivity)
                    }

                    btnMoney?.setOnClickListener {
                        if (getMoneyActivity != null) {
                            getMoneyActivity.shareQurekaUrl(model.url)
                        }
                    }
                    if (llMain != null) llMain.setOnClickListener {
                        btnMoney.performClick()
                    }
                }

                findViewById<View>(R.id.ivClose).setOnClickListener {
                    Constant.isInterShow = false
                    if (isShowing) {
                        dismiss()
                    }
                    nextAction()
                }

                findViewById<View>(R.id.btnClose).setOnClickListener {
                    Constant.isInterShow = false
                    if (isShowing) {
                        dismiss()
                    }
                    nextAction()
                }

                setOnDismissListener {
                    Constant.isInterShow = false
                    startTimer()
                }
                show()
            }
        } else {
            nextAction()
        }

    }

    private fun showGoogleMoney(getMoneyActivity: Activity) {
        val isLoaderMoney = SharedPrefData.getString(Constant.is_loader)
        if (isLoaderMoney.isCheckNotEmpty() && isLoaderMoney.equals("1", true)) {
            showProgressMoney(getMoneyActivity)
            Handler(Looper.getMainLooper()).postDelayed({
                dismissProgressMoney()
                gMoneyAds(getMoneyActivity)
            }, 2000)
        } else {
            gMoneyAds(getMoneyActivity)
        }
    }

    private fun gMoneyAds(getMoneyActivity: Activity) {
        interMoneyGoogle?.show(getMoneyActivity)
        interMoneyGoogle?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                startTimer()
                Constant.isInterShow = false
                interMoneyGoogle = null
                nextAction()
                loadInterMoney(getMoneyActivity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                super.onAdFailedToShowFullScreenContent(adError)
                Constant.isInterShow = false
                nextAction()
            }

            override fun onAdShowedFullScreenContent() {
                Constant.isInterShow = true
            }
        }
    }


    fun showBackInterMoney(getMoneyActivity: Activity, code: Int) {
        showBackCountInterMoney(getMoneyActivity, code, object : AagalJav {
            override fun onNext() {
                Constant.isNotRedirect = false
                getMoneyActivity.finish()
            }
        })
    }

    fun showEveryTimeInterMoney(getMoneyActivity: Activity, jav: AagalJav) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        aagalJav = jav
        val isMoneyGet = SharedPrefData.getString(Constant.isAdAvailable)
        if (whichMoneyGet.isCheckNotEmpty()) {

            if (whichMoneyGet.removeSpace().equals("F", true)) {
                SharedPrefData.newAdvertiseModel?.let { adsData ->
                    val fbMoneyAds = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
                    if (fbMoneyAds == null) {
                        nextAction()
                    } else {
                        if (fbMoneyAds?.acc_type?.isNoAd() == true) {
                            nextAction()
                        } else {
                            if (isMoneyGet.checkIsAdAvailable()) {
                                if (interMoney != null && interMoney?.isAdLoaded == true) {
                                    showFbInterMoney(getMoneyActivity)
                                } else {
                                    loadInterMoney(getMoneyActivity)
                                    showExtraMoney(getMoneyActivity)
                                }
                            } else if (isMoneyGet.checkIsQurekaAvailable()) {
                                showExtraMoney(getMoneyActivity)
                            } else {
                                nextAction()
                            }
                        }
                    }
                } ?: nextAction()
            } else if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                SharedPrefData.newAdvertiseModel?.let { adsData ->
                    val fbMoneyAds = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
                    val gIntersMoney = adsData.ads.firstOrNull { it.key == gInterstitialAd }
                    if (fbMoneyAds == null && gIntersMoney == null) {
                        nextAction()
                    } else {
                        if (fbMoneyAds?.acc_type?.isNoAd() == true && gIntersMoney?.acc_type?.isNoAd() == true) {
                            nextAction()
                        } else {
                            if (isMoneyGet.checkIsAdAvailable()) {
                                if (interMoney != null && interMoney?.isAdLoaded == true) {
                                    showFbInterMoney(getMoneyActivity)
                                } else if (interMoneyGoogle != null) {
                                    showGoogleMoney(getMoneyActivity)
                                } else {
                                    loadInterMoney(getMoneyActivity)
                                    showExtraMoney(getMoneyActivity)
                                }
                            } else if (isMoneyGet.checkIsQurekaAvailable()) {
                                showExtraMoney(getMoneyActivity)
                            } else {
                                nextAction()
                            }
                        }
                    }
                } ?: nextAction()
            } else if (whichMoneyGet.removeSpace().equals("G", true)) {
                SharedPrefData.newAdvertiseModel?.let { adsData ->
                    val gIntersMoney = adsData.ads.firstOrNull { it.key == gInterstitialAd }
                    if (gIntersMoney == null) {
                        nextAction()
                    } else {
                        if (gIntersMoney?.acc_type?.isNoAd() == true) {
                            nextAction()
                        } else {
                            if (isMoneyGet.checkIsAdAvailable()) {
                                if (interMoneyGoogle != null) {
                                    showGoogleMoney(getMoneyActivity)
                                } else {
                                    loadInterMoney(getMoneyActivity)
                                    showExtraMoney(getMoneyActivity)
                                }
                            } else if (isMoneyGet.checkIsQurekaAvailable()) {
                                showExtraMoney(getMoneyActivity)
                            } else {
                                nextAction()
                            }
                        }
                    }
                } ?: nextAction()
            } else if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                SharedPrefData.newAdvertiseModel?.let { adsData ->
                    val fbMoneyAds = adsData.ads.firstOrNull { it.key == fbInterstitialAd }
                    val gIntersMoney = adsData.ads.firstOrNull { it.key == gInterstitialAd }
                    if (fbMoneyAds == null && gIntersMoney == null) {
                        nextAction()
                    } else {
                        if (fbMoneyAds?.acc_type?.isNoAd() == true && gIntersMoney?.acc_type?.isNoAd() == true) {
                            nextAction()
                        } else {
                            if (isMoneyGet.checkIsAdAvailable()) {
                                if (interMoneyGoogle != null) {
                                    showGoogleMoney(getMoneyActivity)
                                } else if (interMoney != null && interMoney?.isAdLoaded == true) {
                                    showFbInterMoney(getMoneyActivity)
                                } else {
                                    loadInterMoney(getMoneyActivity)
                                    showExtraMoney(getMoneyActivity)
                                }
                            } else if (isMoneyGet.checkIsQurekaAvailable()) {
                                showExtraMoney(getMoneyActivity)
                            } else {
                                nextAction()
                            }
                        }
                    }
                } ?: nextAction()
            }
        } else {
            nextAction()
        }
    }

    private fun showFbInterMoney(getMoneyActivity: Activity) {
        val isLoaderMoney = SharedPrefData.getString(Constant.is_loader)
        if (isLoaderMoney.isCheckNotEmpty() && isLoaderMoney.equals("1", true)) {
            showProgressMoney(getMoneyActivity)
            Handler(Looper.getMainLooper()).postDelayed({
                dismissProgressMoney()
                interMoney?.show()
            }, 2000)
        } else {
            interMoney?.show()
        }
    }

    fun showForwardCountInterMoney(getMoneyActivity: Activity, code: Int, act: AagalJav) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        aagalJav = act
        if (whichMoneyGet.isCheckNotEmpty()) {
            if (Constant.isShowMilliseconds) {
                showInterMoneyTime(getMoneyActivity, act)
                return
            }
            if (Constant.forwardCount == 0) {
                nextAction()
            } else {
                if (Constant.isNormalAdCount == Constant.forwardCount) {
                    Constant.forwardCount = 1
                    showEveryTimeInterMoney(getMoneyActivity, act)
                } else {
                    Constant.forwardCount++
                    nextAction()
                }
            }
        } else {
            nextAction()
        }
    }

    private fun showBackCountInterMoney(getMoneyActivity: Activity, code: Int, jav: AagalJav) {
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority) ?: ""
        aagalJav = jav
        if (whichMoneyGet.isCheckNotEmpty()) {
            if (Constant.isShowMilliseconds) {
                showInterMoneyTime(getMoneyActivity, jav)
                return
            }
            if (Constant.backCount == 0) {
                nextAction()
            } else {
                if (Constant.backCount == Constant.isBackAdCount) {
                    Constant.isBackAdCount = 1
                    showEveryTimeInterMoney(getMoneyActivity, jav)
                } else {
                    Constant.isBackAdCount++
                    nextAction()
                }
            }
        } else {
            nextAction()
        }
    }

    private var downTimerMony: CountDownTimer? = null
    var isTimerRun = false

    private fun showInterMoneyTime(getMoneyActivity: Activity, aagalJav1: AagalJav) {
        aagalJav = aagalJav1
        if (isTimerRun) {
            nextAction()
            return
        }
        showEveryTimeInterMoney(getMoneyActivity, aagalJav1)
    }

    fun startTimer() {
        if (Constant.isShowMilliseconds) {
            downTimerMony?.start()
        }
    }

    private fun initTimerMoney() {
        if (Constant.isShowMilliseconds) {
            if (downTimerMony == null) {
                downTimerMony = object : CountDownTimer((Constant.adsInterval * 1000).toLong(), 1000) {
                    override fun onTick(p0: Long) {
                        isTimerRun = true
                    }

                    override fun onFinish() {
                        isTimerRun = false
                    }
                }
            }
        }
    }


    private fun nextAction() {
        aagalJav?.onNext()
        aagalJav = null
    }

    fun extraInterMoney(getMoneyActivity: Activity, action: AagalJav) {
        if (Constant.predChampURL.isCheckNotEmpty() || Constant.qurekaURL.isCheckNotEmpty() || Constant.gameURL.isCheckNotEmpty()) {
            val dialogMoney = Dialog(getMoneyActivity)
            var tvHeaderMoney: TextView
            var titleMoney: TextView
            var tital2: TextView
            var tvMoney: TextView
            var imgBannerMoney: ImageView
            val btnMoney: Button
            var llMoney: LinearLayout
            var gifMoney: GifImageView
            dialogMoney.apply {
                setContentView(GetMoneyUtils.intertialLayout())
                setCancelable(true)
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setGravity(Gravity.CENTER)
                setCancelable(false)
                setCanceledOnTouchOutside(false)

                tvHeaderMoney = findViewById(R.id.tvHeader)
                tvMoney = findViewById(R.id.tvBottomText)
                tvHeaderMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)

                titleMoney = findViewById(R.id.tital1)
                if (titleMoney != null) {
                    titleMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                }

                tital2 = findViewById(R.id.tital2)
                if (tital2 != null) {
                    tital2.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                }

                if (tvMoney != null) {
                    tvMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                }
                val extraAdsModel = GetMoneyUtils.descriptionList(getMoneyActivity)
                extraAdsModel?.let { model ->
                    imgBannerMoney = findViewById(R.id.imgBanner)
                    if (imgBannerMoney != null) {
                        imgBannerMoney.setImageResource(model.banner)
                        imgBannerMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top2)
                    }
                    llMoney = findViewById(R.id.llMain)
                    gifMoney = findViewById(R.id.gifView)
                    tvHeaderMoney.text = model.header
                    titleMoney.text = model.title
                    tvMoney.text = model.body
                    gifMoney.setImageResource(GetMoneyUtils.champOnlyGIFImage())

                    btnMoney = findViewById(R.id.btnPlayNow)
                    if (btnMoney != null) {
                        btnMoney.animation = AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top3)
                        btnMoney.text = GetMoneyUtils.buttonText(getMoneyActivity)
                    }

                    btnMoney?.setOnClickListener {
                        if (getMoneyActivity != null) {
                            getMoneyActivity.shareQurekaUrl(model.url)
                        }
                    }
                    if (llMoney != null) llMoney.setOnClickListener {
                        btnMoney.performClick()
                    }
                }

                findViewById<View>(R.id.ivClose).setOnClickListener {
                    if (isShowing) {
                        dismiss()
                    }
                    action.onNext()
                }

                findViewById<View>(R.id.btnClose).setOnClickListener {
                    if (isShowing) {
                        dismiss()
                    }
                    action.onNext()
                }

                setOnDismissListener {
                }

                show()

            }
        } else {
            action.onNext()
        }
    }



    private fun showProgressMoney(getMoneyActivity: Activity) {
        showPleaseWaitDialogMoney(getMoneyActivity)
        if (progressDialog?.isShowing == false) {
            progressDialog?.show()
        }
    }

    private fun dismissProgressMoney() {
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }


    private fun showPleaseWaitDialogMoney(getMoneyActivity: Activity) {
        progressDialog = CustomDialog(getMoneyActivity, object :
            OnCustomDialogListener<DialogLoadingBinding> {
            override fun getBinding(): DialogLoadingBinding {
                return DialogLoadingBinding.inflate(getMoneyActivity.layoutInflater)
            }

            override fun onDialogBind(binding: DialogLoadingBinding, dialog: Dialog) {
                dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.setCancelable(false)
                binding.apply {
                }
            }
        }).getDialog()
    }

}

