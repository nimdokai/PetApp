package com.nimdokai.pet.core_network.api

import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PetApi {

    @GET("/v1/categories")
    suspend fun getCategories(): Response<List<PetCategoryJson>>

    @GET("/v1/images/search")
    suspend fun getImages(
        @Query("category_ids") categories: String,
        @Query("limit") limit: Int,
    ): Response<List<PetImageJson>>
}