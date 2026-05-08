package com.example.dacs.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dacs.dacs.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    // 1. Khai báo biến để gọi Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Đoạn code làm giao diện tràn viền của bạn giữ nguyên
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Khởi tạo bộ não Firebase
        auth = FirebaseAuth.getInstance()

        // 3. Ánh xạ các ô nhập liệu và nút bấm từ XML
        val edtAccount = findViewById<EditText>(R.id.edtAccount)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnRegister = findViewById<MaterialButton>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        // 4. Bấm vào chữ "Đăng nhập" thì quay lại màn hình Login
        tvLogin.setOnClickListener {
            finish() // Đóng màn hình đăng ký này lại
        }

        // 5. Xử lý khi bấm nút "Đăng ký"
        btnRegister.setOnClickListener {
            // Lấy chữ người dùng vừa gõ vào
            val accountName = edtAccount.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            // Kiểm tra xem có ô nào bị bỏ trống không
            if (accountName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Dừng lại, không chạy code bên dưới nữa
            }

            // Firebase bắt buộc mật khẩu phải từ 6 ký tự trở lên
            if (password.length < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tiến hành gửi Email và Mật khẩu lên máy chủ Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Tạo tài khoản thành công!
                        // Bây giờ ta cập nhật thêm "Tên tài khoản" cho hồ sơ này
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(accountName)
                            .build()

                        user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                            Toast.makeText(this, "Tạo tài khoản thành công!", Toast.LENGTH_LONG).show()

                            // Chuyển người dùng sang màn hình Đăng nhập
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // Đóng hẳn màn hình đăng ký này lại
                        }
                    } else {
                        // Thất bại (Ví dụ: Email đã có người dùng, email sai định dạng...)
                        Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}