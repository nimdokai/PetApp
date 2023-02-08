package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.ImageSize
import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core_domain.model.PetImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCatCategoryFeedUseCase @Inject constructor(private val catRepository: PetRepository) :
    GetPetCategoryFeedUseCase {

    override suspend fun invoke(categoryID: String): Flow<DomainResult<out List<PetImage>>> = flow {

        //ToDo it's not optimal to do 1 big call, I could actually implement pagination
        when (val response =
            catRepository.getPetImages(
                categoryID = categoryID,
                numberOfImages = 25,
                imageSize = ImageSize.MEDIUM,
            )) {
            is DataResponse.Success -> {
                val petImages = response.data.map {
                    PetImage(
                        it.id,
                        it.imageUrl,
                        it.categoriesIDs,
                    )
                }
                emit(DomainResult.Success(petImages))
            }

            DataResponse.NoInternet -> emit(DomainResult.NoInternet)
            DataResponse.ServerError -> emit(DomainResult.ServerError)
        }
    }

}