package com.example.branchandatmlocator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.branchandatmlocator.databinding.FragmentLocationsListBinding
import com.example.branchandatmlocator.ui.adapter.LocatorListAdapter
import com.example.branchandatmlocator.ui.viewmodel.LocationsDetailedViewModel
import com.example.branchandatmlocator.ui.viewmodel.LocationsListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class LocationsListFragment : Fragment() {

    private var _binding: FragmentLocationsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationsListViewModel by viewModels()
    private val detailedViewModel: LocationsDetailedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.locationsList.observe(viewLifecycleOwner) {
            val adapter = LocatorListAdapter(it) { locations ->
                detailedViewModel.locationsDetailed = locations
                val action = LocationsListFragmentDirections
                    .actionLocationsListFragmentToLocationsDetailedFragment(
                        locations.name
                    )

                findNavController().navigate(action)
            }

            binding.apply {
                recyclerView.adapter = adapter
            }
        }
    }
}