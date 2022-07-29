package com.example.twintpoc.view.holidays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twintpoc.data.dto.HolidayDto
import com.example.twintpoc.databinding.HolidayItemBinding


class HolidayListAdapter(private var mList: List<HolidayDto>) : RecyclerView.Adapter<HolidayListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HolidayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.binding.tvHolidayName.text = item.name
        holder.binding.tvHolidayDate.text = item.observedDate
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(data: List<HolidayDto>) {
        mList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: HolidayItemBinding) : RecyclerView.ViewHolder(binding.root)
}