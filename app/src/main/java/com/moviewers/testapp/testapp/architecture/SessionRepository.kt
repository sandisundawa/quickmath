package com.moviewers.testapp.testapp.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.model.SessionResult
import io.reactivex.disposables.CompositeDisposable

class SessionRepository(private val apiService: ApiInterface) {

    lateinit var sessionDataSource: SessionDataSource

    fun getSession(
        compositeDisposable: CompositeDisposable,
        key: String
    ): MutableLiveData<SessionResult> {

        sessionDataSource = SessionDataSource(apiService, compositeDisposable)
        sessionDataSource.getSession(key)

        return sessionDataSource.sessionResponse

    }

    fun getSessionNetwork(): LiveData<NetworkState> {
        return sessionDataSource.networkState
    }
}