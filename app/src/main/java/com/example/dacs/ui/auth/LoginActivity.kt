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
import com.example.dacs.ui.MainActivity
import com.example.dacs.ui.profile.SetupProfileActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // 1. Khai báo biến Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Khởi tạo Firebase
        auth = FirebaseAuth.getInstance()

        // 3. Ánh xạ các UI từ XML
        val edtEmail = findViewById<EditText>(R.id.edtEmailLogin)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordLogin)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        // 4. Nút chuyển sang màn hình Đăng ký
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 5. Xử lý Đăng nhập
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Email và Mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gửi yêu cầu Đăng nhập lên Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Đăng nhập thành công!
                        val user = auth.currentUser
                        Toast.makeText(this, "Chào mừng ${user?.displayName} trở lại!", Toast.LENGTH_SHORT).show()

                        // Chuyển thẳng vào màn hình chính của App
                        val intent = Intent(this, SetupProfileActivity::class.java)
                        startActivity(intent)
                        finish() // Đóng màn hình đăng nhập
                    } else {
                        // Sai mật khẩu hoặc tài khoản không tồn tại
                        Toast.makeText(this, "Sai Email hoặc Mật khẩu!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}