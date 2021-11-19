package com.ntncode.restaurantclient.api

import com.ntncode.restaurantclient.model.CustomerResponse
import com.ntncode.restaurantclient.model.ItemData
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    /*
    * CUSTOMER
     */

    @GET("/api/customer/{customerUID}")
    fun findOneCustomerByUID(@Query("customerUID") customerUID: String): Call<List<CustomerResponse>>

    @FormUrlEncoded
    @POST("/api/customer")
    fun registerCustomer(@Field("uid_customer") uid: String,
                         @Field("firstname_customer") firstname: String,
                         @Field("lastname_customer") lastname: String,
                         @Field("phone_customer") phone: Int?,
                         @Field("email_customer") email: String,
                         @Field("photo_customer") photo: String,):
            Call<List<CustomerResponse>>

    /*
    *ITEMS
     */

    @GET("/api/item/list/{limit}")
    fun getItemList(@Query("limit") limit: Int): Call<List<ItemData>>


}