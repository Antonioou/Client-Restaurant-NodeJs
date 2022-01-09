package com.ntncode.restaurantclient.data.roomdb.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartTemporal")
data class CartEntityModel(

    @PrimaryKey(autoGenerate = true)
    var userId: Int? = null,
    val userName: String?,
    var location: String?,
    val email: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(userId)
        parcel.writeString(userName)
        parcel.writeString(location)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartEntityModel> {
        override fun createFromParcel(parcel: Parcel): CartEntityModel {
            return CartEntityModel(parcel)
        }

        override fun newArray(size: Int): Array<CartEntityModel?> {
            return arrayOfNulls(size)
        }
    }
}