package com.example.twintpoc.data.error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
