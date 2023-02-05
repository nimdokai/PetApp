package com.nimdokai.pet.core.data

import retrofit2.Response
import java.io.IOException


suspend fun <JSON, Data> tryCall(
    call: suspend () -> Response<JSON>,
    mapping: (JSON) -> Data
): DataResponse<out Data> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            val jsonResponse = response.body()!!
            val data = mapping.invoke(jsonResponse)
            DataResponse.Success(data)
        } else {
            DataResponse.ServerError
        }
    } catch (e: IOException) {
        DataResponse.NoInternet
    } catch (e: Exception) {
        DataResponse.ServerError
    }
}