package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface OnCustomDialogListener<B> {
    fun getBinding(): B
    fun onDialogBind(binding: B, dialog: Dialog)
}

class CustomDialog<B : ViewBinding>(
    private var context: Context,
    private var onCustomDialogListener: OnCustomDialogListener<B>,
) {

    private lateinit var dialog: Dialog
    private lateinit var binding: B

    init {
        init()
    }

    private fun init() {

        dialog = Dialog(context)
        binding = onCustomDialogListener.getBinding()

        dialog.apply {
            setContentView(binding.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setGravity(Gravity.CENTER)
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