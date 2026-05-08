package com.example.dacs.ui.home.adapter

import com.google.firebase.auth.FirebaseAuth
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
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
        val imgUserProfile: ImageView = itemView.findViewById(R.id.imgUserProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

        // Logic mở Menu trượt trái
        holder.imgMenuBurger.setOnClickListener { view ->
            val activity = view.context as android.app.Activity
            val drawerLayout = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
            drawerLayout?.openDrawer(GravityCompat.START)
        }

        // Logic bấm vào lá cờ mở Popup chọn ngôn ngữ
        holder.layoutLanguage.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menu.add(0, 1, 0, "🇻🇳 Tiếng Việt")
            popupMenu.menu.add(0, 2, 1, "🇺🇸 English")

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
            popupMenu.show()
        }

        // Logic bấm vào Avatar (Icon Hình người góc phải)
        holder.imgUserProfile.setOnClickListener { view ->
            val context = view.context

            // KIỂM TRA XEM NGƯỜI DÙNG ĐÃ ĐĂNG NHẬP CHƯA
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser

            if (user != null) {
                // TRƯỜNG HỢP 1: ĐÃ ĐĂNG NHẬP -> Hiện bảng hỏi Đăng xuất
                AlertDialog.Builder(context)
                    .setTitle("Thông tin tài khoản")
                    .setMessage("Xin chào ${user.displayName}!\nBạn có muốn đăng xuất không?")
                    .setPositiveButton("Đăng xuất") { _, _ ->
                        auth.signOut() // Xóa thông tin đăng nhập trên máy
                        Toast.makeText(context, "Đã đăng xuất!", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
            } else {
                // TRƯỜNG HỢP 2: CHƯA ĐĂNG NHẬP -> Hiện Dialog chọn Đăng nhập/Đăng ký
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.dialog_login_selection)

                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // Nút X đóng Dialog
                val btnClose = dialog.findViewById<ImageView>(R.id.btnCloseDialog)
                btnClose.setOnClickListener {
                    dialog.dismiss()
                }

                // Nút Đăng nhập Email
                val btnLoginEmail = dialog.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLoginEmail)
                btnLoginEmail.setOnClickListener {
                    val intent = android.content.Intent(context, com.example.dacs.ui.auth.LoginActivity::class.java)
                    context.startActivity(intent)
                    dialog.dismiss()
                }

                // Nút Đăng ký Email
                val btnRegisterEmail = dialog.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnRegisterEmail)
                btnRegisterEmail.setOnClickListener {
                    val intent = android.content.Intent(context, com.example.dacs.ui.auth.RegisterActivity::class.java)
                    context.startActivity(intent)
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }
}