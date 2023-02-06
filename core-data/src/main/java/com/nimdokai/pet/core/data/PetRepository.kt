package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.model.PetCategoryResponse
import com.nimdokai.pet.core.data.model.PetDetailsResponse
import com.nimdokai.pet.core.data.model.PetImageResponse
import com.nimdokai.pet.core_network.api.ParametersKey
import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import javax.inject.Inject

interface PetRepository {

    suspend fun getCategories(): DataResponse<out List<PetCategoryResponse>>
    suspend fun getPetDetails(petID: Int): DataResponse<out PetDetailsResponse>
    suspend fun getPetImages(categoryID: String): DataResponse<out List<PetImageResponse>>
}

class CatRepository @Inject constructor(
    private val petApi: PetApi
) : PetRepository {

    override suspend fun getCategories(): DataResponse<out List<PetCategoryResponse>> {
        return tryCall(petApi::getCategories) { it.map { category -> category.mapToResponse() } }
    }

    override suspend fun getPetDetails(petID: Int): DataResponse<out PetDetailsResponse> {
        TODO()
    }

    override suspend fun getPetImages(categoryID: String): DataResponse<out List<PetImageResponse>> {
        val parameters = mapOf(ParametersKey.CategoryIds.value to categoryID)
        val response =
            tryCall({ petApi.getImages(parameters) }) {
                it.map { petImageJson -> petImageJson.mapToResponse() }
            }
        return response
    }

}

private fun PetImageJson.mapToResponse(): PetImageResponse {
    return PetImageResponse(id, url, categories.map { it.id })
}

private fun PetCategoryJson.mapToResponse(): PetCategoryResponse {
    return PetCategoryResponse(
        id,
        name,
    )
}