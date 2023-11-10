package com.moviewers.testapp.testapp.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moviewers.testapp.testapp.model.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM result")
    fun getAllNowPlaying(): List<Result>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(vararg resultList: Result)
}