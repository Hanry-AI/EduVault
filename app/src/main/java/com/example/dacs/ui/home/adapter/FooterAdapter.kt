package com.example.dacs.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R
import com.google.android.material.card.MaterialCardView

class FooterAdapter : RecyclerView.Adapter<FooterAdapter.FooterViewHolder>() {

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardAskAI: MaterialCardView = itemView.findViewById(R.id.cardAskAI)
        val cardAINotes: MaterialCardView = itemView.findViewById(R.id.cardAINotes)
        val cardQuiz: MaterialCardView = itemView.findViewById(R.id.cardQuiz)
        val cardSummary: MaterialCardView = itemView.findViewById(R.id.cardSummary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_footer, parent, false)
        return FooterViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: FooterViewHolder, position: Int) {
        // Gắn hiệu ứng nhún (Scale) cho cả 4 thẻ
        setupScaleAnimation(holder.cardAskAI, "Hỏi AI")
        setupScaleAnimation(holder.cardAINotes, "Ghi chú AI")
        setupScaleAnimation(holder.cardQuiz, "Tạo Quiz")
        setupScaleAnimation(holder.cardSummary, "Tóm tắt")
    }

    // Tuyệt chiêu tạo Animation nhún khi chạm vào màn hình
    @SuppressLint("ClickableViewAccessibility")
    private fun setupScaleAnimation(card: MaterialCardView, featureName: String) {
        card.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                // Khi đè ngón tay xuống: Thu nhỏ lại 5% (0.95)
                MotionEvent.ACTION_DOWN -> {
                    view.animate().scaleX(0.95f).scaleY(0.95f).setDuration(150).start()
                }
                // Khi nhấc ngón tay lên hoặc trượt ra ngoài: Phình to về cũ (1.0)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    view.animate().scaleX(1f).scaleY(1f).setDuration(150).start()

                    // Nếu là nhấc tay lên (Click thành công), mở màn hình hoặc báo Toast
                    if (motionEvent.action == MotionEvent.ACTION_UP) {
                        Toast.makeText(view.context, "Mở màn hình: $featureName", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }
    }
}