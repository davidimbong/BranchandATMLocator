package com.example.branchandatmlocator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.branchandatmlocator.BaseApplication
import com.example.branchandatmlocator.databinding.FragmentLocationsListBinding
import com.example.branchandatmlocator.ui.viewmodel.LocatorViewModel

/**
 * A fragment representing a list of Items.
 */
class LocationsListFragment : Fragment() {

//    private var columnCount = 1
//    private val viewModel: LocatorViewModel by activityViewModels {
//        LocatorViewModel.LocatorViewModelFactory(
//            (activity?.application as BaseApplication).database.locatorDao()
//        )
//    }

    private var _binding: FragmentLocationsListBinding? = null
    private val binding get() = _binding!!


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLocationsListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adapter = LocatorListAdapter { forageable ->
//            val action = LocationsListFragmentDirections
//                .actionForageableListFragmentToForageableDetailFragment(forageable.id)
//            findNavController().navigate(action)
//        }
//
//        binding.apply {
//            recyclerView.adapter = adapter
//            addForageableFab.setOnClickListener {
//                findNavController().navigate(
//                    R.id.action_forageableListFragment_to_addForageableFragment
//                )
//            }
//        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            LocationsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}