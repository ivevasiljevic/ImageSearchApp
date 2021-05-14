package personal.ive.imagesearchapp.api

import personal.ive.imagesearchapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by ivasil on 5/14/2021
 */

interface UnsplashApi {

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
        const val ACCESS_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $ACCESS_KEY")
    @GET("search/photos")
    suspend fun searchPhotos(@Query("query") query: String, @Query("page") page: Int, @Query("per_page") perPage: Int) : UnsplashResponse
}