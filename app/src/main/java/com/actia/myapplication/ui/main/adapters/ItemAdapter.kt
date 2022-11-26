package com.actia.myapplication.ui.main.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.actia.myapplication.BR
import com.actia.myapplication.R
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.databinding.ImdbItemsBinding
import com.actia.myapplication.util.Constants
import com.squareup.picasso.Picasso


class ItemAdapter(private val listItems:List<Item>) : RecyclerView.Adapter<ItemAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )

        val bindingA: ImdbItemsBinding = DataBindingUtil.inflate(
            layoutInflater,
            com.actia.myapplication.R.layout.imdb_items,
            parent,
            false
        )
        return BaseViewHolder(bindingA)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        val obj = listItems[position]
        holder.bind(obj)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.imdb_items
    }

    class BaseViewHolder(private val binding: ImdbItemsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(obj: Item) {
            val context = binding.root.context
            Picasso.get()
                .load(obj.poster)
                .resize(
                    getDimension(context, R.dimen.width_item_image),
                    getDimension(context, R.dimen.height_item_image)
                )
                .into(binding.imgPoster)

            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()

            binding.cardItem.setOnClickListener {
                redirectToDetail(obj)
            }

        }

        private fun redirectToDetail(obj: Item) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_BUNDLE_ITEM, obj)
            binding.cardItem.findNavController()
                .navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }

        private fun getDimension(context: Context, dimen:Int):Int{
            return context.resources.getDimension(R.dimen.width_item_image).toInt()
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}