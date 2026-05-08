package com.example.dacs.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.dacs.dacs.R
import com.example.dacs.ui.auth.LoginActivity
import com.example.dacs.ui.auth.RegisterActivity
import com.example.dacs.ui.home.HomeFragment
import com.example.dacs.ui.home.UserHomeFragment // Đã thêm import UserHomeFragment
import com.example.dacs.ui.profile.SetupProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // =========================================================
        // 1. TỰ ĐỘNG CHỌN PHÒNG LÚC MỚI MỞ APP
        // =========================================================
        if (savedInstanceState == null) {
            val initialFragment = getSmartHomeFragment()
            initialFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, it)
                    .commit()
            }
        }

        // =========================================================
        // 2. BẮT SỰ KIỆN BẤM NÚT BOTTOM NAV
        // =========================================================
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val correctFragment = getSmartHomeFragment()
                    correctFragment?.let {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, it)
                            .commit()
                        true
                    } ?: false
                }
                R.id.nav_library -> true
                R.id.nav_profile -> true
                else -> false
            }
        }
    }

    /**
     * HÀM THÔNG MINH: Quyết định xem người dùng sẽ nhìn thấy gì
     */
    private fun getSmartHomeFragment(): Fragment? {
        val user = FirebaseAuth.getInstance().currentUser

        // TRƯỜNG HỢP 1: CHƯA ĐĂNG NHẬP -> Vào nhà khách (HomeFragment)
        if (user == null) {
            return HomeFragment()
        }

        // TRƯỜNG HỢP 2: ĐÃ ĐĂNG NHẬP
        val sharedPref = getSharedPreferences("EduVaultPrefs", Context.MODE_PRIVATE)
        val isSetupCompleted = sharedPref.getBoolean("isProfileSetup_${user.uid}", false)

        return if (isSetupCompleted) {
            // Đã setup xong hồ sơ -> Vào thẳng Dashboard AI (UserHomeFragment)
            UserHomeFragment()
        } else {
            // Đã đăng nhập nhưng chưa setup -> Trả về null để onResume xử lý đá ra màn hình Setup
            null
        }
    }

    // Hàm onResume chạy mỗi khi MainActivity được hiển thị trên màn hình
    override fun onResume() {
        super.onResume()

        // --- LÍNH CANH CHẶN CỬA ---
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val sharedPref = getSharedPreferences("EduVaultPrefs", Context.MODE_PRIVATE)
            // Kiểm tra xem cái thẻ "isProfileSetup" của user này có bằng true không
            val isSetupCompleted = sharedPref.getBoolean("isProfileSetup_${user.uid}", false)

            if (!isSetupCompleted) {
                // Nếu chưa hoàn thành -> Đuổi cổ sang màn hình SetupProfile ngay lập tức!
                startActivity(Intent(this, SetupProfileActivity::class.java))
                finish() // Đóng MainActivity lại, không cho nhìn thấy giao diện
                return // Dừng toàn bộ code bên dưới
            }
        }
        // --------------------------

        // Nếu đã setup rồi, hoặc chưa đăng nhập (guest) thì mới chạy lệnh cập nhật Menu
        updateDrawerHeader()
    }

    // Hàm cập nhật Giao diện Menu trượt
    private fun updateDrawerHeader() {
        // Tìm NavigationView (Thường mặc định ID là nav_view trong file activity_main.xml)
        val navigationView = findViewById<NavigationView>(R.id.nav_view) ?: return

        // Lấy cái header (cái file XML ở Bước 1)
        val headerView = navigationView.getHeaderView(0)

        // Ánh xạ các UI từ header
        val layoutGuestButtons = headerView.findViewById<LinearLayout>(R.id.layoutGuestButtons)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val tvSubtitle = headerView.findViewById<TextView>(R.id.tvSubtitle)
        val btnLoginDrawer = headerView.findViewById<MaterialButton>(R.id.btnLoginDrawer)
        val btnRegisterDrawer = headerView.findViewById<MaterialButton>(R.id.btnRegisterDrawer)

        // Lấy thông tin user hiện tại
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // ĐÃ ĐĂNG NHẬP: Ẩn 2 nút, hiện tên và email
            layoutGuestButtons.visibility = View.GONE
            tvUserName.text = user.displayName ?: "Học viên EduVault"
            tvSubtitle.text = user.email
        } else {
            // CHƯA ĐĂNG NHẬP: Hiện 2 nút, để chữ Khách
            layoutGuestButtons.visibility = View.VISIBLE
            tvUserName.text = "Khách"
            tvSubtitle.text = "Đăng nhập để truy cập tài liệu"

            // Đấu nối click cho 2 nút bấm
            btnLoginDrawer.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            btnRegisterDrawer.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}