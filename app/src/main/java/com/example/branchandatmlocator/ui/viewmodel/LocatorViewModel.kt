package com.example.branchandatmlocator.ui.viewmodel

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchandatmlocator.R
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.network.LocatorApi
import com.example.branchandatmlocator.ui.fragments.LocatorFragment
import kotlinx.coroutines.launch

const val TAG = "LocatorViewModel"

class LocatorViewModel : ViewModel() {

    var dialog: Dialog? = null
    val resultsFound = MutableLiveData<String>()
    val buttonSate = MutableLiveData<Boolean>()

    fun search(query: String, locType: String, context: Context?) {
        if (query.isEmpty()) {
            Toast.makeText(context, "Please input a query", Toast.LENGTH_SHORT).show()
        } else {
            displayLoadingWithText(context)
            viewModelScope.launch {
                getResults(query, locType)
            }
        }
    }

    private suspend fun getResults(query: String, locType: String) {
        val call =
            LocatorApi.retrofitService.getLocations(
                RequestBody(
                    Type = locType,
                    Keyword = query
                )
            )
        val list = call.LocationByKeyword
        resultsFound.value = "${list.size} results found"
        buttonSate.value = list.isNotEmpty()
    }

    fun displayLoadingWithText(context: Context?) {
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.api_calling_dialog)
        dialog!!.setCancelable(false)
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }


    private fun checkIfValidQuery() {

    }
}
//    private fun setEnabledButtons(bool: Boolean) {
//        binding.btnViewList.isEnabled = bool
//        binding.btnViewMap.isEnabled = bool
//    }

//    class LocatorViewModelFactory(
//        private val locatorDao: LocatorDao
//    ) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(LocatorViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return LocatorViewModel(locatorDao) as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//
//    }
//        fun viewList() {
//            if (isValid()) {
//
//
//                //findNavController().navigate(R.id.action_locatorFragment_to_locationsListFragment)
//            }
//        }
//    }
//

//class LocatorViewModelFactory(private val locatorDao: LocatorDao) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LocatorViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return LocatorViewModel(locatorDao) as T
//        }
//        throw IllegalArgumentException("Unable to construct viewmodel")
//    }
//}