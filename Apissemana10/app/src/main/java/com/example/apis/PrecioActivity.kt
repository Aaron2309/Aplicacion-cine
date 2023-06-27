package com.example.apis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


class PrecioActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_precio)

        val teatro: TextView;
        teatro = findViewById(R.id.teatro)
        teatro.setText("Compra de entradas en el teatro:\n"+capadatos.SharedApp.eventoseleccionado.EMPRESA.NOMBRE)

        val nombre: TextView;
        nombre = findViewById(R.id.nombre)
        nombre.setText("Función:\n"+capadatos.SharedApp.eventoseleccionado.EVENTO.NOMBRE)

        val txtdia: TextView;
        txtdia = findViewById(R.id.txtdia)
        txtdia.setText("Día:\n"+capadatos.SharedApp.eventoseleccionado.DIA+"/"+capadatos.SharedApp.eventoseleccionado.MES+"/"+capadatos.SharedApp.eventoseleccionado.ANNO)

        val inicio: TextView;
        inicio = findViewById(R.id.horadeinicio)
        inicio.setText("Inicia: "+capadatos.SharedApp.eventoseleccionado.NUMERO_HORA_INICIA+":"+capadatos.SharedApp.eventoseleccionado.NUMERO_MINUTO_INICIA)
        val finaliza: TextView;
        finaliza = findViewById(R.id.horafinaliza)
        finaliza.setText("Finaliza: "+capadatos.SharedApp.eventoseleccionado.NUMERO_HORA_FIN+":"+capadatos.SharedApp.eventoseleccionado.NUMERO_MINUTO_FIN)

        val foto : ImageView;
        foto = findViewById(R.id.foto)
        foto.loadUrl("http://lenguajescuarta-001-site1.ctempurl.com/Imageneseventos/" + capadatos.SharedApp.eventoseleccionado.EVENTO.ID.toString() +".jpg")



        val continuar: Button;
        continuar = findViewById(R.id.btn_continuar);

        val cont: Button;
        cont = findViewById(R.id.btnContinuar);
        cont.isEnabled = false

        val precio: TextView;
        precio = findViewById(R.id.txtprecio)
        val tipo: TextView;
        tipo = findViewById(R.id.txtTipo)
        val cantidad: TextView;
        cantidad = findViewById(R.id.txtCantidad)

        val spinner: Spinner = findViewById(R.id.Spinner)

        var selectedItem =0
        val items = arrayOf(0,1,2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedItem = parent.getItemAtPosition(position) as Int
                // seleccion = spinner.selectedItem.toString()
                if (selectedItem > 0) {
                    cont.isEnabled = true
                } else {
                    cont.isEnabled = false
                    //Toast.makeText(applicationContext, "Debe seleccionar una entrada", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                cont.isEnabled = false
            }
        }


        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Disponibilidad")
        alertDialogBuilder.setMessage("No hay más entradas para este día, intentar otra fecha")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()

         continuar.setOnClickListener {
             CoroutineScope(Dispatchers.IO).launch {
                 val call = getRetrofit().create(PrecioActivity.APIService::class.java)
                     .registrationPost("listarsolodisponibles", capadatos.SharedApp.eventoseleccionado.EMPRESA.ID.toString(),
                         capadatos.SharedApp.eventoseleccionado.LISTA_PRECIOS.ID.toString(),
                         capadatos.SharedApp.eventoseleccionado.ID.toString()

                       )
                     .execute()

                 runOnUiThread {
                     try {
                         capadatos.SharedApp.pago= call.body()!!
                         Log.i("", capadatos.SharedApp.pago.toString())
                         tipo.setText("Tipo de entrada:\n\n\n" + capadatos.SharedApp.pago[0].NOMBRE.toString())
                         precio.setText("Precio de la entrada:\n\n" + capadatos.SharedApp.pago[0].TOTAL.toString())
                         cantidad.setText("Cantidad de asientos:")
                         spinner.visibility = View.VISIBLE
                         cont.visibility = View.VISIBLE
                         // Código que puede lanzar una excepción
                     } catch (e: Exception) {
                         alertDialog.show()
                         Log.e("No hay más entradas para este día", e.message, e)
                     }
                 }
             }
         }
        cont.setOnClickListener {
            if (cont.isEnabled) {
                val intent = Intent(this, AsientosActivity::class.java)
                intent.putExtra("valorSeleccionado", selectedItem)
                startActivity(intent)
            } else {
            }

        }

    }
    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }
    interface APIService {
        @POST("Listaprecios")
        @FormUrlEncoded
        fun registrationPost(
            @Field("op") op: String,
            @Field("idempresa") idempresas: String,
            @Field("id") id: String,
            @Field("calendario") calendario: String


        ): Call<MutableList<capadatos.LISTA_PRECIOS>>
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://lenguajescuarta-001-site1.ctempurl.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}