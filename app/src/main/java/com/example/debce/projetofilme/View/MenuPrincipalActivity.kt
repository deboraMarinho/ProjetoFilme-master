package com.example.debce.projetofilme.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.debce.projetofilme.R
import kotlinx.android.synthetic.main.activity_menu_principal.*

class MenuPrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        btnFilme.setOnClickListener {
            val intent = Intent(this, ListaFilmeActivity::class.java)
            startActivity(intent)
             }
    }
}
