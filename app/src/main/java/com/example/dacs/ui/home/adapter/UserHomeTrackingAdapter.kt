package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class UserHomeTrackingAdapter : RecyclerView.Adapter<UserHomeTrackingAdapter.TrackingViewHolder>() {

    // Đổi tên thành TrackingViewHolder cho khỏi đụng hàng, xóa phần ánh xạ dư thừa
    class TrackingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingViewHolder {
        // Bơm đúng file XML của khối Tracking vào
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_home_tracking, parent, false)
        return TrackingViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: TrackingViewHolder, position: Int) {
        // Tạm thời để trống, sau này bạn có thể lấy danh sách môn học từ Firebase để render vào ChipGroup
    }
}