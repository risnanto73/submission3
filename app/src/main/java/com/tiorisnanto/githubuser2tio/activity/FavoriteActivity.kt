package com.tiorisnanto.githubuser2tio.activity

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiorisnanto.githubuser2tio.R
import com.tiorisnanto.githubuser2tio.activity.DetailActivity.Companion.EXTRA_AVATAR
import com.tiorisnanto.githubuser2tio.activity.DetailActivity.Companion.EXTRA_USERNAME
import com.tiorisnanto.githubuser2tio.adapter.ListUserAdapter
import com.tiorisnanto.githubuser2tio.databinding.ActivityFavoriteBinding
import com.tiorisnanto.githubuser2tio.helper.ViewModelFactory
import com.tiorisnanto.githubuser2tio.model.Favorite
import com.tiorisnanto.githubuser2tio.model.ResponseUser
import com.tiorisnanto.githubuser2tio.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.appbar_favorite)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClick(data: ResponseUser) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(EXTRA_USERNAME, data.username)
                    it.putExtra(EXTRA_ID, data.id)
                    it.putExtra(EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = adapter
        }

        favoriteViewModel.getAllFavorites()?.observe(this, { favoriteList ->
            if (favoriteList != null) {
                val list = mapList(favoriteList)
                adapter.setListUser(list)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun mapList(listFavorites: List<Favorite>): ArrayList<ResponseUser> {
        val listUser = ArrayList<ResponseUser>()
        for (user in listFavorites) {
            val userMapped = ResponseUser(
                username = user.username,
                id = user.id,
                avatarUrl = user.avatarUrl,
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}