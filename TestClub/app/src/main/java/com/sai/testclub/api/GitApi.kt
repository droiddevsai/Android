package com.sai.testclub.api

import com.sai.testclub.model.ProductsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitApi {

    @Headers("Content-Type: application/json")
    @GET("/walmartproducts/{pageNumber}/{pageSize}")
    fun getProducts(@Path("pageNumber") pageNumber: Int, @Path("pageSize") pageSize: Int): Observable<ProductsResponse>
}