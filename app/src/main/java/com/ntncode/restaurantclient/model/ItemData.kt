package com.ntncode.restaurantclient.model

import com.google.gson.annotations.SerializedName

data class ItemData(
    /*val server_code: String,
    val server_message: String? = null,*/

    @SerializedName("description_item")
    val description_item: String,
    @SerializedName("id_category_item")
    val id_category_item: Int,
    @SerializedName("id_item")
    val id_item: String,
    @SerializedName("id_type_item")
    val id_type_item: Int,
    @SerializedName("image_item")
    val image_item: String,
    @SerializedName("image_type_item")
    val image_type_item: String,
    @SerializedName("name_category_item")
    val name_category_item: String,
    @SerializedName("name_item")
    val name_item: String,
    @SerializedName("name_type_item")
    val name_type_item: String,
    @SerializedName("price_detail_item")
    val price_detail_item: Double,
    @SerializedName("qty_detail_item")
    val qty_detail_item: Int,
    @SerializedName("target_item")
    val target_item: Int,
    @SerializedName("state_item")
    val state_item: Int
)

/*
data class ItemRequest(
    @SerializedName("itemId_req")
    val itemId_req: Int,
    @SerializedName("limit_req")
    val limit_req: Int
)*/