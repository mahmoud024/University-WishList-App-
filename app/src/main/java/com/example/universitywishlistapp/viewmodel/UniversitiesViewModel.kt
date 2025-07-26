
package com.example.universitywishlistapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universitywishlistapp.App
import com.example.universitywishlistapp.data.api.RetrofitInstance
import com.example.universitywishlistapp.data.db.AppDatabase
import com.example.universitywishlistapp.data.db.UniversityEntity
import com.example.universitywishlistapp.data.model.University
import com.example.universitywishlistapp.data.repository.UniversityRepository
import kotlinx.coroutines.launch

class UniversitiesViewModel : ViewModel() {
    private var allUniversities: List<University> = emptyList()

    private val repository = UniversityRepository(
        apiService = RetrofitInstance.api,
        universityDao = AppDatabase.getDatabase(App.context).universityDao()
    )

    private val _universities = MutableLiveData<List<University>>()
    val universities: LiveData<List<University>> = _universities

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun searchUniversities(name: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.searchUniversities(name)
                allUniversities = result
                _universities.value = result
            } catch (e: Exception) {
                _universities.value = emptyList()
            }
            _loading.value = false
        }
    }



    fun filterUniversitiesByCountry(country: String) {
        val filtered = allUniversities.filter {
            it.country.equals(country, ignoreCase = true)
        }
        _universities.postValue(filtered)
    }



    fun loadAllUniversities() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getAllUniversities()

                // الحصول على قائمة الأسماء من المفضلة
                val wishlistEntities = repository.getWishlistList()
                val wishlistNames = wishlistEntities.map { it.name }

                // تحديث حالة isInWishlist
                result.forEach { uni ->
                    if (uni.name in wishlistNames) {
                        uni.isInWishlist = true
                    }
                }

                allUniversities = result
                _universities.value = result
            } catch (e: Exception) {
                _universities.value = emptyList()
            }
            _loading.value = false
        }
    }



    fun toggleWishlist(university: University) {
        viewModelScope.launch {
            if (university.isInWishlist) {
                repository.removeFromWishlist(convertToEntity(university))
                university.isInWishlist = false
            } else {
                repository.addToWishlist(convertToEntity(university))
                university.isInWishlist = true
            }

            // تحديث القائمة الكاملة لتعكس التغيير
            allUniversities = allUniversities.map {
                if (it.name == university.name) university else it
            }

            _universities.value = allUniversities
        }
    }

    private fun convertToEntity(university: University) = UniversityEntity(
        name = university.name,
        web_pages = university.web_pages,
        country = university.country,
        alpha_two_code = university.alpha_two_code,
        isChecked = university.isChecked,
        isInWishlist = university.isInWishlist // ✅ أضف هذا السطر
    )


}
