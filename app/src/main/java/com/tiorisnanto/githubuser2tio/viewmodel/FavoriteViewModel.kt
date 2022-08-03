package com.tiorisnanto.githubuser2tio.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tiorisnanto.githubuser2tio.model.Favorite
import com.tiorisnanto.githubuser2tio.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository?

    init {
        mFavoriteRepository = FavoriteRepository(application)
    }

    fun getAllFavorites(): LiveData<List<Favorite>>? = mFavoriteRepository?.getAllFavorites()
}