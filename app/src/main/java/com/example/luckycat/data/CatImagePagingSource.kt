package com.example.luckycat.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.luckycat.network.ApiService
import com.example.luckycat.network.CatProperty
import retrofit2.HttpException
import java.io.IOException

class CatImagePagingSource (val apiService: ApiService): PagingSource<Int, CatProperty>() {
    override fun getRefreshKey(state: PagingState<Int, CatProperty>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatProperty> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getProperties(page, params.loadSize)
            LoadResult.Page(
                response, prevKey = if (page == 1) null else page + 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException){
            return LoadResult.Error(exception)
        }
    }
}