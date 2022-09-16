package com.example.submission3githubuser.di

import android.content.Context
import com.example.submission3githubuser.data.UserRepository
import com.example.submission3githubuser.data.local.UserDatabase
import com.example.submission3githubuser.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}