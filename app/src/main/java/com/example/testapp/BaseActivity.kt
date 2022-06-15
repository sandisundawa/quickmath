package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.api.ApiClient
import com.example.testapp.api.ApiInterface

open class BaseActivity : AppCompatActivity() {

    val apiService: ApiInterface = ApiClient.getClient()

}