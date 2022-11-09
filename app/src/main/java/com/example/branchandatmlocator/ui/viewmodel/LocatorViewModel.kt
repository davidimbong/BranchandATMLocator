package com.example.branchandatmlocator.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.branchandatmlocator.data.LocatorDao
import com.example.branchandatmlocator.data.getDatabase
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.network.LocatorApi
import com.example.branchandatmlocator.repository.LocationsRepository
import kotlinx.coroutines.launch

//const val TAG = "LocatorViewModel"

enum class DialogState {
    SHOW, HIDE, ERROR
}

class LocatorViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val locationsRepository = LocationsRepository(getDatabase(application))
    val locationsList = locationsRepository.locations

    val resultsFound = MutableLiveData<String>()
    val buttonSate = MutableLiveData<Boolean>()
    val loadingDialogState = MutableLiveData<DialogState>()
    private lateinit var queryList: List<Locations>
//    val queryDetails = MutableLiveData<MutableList<String>>()

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            locationsRepository.refreshRepository(queryList)
        }
    }

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
        queryList = call.LocationByKeyword
        resultsFound.value = "${queryList.size} results found"
        buttonSate.value = queryList.isNotEmpty()
        loadingDialogState.value = DialogState.HIDE
    }
    class LocatorFactory(
        val app: Application,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocatorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LocatorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}