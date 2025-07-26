
package com.example.universitywishlistapp.data.repository


import androidx.lifecycle.LiveData
import com.example.universitywishlistapp.data.api.UniversityApi
import com.example.universitywishlistapp.data.db.UniversityDao
import com.example.universitywishlistapp.data.db.UniversityEntity
import com.example.universitywishlistapp.data.model.University
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UniversityRepository(
    private val apiService: UniversityApi,
    private val universityDao: UniversityDao
) {

    // ✅ API Call: Search universities online
    suspend fun searchUniversities(query: String): List<University> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.searchUniversities(query)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }



    // ✅ Room: Add university to wishlist
    suspend fun addToWishlist(entity: UniversityEntity) {
        withContext(Dispatchers.IO) {
            val updatedEntity = entity.copy(isInWishlist = true)  // ✅ تأكيد الإضافة للمفضلة
            universityDao.insert(updatedEntity)
        }
    }


    // ✅ Room: Remove from wishlist
    suspend fun removeFromWishlist(entity: UniversityEntity) {
        withContext(Dispatchers.IO) {
            universityDao.delete(entity)
        }
    }

    // ✅ Room: Get all wishlist universities
    fun getWishlist(): LiveData<List<UniversityEntity>> {
        return universityDao.getWishlist()
    }

    // ✅ Room: Update isChecked status
    suspend fun updateCheckedStatus(name: String, checked: Boolean) {
        withContext(Dispatchers.IO) {
            universityDao.updateChecked(name, checked)
        }
    }

    private fun toEntity(university: University): UniversityEntity {
        return UniversityEntity(
            name = university.name,
            web_pages = university.web_pages,
            country = university.country,
            alpha_two_code = university.alpha_two_code,
            isChecked = false
        )
    }

    private fun toModel(entity: UniversityEntity): University {
        return University(
            name = entity.name,
            web_pages = entity.web_pages,
            country = entity.country,
            alpha_two_code = entity.alpha_two_code,
            isChecked = entity.isChecked
        )
    }

    suspend fun getAllUniversities(): List<University> {
        return apiService.getAllUniversities()
    }

    suspend fun getWishlistList(): List<UniversityEntity> {
        return universityDao.getAllList()
    }




}
