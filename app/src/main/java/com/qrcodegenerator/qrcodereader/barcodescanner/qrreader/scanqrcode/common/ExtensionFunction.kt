package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import java.net.URL


fun String.checkIsAdAvailable(): Boolean {
    return isCheckNotEmpty() && removeSpace().equals("1", true)
}

fun String.removeSpace(): String {
    return replace("\\s".toRegex(), "")
}
fun String.isNoAd(): Boolean {
    return isCheckNotEmpty() && removeSpace().equals("0", true)
}

fun String.isValidUrl(): Boolean {
    val value = removeSpace()
    return !isNullOrEmpty() && isNotBlank() && value != "null" && !value.equals(
        "NA",
        ignoreCase = true
    ) && !value.equals("N/A", ignoreCase = true) && URLUtil.isValidUrl(value)

}

fun Activity.shareQurekaUrl(url: String) {
    if (url.isValidUrl()) {
        try {
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(this, Uri.parse(url))
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(
                this,
                getString(R.string.no_app_found),
                Toast.LENGTH_LONG
            ).show()
        }
    } else {
        Toast.makeText(
            this,
            getString(R.string.url_not_valid),
            Toast.LENGTH_SHORT
        ).show()
    }
}



fun String.isValidationEmptyNew(): Boolean {
    val value = removeSpace()
    return value.equals(
        "", ignoreCase = true
    ) || value.isEmpty() || value.equals("null", ignoreCase = true)
}

fun String.checkIsQurekaAvailable(): Boolean {
    return isCheckNotEmpty() && removeSpace().equals("2", true)
}
fun isValidationUrl(value: String?): Boolean {
    return try {
        if (isValidEmpty(value)) {
            false
        } else {
            URL(value).toURI()
            true
        }
    } catch (e: Exception) {
        false
    }
}

fun isValidEmpty(value: String?): Boolean {
    return value.isNullOrEmpty() || value.equals(
        "null",
        ignoreCase = true
    ) || value.equals("", ignoreCase = true) || (value.isEmpty())
}
fun String.isCheckNotEmpty(): Boolean {
    val value = removeSpace()
    return !isNullOrEmpty() && isNotBlank() && value != "null" && !value.equals("NA", ignoreCase = true) && !value.equals("N/A", ignoreCase = true)
}

fun String.isEmptyText(): Boolean {
    val value = removeSpace()
    return value.equals(
        "", ignoreCase = true
    ) || value.isEmpty() || value.equals("null", ignoreCase = true)
}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun View.gone() {
    this.visibility = View.GONE
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities?.let {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }

                else -> {
                    return false
                }
            }
        }

    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        activeNetworkInfo?.let {
            if ( it.isConnected) {
                return true
            }
        }

    }
    return false
}



@SuppressLint("WrongConstant")
fun Context.openUrl(url: String) {
    if (URLUtil.isValidUrl(url)){
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse(url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val activityInfo = intent.resolveActivityInfo(packageManager, intent.flags)
            activityInfo?.let {
                try {
                    if (activityInfo.exported) {
                        ActivityCompat.startActivity(this, intent, null)
                    }
                }catch (e : Exception){
                    Toast.makeText(
                        this,
                        this.getString(R.string.no_app_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                this.getString(R.string.no_app_found),
                Toast.LENGTH_SHORT
            ).show()

        }
    }else{
        Toast.makeText(this,  this.getString(R.string.url_not_valid), Toast.LENGTH_SHORT).show()
    }


}

