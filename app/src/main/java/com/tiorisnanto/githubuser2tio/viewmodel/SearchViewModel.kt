package com.tiorisnanto.githubuser2tio.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.tiorisnanto.githubuser2tio.model.ResponseSearch
import com.tiorisnanto.githubuser2tio.model.ResponseUser
import com.tiorisnanto.githubuser2tio.network.ApiConfig
import com.tiorisnanto.githubuser2tio.pref.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(val application: Application) : ViewModel() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val pref: SettingPreferences = SettingPreferences.getInstance(application.dataStore)
    val listUsers = MutableLiveData<ArrayList<ResponseUser>>()

    fun setSearchUser(query: String) {
        ApiConfig.service().getSearchUser(query).enqueue(object : Callback<ResponseSearch> {
            override fun onResponse(
                call: Call<ResponseSearch>,
                response: Response<ResponseSearch>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        listUsers.postValue(response.body()?.listUsers)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
                Toast.makeText(
                    application.applicationContext,
                    t.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getSearchUser(): LiveData<ArrayList<ResponseUser>> {
        return listUsers
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}