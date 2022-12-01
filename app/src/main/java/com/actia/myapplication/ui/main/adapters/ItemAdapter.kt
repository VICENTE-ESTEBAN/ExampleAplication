package com.actia.myapplication.ui.main.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.actia.myapplication.BR
import com.actia.myapplication.R
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.databinding.ImdbItemsBinding
import com.actia.myapplication.util.Constants


class ItemAdapter(listOfItems:List<Item>) :
    RecyclerView.Adapter<ItemAdapter.BaseViewHolder>() {

    private val listItems:MutableList<Item> = mutableListOf()

    init{
        listItems.addAll(listOfItems)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val bindingA: ImdbItemsBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.imdb_items,
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

    fun deleteItem(position: Int)
    {
        listItems.removeAt(position)
        notifyItemRemoved(position)
    }

    class BaseViewHolder(private val binding: ImdbItemsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(obj: Item) {
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

    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}