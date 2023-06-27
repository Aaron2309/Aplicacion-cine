package com.example.apis

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.View
import android.widget.*
import android.window.SplashScreen
import android.window.SplashScreenView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : adapter = adapter()

    fun setUpRecyclerView(){
        mRecyclerView = findViewById(R.id.listadeobras) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.RecyclerAdapter(capadatos.SharedApp.diasdelmes, this)
        mRecyclerView.adapter = mAdapter

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val n1 = findViewById<ImageView>(R.id.im_1)
        val n2 = findViewById<TextView>(R.id.et_prueba)
        val foto = intent.getStringExtra("Foto")
        val nombre = intent.getStringExtra("Nombre")
        var apellido = intent.getStringExtra("Apellido")


        n1.setImageURI(Uri.parse(foto))
        n2.setText(nombre+ "\n" + apellido )


        val btnSesion = findViewById<Button>(R.id.btn_sesion)
        btnSesion.setOnClickListener(){
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }


        val mes: Spinner;
        mes = findViewById(R.id.Spinner)

        val meses = resources.getStringArray(R.array.meses)
        val mesesValues = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, meses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mes.adapter = adapter



        var mesSeleccionado: Int? = null
        mes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val mesSeleccionadoStr = meses[position]
                val index = meses.indexOf(mesSeleccionadoStr)
                mesSeleccionado = mesesValues[index]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                mesSeleccionado = null
            }
        }


        val btn_enero: Button;
        btn_enero = findViewById(R.id.btn_obras);
        btn_enero.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(APIService::class.java).registrationPost("listarparamovil",mesSeleccionado.toString(),"2023").execute()

                runOnUiThread {
                        capadatos.SharedApp.diasdelmes = call.body()!!
                        Log.i("", capadatos.SharedApp.diasdelmes.toString())
                        setUpRecyclerView()
                        mRecyclerView.visibility = View.VISIBLE


                }
            }}


    }

    interface APIService {
        @POST("Calendario")
        @FormUrlEncoded
        fun registrationPost(
            @Field("op") op: String,
            @Field("mes") mes: String,
            @Field("anno") anno:String


        ): Call<MutableList<capadatos.CALENDARIO>>
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