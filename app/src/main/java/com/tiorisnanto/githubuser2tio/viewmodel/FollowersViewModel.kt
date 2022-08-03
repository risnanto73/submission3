package com.tiorisnanto.githubuser2tio.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiorisnanto.githubuser2tio.fragment.FollowersFragment
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


class FollowersViewModel : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<ResponseUser>>()

    fun setListFollower(username: String) {
        ApiConfig.service().getUserFollower(username)
            .enqueue(object : Callback<ArrayList<ResponseUser>> {
                override fun onResponse(
                    call: Call<ArrayList<ResponseUser>>,
                    response: Response<ArrayList<ResponseUser>>
                ) {
                    listFollower.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<ResponseUser>>, t: Throwable) {
                   t.localizedMessage?.let {
                       Log.d("onFailure", it)
                   }
                }

            })
    }

    fun getListFollower(): LiveData<ArrayList<ResponseUser>> {
        return listFollower
    }
}