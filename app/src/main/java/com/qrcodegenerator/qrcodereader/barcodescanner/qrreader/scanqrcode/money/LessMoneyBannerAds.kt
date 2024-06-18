package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.OnHierarchyChangeListener
import android.view.WindowMetrics
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdOptionsView
import com.facebook.ads.NativeAdLayout
import com.facebook.ads.NativeAdListener
import com.facebook.ads.NativeBannerAd
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.fbNativeAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.fbNativeBannerAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.fbSmallNativeBanner
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gBanner
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gNativeAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gNativeBannerAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gameURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.predChampURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.qurekaURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.gone
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isCheckNotEmpty
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isNoAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isValidUrl
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.removeSpace
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.shareQurekaUrl
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.visible
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.MoneyOpenAdBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.ExtraAdsModel
import pl.droidsonroids.gif.GifImageView


object LessMoneyBannerAds {




    var dialog: Dialog? = null

    fun callExtraOpenAds(getMoneyActivity: Activity, nextAction: AagalJav) {
        if (predChampURL.isCheckNotEmpty() || qurekaURL.isCheckNotEmpty() || gameURL.isCheckNotEmpty()) {
            val bindingMoney = MoneyOpenAdBinding.inflate(getMoneyActivity.layoutInflater)
            dialog = Dialog(getMoneyActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.let { dialog ->
                dialog.window?.attributes?.windowAnimations = R.style.PauseDialogAnimation
                dialog.setContentView(bindingMoney.root)
                dialog.setCancelable(false)
                dialog.setCanceledOnTouchOutside(false)
                bindingMoney.apply {
                    tvHeader.animation =
                        AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                    tital1.animation =
                        AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                    tital2.animation =
                        AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                    tvBottomText.animation =
                        AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                    imgBanner.animation =
                        AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top2)
                    val model: ExtraAdsModel? = GetMoneyUtils.descriptionList(getMoneyActivity)
                    model?.let {
                        tvHeader.text = it.header
                        tital1.text = it.title
                        tvBottomText.text = it.body
                        gifView.setImageResource(it.gifView)
                        imgBanner.setImageResource(it.banner)

                        btnPlayNow.animation =
                            AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top3)
                        btnPlayNow.text = GetMoneyUtils.buttonText(getMoneyActivity)
                        btnPlayNow.setOnClickListener {
                            if (model.url.removeSpace().isValidUrl()) {
                                getMoneyActivity.shareQurekaUrl(model.url)
                            } else {
                                nextAction.onNext()
                            }
                        }
                    }
                    llMain.setOnClickListener { btnPlayNow.performClick() }
                    llClose.setOnClickListener {
                        if (dialog != null && dialog.isShowing) {
                            dialog.dismiss()
                        }
                        nextAction.onNext()
                    }
                    dialog.show()
                    if (dialog.isShowing) {
                        AppOpenMoney.isShowingMoney = true
                    }
                }
            }

        } else {
            nextAction.onNext()
        }
    }


    fun callExtraSplashOpen(getMoneyActivity: Activity, nextAction: AagalJav) {
        if (predChampURL.isCheckNotEmpty() || qurekaURL.isCheckNotEmpty() || gameURL.isCheckNotEmpty()) {
            val bindingMoney = MoneyOpenAdBinding.inflate(getMoneyActivity.layoutInflater)
            val dialogMoney = Dialog(getMoneyActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialogMoney.window?.attributes?.windowAnimations = R.style.PauseDialogAnimation
            dialogMoney.setContentView(bindingMoney.root)
            dialogMoney.setCancelable(false)
            dialogMoney.setCanceledOnTouchOutside(false)
            bindingMoney.apply {
                tvHeader.animation =
                    AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                tital1.animation =
                    AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                tital2.animation =
                    AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                tvBottomText.animation =
                    AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top1)
                imgBanner.animation =
                    AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top2)
                val extraAdsModel: ExtraAdsModel? = GetMoneyUtils.descriptionList(getMoneyActivity)

                extraAdsModel?.let {
                    tvHeader.text = it.header
                    tital1.text = it.title
                    tvBottomText.text = it.body
                    gifView.setImageResource(it.gifView)
                    imgBanner.setImageResource(it.banner)

                    btnPlayNow.animation =
                        AnimationUtils.loadAnimation(getMoneyActivity, R.anim.move_bottom_to_top3)
                    btnPlayNow.text = GetMoneyUtils.buttonText(getMoneyActivity)
                    btnPlayNow.setOnClickListener {
                        dialogMoney.dismiss()
                        if (extraAdsModel.url.removeSpace().isValidUrl()) {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constant.redirectURL))
                                getMoneyActivity.startActivityForResult(intent, 800)
                            }catch (_:ActivityNotFoundException){
                                Toast.makeText(
                                    getMoneyActivity,
                                    getMoneyActivity.getString(R.string.no_app_found),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            nextAction.onNext()
                        }
                    }
                }
                llMain.setOnClickListener { btnPlayNow.performClick() }
                llClose.setOnClickListener {
                    if (dialogMoney != null && dialogMoney.isShowing) {
                        dialogMoney.dismiss()
                    }
                    nextAction.onNext()
                }
                dialogMoney.show()
                if (dialogMoney.isShowing) {
                    AppOpenMoney.isShowingMoney = true
                }
            }
        } else {
            nextAction.onNext()
        }
    }


    fun loadMotiAds(
        getMoneyActivity: Activity,
        viewMoti: View,
        curtainLayout: ShimmerFrameLayout,
        moneyLayout: NativeAdLayout,
        containerLayout: FrameLayout,
        mainMoneyView: LinearLayout
    ) {
        val isMoneyGet = SharedPrefData.getString(Constant.isAdAvailable)
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        containerLayout.removeAllViews()
        moneyLayout.removeAllViews()
        if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals("G", true)) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val motiAds = adsData.ads.firstOrNull { it.key == gNativeAd }
                if (motiAds?.acc_type?.isNoAd() == false) {
                    if (isMoneyGet.equals("1", true)) {
                        mainMoneyView.visible()
                        if (motiAds.id.isCheckNotEmpty()) {
                            curtainLayout.startShimmer()
                            googleMoti(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                motiAds.id,
                                containerLayout,
                                mainMoneyView,
                                moneyLayout
                            )
                        } else {
                            extraMotiAds(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        }
                    } else if (isMoneyGet.equals("2", true)) {
                        extraMotiAds(viewMoti, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        viewMoti.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    viewMoti.gone()
                }
            }

        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "G,F",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val motiAdsG = adsData.ads.firstOrNull { it.key == gNativeAd }
                val motiAdsFb = adsData.ads.firstOrNull { it.key == fbNativeAd }
                if (isMoneyGet.equals("1", true)) {
                    mainMoneyView.visible()
                    if (motiAdsG?.acc_type?.isNoAd() == false) {
                        if (motiAdsG.id.isCheckNotEmpty()) {
                            curtainLayout.startShimmer()
                            googleMoti(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                motiAdsG.id,
                                containerLayout,
                                mainMoneyView,
                                moneyLayout
                            )
                        } else {
                            if (motiAdsFb?.acc_type?.isNoAd() == false) {
                                if (motiAdsFb.id.isCheckNotEmpty()) {
                                    curtainLayout.startShimmer()
                                    fbMotiAds(
                                        viewMoti,
                                        getMoneyActivity,
                                        curtainLayout,
                                        motiAdsFb.id,
                                        moneyLayout,
                                        containerLayout,
                                        mainMoneyView
                                    )
                                } else {
                                    extraMotiAds(
                                        viewMoti,
                                        getMoneyActivity,
                                        curtainLayout,
                                        containerLayout,
                                        mainMoneyView
                                    )
                                }
                            } else {
                                extraMotiAds(
                                    viewMoti,
                                    getMoneyActivity,
                                    curtainLayout,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        }
                    } else if (motiAdsFb?.acc_type?.isNoAd() == false) {
                        if (motiAdsFb.id.isCheckNotEmpty()) {
                            curtainLayout.startShimmer()
                            fbMotiAds(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                motiAdsFb.id,
                                moneyLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            extraMotiAds(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        }
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        viewMoti.gone()
                    }
                } else if (isMoneyGet.equals("2", true)) {
                    if (motiAdsFb?.id?.isNoAd() == false && motiAdsG?.id?.isNoAd() == false) {
                        extraMotiAds(viewMoti, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        viewMoti.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    viewMoti.gone()
                }
            }
        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "F,G",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val motiAdsG = adsData.ads.firstOrNull { it.key == gNativeAd }
                val motiAdsFb = adsData.ads.firstOrNull { it.key == fbNativeAd }
                if (isMoneyGet.equals("1", true)) {
                    mainMoneyView.visible()

                    if (motiAdsFb?.acc_type?.isNoAd() == true) {
                        if (motiAdsG?.acc_type?.isNoAd() == false) {
                            if (motiAdsG.id.isCheckNotEmpty()) {
                                curtainLayout.startShimmer()
                                googleMoti(
                                    viewMoti,
                                    getMoneyActivity,
                                    curtainLayout,
                                    motiAdsG.id,
                                    containerLayout,
                                    mainMoneyView,
                                    moneyLayout
                                )
                            } else {
                                extraMotiAds(
                                    viewMoti,
                                    getMoneyActivity,
                                    curtainLayout,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        } else {
                            curtainLayout.stopShimmer()
                            mainMoneyView.gone()
                            viewMoti.gone()
                        }
                    } else {
                        if (motiAdsFb?.id?.isCheckNotEmpty() == true) {
                            curtainLayout.startShimmer()
                            fbMotiAds(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                motiAdsFb.id,
                                moneyLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            if (motiAdsG?.acc_type?.isNoAd() == true) {
                                extraMotiAds(
                                    viewMoti,
                                    getMoneyActivity,
                                    curtainLayout,
                                    containerLayout,
                                    mainMoneyView
                                )
                            } else {
                                if (motiAdsG?.id?.isCheckNotEmpty() == true) {
                                    curtainLayout.startShimmer()
                                    googleMoti(
                                        viewMoti,
                                        getMoneyActivity,
                                        curtainLayout,
                                        motiAdsG.id,
                                        containerLayout,
                                        mainMoneyView,
                                        moneyLayout
                                    )
                                } else {
                                    extraMotiAds(
                                        viewMoti,
                                        getMoneyActivity,
                                        curtainLayout,
                                        containerLayout,
                                        mainMoneyView
                                    )
                                }
                            }
                        }
                    }
                } else if (isMoneyGet.equals("2", true)) {
                    if (motiAdsG?.acc_type?.isNoAd() == false && motiAdsFb?.acc_type?.isNoAd() == false) {
                        extraMotiAds(viewMoti, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        viewMoti.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    viewMoti.gone()
                }
            }

        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "F",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val motiAdsFb = adsData.ads.firstOrNull { it.key == fbNativeAd }
                if (motiAdsFb?.acc_type?.isNoAd() == false) {
                    if (isMoneyGet.equals("1", true)) {
                        mainMoneyView.visible()
                        if (motiAdsFb.id.isCheckNotEmpty()) {
                            curtainLayout.startShimmer()
                            fbMotiAds(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                motiAdsFb.id,
                                moneyLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            extraMotiAds(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        }
                    } else if (isMoneyGet.equals("2", true)) {
                        extraMotiAds(viewMoti, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        viewMoti.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    viewMoti.gone()
                }
            }
        } else {
            curtainLayout.stopShimmer()
            mainMoneyView.gone()
            viewMoti.gone()
        }
    }

    private fun googleMoti(
        viewMoti: View,
        getMoneyActivity: Activity,
        curtainLayout: ShimmerFrameLayout,
        motiAdIds: String,
        containerLayout: FrameLayout,
        mainMoneyView: LinearLayout,
        moneyLayout: NativeAdLayout
    ) {
        val moneyBuilder = AdLoader.Builder(getMoneyActivity, motiAdIds)
        moneyBuilder.forNativeAd { nativeAd ->
            val adView =
                getMoneyActivity.layoutInflater.inflate(
                    R.layout.google_money_layout,
                    null
                ) as NativeAdView
            mainMoneyView.gone()
            containerLayout.visible()
            moneyLayout.removeAllViews()
            containerLayout.removeAllViews()
            containerLayout.addView(adView)
            populateVachaliView(getMoneyActivity, nativeAd, adView, 1)
        }
        val options = VideoOptions.Builder().build()
        val nativeAdOptions = NativeAdOptions.Builder().setVideoOptions(options).build()
        moneyBuilder.withNativeAdOptions(nativeAdOptions)

        val adLoader = moneyBuilder.withAdListener(object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                containerLayout.removeAllViews()
                val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
                moneyLayout.removeAllViews()
                if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                    SharedPrefData.newAdvertiseModel?.let { adsData ->

                        val motiAdsFb = adsData.ads.firstOrNull { it.key == fbNativeAd }

                        if (motiAdsFb?.acc_type?.isNoAd() == true) {
                            extraMotiAds(
                                viewMoti,
                                getMoneyActivity,
                                curtainLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            if (motiAdsFb?.id?.isCheckNotEmpty() == true) {
                                containerLayout.removeAllViews()
                                curtainLayout.startShimmer()
                                fbMotiAds(
                                    viewMoti,
                                    getMoneyActivity,
                                    curtainLayout,
                                    motiAdsFb.id,
                                    moneyLayout,
                                    containerLayout,
                                    mainMoneyView
                                )
                            } else {
                                extraMotiAds(
                                    viewMoti,
                                    getMoneyActivity,
                                    curtainLayout,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        }
                    }
                } else if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                    extraMotiAds(viewMoti, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                } else if (whichMoneyGet.removeSpace().equals("G", true)) {
                    extraMotiAds(viewMoti, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                } else {
                    viewMoti.gone()
                }

            }

            override fun onAdOpened() {
                super.onAdOpened()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                mainMoneyView.gone()

            }

            override fun onAdClicked() {
                super.onAdClicked()
            }
        }).build()

        val networkExtras = Bundle()
        networkExtras.putString("max_ad_content_rating", "G")
        val adRequest =
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, networkExtras).build()
        adLoader.loadAd(adRequest)
    }


    private fun extraMotiAds(
        viewMoti: View,
        getMoneyActivity: Activity,
        curtainLayout: ShimmerFrameLayout,
        containerLayout: FrameLayout,
        mainMoneyView: LinearLayout
    ) {
        if (predChampURL.isCheckNotEmpty() || qurekaURL.isCheckNotEmpty() || gameURL.isCheckNotEmpty()) {
            mainMoneyView.gone()
            curtainLayout.stopShimmer()
            val myMoti1 = getMoneyActivity.layoutInflater.inflate(R.layout.ex_moti_ad, null)
            val gifMoti = myMoti1.findViewById<GifImageView>(R.id.gifView)
            val ivBannerMoney = myMoti1.findViewById<ImageView>(R.id.ivBanner)

            val tvTitleMoti = myMoti1.findViewById<TextView>(R.id.tvTitle)
            val tvDescMoti = myMoti1.findViewById<TextView>(R.id.tvDescription)

            val adsModel =
                GetMoneyUtils.descriptionList(getMoneyActivity)

            adsModel?.let { model ->
                ivBannerMoney.setImageResource(model.banner)
                gifMoti.setImageResource(model.gifView)
                tvTitleMoti.text = model.title
                tvDescMoti.text = model.body

                myMoti1.findViewById<View>(R.id.rlAdvertiseMain).setOnClickListener {
                    getMoneyActivity.shareQurekaUrl(model.url)
                }
            }

            if (containerLayout != null) {
                containerLayout.removeAllViews()
                containerLayout.addView(myMoti1)
            }
        } else {
            curtainLayout.stopShimmer()
            mainMoneyView.visibility = View.GONE
            viewMoti.gone()
        }

    }


    private fun fbMotiAds(
        viewMoney: View,
        getMoneyActivity: Activity,
        curtainLayout: ShimmerFrameLayout,
        adIds: String,
        moneyLayout: NativeAdLayout,
        containerLayout: FrameLayout,
        mainMoneyView: LinearLayout
    ) {
        val moneyMotiAdsFb = com.facebook.ads.NativeAd(getMoneyActivity, adIds)
        val p0: NativeAdListener = object : NativeAdListener {
            override fun onMediaDownloaded(ad: Ad) {}
            override fun onError(ad: Ad, adError: AdError) {
                val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
                containerLayout.removeAllViews()
                moneyLayout.removeAllViews()
                if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                    SharedPrefData.newAdvertiseModel?.let { adsData ->
                        val motiAdsG = adsData.ads.firstOrNull { it.key == gNativeAd }

                        if (motiAdsG?.acc_type?.isNoAd() == true) {
                            extraMotiAds(
                                viewMoney,
                                getMoneyActivity,
                                curtainLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            if (motiAdsG?.id?.isCheckNotEmpty() == true) {
                                curtainLayout.startShimmer()
                                googleMoti(
                                    viewMoney,
                                    getMoneyActivity,
                                    curtainLayout,
                                    motiAdsG.id,
                                    containerLayout,
                                    mainMoneyView,
                                    moneyLayout
                                )
                            } else {
                                extraMotiAds(
                                    viewMoney,
                                    getMoneyActivity,
                                    curtainLayout,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        }
                    }
                } else if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                    extraMotiAds(viewMoney, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                } else if (whichMoneyGet.removeSpace().equals("F", true)) {
                    extraMotiAds(viewMoney, getMoneyActivity, curtainLayout, containerLayout, mainMoneyView)
                } else {
                    viewMoney.gone()
                }

            }

            override fun onAdLoaded(ad: Ad) {
                mainMoneyView.gone()
                curtainLayout.stopShimmer()

                containerLayout.removeAllViews()
                exMotiView(moneyMotiAdsFb, getMoneyActivity, moneyLayout)
            }

            override fun onAdClicked(ad: Ad) {

            }

            override fun onLoggingImpression(ad: Ad) {

            }
        }
        moneyMotiAdsFb.loadAd(
            moneyMotiAdsFb.buildLoadAdConfig()
                .withAdListener(p0)
                .build()
        )
    }

    private fun exMotiView(
        motoView: com.facebook.ads.NativeAd,
        getMoneyActivity: Activity,
        moneyLayout: NativeAdLayout?
    ) {
        motoView.unregisterView()
        moneyLayout?.removeAllViews()
        val addMoney = LayoutInflater.from(getMoneyActivity)

        val moneyView =
            addMoney.inflate(R.layout.fb_moti_ad, moneyLayout, false) as LinearLayout
        moneyLayout?.addView(moneyView)

        val adChoicesMoto: LinearLayout =
            moneyView.findViewById(R.id.ad_choices_container)
        val adMotoView = AdOptionsView(getMoneyActivity, motoView, moneyLayout)
        adChoicesMoto.removeAllViews()
        adChoicesMoto.addView(adMotoView, 0)

        val motoAdIcon: com.facebook.ads.MediaView =
            moneyView.findViewById(R.id.native_icon_view)
        val motoAdTitle: TextView = moneyView.findViewById(R.id.native_ad_title)
        val motoAdMedia = moneyView.findViewById<com.facebook.ads.MediaView>(R.id.native_ad_media)
        val motoSocialContext: TextView =
            moneyView.findViewById(R.id.native_ad_social_context)
        val motoAdBody: TextView = moneyView.findViewById(R.id.native_ad_body)
        val motoLabel: TextView = moneyView.findViewById(R.id.native_ad_sponsored_label)
        val motoToAction: TextView =
            moneyView.findViewById(R.id.native_ad_call_to_action)

        motoAdTitle.text = motoView.advertiserName
        motoAdBody.text = motoView.adBodyText
        motoSocialContext.text = motoView.adSocialContext
        motoToAction.visibility =
            if (motoView.hasCallToAction()) View.VISIBLE else View.GONE
        motoToAction.text = motoView.adCallToAction
        motoLabel.text = motoView.sponsoredTranslation

        val clickableViews: MutableList<View> = java.util.ArrayList()
        clickableViews.add(motoAdTitle)
        clickableViews.add(motoToAction)


        motoView.registerViewForInteraction(
            moneyView,
            motoAdMedia,
            motoAdIcon,
            clickableViews
        )

        motoAdMedia.visible()
    }





    fun loadPattiAds(getMoneyActivity: Activity, moneyLayout: NativeAdLayout, pattiLayout: LinearLayout) {
        val isMoneyGet = SharedPrefData.getString(Constant.isAdAvailable)
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        pattiLayout.removeAllViews()
        if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "G",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val pattiG = adsData.ads.firstOrNull { it.key == gBanner }
                if (pattiG?.acc_type?.isNoAd() == false) {
                    if (isMoneyGet.equals("1", true)) {

                        if (pattiG.id.isCheckNotEmpty()) {
                            googlePattiAds(getMoneyActivity, pattiG.id, pattiLayout, moneyLayout)
                        } else {
                            extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                        }

                    } else if (isMoneyGet.equals("2", true)) {
                        extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                    }
                }
            }

        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "G,F",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val pattiG = adsData.ads.firstOrNull { it.key == gBanner }
                val pattiF = adsData.ads.firstOrNull { it.key == fbSmallNativeBanner }

                if (isMoneyGet.equals("1", true)) {
                    if (pattiG?.acc_type?.isNoAd() == false) {
                        if (pattiG.id.isCheckNotEmpty()) {
                            googlePattiAds(getMoneyActivity, pattiG.id, pattiLayout, moneyLayout)
                        } else {
                            if (pattiF?.acc_type?.isNoAd() == false) {
                                if (pattiF.id.isCheckNotEmpty()) {
                                    fbPattiAds(
                                        getMoneyActivity,
                                        pattiF.id,
                                        moneyLayout,
                                        pattiLayout
                                    )
                                } else {
                                    extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                                }
                            } else {
                                extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                            }
                        }
                    } else if (pattiF?.acc_type?.isNoAd() == false) {
                        if (pattiF.id.isCheckNotEmpty()) {
                            fbPattiAds(getMoneyActivity, pattiF.id, moneyLayout, pattiLayout)
                        } else {
                            extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                        }
                    }
                } else if (isMoneyGet.equals("2", true)) {
                    if (pattiF?.id?.isNoAd() == false && pattiG?.id?.isNoAd() == false) {
                        extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                    }
                }
            }
        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "F,G",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val pattiG = adsData.ads.firstOrNull { it.key == gBanner }
                val pattiF = adsData.ads.firstOrNull { it.key == fbSmallNativeBanner }
                if (isMoneyGet.equals("1", true)) {

                    if (pattiF?.acc_type?.isNoAd() == true) {
                        if (pattiG?.acc_type?.isNoAd() == false) {
                            if (pattiG.id.isCheckNotEmpty()) {
                                googlePattiAds(getMoneyActivity, pattiG.id, pattiLayout, moneyLayout)
                            } else {
                                extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                            }
                        }
                    } else {
                        if (pattiF?.id?.isCheckNotEmpty() == true) {
                            fbPattiAds(getMoneyActivity, pattiF.id, moneyLayout, pattiLayout)
                        } else {
                            if (pattiG?.acc_type?.isNoAd() == true) {
                                extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                            } else {
                                if (pattiG?.id?.isCheckNotEmpty() == true) {
                                    googlePattiAds(getMoneyActivity, pattiG.id, pattiLayout, moneyLayout)
                                } else {
                                    extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                                }
                            }
                        }
                    }

                } else if (isMoneyGet.equals("2", true)) {
                    if (pattiG?.acc_type?.isNoAd() == false && pattiF?.acc_type?.isNoAd() == false) {
                        extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                    }
                }
            }

        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "F",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val pattiF = adsData.ads.firstOrNull { it.key == fbSmallNativeBanner }
                if (pattiF?.acc_type?.isNoAd() == false) {
                    if (isMoneyGet.equals("1", true)) {
                        if (pattiF.id.isCheckNotEmpty()) {
                            fbPattiAds(getMoneyActivity, pattiF.id, moneyLayout, pattiLayout)
                        } else {
                            extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                        }
                    } else if (isMoneyGet.equals("2", true)) {
                        extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                    }
                }
            }

        }
    }

    private fun googlePattiAds(getMoneyActivity: Activity, id: String, pattiLayout: LinearLayout, moneyLayout: NativeAdLayout) {
        val networkExtras = Bundle()
        networkExtras.putString("max_ad_content_rating", "G")
        val request =
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, networkExtras).build()
        val moneyView = AdView(getMoneyActivity)

        moneyView.setAdSize(getMoneySize(getMoneyActivity, moneyView))
        moneyView.adUnitId = id
        moneyView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                moneyLayout.removeAllViews()
                pattiLayout.removeAllViews()
                pattiLayout.addView(moneyView)
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
                pattiLayout.removeAllViews()

                if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                    SharedPrefData.newAdvertiseModel?.let { adsData ->

                        val pattiF = adsData.ads.firstOrNull { it.key == fbSmallNativeBanner }

                        if (pattiF?.acc_type?.isNoAd() == true) {
                            extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                        } else {
                            if (pattiF?.id?.isCheckNotEmpty() == true) {
                                fbPattiAds(getMoneyActivity, pattiF.id, moneyLayout, pattiLayout)
                            } else {
                                extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                            }
                        }
                    }
                } else if (whichMoneyGet.removeSpace().equals("F,G", true)) {

                    extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)

                } else if (whichMoneyGet.removeSpace().equals("G", true)) {
                    extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                }

            }

            override fun onAdOpened() {

            }

            override fun onAdClicked() {

            }

            override fun onAdClosed() {

            }
        }

        moneyView.loadAd(request)

    }

    private fun getMoneySize(getMoneyActivity: Activity, moneyView: AdView): AdSize {
        val windowMetrics: WindowMetrics?
        val bounds: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowMetrics = getMoneyActivity.windowManager.currentWindowMetrics
            bounds = windowMetrics.bounds.width()
        } else {
            val metrics = DisplayMetrics()
            getMoneyActivity.windowManager.defaultDisplay.getMetrics(metrics)
            bounds = metrics.widthPixels
        }
        var adWidthPixels = moneyView.width.toFloat()

        if (adWidthPixels == 0f) {
            adWidthPixels = bounds.toFloat()
        }
        val density = getMoneyActivity.resources.displayMetrics.density
        val width = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getMoneyActivity, width)
    }

    private fun fbPattiAds(
        getMoneyActivity: Activity,
        adIds: String,
        moneyLayout: NativeAdLayout,
        pattiLayout: LinearLayout
    ) {
        val moneyAd = NativeBannerAd(getMoneyActivity, adIds)
        val listener: NativeAdListener = object : NativeAdListener {
            override fun onMediaDownloaded(ad: Ad) {
            }

            override fun onError(ad: Ad, adError: AdError) {
                moneyLayout.removeAllViews()
                val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
                if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                    SharedPrefData.newAdvertiseModel?.let { adsData ->
                        val pattiG = adsData.ads.firstOrNull { it.key == gBanner }

                        if (pattiG?.acc_type?.isNoAd() == true) {
                            extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                        } else {
                            if (pattiG?.id?.isCheckNotEmpty() == true) {
                                googlePattiAds(getMoneyActivity, pattiG.id, pattiLayout, moneyLayout)
                            } else {
                                extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                            }
                        }
                    }
                } else if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                    extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                } else if (whichMoneyGet.removeSpace().equals("F", true)) {
                    extraPattiAds(getMoneyActivity, pattiLayout, moneyLayout)
                }

            }

            override fun onAdLoaded(ad: Ad) {
                if (moneyAd != null) {
                    moneyLayout.removeAllViews()
                    exPattiAd(moneyAd, getMoneyActivity, moneyLayout)
                }
                return
            }

            override fun onAdClicked(ad: Ad) {

            }

            override fun onLoggingImpression(ad: Ad) {

            }
        }
        moneyAd.loadAd(
            moneyAd.buildLoadAdConfig()
                .withAdListener(listener)
                .build()
        )
    }

    private fun exPattiAd(
        nativeBannerAd: NativeBannerAd,
        getMoneyActivity: Activity,
        moneyLayout: NativeAdLayout
    ) {
        nativeBannerAd.unregisterView()
        moneyLayout.removeAllViews()
        val inflaterExtra = LayoutInflater.from(getMoneyActivity)
        val viewMoneyEx = inflaterExtra.inflate(
            R.layout.fb_patti_ad,
            moneyLayout,
            false
        ) as LinearLayout
        moneyLayout.addView(viewMoneyEx)

        val adChoicesContainer: LinearLayout =
            viewMoneyEx.findViewById(R.id.ad_choices_container)
        val adOptionsView = AdOptionsView(getMoneyActivity, nativeBannerAd, moneyLayout)
        adChoicesContainer.removeAllViews()
        adChoicesContainer.addView(adOptionsView, 0)

        val nativeAdTitle: TextView = viewMoneyEx.findViewById(R.id.native_ad_title)
        val nativeAdSocialContext: TextView =
            viewMoneyEx.findViewById(R.id.native_ad_social_context)
        val sponsoredLabel: TextView = viewMoneyEx.findViewById(R.id.native_ad_sponsored_label)
        val nativeAdIconView: com.facebook.ads.MediaView =
            viewMoneyEx.findViewById(R.id.native_ad_icon)
        val nativeAdCallToAction: TextView =
            viewMoneyEx.findViewById(R.id.native_ad_call_to_action)

        nativeAdCallToAction.text = nativeBannerAd.adCallToAction
        nativeAdCallToAction.visibility =
            if (nativeBannerAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdTitle.text = nativeBannerAd.advertiserName
        nativeAdSocialContext.text = nativeBannerAd.adSocialContext
        sponsoredLabel.text = nativeBannerAd.sponsoredTranslation

        val views: MutableList<View> = ArrayList()
        views.add(nativeAdTitle)
        views.add(nativeAdCallToAction)
        views.add(viewMoneyEx)
        nativeBannerAd.registerViewForInteraction(viewMoneyEx, nativeAdIconView, views)
    }


    private fun extraPattiAds(
        getMoneyActivity: Activity,
        bannerAdsView: LinearLayout,
        moneyLayout: NativeAdLayout
    ) {
        bannerAdsView.removeAllViews()
        moneyLayout.removeAllViews()
        if (predChampURL.isCheckNotEmpty() || qurekaURL.isCheckNotEmpty() || gameURL.isCheckNotEmpty()) {
            val moneyView: View =
                LayoutInflater.from(getMoneyActivity).inflate(R.layout.ex_patti_ads, null, false)
            val ivBanner = moneyView.findViewById<ImageView>(R.id.ivBanner)
            val model: ExtraAdsModel? =
                GetMoneyUtils.bannerModelRandom(getMoneyActivity)
            model?.let { adsModel ->
                ivBanner.setImageResource(adsModel.banner)
                moneyView.findViewById<View>(R.id.rlAdvertiseMain).setOnClickListener {
                    getMoneyActivity.shareQurekaUrl(adsModel.url)
                }
            }
            bannerAdsView.addView(moneyView)
        } else {

        }
    }
    
    
    
    
    
    
    
    

    


    fun loadVachaliAds(
        getMoneyActivity: Activity,
        view: View,
        curtainLayout: ShimmerFrameLayout,
        moneyLayout: NativeAdLayout,
        containerLayout: FrameLayout,
        mainMoneyView: LinearLayout
    ) {
        val isMoneyGet = SharedPrefData.getString(Constant.isAdAvailable)
        val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
        if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "G",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val vachaliAds = adsData.ads.firstOrNull { it.key == gNativeBannerAd }
                if (vachaliAds?.acc_type?.isNoAd() == false) {
                    if (isMoneyGet.equals("1", true)) {
                        mainMoneyView.visible()
                        if (vachaliAds.id.isCheckNotEmpty()) {
                            containerLayout.removeAllViews()
                            curtainLayout.startShimmer()
                            googleVachali(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                vachaliAds.id,
                                containerLayout,
                                mainMoneyView,
                                moneyLayout
                            )
                        } else {
                            extraVachaliAds(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                containerLayout,
                                mainMoneyView
                            )
                        }
                    } else if (isMoneyGet.equals("2", true)) {
                        extraVachaliAds(
                            view,
                            curtainLayout,
                            getMoneyActivity,
                            containerLayout,
                            mainMoneyView
                        )
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        view.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    view.gone()
                }
            }

        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "G,F",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val vachaliAds = adsData.ads.firstOrNull { it.key == gNativeBannerAd }
                val vachaliAdsFb = adsData.ads.firstOrNull { it.key == fbNativeBannerAd }
                if (isMoneyGet.equals("1", true)) {
                    mainMoneyView.visible()
                    if (vachaliAds?.acc_type?.isNoAd() == false) {
                        if (vachaliAds.id.isCheckNotEmpty()) {
                            containerLayout.removeAllViews()
                            curtainLayout.startShimmer()
                            googleVachali(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                vachaliAds.id,
                                containerLayout,
                                mainMoneyView,
                                moneyLayout
                            )
                        } else {
                            if (vachaliAdsFb?.acc_type?.isNoAd() == false) {
                                if (vachaliAdsFb.id.isCheckNotEmpty()) {
                                    containerLayout.removeAllViews()
                                    curtainLayout.startShimmer()
                                    fbVachaliAds(
                                        view,
                                        curtainLayout,
                                        getMoneyActivity,
                                        vachaliAdsFb.id,
                                        moneyLayout,
                                        containerLayout,
                                        mainMoneyView
                                    )
                                } else {
                                    extraVachaliAds(
                                        view,
                                        curtainLayout,
                                        getMoneyActivity,
                                        containerLayout,
                                        mainMoneyView
                                    )
                                }
                            } else {
                                extraVachaliAds(
                                    view,
                                    curtainLayout,
                                    getMoneyActivity,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        }
                    } else if (vachaliAdsFb?.acc_type?.isNoAd() == false) {
                        if (vachaliAdsFb.id.isCheckNotEmpty()) {
                            containerLayout.removeAllViews()
                            curtainLayout.startShimmer()
                            fbVachaliAds(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                vachaliAdsFb.id,
                                moneyLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            extraVachaliAds(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                containerLayout,
                                mainMoneyView
                            )
                        }
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        view.gone()
                    }
                } else if (isMoneyGet.equals("2", true)) {
                    if (vachaliAdsFb?.id?.isNoAd() == false && vachaliAds?.id?.isNoAd() == false) {
                        extraVachaliAds(
                            view,
                            curtainLayout,
                            getMoneyActivity,
                            containerLayout,
                            mainMoneyView
                        )
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        view.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    view.gone()
                }
            }
        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "F,G",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val vachaliAds = adsData.ads.firstOrNull { it.key == gNativeBannerAd }
                val pattiF = adsData.ads.firstOrNull { it.key == fbNativeBannerAd }
                if (isMoneyGet.equals("1", true)) {
                    mainMoneyView.visible()

                    if (pattiF?.acc_type?.isNoAd() == true) {
                        if (vachaliAds?.acc_type?.isNoAd() == false) {
                            if (vachaliAds.id.isCheckNotEmpty()) {
                                containerLayout.removeAllViews()
                                curtainLayout.startShimmer()
                                googleVachali(
                                    view,
                                    curtainLayout,
                                    getMoneyActivity,
                                    vachaliAds.id,
                                    containerLayout,
                                    mainMoneyView,
                                    moneyLayout
                                )
                            } else {
                                extraVachaliAds(
                                    view,
                                    curtainLayout,
                                    getMoneyActivity,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        } else {
                            curtainLayout.stopShimmer()
                            mainMoneyView.gone()
                            view.gone()
                        }
                    } else {
                        if (pattiF?.id?.isCheckNotEmpty() == true) {
                            containerLayout.removeAllViews()
                            curtainLayout.startShimmer()
                            fbVachaliAds(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                pattiF.id,
                                moneyLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            if (vachaliAds?.acc_type?.isNoAd() == true) {
                                extraVachaliAds(
                                    view,
                                    curtainLayout,
                                    getMoneyActivity,
                                    containerLayout,
                                    mainMoneyView
                                )
                            } else {
                                if (vachaliAds?.id?.isCheckNotEmpty() == true) {
                                    containerLayout.removeAllViews()
                                    curtainLayout.startShimmer()
                                    googleVachali(
                                        view,
                                        curtainLayout,
                                        getMoneyActivity,
                                        vachaliAds.id,
                                        containerLayout,
                                        mainMoneyView,
                                        moneyLayout
                                    )
                                } else {
                                    extraVachaliAds(
                                        view,
                                        curtainLayout,
                                        getMoneyActivity,
                                        containerLayout,
                                        mainMoneyView
                                    )
                                }
                            }
                        }
                    }
                } else if (isMoneyGet.equals("2", true)) {
                    if (vachaliAds?.acc_type?.isNoAd() == false && pattiF?.acc_type?.isNoAd() == false) {
                        extraVachaliAds(
                            view,
                            curtainLayout,
                            getMoneyActivity,
                            containerLayout,
                            mainMoneyView
                        )
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.gone()
                        view.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    view.gone()
                }
            }

        } else if (isMoneyGet.isCheckNotEmpty() && whichMoneyGet.isCheckNotEmpty() && whichMoneyGet.equals(
                "F",
                true
            )
        ) {
            SharedPrefData.newAdvertiseModel?.let { adsData ->
                val vachaliAds = adsData.ads.firstOrNull { it.key == fbNativeBannerAd }
                if (vachaliAds?.acc_type?.isNoAd() == false) {
                    if (isMoneyGet.equals("1", true)) {
                        mainMoneyView.visible()
                        if (vachaliAds.id.isCheckNotEmpty()) {
                            containerLayout.removeAllViews()
                            curtainLayout.startShimmer()
                            fbVachaliAds(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                vachaliAds.id,
                                moneyLayout,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            extraVachaliAds(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                containerLayout,
                                mainMoneyView
                            )
                        }

                    } else if (isMoneyGet.equals("2", true)) {
                        extraVachaliAds(
                            view,
                            curtainLayout,
                            getMoneyActivity,
                            containerLayout,
                            mainMoneyView
                        )
                    } else {
                        curtainLayout.stopShimmer()
                        mainMoneyView.visibility = View.GONE
                        view.gone()
                    }
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.visibility = View.GONE
                    view.gone()
                }
            }

        } else {
            curtainLayout.stopShimmer()
            mainMoneyView.visibility = View.GONE
            view.gone()
        }
    }


    private fun googleVachali(
        view: View,
        curtainLayout: ShimmerFrameLayout,
        getMoneyActivity: Activity,
        adIds: String,
        containerLayout: FrameLayout,
        mainMoneyView: LinearLayout,
        moneyLayout: NativeAdLayout
    ) {
        val builderMoney = AdLoader.Builder(getMoneyActivity, adIds)
        builderMoney.forNativeAd { nativeAd ->
            val adView =
                getMoneyActivity.layoutInflater.inflate(R.layout.google_money_layout, null) as NativeAdView
            mainMoneyView.gone()
            containerLayout.visible()
            containerLayout.removeAllViews()
            containerLayout.addView(adView)
            populateVachaliView(getMoneyActivity, nativeAd, adView, 0)
        }
        val options = VideoOptions.Builder().build()
        val adOptionsMoney = NativeAdOptions.Builder().setVideoOptions(options).build()
        builderMoney.withNativeAdOptions(adOptionsMoney)

        val adLoader = builderMoney.withAdListener(object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                containerLayout.removeAllViews()
                val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)
                if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                    SharedPrefData.newAdvertiseModel?.let { adsData ->
                        val vachaliAdsFb = adsData.ads.firstOrNull { it.key == fbNativeBannerAd }
                        if (vachaliAdsFb?.acc_type?.isNoAd() == true) {
                            extraVachaliAds(
                                view,
                                curtainLayout,
                                getMoneyActivity,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            if (vachaliAdsFb?.id?.isCheckNotEmpty() == true) {
                                containerLayout.removeAllViews()
                                curtainLayout.startShimmer()
                                fbVachaliAds(
                                    view,
                                    curtainLayout,
                                    getMoneyActivity,
                                    vachaliAdsFb.id,
                                    moneyLayout,
                                    containerLayout,
                                    mainMoneyView
                                )
                            } else {
                                extraVachaliAds(
                                    view,
                                    curtainLayout,
                                    getMoneyActivity,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        }
                    }
                } else if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                    extraVachaliAds(view, curtainLayout, getMoneyActivity, containerLayout, mainMoneyView)
                } else if (whichMoneyGet.removeSpace().equals("G", true)) {
                    extraVachaliAds(view, curtainLayout, getMoneyActivity, containerLayout, mainMoneyView)
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    view.gone()
                }
            }

            override fun onAdOpened() {
                super.onAdOpened()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                mainMoneyView.gone()
            }

            override fun onAdClicked() {
                super.onAdClicked()
            }
        }).build()


        val networkExtras = Bundle()
        networkExtras.putString("max_ad_content_rating", "G")
        val adRequest =
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, networkExtras).build()
        adLoader.loadAd(adRequest)

    }

    private fun populateVachaliView(
        getMoneyActivity: Activity,
        nativeAd: NativeAd,
        adView: NativeAdView,
        adsMap: Int
    ) {
        var mideaview: MediaView? = null
        if (adsMap == 1) {
            mideaview = adView.findViewById(R.id.ad_media)

            adView.mediaView = mideaview
        }
        adView.findViewById<View>(R.id.ad_stars).visibility = View.GONE

        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        (adView.headlineView as TextView?)!!.text = nativeAd.headline
        if (mideaview != null) adView.mediaView!!.mediaContent = nativeAd.mediaContent


        if (nativeAd.body == null) {

            adView.bodyView!!.visibility = View.GONE
        } else {
            adView.bodyView!!.visible()
            (adView.bodyView as TextView?)!!.text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {

            adView.callToActionView!!.visibility = View.GONE
        } else {
            adView.callToActionView!!.visible()
            (adView.callToActionView as TextView?)!!.text = nativeAd.callToAction
            (adView.callToActionView as TextView?)!!.background =
                getMoneyActivity.resources.getDrawable(R.drawable.btn_blue_btn)
        }
        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView?)!!.setImageDrawable(
                nativeAd.icon!!.drawable
            )
            adView.iconView!!.visible()
        }
        if (nativeAd.price == null) {

            adView.priceView!!.visibility = View.GONE
        } else {
            adView.priceView!!.visibility = View.GONE
            (adView.priceView as TextView?)!!.text = nativeAd.price
        }
        if (nativeAd.store == null) {

            adView.storeView!!.visibility = View.GONE
        } else {
            adView.storeView!!.visibility = View.GONE
            (adView.storeView as TextView?)!!.text = nativeAd.store
        }
        if (nativeAd.starRating == null) {

            adView.starRatingView!!.visibility = View.GONE
        } else {
            (adView.starRatingView as RatingBar?)?.rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView!!.visibility = View.GONE
        }
        if (nativeAd.advertiser == null) {

            adView.advertiserView!!.visibility = View.GONE
        } else {
            (adView.advertiserView as TextView?)!!.text = nativeAd.advertiser
            adView.advertiserView!!.visible()
        }
        adView.setNativeAd(nativeAd)
        val vc = nativeAd.mediaContent!!.videoController
        if (vc.hasVideoContent()) {
            vc.videoLifecycleCallbacks = object : VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    super.onVideoEnd()
                }
            }
        } else {
        }
        if (mideaview != null) {
            mideaview.setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
                override fun onChildViewAdded(parent: View, child: View) {
                    if (child is ImageView) {
                        child.adjustViewBounds = true
                    }
                }

                override fun onChildViewRemoved(parent: View, child: View) {}
            })
            mideaview.visible()
        }
    }


    private fun fbVachaliAds(
        viewMoney: View,
        curtainLayout: ShimmerFrameLayout,
        getMoneyActivity: Activity,
        moneysIds: String,
        moneyLayout: NativeAdLayout,
        containerLayout: FrameLayout,
        mainMoneyView: LinearLayout
    ) {
        val nativeBannerAd = NativeBannerAd(getMoneyActivity, moneysIds)
        val nativeAdListener: NativeAdListener = object : NativeAdListener {
            override fun onMediaDownloaded(ad: Ad) {

            }

            override fun onError(ad: Ad, adError: AdError) {
                moneyLayout.removeAllViews()
                val whichMoneyGet = SharedPrefData.getString(Constant.strPriority)

                if (whichMoneyGet.removeSpace().equals("F,G", true)) {
                    SharedPrefData.newAdvertiseModel?.let { adsData ->
                        val vachaliAds = adsData.ads.firstOrNull { it.key == gNativeBannerAd }
                        if (vachaliAds?.acc_type?.isNoAd() == true) {
                            extraVachaliAds(
                                viewMoney,
                                curtainLayout,
                                getMoneyActivity,
                                containerLayout,
                                mainMoneyView
                            )
                        } else {
                            if (vachaliAds?.id?.isCheckNotEmpty() == true) {
                                containerLayout.removeAllViews()
                                curtainLayout.startShimmer()
                                googleVachali(
                                    viewMoney,
                                    curtainLayout,
                                    getMoneyActivity,
                                    vachaliAds.id,
                                    containerLayout,
                                    mainMoneyView,
                                    moneyLayout
                                )
                            } else {
                                extraVachaliAds(
                                    viewMoney,
                                    curtainLayout,
                                    getMoneyActivity,
                                    containerLayout,
                                    mainMoneyView
                                )
                            }
                        }
                    }
                } else if (whichMoneyGet.removeSpace().equals("G,F", true)) {
                    extraVachaliAds(viewMoney, curtainLayout, getMoneyActivity, containerLayout, mainMoneyView)
                } else if (whichMoneyGet.removeSpace().equals("F", true)) {
                    extraVachaliAds(viewMoney, curtainLayout, getMoneyActivity, containerLayout, mainMoneyView)
                } else {
                    curtainLayout.stopShimmer()
                    mainMoneyView.gone()
                    viewMoney.gone()
                }

            }

            override fun onAdLoaded(ad: Ad) {
                curtainLayout.stopShimmer()
                mainMoneyView.gone()
                exVachaliView(nativeBannerAd, getMoneyActivity, moneyLayout)
            }

            override fun onAdClicked(ad: Ad) {

            }

            override fun onLoggingImpression(ad: Ad) {

            }
        }
        nativeBannerAd.loadAd(
            nativeBannerAd.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build()
        )
    }

    private fun exVachaliView(
        nativeBannerAd: NativeBannerAd,
        getMoneyActivity: Activity,
        moneyLayout: NativeAdLayout?
    ) {

        nativeBannerAd.unregisterView()
        moneyLayout?.removeAllViews()
        val inflater = LayoutInflater.from(getMoneyActivity)


        val extraMoneyView =
            inflater.inflate(R.layout.fb_vachli_ad, moneyLayout, false) as LinearLayout

        moneyLayout?.addView(extraMoneyView)


        val moneyContainer: LinearLayout =
            extraMoneyView.findViewById(R.id.ad_choices_container)
        val adOptionsView = AdOptionsView(getMoneyActivity, nativeBannerAd, moneyLayout)
        moneyContainer.removeAllViews()
        moneyContainer.addView(adOptionsView, 0)

        val moneyTitle: TextView = extraMoneyView.findViewById(R.id.native_ad_title)
        val moneySocialContext: TextView =
            extraMoneyView.findViewById(R.id.native_ad_social_context)
        val moneyLabel: TextView = extraMoneyView.findViewById(R.id.native_ad_sponsored_label)
        val moneyIconView: com.facebook.ads.MediaView =
            extraMoneyView.findViewById(R.id.native_ad_icon)
        val moneyToAction: TextView =
            extraMoneyView.findViewById(R.id.native_ad_call_to_action)

        moneyToAction.text = nativeBannerAd.adCallToAction
        moneyToAction.visibility =
            if (nativeBannerAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        moneyTitle.text = nativeBannerAd.advertiserName
        moneySocialContext.text = nativeBannerAd.adSocialContext
        moneyLabel.text = nativeBannerAd.sponsoredTranslation

        val moneyViews: MutableList<View> = java.util.ArrayList()
        moneyViews.add(moneyTitle)
        moneyViews.add(moneyToAction)
        nativeBannerAd.registerViewForInteraction(extraMoneyView, moneyIconView, moneyViews)
    }

    private fun extraVachaliAds(
        moneyView: View,
        curtainLayout: ShimmerFrameLayout,
        getMoneyActivity: Activity,
        vachaliAdsG: FrameLayout,
        mainMoneyView: LinearLayout
    ) {
        if (predChampURL.isCheckNotEmpty() || qurekaURL.isCheckNotEmpty() || gameURL.isCheckNotEmpty()) {
            mainMoneyView.gone()
            curtainLayout.stopShimmer()
            val moneyView =
                getMoneyActivity.layoutInflater.inflate(R.layout.ex_vachali_ads, null) as View
            val moneyGif = moneyView.findViewById<GifImageView>(R.id.gifView)
            val ivMoney = moneyView.findViewById<ImageView>(R.id.ivBanner)
            val tvMoney = moneyView.findViewById<TextView>(R.id.tvTitle)
            val tvDescMoney = moneyView.findViewById<TextView>(R.id.tvDescription)
            val model1 =
                GetMoneyUtils.descriptionList(getMoneyActivity)
            model1?.let { model ->
                ivMoney.setImageResource(model.banner)
                moneyGif.setImageResource(model.gifView)
                tvMoney.text = model.title
                tvDescMoney.text = model.body
                moneyView.findViewById<View>(R.id.rlAdvertiseMain).setOnClickListener {
                    getMoneyActivity.shareQurekaUrl(model.url)
                }
            }

            if (vachaliAdsG != null) {
                vachaliAdsG.removeAllViews()
                vachaliAdsG.addView(moneyView)
            }
        } else {
            mainMoneyView.gone()
            moneyView.gone()
        }


    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    





}