package com.example.dotify1

import android.app.Application
import com.example.dotify1.manager.ApiManager
import com.example.dotify1.repository.DataRepository

class DotifyApplication : Application() {
    lateinit var dataRepository: DataRepository
    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()

        this.dataRepository = DataRepository()
        this.apiManager = ApiManager()

    }
}