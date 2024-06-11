package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.CodeHistoryModel


@Dao
interface CodeHistoryDao {

    @Insert()
    fun addHistory(details: CodeHistoryModel)

    @Query("SELECT * FROM codeHistory")
     fun getAllHistory() : List<CodeHistoryModel>

    @Update
    fun updateHistory(details: CodeHistoryModel)

    @Delete
    fun deleteHistory(details: CodeHistoryModel)
}