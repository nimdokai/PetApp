package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core.data.model.PetCategoryResponse
import com.nimdokai.pet.core.data.model.PetImageResponse
import com.nimdokai.pet.core_domain.model.PetCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCatCategoriesUseCase @Inject constructor(
    private val repo: PetRepository
) : GetPetCategoriesUseCase {
    override suspend operator fun invoke(): Flow<DomainResult<out List<PetCategory>>> = flow {

        when (val categoriesResponse = repo.getCategories()) {
            is DataResponse.Success -> {
                val categories = mapToCategories(categoriesResponse.data)
                emit(DomainResult.Success(categories))
                coroutineScope() {
                    val images = categoriesResponse.data.map {
                        val id = it.id.toString()
                        async {
                            when (val imagesResponse = repo.getPetImages(
                                categoryID = id,
                            )) {
                                DataResponse.NoInternet -> emptyList()
                                DataResponse.ServerError -> emptyList()
                                is DataResponse.Success -> imagesResponse.data
                            }
                        }
                    }.awaitAll()
                        .flatten()
                    val categoriesWithImages = mapToCategories(categoriesResponse.data, images)
                    emit(DomainResult.Success(categoriesWithImages))
                }
            }

            DataResponse.NoInternet -> emit(DomainResult.NoInternet)
            DataResponse.ServerError -> emit(DomainResult.ServerError)
        }
    }
}

fun mapToCategories(
    categories: List<PetCategoryResponse>,
    images: List<PetImageResponse> = emptyList()
): List<PetCategory> {

    val imagesByID = images
        .associateBy { it.categoriesIDs.first() }
        .mapValues { it.value.imageUrl }

    return categories.map {
        PetCategory(
            id = it.id,
            name = it.name,
            imageUrl = imagesByID[it.id] ?: ""
        )
    }
}