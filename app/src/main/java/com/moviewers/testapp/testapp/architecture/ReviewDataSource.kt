package com.moviewers.testapp.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.Review
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReviewDataSource(
    private val apiService: ApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _reviewResponse = MutableLiveData<Review>()
    val reviewResponse: MutableLiveData<Review>
        get() = _reviewResponse

    fun fetchReview(movieId: Int, key: String) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getReview(movieId, key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _reviewResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("reviewDataSource", it.message.orEmpty())
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("reviewDataSource", e.message.orEmpty())
        }
    }
}