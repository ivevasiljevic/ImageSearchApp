package personal.ive.imagesearchapp.ui.gallery

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import personal.ive.imagesearchapp.data.UnsplashRepo
import javax.inject.Inject

/**
 * Created by ivasil on 5/15/2021
 */

@HiltViewModel
class GalleryViewModel @Inject constructor(private val savedState: SavedStateHandle, private val unsplashRepo: UnsplashRepo) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "current_query"
        private const val DEFAULT_QUERY = "cats"
    }

    private val currentQuery = savedState.getLiveData(QUERY_KEY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        unsplashRepo.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }
}