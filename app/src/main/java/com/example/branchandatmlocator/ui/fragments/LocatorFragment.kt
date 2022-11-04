package com.example.branchandatmlocator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.databinding.FragmentLocatorBinding
import com.example.branchandatmlocator.ui.ActionBottom
import com.example.branchandatmlocator.ui.viewmodel.LocatorViewModel
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_fragment.*
import kotlinx.android.synthetic.main.fragment_locator.*
import java.util.*

const val TAG = "LocatorFragment"

class LocatorFragment : Fragment() {

    private val dialog = ActionBottom.newInstace()
    private var _binding: FragmentLocatorBinding? = null
    val binding get() = _binding!!

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
            val locType = dialog.getSelected()
            query = svLocator.query.toString()

            viewModel.search(query, locType, context)
        }

        binding.btnViewList.setOnClickListener {
            findNavController().navigate(R.id.action_locatorFragment_to_locationsListFragment)
        }

        binding.btnViewMap.setOnClickListener {
            findNavController().navigate(R.id.action_locatorFragment_to_mapLocationsFragment)
        }

        binding.btnTypeFilter.setOnClickListener {
            dialog.show(parentFragmentManager, ActionBottom.TAG)
        }

        viewModel.resultsFound.observe(viewLifecycleOwner) {
            txtItemsFound.text = it
            viewModel.hideLoading()
        }

        viewModel.buttonSate.observe(viewLifecycleOwner){
            setEnabledButtons(it)
        }
    }

    private fun setEnabledButtons(bool: Boolean) {
        binding.btnViewList.isEnabled = bool
        binding.btnViewMap.isEnabled = bool
    }

}