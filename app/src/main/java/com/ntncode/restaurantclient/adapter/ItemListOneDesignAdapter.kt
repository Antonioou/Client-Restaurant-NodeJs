package com.ntncode.restaurantclient.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.`interface`.ItemClickListener
import com.ntncode.restaurantclient.model.ItemData
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class ItemListOneDesignAdapter() : RecyclerView.Adapter<ItemListOneDesignAdapter.ViewHolder>() {

    var mList = mutableListOf<ItemData>()

    private var clickListener: ItemClickListener.ListClickListener<ItemData>? = null

    fun setItems(items: List<ItemData>) {
        this.mList = items.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_one_design, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.tv_name.text = item.name_item
        holder.tv_cost.text = "S/. ${item.price_detail_item}"

        Picasso.get()
            .load(item.image_item)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            clickListener?.onClick(item, position)
        }

        /*holder.imgDelete.setOnClickListener {
            clickListener?.onDelete(user)
        }*/

    }

    fun setOnItemClick(onClickListener: ItemClickListener.ListClickListener<ItemData>) {
        this.clickListener = onClickListener
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.iv_image_prodonedesign)
        val tv_name: TextView = itemView.findViewById(R.id.tv_name_prodonedesign)
        val tv_cost: TextView = itemView.findViewById(R.id.tv_cost_prodonedesign)
    }
}



