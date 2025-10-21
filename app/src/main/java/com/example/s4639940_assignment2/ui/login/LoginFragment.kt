package com.example.s4639940_assignment2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.s4639940_assignment2.R
import com.example.s4639940_assignment2.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val b get() = _binding!!
    private val vm: LoginViewModel by viewModel() // <-- Koin

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.btnEnter.setOnClickListener {
            val first = b.etFirstName.text?.toString()?.trim().orEmpty()
            val sid = b.etStudentId.text?.toString()?.trim().orEmpty()
            b.tilFirstName.error = null
            b.tilStudentId.error = null
            vm.login(first, sid)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collectLatest { st ->
                    when (st) {
                        is LoginViewModel.UiState.Loading -> b.progressBar.isVisible = true
                        is LoginViewModel.UiState.Success -> {
                            b.progressBar.isVisible = false
                            findNavController().navigate(
                                R.id.action_login_to_dashboard,
                                Bundle().apply { putString("keypass", st.keypass) }
                            )
                        }
                        is LoginViewModel.UiState.Error -> {
                            b.progressBar.isVisible = false
                            b.tilFirstName.error = st.message
                            if (st.message.contains("7")) {
                                b.tilStudentId.error = "Enter your 7-digit student ID"
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}



