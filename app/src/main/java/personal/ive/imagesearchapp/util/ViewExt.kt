package personal.ive.imagesearchapp.util

import androidx.appcompat.widget.SearchView

/**
 * Created by ivasil on 5/17/2021
 */

inline fun SearchView.onQueryTextSubmitted(crossinline handleQuerySubmit: (String) -> Unit) {

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            handleQuerySubmit(query.orEmpty())
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    })
}