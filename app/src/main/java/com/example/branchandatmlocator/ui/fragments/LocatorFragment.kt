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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocatorFragment : Fragment() {

    private val viewModel: LocatorViewModel by viewModels()

    private val dialog = ActionBottom.newInstance()
    private var _binding: FragmentLocatorBinding? = null
    private val binding get() = _binding!!
    private val loadingDialog: Dialog by lazy {
        Dialog(requireContext()).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCancelable(false)
            this.setContentView(R.layout.api_calling_dialog)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var locType: String
        lateinit var query: String
        binding.btnSearch.setOnClickListener {
            locType = dialog.getSelected()
            query = binding.svLocator.query.toString()

            viewModel.search(query, locType)
        }

        binding.btnViewList.setOnClickListener {
            val action =
                LocatorFragmentDirections
                    .actionLocatorFragmentToLocationsListFragment(
                        viewModel.queryList.toTypedArray()
                    )
            findNavController().navigate(action)
        }

        binding.btnViewMap.setOnClickListener {
            viewModel.refreshDataFromRepository()
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
        when (state) {
            DialogState.SHOW -> {
                loadingDialog.show()
            }

            DialogState.HIDE -> {
                loadingDialog.dismiss()
            }

            DialogState.ERROR -> {
                Toast.makeText(context, "Please input a search query", Toast.LENGTH_SHORT).show()
            }
        }
    }
}