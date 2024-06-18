package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ItemDialCodeListBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.databinding.ItemDialCodeTextBinding
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.DialCodeModel

class DialCodeAdapter(private var activity: Activity, private var list: ArrayList<DialCodeModel>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemDialCodeTextBinding.inflate(inflater, parent, false)
        binding.tvCountry.text = list[position].dial

        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemDialCodeListBinding.inflate(inflater, parent, false)
        val data = list[position].dial + " " + "(" + list[position].name + ")"
        binding.tvCountry.text = data
        binding.tvCountry.isSelected = true
        return binding.root
    }
}