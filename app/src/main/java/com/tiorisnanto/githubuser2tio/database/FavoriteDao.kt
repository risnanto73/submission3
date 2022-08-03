package com.tiorisnanto.githubuser2tio.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tiorisnanto.githubuser2tio.model.Favorite

@Dao
interface FavoriteDao {

    @Insert
    fun insert(favorite: Favorite)

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    fun check(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * from favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>

}