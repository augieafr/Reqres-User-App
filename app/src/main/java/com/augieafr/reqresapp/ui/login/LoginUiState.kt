package com.augieafr.reqresapp.ui.login

sealed class LoginUiState {
    data class Loading(val isLoading: Boolean) : LoginUiState()
    data object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}