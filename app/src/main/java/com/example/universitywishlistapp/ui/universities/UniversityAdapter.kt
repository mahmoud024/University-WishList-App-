package com.example.universitywishlistapp.ui.universities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universitywishlistapp.R
import com.example.universitywishlistapp.data.model.University
import com.example.universitywishlistapp.databinding.ItemUniversityBinding

class UniversityAdapter(
    private var universities: List<University>,
    private val onItemClick: (University) -> Unit,
    private val onFavoriteClick: (University) -> Unit
) : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {

    inner class UniversityViewHolder(val binding: ItemUniversityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val binding = ItemUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityViewHolder(binding)
    }

    override fun getItemCount() = universities.size

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        val university = universities[position]
        val b = holder.binding

        b.tvUniversityName.text = university.name
        b.tvCountry.text = university.country
        b.tvWebsite.text = university.web_pages.firstOrNull() ?: ""

        b.ivFavorite.setImageResource(
            if (university.isInWishlist) R.drawable.loved
            else R.drawable.love
        )

        b.root.setOnClickListener {
            onItemClick(university)
        }

        b.ivFavorite.setOnClickListener {
            onFavoriteClick(university)
        }
    }

    fun updateList(newList: List<University>) {
        universities = newList
        notifyDataSetChanged()
    }
}
