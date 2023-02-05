package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core.data.model.PetCategoryResponse
import com.nimdokai.pet.core.data.model.PetImageResponse
import com.nimdokai.pet.core_domain.model.PetCategory
import javax.inject.Inject


interface GetPetCategoriesUseCase {

    suspend operator fun invoke(): DomainResult<out List<PetCategory>>

}

class GetCatCategoriesUseCase @Inject constructor(
    private val repo: PetRepository
) : GetPetCategoriesUseCase {
    override suspend operator fun invoke(): DomainResult<out List<PetCategory>> {

        return when (val categoriesResponse = repo.getCategories()) {
            is DataResponse.Success -> {
                val ids = categoriesResponse.data.map { it.id }
                val images = when (val imagesResponse = repo.getPetImages(ids)) {
                    DataResponse.NoInternet -> emptyList()
                    DataResponse.ServerError -> emptyList()
                    is DataResponse.Success -> imagesResponse.data
                }
                val categories = mapToCategories(categoriesResponse.data, images)
                DomainResult.Success(categories)
            }
            DataResponse.NoInternet -> DomainResult.NoInternet
            DataResponse.ServerError -> DomainResult.ServerError
        }
    }
}

fun mapToCategories(
    categories: List<PetCategoryResponse>,
    images: List<PetImageResponse>
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