package com.ntncode.restaurantclient.data.roomdb.dao

import androidx.room.*
import com.ntncode.restaurantclient.data.roomdb.model.CartEntityModel

@Dao
interface CartDAO {

    @Insert
    fun insertCart(users: CartEntityModel)

    @Query("Select * from cartTemporal")
    fun gelAllCart(): List<CartEntityModel>

    @Update
    fun updateCart(itemCart: CartEntityModel)

    @Delete
    fun deleteCart(itemCart: CartEntityModel)

}