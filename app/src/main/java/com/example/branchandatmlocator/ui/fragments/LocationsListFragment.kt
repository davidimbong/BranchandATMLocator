package com.example.branchandatmlocator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.branchandatmlocator.databinding.FragmentLocationsListBinding
import com.example.branchandatmlocator.ui.adapter.LocatorListAdapter

/**
 * A fragment representing a list of Items.
 */
class LocationsListFragment : Fragment() {

    private var _binding: FragmentLocationsListBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<LocationsListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LocatorListAdapter(args.locationsList.toList()) { locations ->
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