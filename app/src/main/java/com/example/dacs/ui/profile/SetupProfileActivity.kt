package com.example.dacs.ui.profile

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.dacs.dacs.R
import com.example.dacs.ui.MainActivity
import com.example.dacs.ui.auth.LoginActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth

class SetupProfileActivity : AppCompatActivity() {

    private var currentSelectedRole = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_profile)

        val cvAvatarMenu = findViewById<CardView>(R.id.cvAvatarMenu)
        val tvAvatarInitial = findViewById<TextView>(R.id.tvAvatarInitial)
        val edtCountry = findViewById<AutoCompleteTextView>(R.id.edtCountry)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val displayName = user?.displayName
        val email = user?.email

        if (!displayName.isNullOrEmpty()) {
            tvAvatarInitial.text = displayName.take(1).uppercase()
        } else if (!email.isNullOrEmpty()) {
            tvAvatarInitial.text = email.take(1).uppercase()
        } else {
            tvAvatarInitial.text = "K"
        }

        cvAvatarMenu.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menu.add(0, 1, 0, "Đăng xuất")

            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == 1) {
                    auth.signOut()
                    Toast.makeText(this, "Đã đăng xuất!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                true
            }
            popup.show()
        }

        val countries = arrayOf("Vietnam", "United States", "Japan", "South Korea", "Thailand", "Singapore", "Canada", "Australia")
        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        edtCountry.setAdapter(countryAdapter)

        var lastDismissTime = 0L
        edtCountry.setOnDismissListener {
            lastDismissTime = System.currentTimeMillis()
        }
        edtCountry.setOnClickListener {
            if (System.currentTimeMillis() - lastDismissTime > 200) {
                edtCountry.showDropDown()
            }
        }

        val btnRoleUni = findViewById<TextView>(R.id.btnRoleUni)
        val btnRoleHighSchool = findViewById<TextView>(R.id.btnRoleHighSchool)
        val btnRoleTeacher = findViewById<TextView>(R.id.btnRoleTeacher)
        val btnRoleOther = findViewById<TextView>(R.id.btnRoleOther)

        val layoutDynamicInput = findViewById<LinearLayout>(R.id.layoutDynamicInput)
        val tvDynamicLabel = findViewById<TextView>(R.id.tvDynamicLabel)
        val edtDynamicInput = findViewById<AutoCompleteTextView>(R.id.edtDynamicInput)
        val tvQuickPicksLabel = findViewById<TextView>(R.id.tvQuickPicksLabel)
        val chipGroupQuickPicks = findViewById<ChipGroup>(R.id.chipGroupQuickPicks)
        val btnContinueSetup = findViewById<MaterialButton>(R.id.btnContinueSetup)

        val uniQuickPicks = arrayOf(
            "Đại học CNTT và Truyền thông Việt - Hàn (VKU)",
            "Đại học Bách Khoa - ĐHĐN",
            "Đại học Kinh tế - ĐHĐN",
            "Đại học Ngoại ngữ - ĐHĐN",
            "Đại học FPT Đà Nẵng"
        )

        val highSchoolQuickPicks = arrayOf(
            "THPT Chuyên Lê Quý Đôn", "THPT Phan Châu Trinh", "THPT Hoàng Hoa Thám",
            "THPT Trần Phú", "THPT Thái Phiên", "THPT Hòa Vang"
        )

        val teacherQuickPicks = arrayOf(
            "Toán học", "Vật lý", "Hóa học", "Ngữ văn", "Tiếng Anh", "Tiếng Nhật", "Công nghệ thông tin"
        )

        var lastInputDismissTime = 0L
        edtDynamicInput.setOnDismissListener {
            lastInputDismissTime = System.currentTimeMillis()
        }
        edtDynamicInput.setOnClickListener {
            if (edtDynamicInput.adapter != null && System.currentTimeMillis() - lastInputDismissTime > 200) {
                edtDynamicInput.showDropDown()
            }
        }
        edtDynamicInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && edtDynamicInput.adapter != null) edtDynamicInput.showDropDown()
        }

        // =======================================================
        // MÁY QUÉT KIỂM TRA CHỮ ĐỂ BẬT/TẮT MÀU THẺ (CHIPS)
        // =======================================================
        edtDynamicInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val currentText = s?.toString()?.trim() ?: ""

                // Duyệt qua tất cả các thẻ đang hiển thị
                for (i in 0 until chipGroupQuickPicks.childCount) {
                    val chip = chipGroupQuickPicks.getChildAt(i) as Chip
                    if (chip.text.toString() == currentText) {
                        // Nếu chữ trong ô giống hệt tên thẻ -> BẬT SÁNG THẺ LÊN (Viền xanh, chữ xanh)
                        chip.chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#2563EB"))
                        chip.setTextColor(Color.parseColor("#1D4ED8"))
                    } else {
                        // Nếu không giống -> TRẢ VỀ MÀU XÁM MẶC ĐỊNH
                        chip.chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#E5E7EB"))
                        chip.setTextColor(Color.parseColor("#374151"))
                    }
                }
            }
        })

        fun populateQuickPicks(items: Array<String>) {
            chipGroupQuickPicks.removeAllViews()
            if (items.isEmpty()) {
                tvQuickPicksLabel.visibility = View.GONE
                chipGroupQuickPicks.visibility = View.GONE
                return
            }

            tvQuickPicksLabel.visibility = View.VISIBLE
            chipGroupQuickPicks.visibility = View.VISIBLE

            for (item in items) {
                val chip = Chip(this)
                chip.text = item
                chip.isCheckable = false
                chip.setChipBackgroundColorResource(android.R.color.transparent)

                // Mặc định lúc mới tạo là viền xám, chữ xám
                chip.chipStrokeWidth = 3f
                chip.chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#E5E7EB"))
                chip.setTextColor(Color.parseColor("#374151"))

                // Bấm vào thẻ thì tự động điền chữ lên ô nhập
                // (Ngay khi điền xong, cái Máy quét TextWatcher ở trên sẽ tự chạy và làm thẻ bật sáng)
                chip.setOnClickListener {
                    edtDynamicInput.setText(item)
                    edtDynamicInput.setSelection(item.length)
                    edtDynamicInput.clearFocus() // Bỏ nháy nháy chuột cho đỡ rối
                }
                chipGroupQuickPicks.addView(chip)
            }
        }

        fun updateRoleSelection(selectedBtn: TextView?, roleName: String) {
            currentSelectedRole = roleName
            val allBtns = arrayOf(btnRoleUni, btnRoleHighSchool, btnRoleTeacher, btnRoleOther)

            for (btn in allBtns) {
                val bg = GradientDrawable()
                bg.cornerRadius = 40f
                if (btn == selectedBtn) {
                    bg.setColor(Color.parseColor("#EAF2FF"))
                    bg.setStroke(3, Color.parseColor("#2563EB"))
                    btn.setTextColor(Color.parseColor("#1D4ED8"))
                } else {
                    bg.setColor(Color.parseColor("#F3F4F6"))
                    bg.setStroke(0, Color.TRANSPARENT)
                    btn.setTextColor(Color.parseColor("#4B5563"))
                }
                btn.background = bg
            }
        }

        updateRoleSelection(null, "")

        btnRoleUni.setOnClickListener {
            updateRoleSelection(btnRoleUni, "Đại học")
            layoutDynamicInput.visibility = View.VISIBLE
            edtDynamicInput.text.clear()
            tvDynamicLabel.text = "Tôi học tại"
            edtDynamicInput.hint = "Gõ tên trường đại học của bạn..."
            populateQuickPicks(uniQuickPicks)
        }

        btnRoleHighSchool.setOnClickListener {
            updateRoleSelection(btnRoleHighSchool, "Trung học")
            layoutDynamicInput.visibility = View.VISIBLE
            edtDynamicInput.text.clear()
            tvDynamicLabel.text = "Tôi học tại"
            edtDynamicInput.hint = "Gõ tên trường trung học của bạn..."
            populateQuickPicks(highSchoolQuickPicks)
        }

        btnRoleTeacher.setOnClickListener {
            updateRoleSelection(btnRoleTeacher, "Giáo viên / Giảng viên")
            layoutDynamicInput.visibility = View.VISIBLE
            edtDynamicInput.text.clear()
            tvDynamicLabel.text = "Môn học giảng dạy"
            edtDynamicInput.hint = "VD: Toán, Ngữ Văn..."
            populateQuickPicks(teacherQuickPicks)
        }

        btnRoleOther.setOnClickListener {
            updateRoleSelection(btnRoleOther, "Khác")
            layoutDynamicInput.visibility = View.GONE
        }

        btnContinueSetup.setOnClickListener {
            if (currentSelectedRole.isEmpty()) {
                Toast.makeText(this, "Vui lòng cho biết bạn là ai nhé!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var extraInfo = ""
            if (layoutDynamicInput.visibility == View.VISIBLE) {
                extraInfo = edtDynamicInput.text.toString().trim()
                if (extraInfo.isEmpty()) {
                    Toast.makeText(this, "Vui lòng điền thông tin vào ô trống phía trên!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val selectedCountry = edtCountry.text.toString().trim()

            if (user != null) {
                val sharedPref = getSharedPreferences("EduVaultPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putBoolean("isProfileSetup_${user.uid}", true)
                editor.putString("userRole_${user.uid}", currentSelectedRole)
                editor.putString("userExtraInfo_${user.uid}", extraInfo)
                editor.putString("userCountry_${user.uid}", selectedCountry)
                editor.apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}