package com.example.branchandatmlocator.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.databinding.FragmentLocatorBinding
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.network.LocatorApi
import com.example.branchandatmlocator.ui.ActionBottom
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_fragment.*
import kotlinx.android.synthetic.main.fragment_locator.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [LocatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
const val TAG = "LocatorFragment"

class LocatorFragment : Fragment() {

    private lateinit var binding: FragmentLocatorBinding
    private val dialog = ActionBottom.newInstace()
    var selected = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLocatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnViewList.setOnClickListener {
            viewList()
        }

        binding.btnViewMap.setOnClickListener {
            findNavController().navigate(R.id.action_locatorFragment_to_mapLocationsFragment)
        }

        binding.btnTypeFilter.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        dialog.show(parentFragmentManager, ActionBottom.TAG)
    }

    private fun viewList() {
        if (isValid()) {
            lifecycleScope.launch {
                val keyword = svLocator.query.toString()
                val locType = dialog.getSelected()

                val call =
                    LocatorApi.retrofitService.getLocations(
                        RequestBody(
                            Type = locType,
                            Keyword = keyword
                        )
                    )
                Log.d(TAG, call.toString())

                val list: List<Locations> = call.LocationByKeyword

                var x = 0
                while (x < list.size){
                    Log.d(TAG, list[x].toString())
                    x++
                }


//                val call1 =
//                    LocatorApi.retrofitService.getLocations1(
//                        RequestBody(
//                            Type = locType,
//                            Keyword = keyword
//                        )
//                    )
//                Log.d(TAG, call1.toString())

                //findNavController().navigate(R.id.action_locatorFragment_to_locationsListFragment)
            }
        }
        else{
            Toast.makeText(context, "Please input a search query.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValid() : Boolean{
        return svLocator.query.isNotBlank()
    }
}