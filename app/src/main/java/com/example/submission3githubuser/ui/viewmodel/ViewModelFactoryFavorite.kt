package com.example.submission3githubuser.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission3githubuser.data.UserRepository
import com.example.submission3githubuser.di.Injection

class ViewModelFactoryFavorite private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryFavorite? = null
        fun getInstance(context: Context): ViewModelFactoryFavorite =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactoryFavorite(Injection.provideRepository(context))
            }.also { instance = it }
    }
}