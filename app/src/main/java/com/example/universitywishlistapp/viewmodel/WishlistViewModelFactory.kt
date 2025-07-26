package com.example.universitywishlistapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.universitywishlistapp.data.db.UniversityDao

class WishlistViewModelFactory(
    private val dao: UniversityDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            return WishlistViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}