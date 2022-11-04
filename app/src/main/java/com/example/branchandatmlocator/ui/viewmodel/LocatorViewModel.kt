package com.example.branchandatmlocator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.branchandatmlocator.data.LocatorDao
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.network.LocatorApi
import com.example.branchandatmlocator.ui.ActionBottom
import com.example.branchandatmlocator.ui.fragments.LocatorFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait

const val TAG = "LocatorViewModel"
class LocatorViewModel(
    //private val locatorDao: LocatorDao
) : ViewModel() {

    private val dialog = ActionBottom.newInstace()
    private var _list: List<Locations> = listOf()
    private lateinit var query: String
    val list get() = _list

    fun showDialog() {
        dialog.show(LocatorFragment().parentFragmentManager, ActionBottom.TAG)
    }

    suspend fun search(query: String): String {
        viewModelScope.async {
            getResults(query)
        }.await()
        return "${list.size} results found"
    }

    private suspend fun getResults(query: String){
        val locType = dialog.getSelected()

        val call =
            LocatorApi.retrofitService.getLocations(
                RequestBody(
                    Type = locType,
                    Keyword = query
                )
            )
        _list = call.LocationByKeyword
        Log.d(TAG, "1st $list")
    }

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
}

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