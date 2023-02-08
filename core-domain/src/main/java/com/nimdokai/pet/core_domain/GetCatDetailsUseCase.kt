package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core_domain.model.PetDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class GetCatDetailsUseCase @Inject constructor(
    private val catRepository: PetRepository
) : GetPetDetailsUseCase {

    override suspend fun invoke(
        catID: String,
    ): Flow<DomainResult<out PetDetails>> = flow {

        when (val response = catRepository.getPetImage(catID)) {
            DataResponse.NoInternet -> emit(DomainResult.NoInternet)
            DataResponse.ServerError -> emit(DomainResult.ServerError)
            is DataResponse.Success -> {
                val petDetails = response.data.run {
                    PetDetails(
                        id,
                        url,
//                        breed = breeds.first().run {
//                            Breed(
//                                id,
//                                name,
//                                description,
//                                wikiUrl
//                            )
//                        } // simplification because of lack of time
                    )
                }
                emit(DomainResult.Success(petDetails))
            }
        }
    }
}