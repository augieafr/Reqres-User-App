package com.augieafr.reqresapp.data.repository

import com.augieafr.reqresapp.data.local.preference.UserPreference
import com.augieafr.reqresapp.data.network.ApiService
import com.augieafr.reqresapp.data.utils.ResultState
import com.augieafr.reqresapp.data.utils.executeRequest
import com.augieafr.reqresapp.data.utils.toReqresException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    val userToken = userPreference.getUserToken()
    suspend fun login(email: String, password: String) = executeRequest { flowCollector ->
        val result = apiService.login(email, password)
        if (result.isSuccessful) {
            result.body()?.token?.let {
                userPreference.setUserToken(it)
                flowCollector.emit(ResultState.Success(true))
            }
        } else {
            flowCollector.emit(
                ResultState.Error(
                    result.toReqresException()
                )
            )
        }
    }
}