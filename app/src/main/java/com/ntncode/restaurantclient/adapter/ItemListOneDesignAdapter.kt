package com.ntncode.restaurantclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.model.ItemData

class ItemListOneDesignAdapter(private val mList: List<ItemData>) :
    RecyclerView.Adapter<ItemListOneDesignAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_one_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class

        /*Picasso.get()
            .load(ItemsViewModel.image_item)
            .centerCrop()
            .into(holder.imageView)*/

        // sets the text to the textview from our itemHolder class

        holder.tv_name.text = ItemsViewModel.name_item
        holder.tv_cost.text = "S/. "+ItemsViewModel.price_detail_item.toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image_prodonedesign)
        val tv_name: TextView = itemView.findViewById(R.id.tv_name_prodonedesign)
        val tv_cost: TextView = itemView.findViewById(R.id.tv_cost_prodonedesign)
    }
}