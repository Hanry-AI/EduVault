package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu // Import thư viện Popup Menu
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMenuBurger: ImageView = itemView.findViewById(R.id.imgMenuBurger)

        // 1. Ánh xạ khu vực chọn ngôn ngữ
        val layoutLanguage: LinearLayout = itemView.findViewById(R.id.layoutLanguage)
        val tvCurrentFlag: TextView = itemView.findViewById(R.id.tvCurrentFlag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

        // Logic mở Menu trượt trái (giữ nguyên)
        holder.imgMenuBurger.setOnClickListener { view ->
            val activity = view.context as android.app.Activity
            val drawerLayout = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
            drawerLayout?.openDrawer(GravityCompat.START)
        }

        // 2. Logic bấm vào lá cờ mở Popup chọn ngôn ngữ
        holder.layoutLanguage.setOnClickListener { view ->
            // Tạo một menu thả xuống neo vào vị trí lá cờ
            val popupMenu = PopupMenu(view.context, view)

            // Thêm 2 lựa chọn vào Menu (id=1 là VN, id=2 là US)
            popupMenu.menu.add(0, 1, 0, "🇻🇳 Tiếng Việt")
            popupMenu.menu.add(0, 2, 1, "🇺🇸 English")

            // Lắng nghe xem người dùng chọn cái nào
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    1 -> {
                        holder.tvCurrentFlag.text = "🇻🇳"
                        Toast.makeText(view.context, "Đã chuyển sang Tiếng Việt", Toast.LENGTH_SHORT).show()
                        true
                    }
                    2 -> {
                        holder.tvCurrentFlag.text = "🇺🇸"
                        Toast.makeText(view.context, "Switched to English", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            // Hiển thị cái menu lên
            popupMenu.show()
        }
    }
}