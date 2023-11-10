package com.moviewers.testapp.testapp.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Choose(val value: String)