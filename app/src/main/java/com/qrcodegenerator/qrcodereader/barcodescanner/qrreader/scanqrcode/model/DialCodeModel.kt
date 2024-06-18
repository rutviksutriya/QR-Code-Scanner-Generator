package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model

import java.io.Serializable

data class DialCodeModel (
    val dial: String = "",
    val name: String = "",
    val code: String = "",
    val flag: String = ""

) : Serializable {

}