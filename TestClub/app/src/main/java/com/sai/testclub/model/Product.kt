package com.sai.testclub.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val productId: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("longDescription") val longDescription: String?,
    @SerializedName("price") val price: String,
    @SerializedName("productImage") val productImage: String,
    @SerializedName("reviewRating") val reviewRating: Float,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("inStock") val inStock: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(productId)
        dest.writeString(productName)
        dest.writeString(shortDescription)
        dest.writeString(longDescription)
        dest.writeString(price)
        dest.writeString(productImage)
        dest.writeFloat(reviewRating)
        dest.writeInt(reviewCount)
        dest.writeByte(
            if (inStock) {
                1.toByte()
            } else {
                0.toByte()
            }
        )
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
