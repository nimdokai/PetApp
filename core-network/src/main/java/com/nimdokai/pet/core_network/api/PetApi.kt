package com.nimdokai.pet.core_network.api

import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PetApi {

    @GET("/v1/categories")
    suspend fun getCategories(): Response<List<PetCategoryJson>>

    @GET("/v1/images/search")
    suspend fun getImages(@QueryMap filters: Map<String, String>): Response<List<PetImageJson>>
}


sealed interface ParametersKey {
    val value: String

    object CategoryIds : ParametersKey {
        override val value: String = "category_ids"
    }
}