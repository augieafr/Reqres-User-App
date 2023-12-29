package com.augieafr.reqresapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.augieafr.reqresapp.data.network.ApiService
import com.augieafr.reqresapp.data.paging.UserPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
) {
    fun getUsers() = Pager(
        config = PagingConfig(
            pageSize = 6,
            initialLoadSize = 6,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            UserPagingSource(apiService)
        }
    ).flow.flowOn(Dispatchers.IO)
}