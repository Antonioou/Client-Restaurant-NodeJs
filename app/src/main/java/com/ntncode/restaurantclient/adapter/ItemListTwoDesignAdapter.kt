package com.ntncode.restaurantclient.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ntncode.restaurantclient.R
import com.ntncode.restaurantclient.`interface`.ItemClickListener
import com.ntncode.restaurantclient.data.roomdb.model.CartEntityModel

class ItemListTwoDesignAdapter : RecyclerView.Adapter<ItemListTwoDesignAdapter.ViewHolderCC>() {

    private var mList = mutableListOf<CartEntityModel>()

    private var clickListener: ItemClickListener.ListClickListener<CartEntityModel>? = null


    fun setItems(items: List<CartEntityModel>) {
        this.mList = items.toMutableList()
        Log.e("LOG_ADAPTERTD", "ACTION: SET ITEM \n${items.toString()}")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCC {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_two_design, parent, false)
        return ViewHolderCC(view)
    }


    override fun onBindViewHolder(holder: ViewHolderCC, position: Int) {

        val item = mList[1]

        /*.get()
            .load(item.email.toString())
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .into(holder.imageView)*/

        Log.e("LOG_ADAPTER", "ACTION: BIND VIEW HOLDER \n${item.toString()}")

        holder.tvname.text = item.toString()


        /*holder.itemView.setOnClickListener {
            clickListener?.onClick(item, position)
        }*/


        /*holder.imgDelete.setOnClickListener {
            clickListener?.onDelete(user)
        }*/

    }

    fun setOnItemClick(onClickListener: ItemClickListener.ListClickListener<CartEntityModel>) {
        this.clickListener = onClickListener
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolderCC(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.iv_image_prodtwodesign)
        val tvname: TextView = itemView.findViewById(R.id.tvitem0000)
    }
}



