package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.activity

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.SharedPrefData
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.invisible
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isOnline
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isValidationEmptyNew
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.visible
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ActivityOnBoardingBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.LessMoneyBannerAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.InterMoneyAds
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.AagalJav

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {
    val layouts = intArrayOf(R.layout.screen1, R.layout.screen2, R.layout.screen3)

    private var mLastClickTime: Long = 0
    override fun getViewBinding(): ActivityOnBoardingBinding {
        return  ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {

            viewPager.setAdapter(MyViewPagerAdapter(this@OnBoardingActivity, layouts))
            viewPager.addOnPageChangeListener(pageChangeListener)
            tvPrev.invisible()

            tvGetStart.setOnClickListener {
                if (isOnline()) {
                    launchHomeScreen()
                } else {
                    showNoInternet {
                        launchHomeScreen()
                    }
                }
            }

            ivNext.setOnClickListener {
                val nextIndex: Int = viewPager.currentItem + 1
                if (nextIndex < layouts.size) {
                    viewPager.setCurrentItem(nextIndex)
                }
            }
            tvPrev.setOnClickListener {
                val nextIndex: Int = viewPager.currentItem - 1
                if (nextIndex > -1) {
                    viewPager.setCurrentItem(nextIndex)
                }
            }
        }

        if (!SharedPrefData.getBooleanValue(Constant.isOnResume)) {
            binding.apply {
                LessMoneyBannerAds.loadVachaliAds(
                    this@OnBoardingActivity,
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
                    this@OnBoardingActivity,
                    llVachaliMoneyView.root,
                    llVachaliMoneyView.nativeNaniCurtain.nativeNaniCurtain,
                    llVachaliMoneyView.moneyContainer,
                    llVachaliMoneyView.flPlaceView,
                    llVachaliMoneyView.llNaniCurtain
                )
            }
        }
    }

    private var pageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                binding.apply {
                    if (layouts.size - 1 == position) {
                        tvGetStart.visible()
                        ivNext.invisible()
                    } else {
                        ivNext.visible()
                        tvGetStart.invisible()
                    }

                    if (position > 0) {
                        tvPrev.visible()
                    } else {
                        tvPrev.invisible()
                    }


                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        }


    private fun launchHomeScreen() {
        if (isOnline()) {
            val startScreenAvailableStrBtc: String = SharedPrefData.getString(Constant.IS_INFO)
            if (!startScreenAvailableStrBtc.isValidationEmptyNew() && startScreenAvailableStrBtc.equals(
                    "1", ignoreCase = true
                )
            ) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                InterMoneyAds.showEveryTimeInterMoney(this@OnBoardingActivity, object :
                    AagalJav {
                    override fun onNext() {
                        SharedPrefData.setBooleanValue(Constant.IS_FIRST_TIME, true)
                        startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
                        finish()
                    }
                })

            }else{
                SharedPrefData.setBooleanValue(Constant.IS_FIRST_TIME, true)
                startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
                finish()
            }

        }else{
            showNoInternet {
                SharedPrefData.setBooleanValue(Constant.IS_FIRST_TIME, true)
                startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
                finish()
            }
        }

    }

    class MyViewPagerAdapter(context: Context, private val layouts: IntArray) :
        PagerAdapter() {

        private val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = layoutInflater.inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            val view = obj as View
            container.removeView(view)
        }
    }


    override fun initIntentData() {

    }

}