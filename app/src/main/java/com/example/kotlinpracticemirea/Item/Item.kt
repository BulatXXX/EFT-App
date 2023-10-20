package com.example.kotlinpracticemirea.Item

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ItemData(val items: List<Item>)
data class ResponseData(val data: ItemData)
@Entity(tableName = "items")
data class Item(
    val name: String? ,
    val avg24hPrice: Int = 0 ,
    val description: String? = "" ,
    val height: Int = 0 ,
    val iconLink: String? = "https://assets.tarkov.dev/5bf3e03b0db834001d2c4a9c-icon.webp" ,
    @PrimaryKey
    @NonNull
    val id: String="",
    val image512pxLink: String? = "" ,
    val width: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ,
        parcel.readInt() ,
        parcel.readString() ,
        parcel.readInt() ,
        parcel.readString() ,
        parcel.readString().toString() ,
        parcel.readString() ,
        parcel.readInt() ,
    ) {
    }

    override fun writeToParcel(parcel: Parcel , flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(avg24hPrice)
        parcel.writeString(description)
        parcel.writeInt(height)
        parcel.writeString(iconLink)
        parcel.writeString(id)
        parcel.writeString(image512pxLink)
        parcel.writeInt(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}
