package com.moviewers.testapp.testapp.helper

import android.content.Context
import android.widget.Toast

object Warning {

    fun seeGeneralErrorToast(context: Context) {
        Toast.makeText(context, "Gagal Memuat, mohon coba beberapa saat lagi", Toast.LENGTH_SHORT).show()
    }

    fun seeGeneralSuccessToast(context: Context) {
        Toast.makeText(context, "Data telah dimuat", Toast.LENGTH_SHORT).show()
    }
}