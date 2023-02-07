package com.nimdokai.pet.core.testing.fakes

import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import retrofit2.Response

class FakePetApi : PetApi {

    var categories: Response<List<PetCategoryJson>>? = null
    var images: Response<List<PetImageJson>>? = null
    var exception: Exception? = null

    override suspend fun getCategories(): Response<List<PetCategoryJson>> {
        exception?.let { throw it }
        return categories!!
    }

    override suspend fun getImages(
        categories: String,
        limit: Int
    ): Response<List<PetImageJson>> {
        exception?.let { throw it }
        return images!!
    }

}