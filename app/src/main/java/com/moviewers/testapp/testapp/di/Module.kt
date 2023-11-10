package com.moviewers.testapp.testapp.di

import com.moviewers.testapp.testapp.model.MesinTest
import dagger.Module
import dagger.Provides

@Module
class Module {

    @Provides
    fun getMesin(): MesinTest {
        return MesinTest("CVT", "Honda")
    }
}