package com.example.dotify1.repository

import com.example.dotify1.model.SongList
import com.example.dotify1.model.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class DataRepository {
    var baseURL = "https://raw.githubusercontent.com/"


    private val DotifyService = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DotifyService::class.java)

    suspend fun getUserInfo(): UserInfo {
        return DotifyService.getAccount()
    }

    suspend fun getSongs(): SongList {
        return DotifyService.getSongs()
    }

}

interface DotifyService {
    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getAccount(): UserInfo

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    suspend fun getSongs(): SongList

}