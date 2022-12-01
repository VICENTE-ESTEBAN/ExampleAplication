package com.actia.myapplication.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.actia.myapplication.R
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.ui.main.adapters.ItemAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Item>) {
    recyclerView.adapter = ItemAdapter(data)

    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped( viewHolder: RecyclerView.ViewHolder, direction: Int) {
            with(recyclerView)
            {
                // below line is to notify our item is removed from adapter.
                (this.adapter as ItemAdapter).deleteItem(viewHolder.adapterPosition)
            }
        }
    }).attachToRecyclerView(recyclerView)

}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get()
            .load(url)
            .resize(
                view.context.resources.getDimension(R.dimen.width_item_image).toInt(),
                view.context.resources.getDimension(R.dimen.height_item_image).toInt()
            )
            .into(view)
    }
}
