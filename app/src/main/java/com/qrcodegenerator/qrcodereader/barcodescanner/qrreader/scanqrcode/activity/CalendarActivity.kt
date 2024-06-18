package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.SystemClock
import android.text.TextUtils
import android.text.format.DateFormat
import android.widget.Toast
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityCalendarBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds.showBackInterMoney
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class CalendarActivity : BaseActivity<ActivityCalendarBinding>() {
    private var mLastClickTime: Long = 0

    private var getDay1 = 0
    private var getMonth1: Int = 0
    private var getYear1: Int = 0
    private var getHour1: Int = 0
    private var getMinute1: Int = 0

    private var getDay2 = 0
    private var getMonth2: Int = 0
    private var getYear2: Int = 0
    private var getHour2: Int = 0
    private var getMinute2: Int = 0


    override fun getViewBinding(): ActivityCalendarBinding {
        return ActivityCalendarBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {

            toolBarView.tvTitle.text = "Calendar"
            toolBarView.ivBack.setOnClickListener {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
                mLastClickTime = SystemClock.elapsedRealtime()
                showBackInterMoney(this@CalendarActivity, 800)
            }
            val calendar: Calendar = Calendar.getInstance()
            getDay1 = calendar.get(Calendar.DAY_OF_MONTH)
            getMonth1 = calendar.get(Calendar.MONTH)
            getYear1 = calendar.get(Calendar.YEAR)
            getHour1 = calendar.get(Calendar.HOUR)
            getMinute1 = calendar.get(Calendar.MINUTE)
            getDay2 = calendar.get(Calendar.DAY_OF_MONTH)
            getMonth2 = calendar.get(Calendar.MONTH)
            getYear2 = calendar.get(Calendar.YEAR)
            getHour2 = calendar.get(Calendar.HOUR)
            getMinute2 = calendar.get(Calendar.MINUTE)

            iv1.setOnClickListener { tvStartDate.performClick() }
            tvStartDate.setOnClickListener {
                val datePickerDialog =
                    DatePickerDialog(
                        this@CalendarActivity,
                        { _, i, i2, i3 ->
                            getDay1 = i3
                            getYear1 = i
                            getMonth1 = i2 + 1


                            val timePickerDialog = TimePickerDialog(
                                this@CalendarActivity,
                                { _, i, i2 ->
                                    getHour1 = i
                                    getMinute1 = i2



                                    setStartTime(getDay1, getYear1, getMonth1, getHour1, getMinute1)
                                },
                                getHour1,
                                getMinute1,
                                DateFormat.is24HourFormat(this@CalendarActivity)
                            )
                            timePickerDialog.show()

                        },
                        getYear1,
                        getMonth1,
                        getDay1
                    )
                datePickerDialog.show()

            }


            iv2.setOnClickListener { tvEndDate.performClick() }
            tvEndDate.setOnClickListener {

                val datePickerDialog1 =
                    DatePickerDialog(
                        this@CalendarActivity,
                        { _, i, i2, i3 ->
                            getDay2 = i3
                            getYear2 = i
                            getMonth2 = i2 + 1

                            val timePickerDialog1 = TimePickerDialog(
                                this@CalendarActivity,
                                { _, i, i2 ->
                                    getHour2 = i
                                    getMinute2 = i2

                                    setEndTime(getDay2, getYear2, getMonth2, getHour2, getMinute2)
                                },
                                getHour2,
                                getMinute2,
                                DateFormat.is24HourFormat(this@CalendarActivity)
                            )
                            timePickerDialog1.show()

                        },
                        getYear2,
                        getMonth2,
                        getDay2
                    )
                datePickerDialog1.show()
            }

            setStartTime(getDay1, getYear1, getMonth1 + 1, getHour1, getMinute1)
            setEndTime(getDay2, getYear2, getMonth2 + 1, getHour2, getMinute2)

            tvGeneratedCode.setOnClickListener {

                if (TextUtils.isEmpty(editText1.text.toString())) {

                    Toast.makeText(
                        this@CalendarActivity,
                        "Please enter company name",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(editText2.text.toString())) {
                    Toast.makeText(
                        this@CalendarActivity,
                        "Please enter location",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(editText3.text.toString())) {
                    Toast.makeText(
                        this@CalendarActivity,
                        "Please enter description ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if ( !check()) {
                    Toast.makeText(
                        this@CalendarActivity,
                        "Please select before start date and time",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    val codeText = "Company:- " + editText1.text.toString() +
                            "\nLocation:- " + editText2.text.toString() +
                            "\nStart Date & Time:- " + tvStartDate.text.toString() +
                            "\nEnd Date & Time:- " + tvEndDate.text.toString() +
                            "\nDescription:- " + editText3.text.toString()



                    if (isOnline()) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return@setOnClickListener
                        }
                        mLastClickTime = SystemClock.elapsedRealtime()
                        InterMoneyAds.showForwardCountInterMoney(this@CalendarActivity, 101, object :
                            AagalJav {
                            override fun onNext() {
                                startActivity(
                                    Intent(this@CalendarActivity, CodeShowActivity::class.java)
                                        .putExtra("title", "Calendar")
                                        .putExtra("codeText", codeText)
                                )

                            }
                        })
                    }else{
                        showNoInternet {
                            startActivity(
                                Intent(this@CalendarActivity, CodeShowActivity::class.java)
                                    .putExtra("title", "Calendar")
                                    .putExtra("codeText", codeText)
                            )

                        }
                    }





                }
            }
        }

        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@CalendarActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }


    private fun check(): Boolean{
        val dateFormat = SimpleDateFormat(
            "MMM dd yyyy | hh:mm aa"
        )
        var convertedDate: Date? = Date()
        var convertedDate2 = Date()
        try {
            convertedDate = dateFormat.parse(binding.tvStartDate.text.toString())
            convertedDate2 = dateFormat.parse(binding.tvEndDate.text.toString())
            if (convertedDate2.after(convertedDate)) {
                return  true
            } else {
                return false
            }
        } catch (e: ParseException) {
            return false
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@CalendarActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }


    private fun setEndTime(day: Int, year: Int, month: Int, hours: Int, min: Int) {
        val format: String
        val originalFormat = SimpleDateFormat("dd MM yyyy")
        val targetFormat = SimpleDateFormat("MMM dd yyyy")
        val date: Date
        try {
            date =
                originalFormat.parse("$day $month $year")
            var hour = hours
            if (hour == 0) {
                hour += 12
                format = "AM"
            } else if (hour == 12) {
                format = "PM"
            } else if (hour > 12) {
                hour -= 12
                format = "PM"
            } else {
                format = "AM"
            }
            binding.tvEndDate.text =
                StringBuilder().append(targetFormat.format(date)).append(" | ").
                append(String.format("%02d:%02d", hour, min)  )

                    .append(" ").append(format)
        } catch (ex: ParseException) {

        }
    }

    private fun setStartTime(day: Int, year: Int, month: Int, hours: Int, min: Int) {
        val format: String
        val originalFormat = SimpleDateFormat("dd MM yyyy")
        val targetFormat = SimpleDateFormat("MMM dd yyyy")
        val date: Date

        try {
            date =
                originalFormat.parse("$day $month $year")
            var hour = hours
            if (hour == 0) {
                hour += 12
                format = "AM"
            } else if (hour == 12) {
                format = "PM"
            } else if (hour > 12) {
                hour -= 12
                format = "PM"
            } else {
                format = "AM"
            }
            binding.tvStartDate.text =
                StringBuilder().append(targetFormat.format(date)).append(" | ").
                append(String.format("%02d:%02d", hour, min)  )

                    .append(" ").append(format)
        } catch (ex: ParseException) {

        }
    }

    override fun initIntentData() {

    }
}