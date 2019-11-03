package com.sai.testclub.model

import com.google.gson.annotations.SerializedName

class ProductsResponse(
    @SerializedName("products") val products: Array<Product>?,
    @SerializedName("totalProducts") val totalProducts: Int,
    @SerializedName("pageNumber") val pageNumber: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("statusCode") val statusCode: Int
)