package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.testapp.api.ApiClient
import com.example.testapp.api.ApiInterface


open class BaseActivity : AppCompatActivity() {

    val apiService: ApiInterface = ApiClient.getClient()

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