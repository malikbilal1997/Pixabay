package com.phoenixdevelopers.pixabay.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixdevelopers.pixabay.enums.GridType
import com.phoenixdevelopers.pixabay.models.ImageModel
import com.phoenixdevelopers.pixabay.repos.PixabayRepo
import com.phoenixdevelopers.pixabay.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val pixabayRepo: PixabayRepo

) : ViewModel() {

    // For Images List Pagination Purposes.
    private var _pageNumber: Int = 1

    // For Checking if Network Call Running.
    private var _fetchCall: Boolean = false

    // Created State Flow for the Progress Bar.
    private val _progressBar = MutableStateFlow(false)
    val progressBar = _progressBar.asStateFlow()

    // Created State Flow for the Images List.
    private val _imageList = MutableStateFlow(emptyList<ImageModel>())
    val imageList = _imageList.asStateFlow()

    // Created State Flow for the Grid Columns.
    private val _spanCount = MutableStateFlow(GridType.THREE_COLUMNS)
    val spanCount = _spanCount.asStateFlow()

    init {
        fetchImagesList()
    }

    fun fetchImagesList() {

        // If a fetch call is already running do nothing.
        if (!_fetchCall) {

            // Set fetch call to true to means already fetching.
            _fetchCall = true

            viewModelScope.launch {

                pixabayRepo.getImages(_pageNumber).collect {

                    when (it) {

                        is Response.Loading -> {

                            if (_pageNumber == 1) {

                                // Set Progress Bar to True for Showing the Progress Bar.
                                _progressBar.value = true
                            }
                        }

                        is Response.Success -> {

                            val oldList = _imageList.value

                            val newList = mutableListOf<ImageModel>()

                            newList.addAll(oldList)

                            newList.addAll(it.item)

                            _imageList.value = newList

                            // Increasing the Page Number to Be Fetched Next Time.
                            _pageNumber++

                            // Set Fetch Call to False mean Network Call Completed.
                            _fetchCall = false

                            // Set Progress Bar to False for Hiding the Progress Bar.
                            _progressBar.value = false

                        }

                        is Response.Error -> {

                            // Set Fetch Call to False mean Network Call Completed.
                            _fetchCall = false

                            // Set Progress Bar to False for Hiding the Progress Bar.
                            _progressBar.value = false

                            Timber.d("Error -> %s", it.throwable)
                        }
                    }

                }
            }

        } else {

            Timber.d("Error -> A Network Already Running")

        }
    }

    fun changeGridColumns() {

        if (_spanCount.value == GridType.TWO_COLUMNS) {

            _spanCount.value = GridType.THREE_COLUMNS

        } else {

            _spanCount.value = GridType.TWO_COLUMNS
        }
    }
}
