package com.example.debce.projetofilme.View

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.debce.projetofilme.R
import com.example.debce.projetofilme.model.Filme
import kotlinx.android.synthetic.main.activity_novo_filme.*
import java.lang.Exception

class NovoFilmeActivity : AppCompatActivity() {

    lateinit var filme: Filme
    var menu: Menu? = null

    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_filme)

        notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(
                "com.example.debce.projetofilme",
                "Nova Notificação",
                "Exemplo")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fAddFilme?.setOnClickListener {
        fAddFilme.setOnCreateContextMenuListener { menu, v, menuInfo ->
            menu.add(Menu.NONE, 1, Menu.NONE, "Escolher foto")
        }}
        //val opcao = arrayOf("Drama", "Comédia")
        //val adapter =  ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcao)
       // sCategoria.adapter = opcao
        //sCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        val intent = intent
        try {
            filme = intent.getSerializableExtra(EXTRA_REPLY) as Filme
            filme.let {
                etNomeFilme.setText(filme.fNome)
                etAnoLanc.setText(filme.fAno)
                etNumEpisodio.setText(filme.fEpisodio)
                //sCategoria.selectedItem.toString()
            }
            val menuItem = menu?.findItem(R.id.menu_filme_delete)
            menuItem?.isVisible = true
        }catch (e:Exception){
            val menuItem = menu?.findItem(R.id.menu_filme_delete)
            menuItem?.isVisible = false
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GARELLY)
    }

    private fun getPermissionImageFromGallery(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                // permission denied
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, REQUEST_IMAGE_GARELLY)
            } else {
                // permission granted
                pickImageFromGallery()
            }
        }
        else{
            // system < M
            pickImageFromGallery()
        }
    }

    companion object {
        // image pick code
        private val REQUEST_IMAGE_GARELLY = 1000


        const val EXTRA_REPLY = "view.REPLY"
        const val EXTRA_DELETE = "view.Delete"

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_IMAGE_GARELLY -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) pickImageFromGallery()
                else Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_GARELLY) imgNovoFilme.setImageURI(data?.data)

    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            1 -> getPermissionImageFromGallery()
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_novo_filme, menu)
        try{
            filme.let {
                val menuItem = menu?.findItem(R.id.menu_filme_delete)
                menuItem?.isVisible = true
            }
        } catch (e: Exception){
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if(item?.itemId == android.R.id.home){
            finish()
            true
        } else if (item?.itemId == R.id.menu_filme_save){
            val replyIntent = Intent()

            if (etNomeFilme.text.isNullOrEmpty()) {
                Toast.makeText(this, "Insira o Nome do Filme", Toast.LENGTH_SHORT ).show()
                etNomeFilme.requestFocus()
            } else if ((::filme.isInitialized) && (filme.fId > 0)){
                filme.fAno = etAnoLanc.text.toString()
                filme.fEpisodio = etNumEpisodio.text.toString()
                //filme.fCategoria = sCategoria.selectedItem.toString()

            }else {
                filme = Filme(
                        fNome = etNomeFilme.text.toString(),
                        fAno = etAnoLanc.text.toString(),
                        fEpisodio = etNumEpisodio.text.toString()
                    //    fCategoria = sCategoria.selectedItem.toString()
                )
            }
            //val friendViewModel = ViewModelProviders.of(this).get(FriendViewModel::class.java)
            //friendViewModel.insert(friend)
            //
            replyIntent.putExtra(EXTRA_REPLY, filme)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
            true
        } else if(item?.itemId == R.id.menu_filme_delete){
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_DELETE, filme)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
            true
        }
        else{
            super.onOptionsItemSelected(item)
        }
    }

    private fun createNotificationChannel(id: String, name: String,
                                          description: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(id, name, importance)

            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern =
                    longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
