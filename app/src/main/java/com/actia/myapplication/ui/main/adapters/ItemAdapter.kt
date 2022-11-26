package com.actia.myapplication.ui.main.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.actia.myapplication.BR
import com.actia.myapplication.R
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.databinding.ImdbItemsBinding
import com.actia.myapplication.util.Constants
import com.actia.myapplication.util.Constants.EMPTY_FIELD
import com.squareup.picasso.Picasso


class ItemAdapter(private val listOfItems:List<Item>) :
    RecyclerView.Adapter<ItemAdapter.BaseViewHolder>(),
    Filterable {

    private val listItems: ArrayList<Item> by lazy {
        val myArray = arrayListOf<Item>()

        myArray.addAll(listOfItems)

        myArray
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

    class BaseViewHolder(private val binding: ImdbItemsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(obj: Item) {
            val context = binding.root.context

            Picasso.get()
                .load(parseURL(obj.poster))
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

        private fun parseURL(value:String?): String? {
            return if (value.isNullOrEmpty() || value == EMPTY_FIELD)
                null
            else
                value
        }

        private fun redirectToDetail(obj: Item) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_BUNDLE_ITEM, obj)
            binding.cardItem.findNavController()
                .navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }

        private fun getDimension(context: Context, dimen:Int):Int{
            return context.resources.getDimension(dimen).toInt()
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getFilter(): Filter {
        return yearFilter
    }

    private val yearFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: List<Item> = if(constraint == Constants.SHOW_ALL_YEARS)
            {
                fillArrayOfData()
            }
            else {
                listOfItems.filter {
                    it.releaseYear == constraint
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results

        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            listItems.clear()
            listItems.addAll(results.values as List<Item>)
            notifyDataSetChanged()
        }
    }


    private fun fillArrayOfData(): ArrayList<Item> {
        val myArray = arrayListOf<Item>()

        myArray.addAll(listOfItems)

        return myArray
    }

}