package com.example.universitywishlistapp.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.universitywishlistapp.data.db.UniversityEntity
import com.example.universitywishlistapp.databinding.ItemWishlistUniversityBinding

class WishlistAdapter(
    private var universities: List<UniversityEntity>,
    private val onRemoveFromWishlist: (UniversityEntity) -> Unit,
    private val onCheckedChanged: (UniversityEntity, Boolean) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(val binding: ItemWishlistUniversityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(university: UniversityEntity) {
            binding.tvUniversityName.text = university.name
            binding.tvCountry.text = university.country
            binding.tvWebsite.text = university.web_pages.firstOrNull() ?: "N/A"

            // Set favorite heart icon always filled (since it's wishlist)
            binding.ivFavorite.setImageResource(com.example.universitywishlistapp.R.drawable.loved)

            // Handle remove from wishlist when clicking the heart
            binding.ivFavorite.setOnClickListener {
                onRemoveFromWishlist(university)
            }

            // To avoid recycling issues, remove previous listener before setting checked state
            binding.cbChecked.setOnCheckedChangeListener(null)
            binding.cbChecked.isChecked = university.isChecked
            binding.cbChecked.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                onCheckedChanged(university, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val binding = ItemWishlistUniversityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WishlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(universities[position])
    }

    override fun getItemCount(): Int = universities.size

    fun updateList(newList: List<UniversityEntity>) {
        universities = newList
        notifyDataSetChanged()
    }
}
