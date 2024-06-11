package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.ScannerHistoryModel


@Dao
interface ScannerHistoryDao {

    @Insert()
    fun addHistory(details: ScannerHistoryModel)

    @Query("SELECT * FROM scannerHistory")
     fun getAllHistory() : List<ScannerHistoryModel>

    @Update
    fun updateHistory(details: ScannerHistoryModel)

    @Delete
    fun deleteHistory(details: ScannerHistoryModel)
}