
package com.example.universitywishlistapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universitywishlistapp.data.db.UniversityDao
import com.example.universitywishlistapp.data.db.UniversityEntity
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val dao: UniversityDao
) : ViewModel() {

    val wishlist: LiveData<List<UniversityEntity>> = dao.getWishlist()

    fun removeFromWishlist(university: UniversityEntity) {
        viewModelScope.launch {
            dao.delete(university)
        }
    }

}

