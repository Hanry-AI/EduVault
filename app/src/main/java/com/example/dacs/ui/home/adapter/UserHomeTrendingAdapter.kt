package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class UserHomeTrendingAdapter : RecyclerView.Adapter<UserHomeTrendingAdapter.TrendingViewHolder>() {

    // Đổi tên thành TrendingViewHolder cho khỏi đụng hàng, để trống phần ánh xạ
    class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        // Bơm đúng file XML của khối Trending vào
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_home_trending, parent, false)
        return TrendingViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        // Tạm thời để trống
    }
}