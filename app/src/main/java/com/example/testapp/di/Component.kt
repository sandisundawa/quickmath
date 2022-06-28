package com.example.testapp.di

import android.content.Context
import com.example.testapp.MainActivity
import dagger.Component

@Component
interface Component {
    fun inject(context: MainActivity)
}