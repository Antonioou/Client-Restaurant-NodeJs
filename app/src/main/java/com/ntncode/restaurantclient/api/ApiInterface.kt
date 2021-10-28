package com.ntncode.restaurantclient.api

import com.google.gson.annotations.JsonAdapter
import com.ntncode.restaurantclient.data.Item
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @PUT("/api/item/list/{limit_req}")
    fun ItemList(@Field("limit_req") limit : Int) : Call <List<Item>>


}