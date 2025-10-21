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

// Adapter = bridges your data (list of DashboardItem) to the RecyclerView rows.
class DashboardAdapter(
    // Called when a row is tapped.
    private val onClick: (DashboardItem) -> Unit
) : ListAdapter<DashboardItem, DashboardAdapter.VH>(DIFF) {

    companion object {
        // DiffUtil = tells RecyclerView how to spot changes so it can animate efficiently.
        private val DIFF = object : DiffUtil.ItemCallback<DashboardItem>() {
            override fun areItemsTheSame(a: DashboardItem, b: DashboardItem) = a.id == b.id
            override fun areContentsTheSame(a: DashboardItem, b: DashboardItem) = a == b
        }
    }

    // ViewHolder = one row’s views (title/subtitle/meta) and how to bind data to them.
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val subtitle: TextView = view.findViewById(R.id.tvSubtitle)
        private val meta: TextView = view.findViewById(R.id.tvMeta)

        fun bind(item: DashboardItem) {
            title.text = item.title
            subtitle.text = item.subtitle
            // Show "date • location" like your previous adapter did
            meta.text = "${item.date} • ${item.location}"  // compact extra info
            itemView.setOnClickListener { onClick(item) }  // bubble up click
        }
    }

    // Inflate the row layout and create a ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_dashboard_item, parent, false)
        return VH(v)
    }

    // Hand the data item to the holder to display.
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
