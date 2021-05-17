package personal.ive.imagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import personal.ive.imagesearchapp.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ivasil on 5/15/2021
 */

@Singleton
class UnsplashRepo @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        Pager(
            PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).flow
}