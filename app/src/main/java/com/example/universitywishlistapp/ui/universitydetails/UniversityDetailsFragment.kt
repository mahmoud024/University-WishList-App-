package com.example.universitywishlistapp.ui.universitydetails


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.universitywishlistapp.data.model.University
import com.example.universitywishlistapp.databinding.FragmentUniversityDetailsBinding
import com.example.universitywishlistapp.viewmodel.UniversityDetailsViewModel

class UniversityDetailsFragment : Fragment() {

    private var _binding: FragmentUniversityDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UniversityDetailsViewModel by viewModels()

    private lateinit var university: University

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUniversityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // استلام البيانات من Arguments
        university = arguments?.getParcelable("university")!!

        // عرض البيانات
        binding.tvUniversityName.text = university.name
        binding.tvCountry.text = university.country
        binding.tvWebsite.text = university.web_pages.firstOrNull() ?: "N/A"

        binding.tvWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(university.web_pages.first()))
            startActivity(intent)
        }

        // ملاحظة: نستخدم Room هنا لاحقًا
        viewModel.isInWishlist(requireContext(), university) { inWishlist ->
            binding.btnWishlist.text = if (inWishlist) "Remove from Wishlist" else "Add to Wishlist"
        }

        binding.btnWishlist.setOnClickListener {
            viewModel.toggleWishlist(requireContext(), university) { updated ->
                binding.btnWishlist.text = if (updated) "Remove from Wishlist" else "Add to Wishlist"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
