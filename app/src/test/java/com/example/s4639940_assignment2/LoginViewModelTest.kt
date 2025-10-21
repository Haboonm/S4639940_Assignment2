package com.example.s4639940_assignment2   // match the folder

import com.example.s4639940_assignment2.ui.login.LoginViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginViewModelTest {

    @Test
    fun blank_first_name_shows_error() = runTest {
        val vm = LoginViewModel()                //  (default repo)
        vm.login(firstname = "", studentId = "1234567")
        assertTrue(vm.state.value is LoginViewModel.UiState.Error)
    }

    @Test
    fun wrong_student_id_length_shows_error() = runTest {
        val vm = LoginViewModel()                //
        vm.login(firstname = "alex", studentId = "123") // not 7 digits
        assertTrue(vm.state.value is LoginViewModel.UiState.Error)
    }
}
