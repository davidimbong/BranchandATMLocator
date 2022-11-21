package com.example.branchandatmlocator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.network.LocatorApi
import com.example.branchandatmlocator.repository.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class DialogState {
    SHOW, HIDE, ERROR
}

@HiltViewModel
class LocatorViewModel@Inject constructor(
    private val locationsRepository: LocationsRepository,
    application: Application
) : AndroidViewModel(application) {

    val resultsFound = MutableLiveData<String>()
    val buttonSate = MutableLiveData<Boolean>()
    val loadingDialogState = MutableLiveData<DialogState>()
    lateinit var queryList: List<Locations>

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
}