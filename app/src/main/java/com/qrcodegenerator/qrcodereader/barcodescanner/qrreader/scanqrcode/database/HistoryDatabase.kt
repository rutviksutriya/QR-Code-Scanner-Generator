package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.CodeHistoryModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.ScannerHistoryModel


@Database(entities = [ ScannerHistoryModel::class, CodeHistoryModel::class ], version = 1)

abstract class HistoryDatabase : RoomDatabase() {
    abstract fun getScanHistoryDao(): ScannerHistoryDao
    abstract fun getCodeHistoryDao(): CodeHistoryDao

    companion object {

//        val migration_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE 'details' ADD COLUMN 'state' TEXT DEFAULT any")
//            }
//        }

        @Volatile
        private var instance: HistoryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java, "details")
                .allowMainThreadQueries()
            //    .addMigrations(migration_1_2)
                .build()
    }

}