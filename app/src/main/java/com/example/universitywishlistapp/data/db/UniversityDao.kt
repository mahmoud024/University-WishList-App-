
package com.example.universitywishlistapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UniversityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(university: UniversityEntity)

    @Delete
    suspend fun delete(university: UniversityEntity)

    @Query("SELECT * FROM universities WHERE name = :name LIMIT 1")
    suspend fun getUniversityByName(name: String): UniversityEntity?

    @Query("SELECT * FROM universities")
    fun getAll(): LiveData<List<UniversityEntity>>

    @Query("UPDATE universities SET isChecked = :checked WHERE name = :name")
    suspend fun updateChecked(name: String, checked: Boolean)

    @Query("SELECT * FROM universities WHERE isInWishlist = 1")
    fun getWishlist(): LiveData<List<UniversityEntity>>

    @Query("SELECT * FROM universities WHERE isInWishlist = 1")
    suspend fun getAllList(): List<UniversityEntity>

    @Query("UPDATE universities SET isInWishlist = 0 WHERE name = :name")
    suspend fun unmarkFromWishlist(name: String)

    @Query("UPDATE universities SET isChecked = :isChecked WHERE name = :universityName")
    suspend fun updateCheckedStatus(universityName: String, isChecked: Boolean)
}

