package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter

import android.app.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ItemScanHistoryBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.Data


class HistoryListAdapter(private val activity: Activity, private var onItemClicked: ((details: Data, isMore: Boolean, btn : View) -> Unit))
    : RecyclerView.Adapter<HistoryListAdapter.MyViewHolder>() {
    private var mList = emptyList<Data>()


    fun setData(user: List<Data>) {
        this.mList = user
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemScanHistoryBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemViewB.apply {

            val model = mList[position]

            tvName.text = model.title
            tvTime.text = model.time
            tvValue.text = model.value

            card.setOnClickListener {
                onItemClicked(model, false,ivMore)
            }
            ivMore.setOnClickListener {
                onItemClicked(model, true, ivMore)
            }
        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(var itemViewB: ItemScanHistoryBinding) :
        RecyclerView.ViewHolder(itemViewB.root)

}