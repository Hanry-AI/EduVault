package com.example.dacs.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R
import com.example.dacs.ui.home.adapter.HeaderAdapter
import androidx.recyclerview.widget.ConcatAdapter
import com.example.dacs.ui.home.adapter.UniversityAdapter
import com.example.dacs.ui.home.adapter.FeaturesAdapter
import com.example.dacs.ui.home.adapter.ReviewsAdapter
import com.example.dacs.ui.home.adapter.CareersAdapter
import com.example.dacs.ui.home.adapter.FooterAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. Kéo giao diện fragment_home.xml ra
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // 2. Tìm cái RecyclerView
        val rvHome = view.findViewById<RecyclerView>(R.id.rvHome)

        // 3. Quy định danh sách cuộn dọc
        rvHome.layoutManager = LinearLayoutManager(requireContext())

        // 4. Khởi tạo các khối Lego
        val headerAdapter = HeaderAdapter()
        val uniAdapter = UniversityAdapter()
        val featuresAdapter = FeaturesAdapter()
        val reviewsAdapter = ReviewsAdapter()
        val careersAdapter = CareersAdapter()
        val footerAdapter = FooterAdapter()

        // 5. Dùng ConcatAdapter để gộp khối (Đã đổi tên biến cho trong sáng)
        val combinedAdapter = ConcatAdapter(
            headerAdapter,
            uniAdapter,
            featuresAdapter,
            reviewsAdapter,
            careersAdapter,
            footerAdapter)

        // 6. Gắn khối tổng hợp vào danh sách (Nhớ là không có dấu ngoặc đơn nhé)
        rvHome.adapter = combinedAdapter

        return view
    }
}