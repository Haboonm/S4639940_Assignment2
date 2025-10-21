package com.example.s4639940_assignment2.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.data.repo.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Holds dashboard data and business logic.
class DashboardViewModel(
    // Injected by Koin
    private val repo: MainRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<DashboardItem>>(emptyList())
    val items: StateFlow<List<DashboardItem>> = _items

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun load(keypass: String) {
        viewModelScope.launch {
            try {
                _items.value = repo.loadDashboard(keypass)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
