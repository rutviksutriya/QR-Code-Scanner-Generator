package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ItemQrTypeBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.QRCodeTypeModel


class QrCodeTypeAdapter(
    private val activity: Activity,
    private val arrayList: ArrayList<QRCodeTypeModel>,
    private val callback: Callback
) : RecyclerView.Adapter<QrCodeTypeAdapter.MyViewHolder>() {

    interface Callback {
        fun callBack(model: QRCodeTypeModel, pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemQrTypeBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemViewB.apply {

            val model = arrayList[position]
            tvTitle.text = model.name
            image.setImageResource(model.icon)

            card.setOnClickListener {
                callback.callBack(model, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyViewHolder(var itemViewB: ItemQrTypeBinding) :
        RecyclerView.ViewHolder(itemViewB.root)

}