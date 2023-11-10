package com.moviewers.testapp.testapp.di

import com.moviewers.testapp.testapp.DetailMovieActivity
import com.moviewers.testapp.testapp.MainActivity
import com.moviewers.testapp.testapp.ReviewActivity
import dagger.Component

@Component(modules = [Module::class])
interface Component {
    fun injectOnMain(context: MainActivity)

    fun injectDetail(context: DetailMovieActivity)

    fun injectReview(context: ReviewActivity)
}