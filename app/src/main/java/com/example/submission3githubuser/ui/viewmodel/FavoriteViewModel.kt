package com.example.submission3githubuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.submission3githubuser.data.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFavoriteUser() = userRepository.getFavoriteUser()
}