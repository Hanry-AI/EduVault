package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R
import com.google.firebase.auth.FirebaseAuth

// 1. SỬA CHỖ NÀY: Thêm tham số onMenuClick vào hàm khởi tạo của class
class UserHomeTopBarAdapter(private val onMenuClick: () -> Unit) : RecyclerView.Adapter<UserHomeTopBarAdapter.TopBarViewHolder>() {

    class TopBarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAvatarUserAI: TextView = view.findViewById(R.id.tvAvatarUserAI)
        val cvMenu: View = view.findViewById(R.id.cvMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopBarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_home_top_bar, parent, false)
        return TopBarViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopBarViewHolder, position: Int) {
        // Logic lấy tên thật của User bỏ vào Avatar
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val initial = it.displayName?.take(1) ?: it.email?.take(1) ?: "U"
            holder.tvAvatarUserAI.text = initial.uppercase()
        }

        // 2. Vì đã khai báo ở trên, giờ gọi onMenuClick() thoải mái không bị báo đỏ nữa!
        holder.cvMenu.setOnClickListener { onMenuClick() }
    }

    override fun getItemCount() = 1
}