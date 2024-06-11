package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R

class CodeTextView : TextView {

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
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
        attrs?.let {
            setLineSpacing(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 5.0f, resources.displayMetrics
                ), 1.0f
            )
            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
            val fontName = a.getString(R.styleable.CustomTextView_font_text)

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
