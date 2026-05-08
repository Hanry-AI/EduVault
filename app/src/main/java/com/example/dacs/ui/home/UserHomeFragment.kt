package com.example.dacs.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dacs.dacs.R
import com.google.firebase.auth.FirebaseAuth

class UserHomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_home, container, false)

        val tvAvatarUserAI = view.findViewById<TextView>(R.id.tvAvatarUserAI)
        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            val initial = it.displayName?.take(1) ?: it.email?.take(1) ?: "U"
            tvAvatarUserAI.text = initial.uppercase()
        }

        return view
    }
}