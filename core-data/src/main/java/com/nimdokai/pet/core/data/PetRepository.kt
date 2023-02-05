package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.model.PetCategoryResponse
import com.nimdokai.pet.core.data.model.PetDetailsResponse
import com.nimdokai.pet.core.data.model.PetImageResponse
import com.nimdokai.pet.core_network.api.ParametersKey
import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PetRepository {

    suspend fun getCategories(): DataResponse<out List<PetCategoryResponse>>
    suspend fun getPetDetails(petID: Int): DataResponse<out PetDetailsResponse>
    suspend fun getPetImages(petIDs: List<Int>): DataResponse<out List<PetImageResponse>>
}

sealed interface DataResponse<Data> {
    data class Success<Data>(val data: Data) : DataResponse<Data>
    object NoInternet : DataResponse<Nothing>
    object ServerError : DataResponse<Nothing>
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

    override suspend fun getPetImages(categoryIDs: List<Int>): DataResponse<out List<PetImageResponse>> {

        //ToDo use Flow instead

//        coroutineScope {
//            val async = async {
//                for (category in categoryIDs) {
//                    val parameters =
//                        mapOf(ParametersKey.CategoryIds.value to categoryIDs.joinToString())
//
//                        return@async tryCall({ petApi.getImages(parameters) }) { it.map { petImageJson -> petImageJson.mapToResponse() } }
//                }
//            }.join()
//        }
//
//        return
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

fun <P1, P2, R> Function2<P1, P2, R>.curried(): (P1) -> (P2) -> R {
    return { p1: P1 -> { p2: P2 -> this(p1, p2) } }
}