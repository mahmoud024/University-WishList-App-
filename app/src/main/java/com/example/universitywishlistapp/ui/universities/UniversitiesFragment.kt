package com.example.universitywishlistapp.ui.universities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universitywishlistapp.R
import com.example.universitywishlistapp.databinding.FragmentUniversitiesBinding
import com.example.universitywishlistapp.viewmodel.UniversitiesViewModel

class UniversitiesFragment : Fragment() {

    private var _binding: FragmentUniversitiesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UniversitiesViewModel by viewModels()
    private lateinit var adapter: UniversityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUniversitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = UniversityAdapter(
            emptyList(),
            onItemClick = { university ->
                val bundle = Bundle().apply {
                    putParcelable("university", university)
                }
                findNavController().navigate(R.id.action_universitiesFragment_to_universityDetailsFragment, bundle)
            },
            onFavoriteClick = { university ->
                viewModel.toggleWishlist(university)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // اطلب تحميل كل الجامعات عند بدء التشغيل
        viewModel.loadAllUniversities()

        // مراقبة قائمة الجامعات وتحديث الـ Adapter
        viewModel.universities.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        // زر البحث
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchUniversities(query)
            }
        }

        // فلترة حسب الدولة من القائمة الجانبية (savedStateHandle)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("selected_country")
            ?.observe(viewLifecycleOwner) { country ->
                viewModel.filterUniversitiesByCountry(country)
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
