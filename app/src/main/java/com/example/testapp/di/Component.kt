package com.example.testapp.di

import com.example.testapp.DetailMovieActivity
import com.example.testapp.MainActivity
import dagger.Component

@Component
interface Component {
    fun injectOnMain(context: MainActivity)

    fun injectDetail(context: DetailMovieActivity)
}