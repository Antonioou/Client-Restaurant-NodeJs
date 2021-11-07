package com.ntncode.restaurantclient.api

import com.ntncode.restaurantclient.model.ItemData
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @PUT("/api/item/list/{limit_req}")
    fun ItemList(@Field("limit_req") limit: Int): Call<List<ItemData>>


}