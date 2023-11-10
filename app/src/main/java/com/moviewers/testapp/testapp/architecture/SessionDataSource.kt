package com.moviewers.testapp.testapp.architecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.SessionResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SessionDataSource(
    private val apiService: ApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _sessionResponse = MutableLiveData<SessionResult>()
    val sessionResponse: MutableLiveData<SessionResult>
        get() = _sessionResponse

    fun getSession(key: String) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getSession(key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _sessionResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("sessionDataSource", it.message.orEmpty())
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("sessionDataSource", e.message.orEmpty())
        }
    }
}