package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common


import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.GetMoneyModel

import retrofit2.Call

import retrofit2.http.GET

interface GetDataService {

    @GET("getAppAdChange?app_id=1")
    fun getExample(): Call<GetMoneyModel>



}