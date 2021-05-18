package personal.ive.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import personal.ive.imagesearchapp.R
import personal.ive.imagesearchapp.data.UnsplashPhoto
import personal.ive.imagesearchapp.databinding.FragmentGalleryBinding
import personal.ive.imagesearchapp.util.onQueryTextSubmitted

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), UnsplashPhotoAdapter.OnItemClickListener {

    private lateinit var searchView: SearchView
    private val viewModel: GalleryViewModel by viewModels()
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        _binding = FragmentGalleryBinding.bind(view)

        val unsplashPhotoAdapter = UnsplashPhotoAdapter(this)

        binding.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = unsplashPhotoAdapter.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoLoadStateAdapter(unsplashPhotoAdapter::retry),
                    footer = UnsplashPhotoLoadStateAdapter(unsplashPhotoAdapter::retry)
                )
            }
            buttonRetry.setOnClickListener {
                unsplashPhotoAdapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            unsplashPhotoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            unsplashPhotoAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.apply {
                    progressBar.isVisible = loadStates.source.refresh is LoadState.Loading
                    recyclerView.isVisible = loadStates.source.refresh is LoadState.NotLoading
                    buttonRetry.isVisible = loadStates.source.refresh is LoadState.Error
                    textViewError.isVisible = loadStates.source.refresh is LoadState.Error

                    if (loadStates.source.refresh is LoadState.NotLoading &&
                        loadStates.append.endOfPaginationReached &&
                        unsplashPhotoAdapter.itemCount < 1
                    ) {
                        recyclerView.isVisible = false
                        textViewEmpty.isVisible = true
                    } else {
                        textViewEmpty.isVisible = false
                    }

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.onQueryTextSubmitted {
            binding.recyclerView.scrollToPosition(0)
            viewModel.currentQuery.value = it
            searchView.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
        _binding = null
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}