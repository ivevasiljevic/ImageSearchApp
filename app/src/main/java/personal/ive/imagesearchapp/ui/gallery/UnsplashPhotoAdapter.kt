package personal.ive.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import personal.ive.imagesearchapp.R
import personal.ive.imagesearchapp.data.UnsplashPhoto
import personal.ive.imagesearchapp.databinding.ItemUnsplashPhotoBinding

/**
 * Created by ivasil on 5/17/2021
 */

class UnsplashPhotoAdapter(private val onItemClickListener: OnItemClickListener) : PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(PhotoComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder bindTo currentItem
        }
    }

    inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                if(position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if(item != null) {
                        onItemClickListener.onItemClick(item)
                    }
                }
            }
        }

        infix fun bindTo(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                userName.text = photo.user.username
            }
        }
    }

    object PhotoComparator : DiffUtil.ItemCallback<UnsplashPhoto>() {

        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
            oldItem == newItem

    }

    interface OnItemClickListener {
        fun onItemClick(photo: UnsplashPhoto)
    }
}