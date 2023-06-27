package com.example.apis

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

class capadatos {

    class Prefs (context: Context) {
        val PREFS_NAME = "com.cursokotlin.sharedpreferences"
        val SHARED_NAME = "shared_name"
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

        var name: String?
            get() = prefs.getString(SHARED_NAME, "")
            set(value) = prefs.edit().putString(SHARED_NAME, value).apply()
    }

    data class EMPRESA(

        @SerializedName("CEDULAJURIDICA") var CEDULAJURIDICA: String,
        @SerializedName("DESPEDIDA") var DESPEDIDA: String,
        @SerializedName("DIRECCION") var DIRECCION: String,
        @SerializedName("DIRECCIONXY") var DIRECCIONXY: String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("IMAGENURL") var IMAGENURL: String,
        @SerializedName("IMAGENURLMOVIL") var IMAGENURLMOVIL: String,
        @SerializedName("IMPUESTOAPLICA") var IMPUESTOAPLICA: String,
        @SerializedName("NOMBRE") var NOMBRE: String,
        @SerializedName("PAGADO") var PAGADO: String,
        @SerializedName("SALUDO") var SALUDO: String,
        @SerializedName("TELEFONO1") var TELEFONO1: String,
        @SerializedName("TELEFONO2") var TELEFONO2: String,
        @SerializedName("URLESCENARIO") var URLESCENARIO: String,
        @SerializedName("USUARIO") var USUARIO: USUARIO

    )
    data class USUARIO(
        @SerializedName("DIRECCION") var DIRECCION: String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("MOVIL") var MOVIL: String,
        @SerializedName("NOMBRE") var NOMBRE: String,
        @SerializedName("PRIMER_APELLIDO") var PRIMER_APELLIDO: String,
        @SerializedName("ROL") var ROL: String,
        @SerializedName("SEGUNDO_APELLIDO") var SEGUNDO_APELLIDO: String,
        @SerializedName("TELEFONO") var TELEFONO: String

    )
    data class EVENTO(

        @SerializedName("DESCRIPCION") var DESCRIPCION: String,
        @SerializedName("DETALLE") var DETALLE: String,
        @SerializedName("EMPRESA") var EMPRESA: String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("IMAGENURL") var IMAGENURL: String,
        @SerializedName("IMPUESTO") var IMPUESTO: Number,
        @SerializedName("NOMBRE") var NOMBRE: String


    )
    data class LISTA_PRECIOS(

        @SerializedName("CODIGO_ACCESO") var CODIGO_ACCESO:String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("NOMBRE") var NOMBRE:String,
        @SerializedName("NUMERO") var NUMERO:Number,
        @SerializedName("TOTAL") var TOTAL:Number

    )

    //clase padre donde se utiliza relaciones de composicion con las clases anteriores para obtener una unica clase con los
    //atributos necesarios que devuelve la peticion
    data class CALENDARIO(

        @SerializedName("ANNO") var ANNO: Number,
        @SerializedName("CERRADO") var CERRADO: String,
        @SerializedName("DIA") var DIA: Number,
        @SerializedName("EMPRESA") var EMPRESA: EMPRESA,
        @SerializedName("EVENTO") var EVENTO: EVENTO,
        @SerializedName("HORAS_CADUCA") var HORAS_CADUCA: Number,
        @SerializedName("HORA_FIN") var HORA_FIN:String,
        @SerializedName("HORA_INICIA") var HORA_INICIA:String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("LISTA_PRECIOS") var LISTA_PRECIOS: LISTA_PRECIOS,
        @SerializedName("MES") var MES: Number,
        @SerializedName("NUMERO_HORA_FIN") var NUMERO_HORA_FIN: Number,
        @SerializedName("NUMERO_HORA_INICIA") var NUMERO_HORA_INICIA: Number,
        @SerializedName("NUMERO_MINUTO_FIN") var NUMERO_MINUTO_FIN: Number,
        @SerializedName("NUMERO_MINUTO_INICIA") var NUMERO_MINUTO_INICIA: Number,
        //@SerializedName("TERMINAL") var TERMINAL:String

    )
    data class ADM_BUTACACOORDENADA(
        @SerializedName("ID_BUTACACOORDENADA") var ID_BUTACACOORDENADA:ID_BUTACACOORDENADA,
        @SerializedName("EMPRESA") var EMPRESA: EMPRESA,
        @SerializedName("PAGADO") var PAGADO: Boolean,
        @SerializedName("RESERVACION") var RESERVACION: RESERVACION,
        @SerializedName("COMPRA") var COMPRA:COMPRA,
        @SerializedName("CALENDARIO") var CALENDARIO:CALENDARIO,
        @SerializedName("PAGO") var PAGO:PAGO,
        @SerializedName("PRECIO") var PRECIO:Number
    )

