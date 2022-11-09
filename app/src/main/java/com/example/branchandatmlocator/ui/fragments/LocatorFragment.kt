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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.databinding.FragmentLocatorBinding
import com.example.branchandatmlocator.ui.ActionBottom
import com.example.branchandatmlocator.ui.viewmodel.DialogState
import com.example.branchandatmlocator.ui.viewmodel.LocationsMapViewModel
import com.example.branchandatmlocator.ui.viewmodel.LocatorViewModel

//const val TAG = "LocatorFragment"

class LocatorFragment : Fragment() {

    private val viewModel: LocatorViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            LocatorViewModel.LocatorFactory(activity.application)
        )
            .get(LocatorViewModel::class.java)
    }

    private val dialog = ActionBottom.newInstace()
    private var _binding: FragmentLocatorBinding? = null
    private val binding get() = _binding!!
    private val loadingDialog: Dialog by lazy {
        Dialog(requireContext()).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCancelable(false)
            this.setContentView(R.layout.api_calling_dialog)
        }
    }

//    private val viewModel: LocatorViewModel by activityViewModels {
//        LocatorViewModelFactory(
//            (activity?.application as BaseApplication).database.locatorDao()
//        )
//    }

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
            goToNextScreen(R.id.action_locatorFragment_to_locationsListFragment, query, locType)
        }

        binding.btnViewMap.setOnClickListener {
            goToNextScreen(R.id.action_locatorFragment_to_mapLocationsFragment, query, locType)
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

    private fun goToNextScreen(location: Int, keyword: String, loctype: String) {
        viewModel.refreshDataFromRepository()
        setEnabledButtons(false)
        findNavController().navigate(location)
    }
}