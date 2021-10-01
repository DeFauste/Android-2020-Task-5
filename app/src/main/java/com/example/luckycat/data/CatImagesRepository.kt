package com.example.luckycat.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.luckycat.network.ApiService
import com.example.luckycat.network.CatApi
import com.example.luckycat.network.CatProperty
import kotlinx.coroutines.flow.Flow

class CatImagesRepository(private val catApiService: ApiService = CatApi.retrofitService) {


    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 1, enablePlaceholders = false)
    }


    fun letCatImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CatProperty>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { CatImagePagingSource(catApiService) }
        ).flow
    }

    fun letCatImagesLiveData(pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<CatProperty>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { CatImagePagingSource(catApiService) }
        ).liveData
    }

}