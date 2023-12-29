package com.augieafr.reqresapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.augieafr.reqresapp.data.network.ApiService
import com.augieafr.reqresapp.data.utils.ReqresException
import com.augieafr.reqresapp.data.utils.toReqresException
import com.augieafr.reqresapp.data.utils.toUserUiModel
import com.augieafr.reqresapp.ui.model.UserUiModel

class UserPagingSource(private val apiService: ApiService) :
    PagingSource<Int, UserUiModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, UserUiModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserUiModel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val result = apiService.getUsers(position, params.loadSize)
            if (result.isSuccessful) {
                val resultData = result.body()?.data ?: emptyList()
                LoadResult.Page(
                    data = resultData.map { it.toUserUiModel() },
                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                    nextKey = if (resultData.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(result.toReqresException())
            }

        } catch (exception: Exception) {
            return LoadResult.Error(ReqresException.UnexpectedError)
        }
    }
}