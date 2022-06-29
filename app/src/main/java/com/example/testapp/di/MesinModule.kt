package com.example.testapp.di

import dagger.Module
import dagger.Provides

@Module
class MesinModule {
    private var jumlah = 5

    @MesinScope
    @Provides
    fun uniqueMesin(): UniqueMesin {
        return UniqueMesin(jumlah)
    }
}