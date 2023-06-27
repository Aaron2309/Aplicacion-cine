package com.example.apis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapter : RecyclerView.Adapter<adapter.ViewHolder>()  {
    //var estudiantes: MutableList<MainActivity.Estudiante> = ArrayList()
    var fechas:MutableList<capadatos.CALENDARIO> = ArrayList()

    lateinit var context: Context

    fun RecyclerAdapter(fechas: MutableList<capadatos.CALENDARIO>, context: Context){
        this. fechas = fechas
        this.context = context

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = fechas.get(position)
        holder.bind(item, context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_layout, parent, false))
    }
    override fun getItemCount(): Int {
        return fechas.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombre = view.findViewById(R.id.nombre) as TextView
        val descripcion = view.findViewById(R.id.descripcion) as TextView
        val horadeinicio = view.findViewById(R.id.horadeinicio) as TextView
        val teatro = view.findViewById(R.id.teatro) as TextView
        val horafinaliza = view.findViewById(R.id.horafinaliza) as TextView
        val foto = view.findViewById(R.id.foto) as ImageView
        val dia = view.findViewById(R.id.dia) as TextView
        val botoncompraa = view.findViewById(R.id.btncomprar) as Button


        fun bind(fecha: capadatos.CALENDARIO, context: Context){
            nombre.text = fecha.EVENTO.NOMBRE
            descripcion.text = fecha.EVENTO.DESCRIPCION
            horadeinicio.text= "Empieza: " +  fecha.NUMERO_HORA_INICIA.toString() + ":" + fecha.NUMERO_MINUTO_INICIA.toString()
            teatro.text= fecha.EMPRESA.NOMBRE;
            horafinaliza.text= "Termina: " +  fecha.NUMERO_HORA_FIN .toString() + ":" + fecha.NUMERO_MINUTO_FIN.toString()
            dia.text = fecha.DIA.toString()
            foto.loadUrl("http://lenguajescuarta-001-site1.ctempurl.com/Imageneseventos/" + fecha.EVENTO.ID.toString() +".jpg")
            botoncompraa.setOnClickListener {

                capadatos.SharedApp.eventoseleccionado = fecha;
                context.startActivity(Intent(context, PrecioActivity::class.java))
            }
        }
        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this)
        }
    }

}