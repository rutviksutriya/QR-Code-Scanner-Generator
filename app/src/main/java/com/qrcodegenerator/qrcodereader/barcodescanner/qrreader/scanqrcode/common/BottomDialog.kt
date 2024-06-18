package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money.OnCustomBottomDialogListener


class CustomBottomDialog<B : ViewBinding>(
    private var co: Context,
    private var dialogListener: OnCustomBottomDialogListener<B>
) {

    private lateinit var dialogBottom: BottomSheetDialog
    private lateinit var bindingView: B

    init {
        init()
    }

    private fun init() {

        dialogBottom = BottomSheetDialog(co, R.style.CustomBottomSheetDialogTheme)
        bindingView = dialogListener.getBinding()

        dialogBottom.apply {
            setContentView(bindingView.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setGravity(Gravity.BOTTOM)
        }

        dialogListener.onDialogBind(bindingView, dialogBottom)

    }

    fun getDialog(): Dialog {
        return dialogBottom
    }

    fun getBinding(): B {
        return bindingView
    }

}