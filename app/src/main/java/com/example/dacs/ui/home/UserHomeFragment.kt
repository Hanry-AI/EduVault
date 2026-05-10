package com.example.dacs.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ConcatAdapter
import com.dacs.dacs.R
import com.example.dacs.ui.home.adapter.UserHomeTopBarAdapter
import com.example.dacs.ui.home.adapter.UserHomeFilterAdapter
import com.example.dacs.ui.home.adapter.UserHomeBannerAdapter
import com.example.dacs.ui.home.adapter.UserHomeToolsAdapter
import com.example.dacs.ui.home.adapter.UserHomeTrackingAdapter
import com.example.dacs.ui.home.adapter.UserHomeFeaturedAdapter
import com.example.dacs.ui.home.adapter.UserHomeRecentAdapter
import com.example.dacs.ui.home.adapter.UserHomeTrendingAdapter

class UserHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_home, container, false)
        val rvUserHome = view.findViewById<RecyclerView>(R.id.rvUserHome)

        // 1. Quy định danh sách cuộn dọc
        rvUserHome.layoutManager = LinearLayoutManager(requireContext())

        // 2. Khởi tạo 8 khối Lego
        val topBarAdapter = UserHomeTopBarAdapter()
        val filterAdapter = UserHomeFilterAdapter()
        val bannerAdapter = UserHomeBannerAdapter()
        val toolsAdapter = UserHomeToolsAdapter()
        val trackingAdapter = UserHomeTrackingAdapter()
        val featuredAdapter = UserHomeFeaturedAdapter()
        val recentAdapter = UserHomeRecentAdapter()
        val trendingAdapter = UserHomeTrendingAdapter()

        // 3. Nối các khối lại theo đúng thứ tự thiết kế
        val combinedAdapter = ConcatAdapter(
            topBarAdapter,
            filterAdapter,
            bannerAdapter,
            toolsAdapter,
            trackingAdapter,
            featuredAdapter,
            recentAdapter,
            trendingAdapter
        )

        // 4. Bơm vào RecyclerView
        rvUserHome.adapter = combinedAdapter

        return view
    }
}