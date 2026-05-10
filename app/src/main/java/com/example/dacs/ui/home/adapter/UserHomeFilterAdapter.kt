package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class UserHomeFilterAdapter : RecyclerView.Adapter<UserHomeFilterAdapter.FilterViewHolder>() {

    // Lớp ViewHolder trống trơn y như ReviewsAdapter
    class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_home_filter, parent, false)
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {}
}