package com.example.branchandatmlocator.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.network.LocatorApi
import kotlinx.coroutines.launch

//const val TAG = "LocatorViewModel"

enum class DialogState {
    SHOW, HIDE, ERROR
}

class LocatorViewModel : ViewModel() {

    val resultsFound = MutableLiveData<String>()
    val buttonSate = MutableLiveData<Boolean>()
    val loadingDialogState = MutableLiveData<DialogState>()

    fun search(query: String, locType: String) {
        if (query.isNotEmpty()) {
            loadingDialogState.value = DialogState.SHOW
            viewModelScope.launch {
                getResults(query, locType)
            }
        } else {
            loadingDialogState.value = DialogState.ERROR
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
        loadingDialogState.value = DialogState.HIDE
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