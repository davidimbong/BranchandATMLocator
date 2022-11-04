package com.example.branchandatmlocator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.databinding.FragmentLocatorBinding
import com.example.branchandatmlocator.ui.viewmodel.LocatorViewModel
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_fragment.*
import kotlinx.android.synthetic.main.fragment_locator.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "LocatorFragment"

class LocatorFragment : Fragment() {

    private var _binding: FragmentLocatorBinding? = null
    private val binding get() = _binding!!

//    private val viewModel: LocatorViewModel by activityViewModels {
//        LocatorViewModelFactory(
//            (activity?.application as BaseApplication).database.locatorDao()
//        )
//    }

    private val viewModel: LocatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var query: String
        binding.btnSearch.setOnClickListener {
            query = svLocator.query.toString()
            if (svLocator.query.isNotBlank()) {
                GlobalScope.launch() {
                    txtItemsFound.text = viewModel.search(query)
                }
                if (viewModel.list.isNotEmpty()) {
                    binding.btnViewList.isEnabled = true
                    binding.btnViewMap.isEnabled = true
                } else {
                    binding.btnViewList.isEnabled = false
                    binding.btnViewMap.isEnabled = false
                }
            }
        }

        binding.btnViewList.setOnClickListener {
            findNavController().navigate(R.id.action_locatorFragment_to_locationsListFragment)
        }

        binding.btnViewMap.setOnClickListener {
            findNavController().navigate(R.id.action_locatorFragment_to_mapLocationsFragment)
        }

        binding.btnTypeFilter.setOnClickListener {
            viewModel.showDialog()
        }
    }

}