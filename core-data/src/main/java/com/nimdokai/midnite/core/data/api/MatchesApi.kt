package com.nimdokai.midnite.core.data.api

import com.nimdokai.midnite.core.data.model.AnimalCategoryJson
import retrofit2.Response
import retrofit2.http.GET

interface MatchesApi {

    @GET("/v1/categories")
    suspend fun getCategories(): Response<List<AnimalCategoryJson>>

}