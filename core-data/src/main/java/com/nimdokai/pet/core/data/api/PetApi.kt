package com.nimdokai.pet.core.data.api

import com.nimdokai.pet.core.data.model.PetCategoryJson
import retrofit2.Response
import retrofit2.http.GET

interface PetApi {

    @GET("/v1/categories")
    suspend fun getCategories(): Response<List<PetCategoryJson>>

}