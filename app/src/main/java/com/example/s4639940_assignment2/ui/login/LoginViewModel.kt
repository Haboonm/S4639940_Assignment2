package com.example.s4639940_assignment2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s4639940_assignment2.data.repo.MainRepository
import com.example.s4639940_assignment2.data.remote.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    //  default to the real repo so app works AND tests can call LoginViewModel()
    private val repo: MainRepository = ServiceLocator.repo
) : ViewModel() {

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val keypass: String) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state

    fun login(firstname: String, studentId: String) {
        if (firstname.isBlank()) {
            _state.value = UiState.Error("First name is required")
            return
        }
        if (studentId.length != 7) {
            _state.value = UiState.Error("Enter your 7-digit student ID")
            return
        }

        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val key = repo.login(firstname, studentId)
                _state.value = UiState.Success(key)
            } catch (_: Exception) {
                _state.value = UiState.Error("Login failed. Check name/ID and try again.")
            }
        }
    }
}
