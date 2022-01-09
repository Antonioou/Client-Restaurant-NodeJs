package com.ntncode.restaurantclient.model

import com.google.gson.annotations.SerializedName

data class ItemData(
    @SerializedName("id_item")
    val id_item: String,
    @SerializedName("name_item")
    val name_item: String,
    @SerializedName("description_item")
    val description_item: String,
    @SerializedName("image_item")
    val image_item: String,
    @SerializedName("target_item")
    val target_item: Int,
    @SerializedName("state_item")
    val state_item: Int,
    @SerializedName("id_category_item")
    val id_category_item: Int,
    @SerializedName("name_category_item")
    val name_category_item: String,
    @SerializedName("id_type_item")
    val id_type_item: Int,
    @SerializedName("name_type_item")
    val name_type_item: String,
    @SerializedName("image_type_item")
    val image_type_item: String,
    @SerializedName("price_detail_item")
    val price_detail_item: Double,
    @SerializedName("qty_detail_item")
    val qty_detail_item: Int
)


data class ItemResponse(
    @SerializedName("code_server")
    val code_server: Int,
    @SerializedName("code_status")
    val code_status: Int,
    @SerializedName("message_server")
    val message_server: String,
    @SerializedName("response")
    val response: ItemData


)