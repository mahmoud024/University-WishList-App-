package com.example.universitywishlistapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universitywishlistapp.data.db.AppDatabase
import com.example.universitywishlistapp.data.db.UniversityEntity
import com.example.universitywishlistapp.data.model.University
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UniversityDetailsViewModel : ViewModel() {

    fun isInWishlist(context: Context, university: University, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val dao = AppDatabase.getDatabase(context).universityDao()
            val item = dao.getUniversityByName(university.name)
            callback(item != null)
        }
    }

    fun toggleWishlist(context: Context, university: University, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val dao = AppDatabase.getDatabase(context).universityDao()
            val item = dao.getUniversityByName(university.name)
            if (item != null) {
                dao.delete(item)
                callback(false)
            } else {
                val entity = UniversityEntity(
                    name = university.name,
                    country = university.country,
                    web_pages = university.web_pages,        // هنا نمرر القائمة كاملة
                    alpha_two_code = university.alpha_two_code,
                    isChecked = true
                )

                dao.insert(entity)
                callback(true)
            }
        }
    }
}