package personal.ive.imagesearchapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import personal.ive.imagesearchapp.data.UnsplashRepo

/**
 * Created by ivasil on 5/15/2021
 */

class GalleryViewModel @ViewModelInject constructor(private val unsplashRepo: UnsplashRepo) :
    ViewModel() {

}