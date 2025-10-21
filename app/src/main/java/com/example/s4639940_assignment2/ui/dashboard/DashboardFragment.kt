package com.example.s4639940_assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s4639940_assignment2.R
import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.databinding.FragmentDashboardBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val b get() = _binding!!
    private val vm: DashboardViewModel by viewModel() // <-- Koin

    private val adapter = DashboardAdapter { item: DashboardItem ->
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
        b.rvList.layoutManager = LinearLayoutManager(requireContext())
        b.rvList.adapter = adapter

        val key = arguments?.getString("keypass").orEmpty()
        android.util.Log.d("DASH_DEBUG", "Dashboard received keypass='$key'")
        if (key.isNotBlank()) vm.load(key)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.items.collectLatest { items ->
                    adapter.submitList(items)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
