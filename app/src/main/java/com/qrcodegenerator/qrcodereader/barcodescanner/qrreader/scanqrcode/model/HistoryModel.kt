package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model


import java.io.Serializable

data class HistoryModel(


    val date: String = "",

    val data: ArrayList<Data>

) : Serializable

data class Data(
    var id: Int,
    val title: String = "",
    val value: String = "",
    val time: String = "",
    val imgBtmp: ByteArray?,
) : Serializable