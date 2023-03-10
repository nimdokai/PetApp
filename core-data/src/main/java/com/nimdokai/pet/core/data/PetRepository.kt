package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.model.BreedDetailsResponse
import com.nimdokai.pet.core.data.model.PetCategoryResponse
import com.nimdokai.pet.core.data.model.PetDetailsResponse
import com.nimdokai.pet.core.data.model.PetImageResponse
import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.model.GetBreedJson
import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import javax.inject.Inject

interface PetRepository {

    suspend fun getCategories(): DataResponse<out List<PetCategoryResponse>>
    suspend fun getBreedDetails(breedID: String): DataResponse<out BreedDetailsResponse>
    suspend fun getPetImage(imageID: String): DataResponse<out PetDetailsResponse>
    suspend fun getPetImages(
        categoryID: String,
        numberOfImages: Int = 1,
        imageSize: ImageSize = ImageSize.SMALL,
    ): DataResponse<out List<PetImageResponse>>
}

class CatRepository @Inject constructor(
    private val petApi: PetApi
) : PetRepository {

    override suspend fun getCategories(): DataResponse<out List<PetCategoryResponse>> {
        return tryCall(petApi::getCategories) { it.map { category -> category.mapToResponse() } }
    }

    override suspend fun getBreedDetails(breedID: String): DataResponse<out BreedDetailsResponse> {
        return tryCall({ petApi.getBreedDetails(breedID) }) { it.mapToResponse() }
    }

    override suspend fun getPetImage(imageID: String): DataResponse<out PetDetailsResponse> {
        return tryCall({ petApi.getImage(imageID) }) { it.mapToPetDetails() }
    }

    override suspend fun getPetImages(
        categoryID: String,
        numberOfImages: Int,
        imageSize: ImageSize,
    ): DataResponse<out List<PetImageResponse>> {
        return tryCall({ petApi.getImages(categoryID, numberOfImages, imageSize.value) }) {
            it.map { petImageJson -> petImageJson.mapToResponse() }
        }
    }

}

private fun GetBreedJson.mapToResponse(): BreedDetailsResponse {
    return BreedDetailsResponse(id, name, description, wikipediaUrl)
}

private fun PetImageJson.mapToResponse(): PetImageResponse {
    return PetImageResponse(id, url, categories.map { it.id })
}

private fun PetImageJson.mapToPetDetails(): PetDetailsResponse {
    return PetDetailsResponse(
        id,
        url,
    )
}

private fun PetCategoryJson.mapToResponse(): PetCategoryResponse {
    return PetCategoryResponse(
        id,
        name,
    )
}

@Suppress("unused")
enum class ImageSize(val value: String) {
    SMALL("small"),
    MEDIUM("med"),
    FULL("full");
}
