package com.example.apis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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

class AsientosActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asientos)


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

        var contador =0
        var asientosMax = 0
        val extras = intent.extras
        if (extras != null) {
            val valorEntero = extras.getInt("valorSeleccionado")
            asientosMax = valorEntero
        }
        val texto: TextView;
        texto = findViewById(R.id.txtp);
        texto.setText("Seleccione la cantidad de "+asientosMax+" asiento(s) para continuar con la compra")

        val imageButton1 = findViewById<ImageButton>(R.id.imageButton)
        val imageButton2 = findViewById<ImageButton>(R.id.imageButton2)
        val imageButton3 = findViewById<ImageButton>(R.id.imageButton3)
        val imageButton4 = findViewById<ImageButton>(R.id.imageButton4)
        val imageButton5 = findViewById<ImageButton>(R.id.imageButton5)
        val imageButton6 = findViewById<ImageButton>(R.id.imageButton6)
        val imageButtons = mutableListOf<ImageButton>()
        imageButtons.add(imageButton1)
        imageButtons.add(imageButton2)
        imageButtons.add(imageButton3)
        imageButtons.add(imageButton4)
        imageButtons.add(imageButton5)
        imageButtons.add(imageButton6)




        val selectedImage = R.drawable.asientod
        val unselectedImage = R.drawable.asiento

        val cont: Button;
        cont = findViewById(R.id.btn_Continuar);

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(AsientosActivity.APIService::class.java)
                .registrationPost(
                    "listar",
                    capadatos.SharedApp.eventoseleccionado.ID.toString(),
                    capadatos.SharedApp.eventoseleccionado.EMPRESA.ID.toString()

                )
                .execute()

            runOnUiThread {

                capadatos.SharedApp.asientos = call.body()!!
                Log.i("", capadatos.SharedApp.asientos.toString())
            }
        }

        imageButton1.setOnClickListener {
            if (contador < asientosMax || imageButton1.isSelected) {
                imageButton1.isSelected = !imageButton1.isSelected
                if(imageButton1.isSelected){
                    contador++
                    capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.ID = 15
                    capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = "D30"
                    capadatos.SharedApp.asientos[0].PAGADO = true
                    imageButton1.setImageResource(selectedImage)
                }else{
                    contador--
                    capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.ID = 0
                    capadatos.SharedApp.asientos[0].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = " "
                    capadatos.SharedApp.asientos[0].PAGADO = false
                    imageButton1.setImageResource(unselectedImage)
                }
                if(contador == asientosMax){
                    cont.visibility = View.VISIBLE
                } else{
                    cont.visibility = View.INVISIBLE
                }
            }
        }

        imageButton2.setOnClickListener {
            if (contador < asientosMax || imageButton2.isSelected) {
                imageButton2.isSelected = !imageButton2.isSelected
                if(imageButton2.isSelected){
                    contador++
                    capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.ID = 5
                    capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = "E25"
                    capadatos.SharedApp.asientos[1].PAGADO = true
                    imageButton2.setImageResource(selectedImage)
                }else{
                    contador--
                    capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.ID = 0
                    capadatos.SharedApp.asientos[1].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = " "
                    capadatos.SharedApp.asientos[1].PAGADO = false
                    imageButton2.setImageResource(unselectedImage)
                }
                if(contador == asientosMax){
                    cont.visibility = View.VISIBLE
                } else{
                    cont.visibility = View.INVISIBLE
                }
            }
        }

        imageButton3.setOnClickListener {
            if (contador < asientosMax || imageButton3.isSelected) {
                imageButton3.isSelected = !imageButton3.isSelected
                if(imageButton3.isSelected){
                    contador++
                    capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.ID = 10
                    capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = "D20"
                    capadatos.SharedApp.asientos[2].PAGADO = true
                    imageButton3.setImageResource(selectedImage)
                }else{
                    contador--
                    capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.ID = 0
                    capadatos.SharedApp.asientos[2].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = " "
                    capadatos.SharedApp.asientos[2].PAGADO = false
                    imageButton3.setImageResource(unselectedImage)
                }
                if(contador == asientosMax){
                    cont.visibility = View.VISIBLE
                } else{
                    cont.visibility = View.INVISIBLE
                }
            }
        }

        imageButton4.setOnClickListener {
            if (contador < asientosMax || imageButton4.isSelected) {
                imageButton4.isSelected = !imageButton4.isSelected
                if(imageButton4.isSelected){
                    contador++
                    capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.ID = 20
                    capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = "C15"
                    capadatos.SharedApp.asientos[3].PAGADO = true
                    imageButton4.setImageResource(selectedImage)
                }else{
                    contador--
                    capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.ID = 0
                    capadatos.SharedApp.asientos[3].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = " "
                    capadatos.SharedApp.asientos[3].PAGADO = false
                    imageButton4.setImageResource(unselectedImage)
                }
                if(contador == asientosMax){
                    cont.visibility = View.VISIBLE
                } else{
                    cont.visibility = View.INVISIBLE
                }
            }
        }

        imageButton5.setOnClickListener {
            if (contador < asientosMax || imageButton5.isSelected) {
                imageButton5.isSelected = !imageButton5.isSelected
                if(imageButton5.isSelected){
                    contador++
                    capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.ID = 25
                    capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = "B10"
                    capadatos.SharedApp.asientos[4].PAGADO = true
                    imageButton5.setImageResource(selectedImage)
                }else{
                    contador--
                    capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.ID = 0
                    capadatos.SharedApp.asientos[4].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = " "
                    capadatos.SharedApp.asientos[4].PAGADO = false
                    imageButton5.setImageResource(unselectedImage)
                }
                if(contador == asientosMax){
                    cont.visibility = View.VISIBLE
                } else{
                    cont.visibility = View.INVISIBLE
                }
            }
        }

        imageButton6.setOnClickListener {
            if (contador < asientosMax || imageButton6.isSelected) {
                imageButton6.isSelected = !imageButton6.isSelected
                if(imageButton6.isSelected){
                    contador++
                    capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.ID = 30
                    Log.e("",capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.ID.toString())
                    capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = "A5"
                    capadatos.SharedApp.asientos[5].PAGADO = true
                    imageButton6.setImageResource(selectedImage)
                }else{
                    contador--
                    capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.ID = 0
                    capadatos.SharedApp.asientos[5].ID_BUTACACOORDENADA.NOMBREDEFANTASIA = " "
                    capadatos.SharedApp.asientos[5].PAGADO = false
                    imageButton6.setImageResource(unselectedImage)
                }
                if(contador == asientosMax){
                    cont.visibility = View.VISIBLE
                } else{
                    cont.visibility = View.INVISIBLE
                }
            }
        }
        cont.setOnClickListener {
            val intent = Intent(this, PagoActivity::class.java)
            intent.putExtra("valor", contador)
            startActivity(intent)
        }


    }
    interface APIService {
        @POST("AdmButacacoordenada")
        @FormUrlEncoded
        fun registrationPost(
            @Field("op") op: String,
            @Field("calendario") calendario: String,
            @Field("idempresa") idempresa: String
        ): Call<MutableList<capadatos.ADM_BUTACACOORDENADA>>
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://lenguajescuarta-001-site1.ctempurl.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }
}