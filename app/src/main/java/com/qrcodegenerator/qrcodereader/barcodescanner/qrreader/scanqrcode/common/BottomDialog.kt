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

interface OnCustomBottomDialogListener<B> {
    fun getBinding(): B
    fun onDialogBind(binding: B, dialog: BottomSheetDialog)
}

class CustomBottomDialog<B : ViewBinding>(
    private var context: Context,
    private var onCustomDialogListener: OnCustomBottomDialogListener<B>
) {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: B

    init {
        init()
    }

    private fun init() {

        dialog = BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme)
        binding = onCustomDialogListener.getBinding()

        dialog.apply {
            setContentView(binding.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setGravity(Gravity.BOTTOM)
        }

        onCustomDialogListener.onDialogBind(binding, dialog)

    }

    fun getDialog(): Dialog {
        return dialog
    }

    fun getBinding(): B {
        return binding
    }

}