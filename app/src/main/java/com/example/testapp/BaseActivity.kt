package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.testapp.api.ApiClient
import com.example.testapp.api.ApiInterface


open class BaseActivity : AppCompatActivity() {

    val apiService: ApiInterface = ApiClient.getClient()

    fun showConfirmDialog(title: String, desc: String, positif: String, negatif: String, onPositifClicked:() -> (Unit)) {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(desc)
            .setConfirmText(positif)
            .setCancelText(negatif)
            .showCancelButton(true)
            .setCancelClickListener { dialog ->
                dialog.cancel()
            }
            .setConfirmClickListener {
                onPositifClicked()
            }.show()
    }

}