package com.example.dacs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacs.dacs.R

class CareersAdapter : RecyclerView.Adapter<CareersAdapter.CareersViewHolder>() {

    class CareersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_careers, parent, false)
        return CareersViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: CareersViewHolder, position: Int) {}
}