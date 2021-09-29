package com.example.luckycat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.luckycat.data.CatImagesRepository

class CatsListViewModel : ViewModel() {
    var repository= CatImagesRepository()

    var catsLiveData=
        repository.letCatImagesLiveData().cachedIn(viewModelScope)

    fun refresh(){
        repository= CatImagesRepository()
        catsLiveData=
            repository.letCatImagesLiveData().cachedIn(viewModelScope)
    }
}