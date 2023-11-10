package com.moviewers.testapp.testapp.di

import dagger.Component

@MesinScope
@Component(modules = [MesinModule::class])
interface MesinComponent {
    fun inject(house: House)
}