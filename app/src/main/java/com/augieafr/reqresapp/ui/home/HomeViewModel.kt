package com.augieafr.reqresapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.augieafr.reqresapp.data.repository.AuthRepository
import com.augieafr.reqresapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) :ViewModel() {

    fun getUsers() = userRepository.getUsers().cachedIn(viewModelScope)

    fun logout() = viewModelScope.launch {
        authRepository.logout()
    }
}