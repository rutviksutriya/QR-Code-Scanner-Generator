package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.SystemClock
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter.HistoryDateAdapter
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.CustomDialog
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.OnCustomDialogListener
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.gone
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.visible
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.database.HistoryDatabase
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityScannerHistoryBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.DialogCommonBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.CodeHistoryModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.Data
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.HistoryModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.ScannerHistoryModel
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav

class ScannerHistoryActivity : BaseActivity<ActivityScannerHistoryBinding>() {
    private var adapter: HistoryDateAdapter? = null
    private var scanHistory: ArrayList<ScannerHistoryModel> ?=  ArrayList()
    private var codeHistory: ArrayList<CodeHistoryModel> ? = ArrayList()
    private var mLastClickTime: Long = 0

    var historyDatabase: HistoryDatabase?= null


    override fun getViewBinding(): ActivityScannerHistoryBinding {
        return ActivityScannerHistoryBinding.inflate(layoutInflater)
    }

    override fun initView() {

        historyDatabase =  HistoryDatabase(this@ScannerHistoryActivity)
        binding.apply {
            toolBarView.apply {
                tvTitle.text = getString(R.string.scanner_history)
                ivBack.setOnClickListener {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return@setOnClickListener
                    }
                    mLastClickTime = SystemClock.elapsedRealtime()
                    InterMoneyAds.showBackInterMoney(this@ScannerHistoryActivity, 800)
                }
            }

            val typeface = ResourcesCompat.getFont(this@ScannerHistoryActivity, R.font.librefranklin_bold)
            val typeface1 = ResourcesCompat.getFont(this@ScannerHistoryActivity, R.font.librefranklin_regular)
            tvGeneratedHistory.setOnClickListener {
                tvGeneratedHistory.typeface = typeface
                tvScannerHistory.typeface = typeface1
                tvGeneratedHistory.setBackgroundResource(R.drawable.btn_orange_n)
                tvGeneratedHistory.setTextColor(resources.getColor(R.color.white, null))
                tvScannerHistory.setBackgroundResource(0)
                tvScannerHistory.setTextColor(resources.getColor(R.color.text, null))
                setUpCodeHistory()
            }

            tvScannerHistory.setOnClickListener {
                tvScannerHistory.typeface = typeface
                tvGeneratedHistory.typeface = typeface1
                tvScannerHistory.setBackgroundResource(R.drawable.btn_orange_n)
                tvScannerHistory.setTextColor(resources.getColor(R.color.white, null))
                tvGeneratedHistory.setBackgroundResource(0)
                tvGeneratedHistory.setTextColor(resources.getColor(R.color.text, null))
                setUpDataScannerHistory()
            }
            setUpCodeHistory()


        }
        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@ScannerHistoryActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@ScannerHistoryActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }

    private fun setUpCodeHistory(){
            binding.apply {
                rvScannerHistory.layoutManager = LinearLayoutManager(this@ScannerHistoryActivity)
                adapter =
                    HistoryDateAdapter(this@ScannerHistoryActivity) { details, isMore, btn ->
                        if (isMore) {
                            val popupMenu = PopupMenu(this@ScannerHistoryActivity, btn)
                            popupMenu.menuInflater.inflate(R.menu.more_menu, popupMenu.menu)
                            popupMenu.setOnMenuItemClickListener { item ->
                                when (item.itemId) {
                                    R.id.tvDetail ->

                                        if (isOnline()) {
                                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {

                                            }else {
                                                mLastClickTime = SystemClock.elapsedRealtime()
                                                InterMoneyAds.showForwardCountInterMoney(
                                                    this@ScannerHistoryActivity,
                                                    101,
                                                    object :
                                                        AagalJav {
                                                        override fun onNext() {
                                                            startActivity(
                                                                Intent(
                                                                    this@ScannerHistoryActivity,
                                                                    CodeShowActivity::class.java
                                                                )
                                                                    .putExtra("isHistory", true)
                                                                    .putExtra("details", details)
                                                            )
                                                        }
                                                    })
                                            }
                                        }else{
                                            showNoInternet {
                                                startActivity(Intent(this@ScannerHistoryActivity, CodeShowActivity::class.java)
                                                    .putExtra("isHistory", true)
                                                    .putExtra("details", details))
                                            }
                                        }






                                    R.id.tvShare -> {
                                        val bitmap: Bitmap? = details.imgBtmp?.size?.let {
                                            BitmapFactory.decodeByteArray(
                                                details.imgBtmp, 0,
                                                it
                                            )
                                        }

                                        bitmap?.also { bit ->
                                            val imageUri = Constant.saveBitmapToFile(bit, this@ScannerHistoryActivity)
                                            val shareIntent = Intent()
                                            shareIntent.setAction(Intent.ACTION_SEND)
                                            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                                            shareIntent.setType("image/*")
                                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                            startActivity(
                                                Intent.createChooser(
                                                    shareIntent,
                                                    "QR Code"
                                                )
                                            )

                                        } ?: run {
                                            Toast.makeText(
                                                this@ScannerHistoryActivity,
                                                "Something want wrong",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    R.id.tvDelete -> {
                                        codeHistory?.also {list ->

                                            showNotValidDialog(details, true, list, ArrayList())
                                        }
                                    }
                                }
                                true
                            }

                            popupMenu.show()
                        } else {

                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                                 return@HistoryDateAdapter
                            }
                                mLastClickTime = SystemClock.elapsedRealtime()
                                InterMoneyAds.showForwardCountInterMoney(
                                    this@ScannerHistoryActivity,
                                    101,
                                    object :
                                        AagalJav {
                                        override fun onNext() {
                                            startActivity(
                                                Intent(
                                                    this@ScannerHistoryActivity,
                                                    CodeShowActivity::class.java
                                                )
                                                    .putExtra("isHistory", true)
                                                    .putExtra("details", details)
                                            )
                                        }
                                    })

                        }
                    }
                rvScannerHistory.adapter = adapter
                llNoData.visibility = View.VISIBLE

                getCodeHistory()
            }
    }

    fun getCodeHistory(){
        codeHistory?.clear()
        codeHistory = historyDatabase?.getCodeHistoryDao()?.getAllHistory() as ArrayList<CodeHistoryModel>?
        val  historyModel = ArrayList<HistoryModel> ()
        val  dataList = ArrayList<String> ()
        codeHistory?.also { code ->
            for (i in 0 until code.size){
                if (!dataList.contains( code[i].date)){
                    dataList.add(code[i].date)
                }
            }
            dataList.reverse()
            dataList.also { data ->
                for (i in 0 until data.size){
                    val  arrayList = ArrayList<Data> ()
                    for (j in 0 until code.size) {
                        if (data[i].equals(code[j].date, true))
                            arrayList.add(Data(code[j].id, code[j].title,
                                code[j].value, code[j].time, code[j].imgBtmp))
                    }
                    arrayList.reverse()
                    historyModel.add(HistoryModel (data[i],arrayList))
                }
            }
        }
        if(historyModel.size == 0) {
            binding.llNoData.visibility = View.VISIBLE
            adapter?.setDataHis(historyModel)
        }else{
            binding.llNoData.visibility = View.GONE
            adapter?.setDataHis(historyModel) }
    }

    fun setUpDataScannerHistory() {

        binding.apply {
            scanHistory = historyDatabase?.getScanHistoryDao()?.getAllHistory() as ArrayList<ScannerHistoryModel>?
            val historyModel = ArrayList<HistoryModel>()
            val dataList = ArrayList<String>()

            scanHistory?.also { scan ->
                for (i in 0 until scan.size) {
                    if (!dataList.contains(scan[i].date)) {
                        dataList.add(scan[i].date)
                    }
                }
                dataList.reverse()

                for (i in 0 until dataList.size) {
                    val data = ArrayList<Data>()
                    for (j in 0 until scan.size) {
                        if (dataList[i].equals(scan[j].date, true))
                            data.add(
                                Data(
                                    scan[j].id, scan[j].title,
                                    scan[j].value, scan[j].time, null
                                )
                            )
                    }
                    data.reverse()
                    historyModel.add(HistoryModel(dataList[i], data))
                }
            }

            rvScannerHistory.layoutManager =
                LinearLayoutManager(this@ScannerHistoryActivity)
            adapter = HistoryDateAdapter(this@ScannerHistoryActivity) { details, isMore, btn ->
                    if (isMore) {
                        val popupMenu = PopupMenu(this@ScannerHistoryActivity, btn)
                        popupMenu.menuInflater.inflate(R.menu.more_menu1, popupMenu.menu)
                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.tvDetail ->
                                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {

                                    }else {
                                        mLastClickTime = SystemClock.elapsedRealtime()
                                        InterMoneyAds.showForwardCountInterMoney(
                                            this@ScannerHistoryActivity,
                                            101,
                                            object :
                                                AagalJav {
                                                override fun onNext() {
                                                    startActivity(
                                                        Intent(
                                                            this@ScannerHistoryActivity,
                                                            ShareDataActivity::class.java
                                                        ).putExtra("result", details.value)
                                                            .putExtra("isHistory", true)
                                                    )
                                                }
                                            })
                                    }
                                    R.id.tvDelete -> {
                                    scanHistory?.also { list ->
                                        showNotValidDialog(details, false, ArrayList(), list)
                                    }
                                }
                            }
                            true
                        }
                        popupMenu.show()
                    } else {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return@HistoryDateAdapter
                        }
                        mLastClickTime = SystemClock.elapsedRealtime()
                        InterMoneyAds.showForwardCountInterMoney(
                            this@ScannerHistoryActivity,
                            101,
                            object :
                                AagalJav {
                                override fun onNext() {
                                    startActivity(
                                        Intent(
                                            this@ScannerHistoryActivity,
                                            ShareDataActivity::class.java
                                        ).putExtra("result", details.value).putExtra("isHistory", true)
                                    )
                                }
                            })
                    }
                }

            rvScannerHistory.adapter = adapter
            llNoData.visibility = View.VISIBLE

            if (historyModel.size == 0) {
                llNoData.visibility = View.VISIBLE
                adapter?.setDataHis(historyModel)
            } else {
                llNoData.visibility = View.GONE
                adapter?.setDataHis(historyModel)
            }

        }
    }

    private fun showNotValidDialog(details: Data, b: Boolean, code: ArrayList<CodeHistoryModel>, scan: ArrayList<ScannerHistoryModel>) {
        CustomDialog(this@ScannerHistoryActivity, object : OnCustomDialogListener<DialogCommonBinding> {
            override fun getBinding(): DialogCommonBinding {
                return DialogCommonBinding.inflate(layoutInflater)
            }

            override fun onDialogBind(binding: DialogCommonBinding, dialog: Dialog) {
                binding.apply {
                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                    tvDialogOk.text = getString(R.string.delete)
                    tvDialogDesc.text = getString(R.string.are_you_sure_you_want_to_delete)
                    tvDialogOk.gone()
                    llTow.visible()
                    tvDialogCancelEx.setOnClickListener {
                        dialog.dismiss()
                    }
                    tvDialogOkEx.setOnClickListener{
                        dialog.dismiss()
                        if (b){
                            for (j in 0 until code.size) {
                                if (code[j].id == details.id) {
                                    historyDatabase?.getCodeHistoryDao()
                                        ?.deleteHistory(code[j])
                                    break
                                }
                            }
                            getCodeHistory()
                        }else{
                            for (j in 0 until scan.size) {
                                if (scan[j].id == details.id) {
                                    historyDatabase?.getScanHistoryDao()
                                        ?.deleteHistory(scan[j])
                                    break
                                }
                            }
                            setUpDataScannerHistory()
                        }
                    }
                }
            }
        }).getDialog().show()
    }


    override fun initIntentData() {
    }

}