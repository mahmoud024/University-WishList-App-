
package com.example.universitywishlistapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class University(
    val name: String,
    val web_pages: List<String>,
    val country: String,
    val alpha_two_code: String,
    var isChecked: Boolean = false,
    var isInWishlist: Boolean = false  // هذه الخاصية لتحديد إذا مضافة للمفضلة

) : Parcelable
