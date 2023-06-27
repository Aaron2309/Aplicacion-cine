package com.example.apis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val listaser = capadatos.SharedApp.prefs.name
        var validar = false



        if (listaser != ""){
            val gson = GsonBuilder().create()
            val Model = gson.fromJson(listaser,Array<capadatos.UsuarioL>::class.java).toMutableList()
            capadatos.SharedApp.usuariostodos = Model
        }

        val btnIngresar = findViewById<Button>(R.id.btn_ingresar)
        val Usuario = findViewById<EditText>(R.id.Et_Usuario)
        val Contraseña = findViewById<EditText>(R.id.Et_contraseña)

        btnIngresar.setOnClickListener(){
            for(i in 0..(capadatos.SharedApp.usuariostodos.size-1)){
                if (Usuario.text.toString().equals(capadatos.SharedApp.usuariostodos[i].usuario) && Contraseña.text.toString().equals(capadatos.SharedApp.usuariostodos[i].contraseña)){
                    validar = true
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Foto", capadatos.SharedApp.usuariostodos[i].foto )
                    intent.putExtra("Nombre", capadatos.SharedApp.usuariostodos[i].nombre)
                    intent.putExtra("Apellido", capadatos.SharedApp.usuariostodos[i].apellido1 )

                    Usuario.setText("")
                    Contraseña.setText("")

                    startActivity(intent)
                }
            }

            if (validar == false){
                Toast.makeText(getApplicationContext(), "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show()
            }
        }

        val registrar = findViewById<TextView>(R.id.btn_registrar)

        registrar.setOnClickListener(){
            val intent = Intent(this, ActivityUsu::class.java)
            startActivity(intent)
        }


    }
    override fun onBackPressed() {
        //Para que no haga nada al presionar la volver
    }
}