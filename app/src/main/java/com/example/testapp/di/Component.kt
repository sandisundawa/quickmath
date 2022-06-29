package com.example.testapp.di

import com.example.testapp.DetailMovieActivity
import com.example.testapp.MainActivity
import com.example.testapp.ReviewActivity
import dagger.Component

@Component(modules = [Module::class])
interface Component {
    fun injectOnMain(context: MainActivity)

    fun injectDetail(context: DetailMovieActivity)

    fun injectReview(context: ReviewActivity)
}