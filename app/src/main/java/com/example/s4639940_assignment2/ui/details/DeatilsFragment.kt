package com.example.s4639940_assignment2.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val b get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        // Set the title of the current screen
        requireActivity().title = "Book Details"

        val item = if (android.os.Build.VERSION.SDK_INT >= 33) {
            requireArguments().getParcelable("item", DashboardItem::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable<DashboardItem>("item")!!
        }

        b.tvTitle.text = item.title
        b.tvSubtitle.text = item.subtitle
        b.tvMeta.text = "${item.date} • ${item.location}"
        b.tvDesc.text = item.description
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
