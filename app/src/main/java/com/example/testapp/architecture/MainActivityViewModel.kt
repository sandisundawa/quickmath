package com.example.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.MainGenre
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(
    private val genreRepository: GenreRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  genre : LiveData<MainGenre> by lazy {
        genreRepository.fetchGenre(compositeDisposable, "2c35c921410c9727265ed66192629a38")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}