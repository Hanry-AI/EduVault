package com.example.dacs.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.dacs.dacs.R
import com.example.dacs.ui.auth.LoginActivity
import com.example.dacs.ui.auth.RegisterActivity
import com.example.dacs.ui.home.HomeFragment
import com.example.dacs.ui.home.UserHomeFragment
import com.example.dacs.ui.profile.SetupProfileActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var imgHome: ImageView
    private lateinit var tvHome: TextView
    private lateinit var imgLibrary: ImageView
    private lateinit var tvLibrary: TextView
    private lateinit var imgQuiz: ImageView
    private lateinit var tvQuiz: TextView
    private lateinit var imgProfile: ImageView
    private lateinit var tvProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        val tabHome = findViewById<LinearLayout>(R.id.tabHome)
        val tabLibrary = findViewById<LinearLayout>(R.id.tabLibrary)
        val tabQuiz = findViewById<LinearLayout>(R.id.tabQuiz)
        val tabProfile = findViewById<LinearLayout>(R.id.tabProfile)

        imgHome = findViewById(R.id.imgHome)
        tvHome = findViewById(R.id.tvHome)
        imgLibrary = findViewById(R.id.imgLibrary)
        tvLibrary = findViewById(R.id.tvLibrary)
        imgQuiz = findViewById(R.id.imgQuiz)
        tvQuiz = findViewById(R.id.tvQuiz)
        imgProfile = findViewById(R.id.imgProfile)
        tvProfile = findViewById(R.id.tvProfile)

        if (savedInstanceState == null) {
            selectTabHome()
        }

        tabHome.setOnClickListener {
            selectTabHome()
        }

        tabLibrary.setOnClickListener {
            selectTab(imgLibrary, tvLibrary)
        }

        tabQuiz.setOnClickListener {
            selectTab(imgQuiz, tvQuiz)
        }

        tabProfile.setOnClickListener {
            selectTab(imgProfile, tvProfile)
        }
    }

    private fun selectTabHome() {
        selectTab(imgHome, tvHome)
        val correctFragment = getSmartHomeFragment()
        correctFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, it)
                .commit()
        }
    }

    private fun selectTab(activeImg: ImageView, activeTv: TextView) {
        val grayColor = Color.parseColor("#9CA3AF")

        imgHome.alpha = 0.5f
        tvHome.setTextColor(grayColor)
        tvHome.setTypeface(null, Typeface.NORMAL)

        imgLibrary.alpha = 0.5f
        tvLibrary.setTextColor(grayColor)
        tvLibrary.setTypeface(null, Typeface.NORMAL)

        imgQuiz.alpha = 0.5f
        tvQuiz.setTextColor(grayColor)
        tvQuiz.setTypeface(null, Typeface.NORMAL)

        imgProfile.alpha = 0.5f
        tvProfile.setTextColor(grayColor)
        tvProfile.setTypeface(null, Typeface.NORMAL)

        activeImg.alpha = 1.0f
        activeTv.setTextColor(Color.parseColor("#EAB308"))
        activeTv.setTypeface(null, Typeface.BOLD)
    }

    private fun getSmartHomeFragment(): Fragment? {
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            return HomeFragment()
        }

        val sharedPref = getSharedPreferences("EduVaultPrefs", Context.MODE_PRIVATE)
        val isSetupCompleted = sharedPref.getBoolean("isProfileSetup_${user.uid}", false)

        return if (isSetupCompleted) {
            UserHomeFragment()
        } else {
            null
        }
    }

    override fun onResume() {
        super.onResume()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val sharedPref = getSharedPreferences("EduVaultPrefs", Context.MODE_PRIVATE)
            val isSetupCompleted = sharedPref.getBoolean("isProfileSetup_${user.uid}", false)

            if (!isSetupCompleted) {
                startActivity(Intent(this, SetupProfileActivity::class.java))
                finish()
                return
            }
        }
        updateDrawerHeader()
    }

    private fun updateDrawerHeader() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view) ?: return
        navigationView.setCheckedItem(R.id.nav_home)
        val headerView = navigationView.getHeaderView(0)

        val layoutGuestButtons = headerView.findViewById<LinearLayout>(R.id.layoutGuestButtons)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val tvSubtitle = headerView.findViewById<TextView>(R.id.tvSubtitle)
        val tvAvatarInitial = headerView.findViewById<TextView>(R.id.tvAvatarInitial)
        val btnCloseDrawer = headerView.findViewById<TextView>(R.id.btnCloseDrawer)
        val btnLoginDrawer = headerView.findViewById<MaterialButton>(R.id.btnLoginDrawer)
        val btnRegisterDrawer = headerView.findViewById<MaterialButton>(R.id.btnRegisterDrawer)

        btnCloseDrawer.setOnClickListener {
            findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
        }

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val displayName = user.displayName?.takeIf { it.isNotBlank() } ?: "Gamer Hary"
            layoutGuestButtons.visibility = View.GONE
            tvUserName.text = displayName
            tvSubtitle.text = "ĐH Kinh tế TP.HCM"
            tvAvatarInitial.text = displayName.trim().firstOrNull()?.uppercaseChar()?.toString() ?: "G"
        } else {
            layoutGuestButtons.visibility = View.VISIBLE
            tvUserName.text = "Khách"
            tvSubtitle.text = "+ Thêm trường học của bạn"
            tvAvatarInitial.text = "K"

            btnLoginDrawer.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            btnRegisterDrawer.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    fun openDrawer() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.openDrawer(GravityCompat.START)
    }
}
