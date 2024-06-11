package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Button
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R

class CodeButton : Button {


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    private fun init(attrs: AttributeSet?) {
        attrs.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
            val fontName = a.getString(R.styleable.CustomButton_font_btn)

            fontName?.let {
                try {
                    val myTypeface = Typeface.createFromAsset(context.assets, "font/$fontName")
                    setTypeface(myTypeface)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            a.recycle()
        }
    }
}
