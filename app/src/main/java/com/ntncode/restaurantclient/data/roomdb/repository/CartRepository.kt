package com.ntncode.restaurantclient.data.roomdb.repository

import android.content.Context
import android.os.AsyncTask
import com.ntncode.restaurantclient.data.roomdb.AppDataBase
import com.ntncode.restaurantclient.data.roomdb.dao.CartDAO
import com.ntncode.restaurantclient.data.roomdb.model.CartEntityModel

class CartRepository(context: Context)  {

    private var db: CartDAO = AppDataBase.getInstance(context)?.cartDAO()!!


    //Fetch All the Users
    fun getAllCart(): List<CartEntityModel> {
        return db.gelAllCart()
    }

    // Insert new user
    fun insertItemToCart(cart: CartEntityModel) {
        insertAsyncTask(db).execute(cart)
    }

    // update user
    fun updateItemToCart(cart: CartEntityModel) {
        db.updateCart(cart)
    }

    // Delete user
    fun deleteCart(cart: CartEntityModel) {
        db.deleteCart(cart)
    }

    private class insertAsyncTask(private val usersDao: CartDAO) :
        AsyncTask<CartEntityModel, Void, Void>() {

        override fun doInBackground(vararg params: CartEntityModel): Void? {
            usersDao.insertCart(params[0])
            return null
        }
    }
}