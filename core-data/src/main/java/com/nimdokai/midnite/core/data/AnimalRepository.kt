package com.nimdokai.midnite.core.data

import com.nimdokai.midnite.core.data.api.MatchesApi
import com.nimdokai.midnite.core.data.model.AnimalCategoryJson
import com.nimdokai.midnite.core.data.model.AnimalCategoryResponse
import com.nimdokai.midnite.core.data.model.AnimalDetails
import java.io.IOException
import javax.inject.Inject

interface AnimalRepository {

    suspend fun getCategories(): GetCategoriesResponse
    suspend fun getAnimalDetails(matchID: Int): GetMatchDetailsResponse
}

sealed interface GetCategoriesResponse {
    data class Success(val categories: List<AnimalCategoryResponse>) : GetCategoriesResponse
    object NoInternet : GetCategoriesResponse
    object ServerError : GetCategoriesResponse
}

sealed interface GetMatchDetailsResponse {
    data class Success(val animalDetails: AnimalDetails) : GetMatchDetailsResponse
    object NoInternet : GetMatchDetailsResponse
    object ServerError : GetMatchDetailsResponse
}

class CatRepository @Inject constructor(
    private val matchesApi: MatchesApi
) : AnimalRepository {

    override suspend fun getCategories(): GetCategoriesResponse {
        return try {
            val response = matchesApi.getCategories()
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

    override suspend fun getAnimalDetails(matchID: Int): GetMatchDetailsResponse {
        TODO()
    }

}

private fun AnimalCategoryJson.mapToResponse(): AnimalCategoryResponse {
    return AnimalCategoryResponse(
        id,
        name
    )
}
