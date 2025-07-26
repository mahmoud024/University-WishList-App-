package com.example.universitywishlistapp.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universitywishlistapp.data.db.AppDatabase
import com.example.universitywishlistapp.databinding.FragmentWishlistBinding
import com.example.universitywishlistapp.viewmodel.WishlistViewModel
import com.example.universitywishlistapp.viewmodel.WishlistViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WishlistViewModel
    private lateinit var adapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dao = AppDatabase.getDatabase(requireContext()).universityDao()
        val factory = WishlistViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[WishlistViewModel::class.java]

        adapter = WishlistAdapter(
            emptyList(),
            onRemoveFromWishlist = { university ->
                viewModel.removeFromWishlist(university)
            },
            onCheckedChanged = { university, isChecked ->
                // هنا تحديث حالة isChecked في قاعدة البيانات
                CoroutineScope(Dispatchers.IO).launch {
                    dao.updateCheckedStatus(university.name, isChecked)
                }
            }
        )

        binding.recyclerViewWishlist.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewWishlist.adapter = adapter

        viewModel.wishlist.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
