package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "scannerHistory")
data class ScannerHistoryModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val title: String = "",
    val date: String = "",
    val value: String = "",
    val time: String = "",
    var imgBtmp: ByteArray? = null

) : Serializable

