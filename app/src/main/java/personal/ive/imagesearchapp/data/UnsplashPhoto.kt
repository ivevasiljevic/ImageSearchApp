package personal.ive.imagesearchapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by ivasil on 5/14/2021
 */

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
) : Parcelable {

    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(val name: String, val username: String) : Parcelable {
        val attributionUrl: String
            get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}
