package com.example.kotlinpracticemirea.Item

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.regex.Pattern

data class ItemData(val items: List<Item>)
data class ResponseData(val data: ItemData)
@Entity(tableName = "items")
data class Item(
    val name: String? ,
    val avg24hPrice: Int = 0 ,
    val description: String? = "" ,
    val height: Int = 0 ,
    val iconLink: String? = "" ,
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
        fun fromString(string: String): Item {
            val pattern = Pattern.compile("Item\\(name=(.*?), avg24hPrice=(\\d+), description=(.*?), height=(\\d+), iconLink=(.*?), id=(.*?), image512pxLink=(.*?), width=(\\d+)\\)")
            val matcher = pattern.matcher(string)
            if (matcher.find()) {
                return Item(
                    matcher.group(1),
                    matcher.group(2).toInt(),
                    matcher.group(3),
                    matcher.group(4).toInt(),
                    matcher.group(5),
                    matcher.group(6),
                    matcher.group(7),
                    matcher.group(8).toInt()
                )
            } else {
                throw IllegalArgumentException("Invalid string format")
            }
        }
    }

    fun toHistoryString(): String {
        return "[${name}, ${iconLink}]"
    }



}
