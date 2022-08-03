package com.tiorisnanto.githubuser2tio.model

import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @field:SerializedName("items")
    val listUsers: ArrayList<ResponseUser>
)