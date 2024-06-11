package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.BuildConfig
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.MyAppClass
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gameURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.isNotificationClicked
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.isOnResume
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.predChampURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.qurekaURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.redirectURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.GetDataService
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.checkIsAdAvailable
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.checkIsQurekaAvailable
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isCheckNotEmpty
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isNoAd
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.removeSpace
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivitySplashBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.GetMoneyModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AppOpenMoney
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds.loadInterMoney
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.SplashMoneyApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getViewBinding(): ActivitySplashBinding {
        return  ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView() {
        enableEdgeToEdge()
        init()

        onBackPressedDispatcher.addCallback(this@SplashActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            })
    }
    private fun init() {
        FirebaseApp.initializeApp(this@SplashActivity)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        if (isOnline()) {
            callAppsAdsApi()
        } else {
            showNoInternet {
                callAppsAdsApi()
            }
        }
    }


    private fun callAppsAdsApi() {
        val retrofit =
            Retrofit.Builder().baseUrl(BuildConfig.BASE_AD_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

        val apiService = retrofit.create(GetDataService::class.java)

        val call = apiService.getExample()
        call.enqueue(object : Callback<GetMoneyModel> {
            override fun onResponse(call: Call<GetMoneyModel>, response: Response<GetMoneyModel>) {
                if (response.isSuccessful) {
                    val adsData = response.body()
                    adsData?.let { it ->

                        it.data.let { data ->
                            Constant.issplashshowed = false
                            AppOpenMoney.isShowingMoney = true
                            isNotificationClicked = false
                            Constant.DEVELOPER_NAME = data.developer_name
                            if (data.app_version_code?.isCheckNotEmpty()==true) {
                                SharedPrefData.setString(Constant.App_VERSION_CODE, data.app_version_code)
                            }


                            if (data.is_loader.isCheckNotEmpty()) {
                                SharedPrefData.setString(Constant.is_loader, data.is_loader)
                            }
                            if (data.user_dialog_update_text.isCheckNotEmpty()) {
                                SharedPrefData.setString(Constant.USER_DIALOG_UPDATE_TEXT, data.user_dialog_update_text)
                            } else {
                                SharedPrefData.setString(Constant.USER_DIALOG_UPDATE_TEXT, getString(
                                    R.string.update_mssg))
                            }
                            if (data.is_info_available.isCheckNotEmpty()) {
                                SharedPrefData.setString(
                                    Constant.IS_INFO, data.is_info_available
                                )
                            }



                            SharedPrefData.setString(Constant.isAdAvailable, data.is_advertise_available.removeSpace())
                            SharedPrefData.setString(Constant.IS_QUREKA, data.is_qureka.removeSpace())

                            SharedPrefData.setString(Constant.strPriority, it.ad_priority.priority.removeSpace())
                            Constant.adsPriority = it.ad_priority.priority.removeSpace()
                            Constant.isShowMilliseconds = data.is_show_second
                            Constant.adsInterval = data.inter_mill_sec

                            for (ads in it.ad_priority.ads) {
                                ads.key = ads.key.removeSpace()
                                ads.id = ads.id.removeSpace()
                            }

                            SharedPrefData.setNewAdvertiseModel(it.ad_priority)
                            SharedPrefData.setBooleanValue(isOnResume, data.is_on_resume)


                            if (data.is_back_press_count.isCheckNotEmpty()) {
                                Constant.isBackAdCount = data.is_back_press_count.toInt()
                                Constant.backCount = data.is_back_press_count.toInt()
                                SharedPrefData.setString(Constant.BACK_PRESS_COUNT, data.is_back_press_count)
                            }
                            if (data.is_normal_ad_count.isCheckNotEmpty()) {
                                Constant.isNormalAdCount = data.is_normal_ad_count.toInt()
                                Constant.forwardCount = data.is_normal_ad_count.toInt()
                                SharedPrefData.setString(Constant.NORMAL_AD_COUNT, data.is_normal_ad_count)
                            }
                            if (data.predchamp_url.removeSpace().isCheckNotEmpty()) {
                                predChampURL = data.predchamp_url.removeSpace()
                                redirectURL = data.predchamp_url.removeSpace()
                            }
                            if (data.bitcoin_url.removeSpace().isCheckNotEmpty()) {
                                qurekaURL = data.bitcoin_url.removeSpace()
                                redirectURL = data.bitcoin_url.removeSpace()
                            }
                            if (data.game_url.removeSpace().isCheckNotEmpty()) {
                                gameURL = data.game_url.removeSpace()
                                redirectURL = data.game_url.removeSpace()
                            }
          
                            if (it.ad_priority.ads.isNotEmpty()) {
                                it.ad_priority.apply {
                                    val openAds = it.ad_priority.ads.firstOrNull { it.key == "g_app_open_ad" }
                                    val fbModel = it.ad_priority.ads.firstOrNull { it.key == "fb_interstitial_ad" }

                                    Constant.appOpenAdsId = openAds?.id ?: ""
                                    Constant.adsPriority = priority.removeSpace()
                                    LessMoneyBannerAds.loadPattiAds(this@SplashActivity, binding.llPattiMoneyView.nativeAdContainer50, this@SplashActivity.findViewById(R.id.llBanner50))
                                    loadInterMoney(this@SplashActivity)
                                    Handler(mainLooper).postDelayed(Runnable {
                                        if (priority.isCheckNotEmpty() && priority.removeSpace().equals("G", ignoreCase = true)) {

                                            //     Enter when priority G
                                            if (openAds?.acc_type?.isNoAd() == false && openAds.id.isCheckNotEmpty()) {
                                                AppOpenMoney(MyAppClass.application).fetchMoney(openAds.id)
                                            }
                                            if (data.is_advertise_available.checkIsAdAvailable()) {
                                                if (data.is_splash_available.checkIsAdAvailable() && openAds?.acc_type?.isNoAd() == false) {
                                                    if (Constant.appOpenAdsId.isNotEmpty()) {
                                                        Log.e("TAG", "onResponse: ")
                                                        SplashMoneyApp(MyAppClass.application, this@SplashActivity, object :
                                                            AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }
                                                        })
                                                    } else {
                                                        LessMoneyBannerAds.callExtraSplashOpen(this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }
                                                        })
//
                                                    }
                                                } else {
                                                    startNextIntent()
                                                }

                                            } else if (data.is_advertise_available.checkIsQurekaAvailable()) {
//                                        Enter when is_advertise_available - 2
                                                if (openAds?.acc_type?.isNoAd() == true) {
                                                    startNextIntent()
                                                } else {
                                                    if (data.is_splash_available.checkIsAdAvailable()) {
                                                        LessMoneyBannerAds.callExtraSplashOpen(this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }
                                                        })
                                                    } else {
                                                        startNextIntent()
                                                    }
                                                }

                                            } else {
                                                startNextIntent()
                                            }
                                        } else if (priority.isCheckNotEmpty() && priority.removeSpace().equals("G,F", ignoreCase = true)) {
                                            Log.e("TAG", "onResponse: .....1111...........")
                                            //     Enter when priority G,F

                                            if (openAds?.acc_type?.isNoAd() == false) {
                                                AppOpenMoney(MyAppClass.application).fetchMoney(openAds.id)
                                            }

                                            if (data.is_advertise_available.checkIsAdAvailable()) {
                                                if (data.is_splash_available.checkIsAdAvailable()) {
                                                    Log.e("TAG", "onResponse: ..openAds.acc_type......." + openAds?.acc_type)
                                                    if (openAds?.acc_type?.isNoAd() == false) {
                                                        SplashMoneyApp(MyAppClass.application, this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }
                                                        })
                                                    } else if (fbModel?.acc_type?.isNoAd() == false) {
                                                        SplashMoneyApp(MyAppClass.application, this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }
                                                        })
                                                    } else {
                                                        startNextIntent()
                                                    }
                                                } else {
                                                    startNextIntent()
                                                }

                                            } else if (data.is_advertise_available.checkIsQurekaAvailable()) {
//                                        Enter when is_advertise_available - 2
                                                if (openAds?.acc_type?.isNoAd() == true && fbModel?.acc_type?.isNoAd() == true) {
                                                    startNextIntent()
                                                } else {
                                                    if (data.is_splash_available.checkIsAdAvailable()) {
                                                        if (openAds?.acc_type?.isNoAd() == true) {
                                                            InterMoneyAds.extraInterMoney(this@SplashActivity, object : AagalJav {
                                                                override fun onNext() {
                                                                    startNextIntent()
                                                                }
                                                            })
                                                        } else {
                                                            LessMoneyBannerAds.callExtraSplashOpen(this@SplashActivity, object : AagalJav {
                                                                override fun onNext() {
                                                                    startNextIntent()
                                                                }
                                                            })
                                                        }
                                                    } else {
                                                        startNextIntent()
                                                    }
                                                }

                                            } else {
                                                startNextIntent()
                                            }
                                        } else if (priority.isCheckNotEmpty() && priority.removeSpace().equals("F", ignoreCase = true) && data.is_splash_available.checkIsAdAvailable()) {
                                            if (data.is_advertise_available.checkIsAdAvailable()) {
//                                        Enter when priority F
                                                Log.e("TAG", "fbModel.id.............." + fbModel?.id)
                                                if (fbModel?.acc_type?.isNoAd() == false) {
                                                    if (fbModel.id.isCheckNotEmpty()) {
                                                        SplashMoneyApp(MyAppClass.application, this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }
                                                        })
                                                    } else {
                                                        InterMoneyAds.extraInterMoney(this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }

                                                        })
                                                    }
                                                } else {
                                                    startNextIntent()
                                                }

                                            } else if (data.is_advertise_available.checkIsQurekaAvailable()) {
                                                if (fbModel?.acc_type?.isNoAd() == true) {
                                                    startNextIntent()
                                                } else {
                                                    InterMoneyAds.extraInterMoney(this@SplashActivity, object : AagalJav {
                                                        override fun onNext() {
                                                            startNextIntent()
                                                        }

                                                    })
                                                }

                                            } else {
                                                startNextIntent()
                                            }
                                        } else if (priority.isCheckNotEmpty() && priority.removeSpace().equals("F,G", ignoreCase = true)) {

                                            if (openAds?.acc_type?.isNoAd() == false) {
                                                AppOpenMoney(MyAppClass.application).fetchMoney(openAds.id)
                                            }

                                            if (data.is_advertise_available.checkIsAdAvailable() && data.is_splash_available.checkIsAdAvailable()) {
//                                        Enter when priority F
                                                if (fbModel?.acc_type?.isNoAd() == true && openAds?.acc_type?.isNoAd() == true) {
                                                    startNextIntent()
                                                } else {
                                                    SplashMoneyApp(MyAppClass.application, this@SplashActivity, object : AagalJav {
                                                        override fun onNext() {
                                                            startNextIntent()
                                                        }
                                                    })
                                                }
                                            } else if (data.is_advertise_available.checkIsQurekaAvailable() && data.is_splash_available.checkIsAdAvailable()) {
                                                if (openAds?.acc_type?.isNoAd() == true && fbModel?.acc_type?.isNoAd() == true) {
                                                    startNextIntent()
                                                } else {
                                                    if (fbModel?.acc_type?.isNoAd() == true) {
                                                        LessMoneyBannerAds.callExtraSplashOpen(this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }
                                                        })
                                                    } else {
                                                        InterMoneyAds.extraInterMoney(this@SplashActivity, object : AagalJav {
                                                            override fun onNext() {
                                                                startNextIntent()
                                                            }

                                                        })
                                                    }
                                                }
                                            } else {
                                                startNextIntent()
                                            }
                                        } else {
                                            startNextIntent()
                                        }
                                    }, 1000)
                                }
                            } else {
                                startNextIntent()
                            }

                        }
                    } ?: startNextIntent()
                }
            }

            override fun onFailure(call: Call<GetMoneyModel>, t: Throwable) {
                startNextIntent()
            }
        })
    }


    private fun startNextIntent() {


        Handler(mainLooper).postDelayed({
            if (SharedPrefData.getBooleanValue(Constant.IS_FIRST_TIME)) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
//                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 800) {
            startNextIntent()
        }
    }
    override fun initIntentData() {

    }

}