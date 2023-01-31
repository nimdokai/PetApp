@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.api.PetApi
import com.nimdokai.pet.core.data.model.PetCategoryJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class CatRepositoryTest {

    private lateinit var repository: PetRepository
    private val fakePetApi = FakePetApi()

    @Before
    fun setUp() {
        repository = CatRepository(fakePetApi)
    }

    @Test
    fun `GIVEN success empty list of categories WHEN getCategories called THEN Success returned`() =
        runTest {
            //GIVEN
            fakePetApi.categories = Response.success(emptyList())

            //WHEN
            val categories = repository.getCategories()

            //THEN

            Assert.assertEquals(GetCategoriesResponse.Success(emptyList()), categories)
        }

    @Test
    fun `GIVEN exception WHEN getCategories called THEN ServerError returned`() =
        runTest {
            //GIVEN
            fakePetApi.categories = null

            //WHEN
            val categories = repository.getCategories()

            //THEN

            Assert.assertEquals(GetCategoriesResponse.ServerError, categories)
        }

    @Test
    fun `GIVEN error WHEN getCategories called THEN ServerError returned`() =
        runTest {
            //GIVEN
            fakePetApi.categories = Response.error(401, ResponseBody.create(null, "unauthorized"))

            //WHEN
            val categories = repository.getCategories()

            //THEN

            Assert.assertEquals(GetCategoriesResponse.ServerError, categories)
        }

    @Test
    fun `GIVEN no internet WHEN getCategories called THEN NoInternet returned`() =
        runTest {
            //GIVEN
            fakePetApi.internetAccessible = false

            //WHEN
            val categories = repository.getCategories()

            //THEN

            Assert.assertEquals(GetCategoriesResponse.NoInternet, categories)
        }
}


private class FakePetApi : PetApi {

    var categories: Response<List<PetCategoryJson>>? = null
    var internetAccessible = true

    override suspend fun getCategories(): Response<List<PetCategoryJson>> {

        if (!internetAccessible) throw IOException("no internet")

        return categories?.let {
            categories
        } ?: throw NullPointerException()
    }

}