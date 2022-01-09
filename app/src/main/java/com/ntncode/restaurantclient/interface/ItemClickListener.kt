package com.ntncode.restaurantclient.`interface`

interface ItemClickListener<T>{

    interface ListClickListener<T>{
        fun onClick(list: T, position: Int)
        fun onDelete(list: T)
    }
}