package com.example.s4639940_assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s4639940_assignment2.R
import com.example.s4639940_assignment2.data.model.DashboardItem

class DashboardAdapter(
    private val onClick: (DashboardItem) -> Unit
) : ListAdapter<DashboardItem, DashboardAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<DashboardItem>() {
            override fun areItemsTheSame(a: DashboardItem, b: DashboardItem) = a.id == b.id
            override fun areContentsTheSame(a: DashboardItem, b: DashboardItem) = a == b
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val subtitle: TextView = view.findViewById(R.id.tvSubtitle)
        private val meta: TextView = view.findViewById(R.id.tvMeta)

        fun bind(item: DashboardItem) {
            title.text = item.title
            subtitle.text = item.subtitle
            // Show "date • location" like your previous adapter did
            meta.text = "${item.date} • ${item.location}"
            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_dashboard_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
