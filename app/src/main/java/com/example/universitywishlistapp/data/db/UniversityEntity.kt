
package com.example.universitywishlistapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "universities")
@TypeConverters(Converters::class)
data class UniversityEntity(
    @PrimaryKey val name: String,
    val web_pages: List<String>,
    val country: String,
    val alpha_two_code: String,
    val isChecked: Boolean = false,
    val isInWishlist: Boolean = false   

)
