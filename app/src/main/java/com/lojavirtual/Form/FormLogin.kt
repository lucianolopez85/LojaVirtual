package com.lojavirtual.Form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lojavirtual.R

class FormLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        supportActionBar!!.hide()
    }
}