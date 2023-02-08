package com.nimdokai.pet.core_network.api

import com.nimdokai.pet.core_network.model.GetBreedJson
import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PetApi {

    @GET("/v1/categories")
    suspend fun getCategories(): Response<List<PetCategoryJson>>

    @GET("/v1/images/search")
    suspend fun getImages(
        @Query("category_ids") categories: String,
        @Query("limit") limit: Int,
        @Query("size") size: String,
    ): Response<List<PetImageJson>>

    @GET("/v1/images/{imageID}")
    suspend fun getImage(
        @Path("imageID") imageID: String
    ): Response<PetImageJson>

    @GET("/v1/breeds/{breedID}")
    suspend fun getBreedDetails(
        @Path("breedID") breedID: String
    ): Response<GetBreedJson>

}