package com.tiorisnanto.githubuser2tio.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiorisnanto.githubuser2tio.model.Favorite
import com.tiorisnanto.githubuser2tio.model.ResponseUser
import com.tiorisnanto.githubuser2tio.network.ApiConfig
import com.tiorisnanto.githubuser2tio.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(val application: Application) : ViewModel() {
    val listUsers = MutableLiveData<ResponseUser>()
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun setDetailUser(username: String) {
        ApiConfig.service().getDetailUser(username).enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
                Toast.makeText(
                    application.applicationContext,
                    t.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getDetailUser(): LiveData<ResponseUser> {
        return listUsers
    }

    fun checkUser(id: Int) = mFavoriteRepository.check(id)

    fun addToFavorite(username: String, id: Int, avatarUrl: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = Favorite(
                id,
                avatarUrl,
                username
            )
            mFavoriteRepository.insert(user)
        }
    }

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mFavoriteRepository.delete(id)
        }
    }
}