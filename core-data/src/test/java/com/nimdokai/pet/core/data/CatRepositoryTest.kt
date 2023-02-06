@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.model.PetCategoryResponse
import com.nimdokai.pet.core.data.model.PetImageResponse
import com.nimdokai.pet.core.testing.fakes.FakePetApi
import com.nimdokai.pet.core_network.model.PetCategoryJson
import com.nimdokai.pet.core_network.model.PetImageJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
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

            Assert.assertEquals(
                DataResponse.Success<List<PetCategoryResponse>>(emptyList()),
                categories
            )
        }

    @Test
    fun `GIVEN success with 2 categories WHEN getCategories called THEN Success returned`() =
        runTest {
            //GIVEN
            fakePetApi.categories = Response.success(
                listOf(
                    PetCategoryJson(
                        1,
                        "name 1"
                    ),
                    PetCategoryJson(
                        1,
                        "name 1"
                    ),
                )
            )

            //WHEN
            val categories = repository.getCategories()

            //THEN
            val expected = DataResponse.Success(
                listOf(
                    PetCategoryResponse(
                        1,
                        "name 1"
                    ),
                    PetCategoryResponse(
                        1,
                        "name 1"
                    ),
                )
            )

            Assert.assertEquals(categories, expected)
        }

    @Test
    fun `GIVEN exception WHEN getCategories called THEN ServerError returned`() =
        runTest {
            //GIVEN
            fakePetApi.categories = null

            //WHEN
            val categories = repository.getCategories()

            //THEN

            Assert.assertEquals(DataResponse.ServerError, categories)
        }

    @Test
    fun `GIVEN error WHEN getCategories called THEN ServerError returned`() =
        runTest {
            //GIVEN
            fakePetApi.categories = Response.error(401, "unauthorized".toResponseBody(null))

            //WHEN
            val categories = repository.getCategories()

            //THEN

            Assert.assertEquals(DataResponse.ServerError, categories)
        }

    @Test
    fun `GIVEN no internet WHEN getCategories called THEN NoInternet returned`() =
        runTest {
            //GIVEN
            fakePetApi.exception = IOException()

            //WHEN
            val categories = repository.getCategories()

            //THEN

            Assert.assertEquals(DataResponse.NoInternet, categories)
        }

    @Test
    fun `GIVEN success empty list of images WHEN getPetImages called THEN Success returned`() =
        runTest {
            //GIVEN
            fakePetApi.images = Response.success(emptyList())

            //WHEN
            val images = repository.getPetImages("categoryID")

            //THEN

            Assert.assertEquals(
                DataResponse.Success<List<PetImageResponse>>(emptyList()),
                images
            )
        }

    @Test
    fun `GIVEN success with 2 images WHEN getPetImages called THEN Success returned`() =
        runTest {
            //GIVEN
            fakePetApi.images = Response.success(
                listOf(
                    PetImageJson(
                        categories = listOf(PetImageJson.Category(1, "category name 1")),
                        id = "image id 1",
                        url = "imageUrl 1",
                        width = 100,
                        height = 200,
                    ),
                    PetImageJson(
                        categories = listOf(PetImageJson.Category(2, "category name 2")),
                        id = "image id 2",
                        url = "imageUrl 2",
                        width = 10,
                        height = 20,
                    )
                )
            )

            //WHEN
            val images = repository.getPetImages("categoryID")

            //THEN
            val expected = DataResponse.Success(
                listOf(
                    PetImageResponse(
                        id = "image id 1",
                        imageUrl = "imageUrl 1",
                        categoriesIDs = listOf(1),
                    ),
                    PetImageResponse(
                        id = "image id 2",
                        imageUrl = "imageUrl 2",
                        categoriesIDs = listOf(2),
                    )
                )
            )
            Assert.assertEquals(expected, images)
        }

    @Test
    fun `GIVEN exception WHEN getPetImages called THEN ServerError returned`() =
        runTest {
            //GIVEN
            fakePetApi.images = null

            //WHEN
            val images = repository.getPetImages("categoryID")

            //THEN

            Assert.assertEquals(DataResponse.ServerError, images)
        }

    @Test
    fun `GIVEN error WHEN getPetImages called THEN ServerError returned`() =
        runTest {
            //GIVEN
            fakePetApi.images = Response.error(401, "unauthorized".toResponseBody(null))

            //WHEN
            val images = repository.getPetImages("categoryID")

            //THEN

            Assert.assertEquals(DataResponse.ServerError, images)
        }

    @Test
    fun `GIVEN no internet WHEN getPetImages called THEN NoInternet returned`() =
        runTest {
            //GIVEN
            fakePetApi.exception = IOException()

            //WHEN
            val images = repository.getPetImages("categoryID")

            //THEN

            Assert.assertEquals(DataResponse.NoInternet, images)
        }
}


