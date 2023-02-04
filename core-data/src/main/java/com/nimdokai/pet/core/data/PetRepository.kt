package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.model.PetCategory
import com.nimdokai.pet.core.data.model.PetDetails
import com.nimdokai.pet.core.data.model.PetImage
import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.model.PetCategoryJson
import java.io.IOException
import javax.inject.Inject

interface PetRepository {

    suspend fun getCategories(): DataResponse<out List<PetCategory>>
    suspend fun getPetDetails(petID: Int): DataResponse<out PetDetails>
    suspend fun getPetImage(petID: Int): DataResponse<out PetImage>
}

sealed interface DataResponse<Data> {
    data class Success<Data>(val data: Data) : DataResponse<Data>
    object NoInternet : DataResponse<Nothing>
    object ServerError : DataResponse<Nothing>
}

class CatRepository @Inject constructor(
    private val petApi: PetApi
) : PetRepository {

    override suspend fun getCategories(): DataResponse<out List<PetCategory>> {
        return try {
            val response = petApi.getCategories()
            if (response.isSuccessful) {
                val jsonResponse = response.body()!!
                DataResponse.Success(jsonResponse.map { it.mapToResponse() })
            } else {
                DataResponse.ServerError
            }
        } catch (e: IOException) {
            DataResponse.NoInternet
        } catch (e: Exception) {
            //ToDo Logging exception
            DataResponse.ServerError
        }
    }

    override suspend fun getPetDetails(petID: Int): DataResponse<out PetDetails> {
        TODO()
    }

    override suspend fun getPetImage(petID: Int): DataResponse<out PetImage> {
        TODO()
    }

}

private fun PetCategoryJson.mapToResponse(): PetCategory {
    return PetCategory(
        id,
        name,
    )
}
