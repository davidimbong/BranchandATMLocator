package com.example.branchandatmlocator.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.databinding.FragmentLocatorBinding
import com.example.branchandatmlocator.ui.ActionBottom
import com.example.branchandatmlocator.ui.viewmodel.DialogState
import com.example.branchandatmlocator.ui.viewmodel.LocatorViewModel

//const val TAG = "LocatorFragment"

class LocatorFragment : Fragment() {

    private val dialog = ActionBottom.newInstace()
    private var _binding: FragmentLocatorBinding? = null
    private val binding get() = _binding!!
    private var loadingDialog: Dialog? = null

//    private val viewModel: LocatorViewModel by activityViewModels {
//        LocatorViewModelFactory(
//            (activity?.application as BaseApplication).database.locatorDao()
//        )
//    }

    private val viewModel: LocatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
            val locType = dialog.getSelected()
            val query = binding.svLocator.query.toString()

            viewModel.search(query, locType)
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
            binding.txtItemsFound.text = it
        }

        viewModel.buttonSate.observe(viewLifecycleOwner) {
            setEnabledButtons(it)
        }

        viewModel.loadingDialogState.observe(viewLifecycleOwner) {
            apiCalling(it)
        }
    }

    private fun setEnabledButtons(bool: Boolean) {
        binding.btnViewList.isEnabled = bool
        binding.btnViewMap.isEnabled = bool
    }

    private fun apiCalling(state: DialogState) {
        if (state == DialogState.SHOW) {
            loadingDialog = Dialog(requireContext())
            loadingDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            loadingDialog!!.setContentView(R.layout.api_calling_dialog)
            loadingDialog!!.setCancelable(false)
            loadingDialog!!.show()
        } else if (state == DialogState.HIDE) {
            if (loadingDialog != null) {
                loadingDialog!!.dismiss()
            }
        } else if (state == DialogState.ERROR) {
            Toast.makeText(context, "Please input a search query", Toast.LENGTH_SHORT).show()
        }
    }
}