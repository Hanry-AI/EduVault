package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R
import com.google.firebase.auth.FirebaseAuth

// 1. Khai báo rõ ràng dùng TopBarViewHolder
class UserHomeTopBarAdapter : RecyclerView.Adapter<UserHomeTopBarAdapter.TopBarViewHolder>() {

    // 2. Đổi tên class để không đụng hàng với thư viện Android
    class TopBarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAvatarUserAI: TextView = view.findViewById(R.id.tvAvatarUserAI)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopBarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_home_top_bar, parent, false)
        return TopBarViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopBarViewHolder, position: Int) {
        // Logic này rất xịn: Lấy tên thật của User bỏ vào Avatar
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val initial = it.displayName?.take(1) ?: it.email?.take(1) ?: "U"
            holder.tvAvatarUserAI.text = initial.uppercase()
        }
    }

    override fun getItemCount() = 1 // Vì Top Bar chỉ hiện 1 lần
}