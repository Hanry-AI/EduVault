package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class UserHomeRecentAdapter : RecyclerView.Adapter<UserHomeRecentAdapter.RecentViewHolder>() {

    // Đổi tên thành RecentViewHolder cho khỏi đụng hàng, để trống phần ánh xạ
    class RecentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        // Bơm đúng file XML của khối Recent vào
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_home_recent, parent, false)
        return RecentViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        // Tạm thời để trống, sau này đổ list lịch sử học tập vào đây
    }
}