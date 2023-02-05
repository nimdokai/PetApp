package com.nimdokai.pet.core_domain

sealed interface DomainResult<Data> {
    data class Success<Data>(val data: Data) : DomainResult<Data>
    object NoInternet : DomainResult<Nothing>
    object ServerError : DomainResult<Nothing>
}
