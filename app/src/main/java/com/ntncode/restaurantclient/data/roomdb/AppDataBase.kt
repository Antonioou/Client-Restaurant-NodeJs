package com.ntncode.restaurantclient.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ntncode.restaurantclient.data.roomdb.dao.CartDAO
import com.ntncode.restaurantclient.data.roomdb.model.CartEntityModel


//https://github.com/velmurugan-murugesan/Android-Example/tree/master/RoomAndroidExample/app/src/main/java/com/example/roomandroidexample


@Database(entities = [CartEntityModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun cartDAO() : CartDAO

    companion object {

        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDataBase::class.java, "cartTemporal.db").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}