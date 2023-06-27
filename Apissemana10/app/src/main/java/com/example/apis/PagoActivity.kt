package com.example.apis

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.*
import kotlin.random.Random


class PagoActivity : AppCompatActivity() {


    private lateinit var email: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)
        val info = findViewById<TextView>(R.id.txtInfo)
        var cantidad = 0;
        val extras = intent.extras


        if (extras != null) {
            val valorEntero = extras.getInt("valor")
            cantidad = valorEntero
        }
        var monto = 0
        monto = capadatos.SharedApp.pago[0].TOTAL.toInt()
        val montoApagar = monto*cantidad

        if(capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " " ||
            capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " " ||
            capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " " ||
            capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " "||
            capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " "||
            capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " ")
        {
            var nombresAsientos = ""
            if (capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.ID == 15) {
                nombresAsientos+=  capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.NOMBREDEFANTASIA +" "
            }

            if (capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.ID == 5) {
                nombresAsientos+= capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.NOMBREDEFANTASIA+ " "
            }

            if (capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.ID == 10) {
                nombresAsientos+= capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.NOMBREDEFANTASIA+ " "
            }

            if (capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.ID == 20) {
                nombresAsientos+=  capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.NOMBREDEFANTASIA+ " "
            }

            if (capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.ID == 25) {
                nombresAsientos+=  capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.NOMBREDEFANTASIA+ " "
            }

            if (capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.ID == 30) {
                nombresAsientos+=  capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.NOMBREDEFANTASIA+ " "
            }

            info.setText("La cantidad de boletos a comprar es de: "+cantidad+
                    " por un monto de: "+monto+" c/u"+ "  asiento(s): "+nombresAsientos)
        }



        val email = findViewById<EditText>(R.id.et_email)
        val nombre = findViewById<EditText>(R.id.et_nombre)
        val papellido = findViewById<EditText>(R.id.et_papellido)
        val sapellido = findViewById<EditText>(R.id.et_sapellido)
        val cel = findViewById<EditText>(R.id.cel)
        val titular = findViewById<EditText>(R.id.etCardHolderName)
        val numeroTC = findViewById<EditText>(R.id.etCardNumber)
        val expiraM =findViewById<EditText>(R.id.etExpirationMonth)
        val expiraA = findViewById<EditText>(R.id.etExpirationYear)
        val cvv = findViewById<EditText>(R.id.etSecurityCode)

        val btn = findViewById<Button>(R.id.btn_button)
        btn.isEnabled =false

        val checkBox = findViewById<CheckBox>(R.id.checkBox)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btn.isEnabled = isChecked
                mostrarTerminos()
            }
        }

        btn.setOnClickListener{
            val correoElectronico = email.getText().toString()
            capadatos.SharedApp.usuariostodos[0].correo = correoElectronico
            guardacliente(
                correoElectronico, nombre.getText().toString(),
                papellido.getText().toString(),  sapellido.getText().toString(),
                cel.getText().toString(), titular.getText().toString(),
                numeroTC.getText().toString(), expiraM.getText().toString(),
                expiraA.getText().toString(), cvv.getText().toString()
            )

        }

    }



    fun guardacliente (email: String, nombre: String, papellido: String, sapellido: String, cel: String
    , titular: String, numeroTC: String, expiraM: String, expiraA: String, cvv: String)  {
        var eleccion : Int
        if(email.isEmpty()||nombre.isEmpty()||papellido.isEmpty()||sapellido.isEmpty()||cel.isEmpty()
            ||titular.isEmpty() || numeroTC.isEmpty() || expiraM.isEmpty()|| expiraA.isEmpty()||cvv.isEmpty()){
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            eleccion = 0;

        }else {
            val compruebaemail = email.trim()
            if (!compruebaemail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+".toRegex())) {
                Toast.makeText(this@PagoActivity, "Por favor, introduce bien su email", Toast.LENGTH_LONG).show()
            }else{eleccion = 1
                generarTicket(eleccion, email)}
            }

    }
    fun generarTicket (eleccion: Int, email: String){
        if (eleccion==1) {

            val min = 1
            val max = 999
            val randomNumber = Random.nextInt(
                min,
                max + 1
            )
            Log.i("El número aleatorio generado es: ", randomNumber.toString())
            val numerocompra = randomNumber

            val intent = Intent(this, TiqueteActivity::class.java)
            intent.putExtra("valor2", numerocompra)
            intent.putExtra("valor3", email)

            startActivity(intent)
        }
    }
    private fun mostrarTerminos() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Términos y condiciones")
            .setMessage("Esta aplicación está destinada a su uso por parte de los usuarios que acepten estos términos y condiciones. Al utilizar esta aplicación, usted acepta estos términos y condiciones en su totalidad." +
                    " Se le puede solicitar información personal, incluidos sus contactos, ubicación y cámara. Esta información se utiliza para brindar un mejor servicio y mejorar la experiencia del usuario. Al aceptar estos términos y condiciones, usted otorga su consentimiento para la recopilación y el uso de esta información.")
            .setPositiveButton("Aceptar") { _, _ ->
            }
            .create()

        dialog.show()
    }
}

