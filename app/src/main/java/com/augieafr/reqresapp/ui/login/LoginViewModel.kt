package com.augieafr.reqresapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.reqresapp.data.repository.AuthRepository
import com.augieafr.reqresapp.data.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _formsErrorState = MutableStateFlow(Pair(false, false))
    val formsErrorState: StateFlow<Pair<Boolean, Boolean>>
        get() = _formsErrorState

    private val _loginUiState = MutableSharedFlow<LoginUiState>()
    val loginUiState: SharedFlow<LoginUiState>
        get() = _loginUiState

    val token = repository.userToken

    fun setEmailErrorState(isError: Boolean) {
        _formsErrorState.value = _formsErrorState.value.copy(first = isError)
    }

    fun setPasswordErrorState(isError: Boolean) {
        _formsErrorState.value = _formsErrorState.value.copy(second = isError)
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        repository.login(email, password).collectLatest { result ->
            when (result) {
                is ResultState.Error -> {
                    _loginUiState.emit(LoginUiState.Loading(false))
                    _loginUiState.emit(LoginUiState.Error(result.exceptionType.message))
                }
                is ResultState.Loading -> _loginUiState.emit(LoginUiState.Loading(true))
                is ResultState.Success -> {
                    _loginUiState.emit(LoginUiState.Loading(false))
                    _loginUiState.emit(LoginUiState.Success)
                }
            }
        }
    }


}