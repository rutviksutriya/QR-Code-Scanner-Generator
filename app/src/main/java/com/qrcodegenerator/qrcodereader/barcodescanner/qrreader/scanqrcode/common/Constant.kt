package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.BuildConfig
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.DialCodeModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object Constant {
    var DEVELOPER_NAME = "Color Call Themes"
    var tiny = "https://tinyurl.com/mr2c3y4b"
    var PRIVACY_LINK = BuildConfig.PP
    const val IS_FIRST_TIME = "isFirstTime"
    var dialogTrue = true
    val App_VERSION_CODE: String = "app_versionCode"
    val USER_DIALOG_UPDATE_TEXT: String = "USER_DIALOG_UPDATE_TEXT"

    var BACK_PRESS_COUNT = "is_back_press_count"
    var isOnResume = "isOnResume"
    const val IS_INFO: String = "IS_INFO"

    var NORMAL_AD_COUNT = "is_normal_ad_count"
    var isNotificationClicked: Boolean = false
    var predChampURL = ""
    var qurekaURL = ""
    var gameURL = ""
    var redirectURL = ""
    var isShowMilliseconds = false
    var backCount = 1
    var isBackAdCount = 0
    var isNormalAdCount = 0
    var forwardCount = 1
    val is_loader: String = "is_loader"
    val IS_QUREKA: String = "IS_QUREKA"
    var isInitialize: Boolean = false
    var isInterShow: Boolean = false
    var adsInterval = 0
    var issplashshowed = false
    var isNotRedirect = false
    val strPriority: String = "strPriority"
    var appOpenAdsId: String = ""
    var adsPriority: String = ""
    val isAdAvailable: String = "is_advertise_available"


    const val fbNativeAd = "fb_native_ad"
    const val gNativeAd = "g_native_ad"
    const val fbSmallNativeBanner = "fb_small_native_banner_ad"
    const val gBanner = "g_banner_ad"
    const val fbNativeBannerAd = "fb_native_banner_ad"
    const val gNativeBannerAd = "g_native_banner_ad"
    const val fbInterstitialAd = "fb_interstitial_ad"
    const val gInterstitialAd = "g_interstitial_ad"
    const val gAppOpenAds = "g_app_open_ad"



    fun getCountryCodeList(activity: Activity): ArrayList<DialCodeModel> {
        val formList = ArrayList<DialCodeModel>()
        val code = ArrayList<String>()
        try {
            val obj = JSONObject(getJsonDataFromAssets(activity))
            val mJarry: JSONArray = obj.getJSONArray("countries")
            for (i in 0 until mJarry.length()) {
                val jsonObject = mJarry.getJSONObject(i)
                val formulaValue = jsonObject.getString("dial")
                val urlValue = jsonObject.getString("name")
                val codeValue = jsonObject.getString("code")
                val flagValue = jsonObject.getString("flag")
                formList.add(DialCodeModel(formulaValue, urlValue, codeValue, flagValue))
                code.add("" + formulaValue)
            }
            return formList
        } catch (e: JSONException) {
            e.printStackTrace()
            return formList
        }
    }


    private fun getJsonDataFromAssets(activity: Activity): String? {
        val json: String? = try {
            val stream = activity.assets.open("countrycode.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }




    fun saveBitmapToFile(bitmap: Bitmap, context: Context): Uri {
        val imagesFolder  = File(context.getExternalFilesDir(null), "images")
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs()
        }
        val imageFile = File(imagesFolder, "shared_image.png")
        val outputStream: FileOutputStream
        try {
            outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return FileProvider.getUriForFile(context, "your.package.name.fileprovider", imageFile)
    }

}