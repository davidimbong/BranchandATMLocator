package com.example.branchandatmlocator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

/**
 * A simple [Fragment] subclass.
 * Use the [LocatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        lifecycleScope.launch {
//            println(
//                LocatorApi.retrofitService.getLocations(
//                    RequestBody(
//                        Type = dialog.getSelected(),
//                        Keyword = svLocator.query.toString()
//                    )
//                ).toString()
//            )
//
//            LocatorApi.retrofitService.getLocations(
//                RequestBody(
//                    Type = dialog.getSelected(),
//                    Keyword = svLocator.query.toString()
//                )
//            )
            var call: Call<Locations> = LocatorApi.retrofitService.getLocations(RequestBody(Type = "0", Keyword = "GMA"))
            call.enqueue(object : Callback<Locations>{
                override fun onResponse(call: Call<Locations>, response: Response<Locations>) {
                    if (response.code() == 200){
                        println(call.toString())
                    }
                    else {
                        println("ERROR ERROR ERROR")
                    }
                }

                override fun onFailure(call: Call<Locations>, t: Throwable) {
                    Toast.makeText(context, "FAILED TO CALL", Toast.LENGTH_SHORT).show()
                }

            })
        }

        findNavController().navigate(R.id.action_locatorFragment_to_locationsListFragment)
    }
}