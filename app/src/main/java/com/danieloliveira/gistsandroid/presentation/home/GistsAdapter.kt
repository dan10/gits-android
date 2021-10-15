package com.danieloliveira.gistsandroid.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.danieloliveira.gistsandroid.databinding.ItemGistBinding
import com.danieloliveira.gistsandroid.domain.model.GistModel

class GistsAdapter(
    private val onSelectFavorite: (
        item: GistModel,
        position: Int
    ) -> Unit = { _: GistModel, _: Int -> }
) : PagingDataAdapter<GistModel, GistsAdapter.GistViewHolder>(GistsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistViewHolder {
        return GistViewHolder(
            binding = ItemGistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: GistViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            (payloads[0] as? GistModel)?.let { holder.bindGist(it) }
        }
    }

    override fun onBindViewHolder(holder: GistViewHolder, position: Int) {
        getItem(position)?.let { holder.bindGist(it) }
    }

    inner class GistViewHolder(
        private val binding: ItemGistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindGist(item: GistModel) {
            with(binding) {
                val title = if (item.description.isEmpty()) item.files[0].fileName
                else item.description
                tvDescription.text = title
                tvFilesTypes.text = item.files.joinToString(separator = ", ") { it.type }
                imgUser.load(item.owner.imageUrl)
                tvUserName.text = item.owner.login
                imgFavorite.isSelected = item.isFavorite
                imgFavorite.setOnClickListener { onSelectFavorite(item, absoluteAdapterPosition) }
            }
        }
    }
}

object GistsComparator : DiffUtil.ItemCallback<GistModel>() {
    override fun areItemsTheSame(oldItem: GistModel, newItem: GistModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GistModel, newItem: GistModel): Boolean {
        return oldItem == newItem
    }
}