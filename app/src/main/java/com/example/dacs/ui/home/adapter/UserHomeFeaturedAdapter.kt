package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class UserHomeFeaturedAdapter : RecyclerView.Adapter<UserHomeFeaturedAdapter.FeaturedViewHolder>() {

    // Đổi tên thành FeaturedViewHolder cho khỏi đụng hàng
    class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_home_featured, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {}
}