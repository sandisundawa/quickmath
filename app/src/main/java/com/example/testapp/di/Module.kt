package com.example.testapp.di

import com.example.testapp.model.MesinTest
import dagger.Module
import dagger.Provides

@Module
class Module {

    @Provides
    fun getMesin(): MesinTest {
        return MesinTest("CVT", "Honda")
    }
}