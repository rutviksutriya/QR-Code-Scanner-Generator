package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ItemScanHistoryDateBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.Data
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.HistoryModel


class HistoryDateAdapter(val activity: Activity, private var onItemClicked: ((details: Data, isDelete: Boolean, btn : View ) -> Unit))
    : RecyclerView.Adapter<HistoryDateAdapter.MyViewHolder>() {
    private var mList = emptyList<HistoryModel>()

    fun setDataHis(user: ArrayList<HistoryModel>) {
        this.mList = user
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemScanHistoryDateBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemViewB.apply {
                val model = mList[position]
                tvDate.text = model.date

                rvScanHistory.layoutManager = LinearLayoutManager(activity)
                val adapter = HistoryListAdapter(activity) { details, isDelete, btn ->
                    onItemClicked(details, isDelete, btn)
                }

                rvScanHistory.adapter = adapter
                adapter.setData(model.data)
            }
        }


        override fun getItemCount(): Int {
            return mList.size
        }

    inner class MyViewHolder(var itemViewB: ItemScanHistoryDateBinding) :
        RecyclerView.ViewHolder(itemViewB.root)

}