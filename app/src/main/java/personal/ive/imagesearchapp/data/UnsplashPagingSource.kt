package personal.ive.imagesearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import personal.ive.imagesearchapp.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by ivasil on 5/17/2021
 */

const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(private val unsplashApi: UnsplashApi, private val query: String) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if(position == UNSPLASH_STARTING_PAGE_INDEX) null else position.minus(1),
                nextKey = if(photos.isEmpty()) null else position.plus(1)
            )
        }
        //Make this better.
        catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}