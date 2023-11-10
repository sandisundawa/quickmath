package com.moviewers.testapp.testapp.methodInterface

interface ResponseInterface<T> {
    fun showToast(s: String?)
    fun display(data: T)
    fun displayError(s: String?)
}