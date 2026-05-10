package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class UserHomeToolsAdapter : RecyclerView.Adapter<UserHomeToolsAdapter.ToolsViewHolder>() {

    // Đổi tên thành ToolsViewHolder cho khỏi đụng hàng, xóa phần ánh xạ dư thừa
    class ToolsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolsViewHolder {
        // Bơm đúng file XML của khối Tools vào
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_home_tools, parent, false)
        return ToolsViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ToolsViewHolder, position: Int) {
        // Tạm thời để trống
    }
}