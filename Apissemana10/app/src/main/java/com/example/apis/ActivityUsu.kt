package com.example.apis

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ActivityUsu : AppCompatActivity() {
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    private lateinit var checkBox: CheckBox


    var image_uri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usu)

        val listaser = capadatos.SharedApp.prefs.name
        val btnFoto = findViewById<Button>(R.id.btn_Foto)
        val btnAgregar = findViewById<Button>(R.id.btn_Agregar)


        //btnAgregar.isEnabled = false



        if (listaser != "") {
            val gson = GsonBuilder().create()
            val Model =
                gson.fromJson(listaser, Array<capadatos.UsuarioL>::class.java).toMutableList()
            capadatos.SharedApp.usuariostodos = Model
        }

        btnFoto.setOnClickListener() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {

                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )

                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    openCamera()
                }
            } else {
                openCamera()
            }
        }

        btnAgregar.setOnClickListener() {

            val intent = Intent(this, ActivityLogin::class.java)
            val correo = findViewById<EditText>(R.id.et_Correo)
            val nombre_usuario = findViewById<EditText>(R.id.et_usuario)
            val contraseña = findViewById<EditText>(R.id.et_Contraseña)
            val nombre = findViewById<EditText>(R.id.et_Nombre)
            val apellido1 = findViewById<EditText>(R.id.et_Apellido1)
            val apellido2 = findViewById<EditText>(R.id.et_Apellido2)
            if (correo.text.toString().isEmpty() || nombre_usuario.text.toString()
                    .isEmpty() || nombre.text.toString().isEmpty() || contraseña.text.toString()
                    .isEmpty() || apellido1.text.toString().isEmpty() || apellido2.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                    actualizausuarios(
                    capadatos.UsuarioL(
                        correo.text.toString(),
                        nombre_usuario.text.toString(),
                        nombre.text.toString(),
                        apellido1.text.toString(),
                        apellido2.text.toString(),
                        image_uri.toString(),
                        contraseña.text.toString()
                    )
                )


                val gson = Gson()
                val listaser = gson.toJson(capadatos.SharedApp.usuariostodos)
                capadatos.SharedApp.prefs.name = listaser
                nombre_usuario.setText("")
                correo.setText("")
                nombre.setText("")
                apellido1.setText("")
                apellido2.setText("")
                contraseña.setText("")
                startActivity(intent)
            }
        }

    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    var imagenFinal = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var foto: ImageView = findViewById(R.id.fotoUsuario)
            foto.setImageURI(image_uri)
            val imageStream: InputStream? = image_uri?.let { contentResolver.openInputStream(it) }
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            imagenFinal = "data:image/png;base64," + encodeImage(selectedImage)
            // getLastLocation()
            Log.d("imbase64", imagenFinal)
        }
    }

    fun actualizausuarios(nuevousuario: capadatos.UsuarioL): MutableList<capadatos.UsuarioL> {
        capadatos.SharedApp.usuariostodos.add(nuevousuario)
        Toast.makeText(this, "Nuevo usuario agregado", Toast.LENGTH_SHORT).show()
        return capadatos.SharedApp.usuariostodos
    }

}