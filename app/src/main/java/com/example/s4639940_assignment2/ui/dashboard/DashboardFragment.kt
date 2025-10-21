package com.example.s4639940_assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s4639940_assignment2.R
import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.databinding.FragmentDashboardBinding
import com.example.s4639940_assignment2.databinding.RowDashboardItemBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val b get() = _binding!!
    private val vm: DashboardViewModel by viewModels()

    private val adapter = DashboardAdapter { item ->
        findNavController().navigate(
            R.id.action_dashboard_to_details,
            Bundle().apply { putParcelable("item", item) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.rvList.adapter = adapter

        // get key from LoginFragment navigation args
        val key = arguments?.getString("keypass").orEmpty()
        vm.load(key)

        // Modern collection (replaces launchWhenStarted)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.items.collectLatest { adapter.submitList(it) }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

private object ItemDiff : DiffUtil.ItemCallback<DashboardItem>() {
    override fun areItemsTheSame(a: DashboardItem, b: DashboardItem) = a.id == b.id
    override fun areContentsTheSame(a: DashboardItem, b: DashboardItem) = a == b
}

private class DashboardAdapter(
    val onClick: (DashboardItem) -> Unit
) : ListAdapter<DashboardItem, DashboardVH>(ItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardVH {
        val b = RowDashboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardVH(b, onClick)
    }

    override fun onBindViewHolder(holder: DashboardVH, position: Int) {
        holder.bind(getItem(position))
    }
}

private class DashboardVH(
    private val b: RowDashboardItemBinding,
    val onClick: (DashboardItem) -> Unit
) : RecyclerView.ViewHolder(b.root) {
    fun bind(item: DashboardItem) {
        b.tvTitle.text = item.title
        b.tvSubtitle.text = item.subtitle
        b.tvMeta.text = "${item.date} • ${item.location}"
        b.root.setOnClickListener { onClick(item) }
    }
}
