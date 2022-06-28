package com.example.testapp.di

import javax.inject.Inject

class ApiKey @Inject constructor() {
    fun getApiKey(): String {
        return "2c35c921410c9727265ed66192629a38"
    }
}