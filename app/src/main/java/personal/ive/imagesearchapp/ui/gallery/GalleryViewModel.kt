package personal.ive.imagesearchapp.ui.gallery

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import personal.ive.imagesearchapp.data.UnsplashRepo
import javax.inject.Inject

/**
 * Created by ivasil on 5/15/2021
 */

@HiltViewModel
class GalleryViewModel @Inject constructor(private val unsplashRepo: UnsplashRepo) :
    ViewModel() {

}