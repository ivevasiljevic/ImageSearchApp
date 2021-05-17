package personal.ive.imagesearchapp.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import personal.ive.imagesearchapp.data.UnsplashRepo
import javax.inject.Inject

/**
 * Created by ivasil on 5/15/2021
 */

@HiltViewModel
class GalleryViewModel @Inject constructor(private val unsplashRepo: UnsplashRepo) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }

    private val currentQuery = MutableStateFlow(DEFAULT_QUERY)

    private val photosFlow = currentQuery.flatMapLatest { queryString ->
        unsplashRepo.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    val photos = photosFlow.asLiveData()
}