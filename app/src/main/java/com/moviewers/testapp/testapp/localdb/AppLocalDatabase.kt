package com.moviewers.testapp.testapp.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moviewers.testapp.testapp.model.Result

@Database(entities = [Result::class], version = 1)
abstract class AppLocalDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}