    data class ID_BUTACACOORDENADA(
        @SerializedName("ID") var ID: Number,
        @SerializedName("EJEX") var EJEX: Double,
        @SerializedName("EJEY") var EJEY: Double,
        @SerializedName("IMAGEN") var IMAGEN: String,
        @SerializedName("ESTILO") var ESTILO: String,
        @SerializedName("EMPRESA") var EMPRESA: EMPRESA,
        @SerializedName("NOMBREDEFANTASIA") var NOMBREDEFANTASIA: String,
        @SerializedName("ESTILOMOVIL") var ESTILOMOVIL: String
    )
    data class RESERVACION(
        @SerializedName("ID") var ID: Number,
        @SerializedName("CALENDARIO") var CALENDARIO: Number,
        @SerializedName("FECHA_RESERVADA") var FECHA_RESERVADA: String,
        @SerializedName("FECHA_CADUCA") var FECHA_CADUCA: String,
        @SerializedName("CLIENTE") var CLIENTE: CLIENTE
    )
    data class CLIENTE(
        @SerializedName("ID") var ID: String,
        @SerializedName("NOMBRE") var NOMBRE: String,
        @SerializedName("PRIMER_APELLIDO") var PRIMER_APELLIDO: String,
        @SerializedName("SEGUNDO_APELLIDO") var SEGUNDO_APELLIDO: String,
        @SerializedName("CORREO_ELECTRONICO") var CORREO_ELECTRONICO: String,
        @SerializedName("CELULAR") var CELULAR: String,
        @SerializedName("PUBLICIDAD") var PUBLICIDAD: Boolean
    )
    data class COMPRA(
        @SerializedName("ID") var ID: Number,
        @SerializedName("CALENDARIO") var CALENDARIO: Number,
        @SerializedName("FECHA_COMPRA") var FECHA_COMPRA: String,
        @SerializedName("CLIENTE") var CLIENTE: CLIENTE
    )
    data class PAGO(
        @SerializedName("ID") var ID: Number,
        @SerializedName("FECHA") var FECHA: String,
        @SerializedName("MONTO") var MONTO: Double,
        @SerializedName("TIPO") var TIPO: String,
        @SerializedName("TARJETA") var TARJETA: String,
        @SerializedName("REFERENCIA") var REFERENCIA: String,
        @SerializedName("AUTORIZACION") var AUTORIZACION: String,
        @SerializedName("TRANSACCION") var TRANSACCION: String,
        @SerializedName("CALENDARIO") var CALENDARIO: Number
    )

    data class UsuarioL(
        var correo: String,
        var usuario:String,
        var nombre: String,
        var apellido1:String,
        var apellido2:String,
        var foto:String,
        var contraseña:String
    )



    class SharedApp : Application() {
        companion object {
            lateinit var diasdelmes: MutableList<CALENDARIO>
            lateinit var eventoseleccionado: CALENDARIO;
            lateinit var pago: MutableList<LISTA_PRECIOS>
            lateinit var asientos:MutableList<ADM_BUTACACOORDENADA>
            lateinit var usuariostodos: MutableList <UsuarioL>
            lateinit var prefs: Prefs


        }

        override fun onCreate() {
            super.onCreate()
            diasdelmes = ArrayList()
            pago = ArrayList()
            asientos = ArrayList()
            usuariostodos = ArrayList()
            prefs = Prefs(applicationContext)
        }


    }

}