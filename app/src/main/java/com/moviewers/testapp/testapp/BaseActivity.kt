package com.moviewers.testapp.testapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import cn.pedant.SweetAlert.SweetAlertDialog
import com.moviewers.testapp.testapp.api.ApiClient
import com.moviewers.testapp.testapp.api.ApiInterface
import com.moviewers.testapp.testapp.localdb.AppLocalDatabase
import com.moviewers.testapp.testapp.localdb.MovieDao


open class BaseActivity : AppCompatActivity() {

    val apiService: ApiInterface = ApiClient.getClient()

    fun initLocalDB(context: Context): MovieDao {
        val db = Room.databaseBuilder(
            context,
            AppLocalDatabase::class.java, "movie_db"
        ).build()
        return db.movieDao()
    }

    fun showConfirmDialog(title: String, desc: String, positifText: String, negatifText: String, onPositifClicked:() -> (Unit)) {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(desc)
            .setConfirmText(positifText)
            .setCancelText(negatifText)
            .showCancelButton(true)
            .setCancelClickListener { dialog ->
                dialog.cancel()
            }
            .setConfirmClickListener {
                onPositifClicked()
            }.show()
    }
}