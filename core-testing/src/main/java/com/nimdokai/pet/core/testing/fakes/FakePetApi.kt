package com.nimdokai.pet.core.testing.fakes

import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.model.GetBreedJson
import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import retrofit2.Response
import retrofit2.http.Query

class FakePetApi : PetApi {

    var categories: Response<List<PetCategoryJson>>? = null
    var images: Response<List<PetImageJson>>? = null
    var image: Response<PetImageJson>? = null
    var exception: Exception? = null

    override suspend fun getCategories(): Response<List<PetCategoryJson>> {
        exception?.let { throw it }
        return categories!!
    }

    override suspend fun getImages(
        @Query(value = "category_ids") categories: String,
        @Query(value = "limit") limit: Int,
        @Query(value = "size") size: String,
    ): Response<List<PetImageJson>> {
        exception?.let { throw it }
        return images!!
    }

    override suspend fun getImage(imageID: String): Response<PetImageJson> {
        exception?.let { throw it }
        return image!!
    }

    override suspend fun getBreedDetails(breedID: String): Response<GetBreedJson> {
        TODO("Not yet implemented")
    }

}