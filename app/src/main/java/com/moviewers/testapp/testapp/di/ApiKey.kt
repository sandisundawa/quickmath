package com.moviewers.testapp.testapp.di

import com.moviewers.testapp.testapp.model.MesinTest
import javax.inject.Inject

class ApiKey @Inject constructor(val mesinTest: MesinTest) {
    fun getApiKey(): String {
        return "2c35c921410c9727265ed66192629a38"
    }

    fun getMesin(): String {
        return mesinTest.namaMesin()
    }
}