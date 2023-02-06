package com.nimdokai.pet.core.data

sealed interface DataResponse<Data> {
    data class Success<Data>(val data: Data) : DataResponse<Data>
    object NoInternet : DataResponse<Nothing>
    object ServerError : DataResponse<Nothing>
}
