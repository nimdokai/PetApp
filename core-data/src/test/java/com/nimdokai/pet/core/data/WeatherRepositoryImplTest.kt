package com.nimdokai.pet.core.data

import com.nimdokai.pet.core.data.fakes.FakeWeatherApi
import com.nimdokai.pet.core.data.fakes.fakeCurrentConditions
import com.nimdokai.pet.core.data.fakes.fakeCurrentConditionsJson
import com.nimdokai.pet.core.data.fakes.fakeHourlyForecast
import com.nimdokai.pet.core.data.fakes.fakeHourlyForecastJson
import com.nimdokai.pet.core.data.repositories.WeatherRepository
import com.nimdokai.pet.core.data.repositories.WeatherRepositoryImpl
import com.nimdokai.pet.core_network.model.HourlyForecastJsonResponse
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class WeatherRepositoryImplTest {

    private val api = FakeWeatherApi()
    private val repository: WeatherRepository = WeatherRepositoryImpl(api)

    @Test
    fun `GIVEN success with current conditions WHEN getCurrentConditions called THEN Success returned`() =
        runTest {
            //GIVEN
            api.currentConditionsResponse = Response.success(listOf(fakeCurrentConditionsJson))

            //WHEN
            val response = repository.getCurrentConditions("locationId", "C")

            //THEN
            Assert.assertEquals(
                DataResponse.Success(fakeCurrentConditions),
                response
            )
        }

    @Test
    fun `GIVEN exception WHEN getCurrentConditions called THEN ServerError returned`() =
        runTest {
            //GIVEN
            // not setting the response will throw exception

            //WHEN
            val categories = repository.getCurrentConditions("locationId", "C")

            //THEN
            Assert.assertEquals(DataResponse.ServerError, categories)
        }

    @Test
    fun `GIVEN server error WHEN getCurrentConditions called THEN ServerError returned`() =
        runTest {
            //GIVEN
            api.currentConditionsResponse = Response.error(500, "server error".toResponseBody(null))

            //WHEN
            val categories = repository.getCurrentConditions("locationId", "C")

            //THEN

            Assert.assertEquals(DataResponse.ServerError, categories)
        }

    @Test
    fun `GIVEN no internet WHEN getCurrentConditions called THEN NoInternet returned`() =
        runTest {
            //GIVEN
            api.exception = IOException()

            //WHEN
            val categories = repository.getCurrentConditions("locationId", "C")

            //THEN
            Assert.assertEquals(DataResponse.NoInternet, categories)
        }
    
    @Test
    fun `GIVEN success with current conditions WHEN get12HourForecast called THEN Success returned`() =
        runTest {
            //GIVEN
            api.hourlyForecastResponse = Response.success(listOf(fakeHourlyForecastJson))

            //WHEN
            val response = repository.get12HourForecast("locationId", true)

            //THEN
            Assert.assertEquals(
                DataResponse.Success(listOf(fakeHourlyForecast)),
                response
            )
        }

    @Test
    fun `GIVEN exception WHEN get12HourForecast called THEN ServerError returned`() =
        runTest {
            //GIVEN
            // not setting the response will throw exception

            //WHEN
            val categories = repository.get12HourForecast("locationId", true)

            //THEN
            Assert.assertEquals(DataResponse.ServerError, categories)
        }

    @Test
    fun `GIVEN server error WHEN get12HourForecast called THEN ServerError returned`() =
        runTest {
            //GIVEN
            api.currentConditionsResponse = Response.error(500, "server error".toResponseBody(null))

            //WHEN
            val categories = repository.get12HourForecast("locationId", true)

            //THEN

            Assert.assertEquals(DataResponse.ServerError, categories)
        }


    @Test
    fun `GIVEN no internet WHEN get12HourForecast called THEN NoInternet returned`() =
        runTest {
            //GIVEN
            api.exception = IOException()

            //WHEN
            val categories = repository.get12HourForecast("locationId", true)

            //THEN
            Assert.assertEquals(DataResponse.NoInternet, categories)
        }

}