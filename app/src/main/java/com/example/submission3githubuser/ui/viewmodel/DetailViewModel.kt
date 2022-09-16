package com.example.submission3githubuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission3githubuser.data.UserRepository
import com.example.submission3githubuser.data.local.UserEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDetailUser(username: String) = userRepository.getDetailUser(username)

    fun insertFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavoriteUser(user, true)
        }
    }

    fun deleteFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavoriteUser(user, false)
        }
    }
}