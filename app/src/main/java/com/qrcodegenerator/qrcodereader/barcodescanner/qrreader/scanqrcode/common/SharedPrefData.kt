package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common

import android.content.Context
import android.content.SharedPreferences
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.NewAdPriority
import com.google.gson.Gson
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.MyAppClass

object SharedPrefData {

    const val newAdvertiseObject = "newAdvertiseObject"


    private fun get(): SharedPreferences {
        return MyAppClass.appContext?.getSharedPreferences("Code", Context.MODE_PRIVATE)!!
    }


    fun getString(key: String): String {
        return get().getString(key, "")?: ""
    }


    fun setString(key: String?, value: String?) {
        get().edit().putString(key, value).apply()
    }

    fun getBooleanValue(s: String): Boolean {
        return get().getBoolean(s, false)
    }

    fun setBooleanValue(key: String, isBool: Boolean) {
        get().edit().putBoolean(key, isBool).apply()
    }


    val newAdvertiseModel: NewAdPriority?
        get() {
            try {
                val gson = Gson()
                val json = get().getString(newAdvertiseObject, "")
                return gson.fromJson(json, NewAdPriority::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    fun setNewAdvertiseModel(res: NewAdPriority?): Boolean {
        val gson = Gson()
        val json = gson.toJson(res)
        return get().edit()
            .putString(newAdvertiseObject, json)
            .commit()
    }

}
