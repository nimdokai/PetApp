package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.api.PetApi
import com.nimdokai.pet.core.data.model.PetCategoryJson
import com.nimdokai.pet.core.data.model.PetCategoryResponse
import com.nimdokai.pet.core.data.model.PetDetails
import java.io.IOException
import javax.inject.Inject

interface PetRepository {

    suspend fun getCategories(): GetCategoriesResponse
    suspend fun getPetDetails(petID: Int): GetPetDetailsResponse
}

sealed interface GetCategoriesResponse {
    data class Success(val categories: List<PetCategoryResponse>) : GetCategoriesResponse
    object NoInternet : GetCategoriesResponse
    object ServerError : GetCategoriesResponse
}

sealed interface GetPetDetailsResponse {
    data class Success(val petDetails: PetDetails) : GetPetDetailsResponse
    object NoInternet : GetPetDetailsResponse
    object ServerError : GetPetDetailsResponse
}

class CatRepository @Inject constructor(
    private val petApi: PetApi
) : PetRepository {

    override suspend fun getCategories(): GetCategoriesResponse {
        return try {
            val response = petApi.getCategories()
            if (response.isSuccessful) {
                val jsonResponse = response.body()!!
                GetCategoriesResponse.Success(jsonResponse.map { it.mapToResponse() })
            } else {
                GetCategoriesResponse.ServerError
            }
        } catch (e: IOException) {
            GetCategoriesResponse.NoInternet
        } catch (e: Exception) {
            //ToDo Logging exception
            GetCategoriesResponse.ServerError
        }
    }

    override suspend fun getPetDetails(petID: Int): GetPetDetailsResponse {
        TODO()
    }

}

private fun PetCategoryJson.mapToResponse(): PetCategoryResponse {
    return PetCategoryResponse(
        id,
        name
    )
}
