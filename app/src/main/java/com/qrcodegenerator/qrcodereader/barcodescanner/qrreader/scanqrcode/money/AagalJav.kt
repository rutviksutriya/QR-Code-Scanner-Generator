package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money

import android.app.Dialog
import com.google.android.material.bottomsheet.BottomSheetDialog

interface AagalJav {
    fun onNext()
}

interface OnCustomDialogListener<B> {
    fun getBinding(): B
    fun onDialogBind(binding: B, dialog: Dialog)
}

interface OnCustomBottomDialogListener<B> {
    fun getBinding(): B
    fun onDialogBind(binding: B, dialog: BottomSheetDialog)
}
