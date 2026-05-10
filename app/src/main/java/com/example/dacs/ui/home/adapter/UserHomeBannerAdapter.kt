package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class UserHomeBannerAdapter : RecyclerView.Adapter<UserHomeBannerAdapter.BannerViewHolder>() {

    // Lớp ViewHolder trống trơn, đổi tên thành BannerViewHolder cho khỏi đụng hàng
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        // Bơm đúng file XML của Banner vào
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_home_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // Tạm thời để trống
    }
}