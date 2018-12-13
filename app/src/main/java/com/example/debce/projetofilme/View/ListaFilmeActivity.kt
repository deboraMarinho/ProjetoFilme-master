package com.example.debce.projetofilme.View

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.debce.projetofilme.R
import com.example.debce.projetofilme.adapter.FilmeRecyclerAdapter
import com.example.debce.projetofilme.model.Filme
import com.example.debce.projetofilme.viewmodel.FilmeViewModel
import kotlinx.android.synthetic.main.activity_lista_filme.*

class ListaFilmeActivity : AppCompatActivity() {

    private lateinit var filmeViewModel: FilmeViewModel
    private val requestCodeNote = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_filme)

        fbtnAddFilme.setOnClickListener {
            startActivity(Intent(this, NovoFilmeActivity::class.java))
        }

        val recyclerView = rvFilme
        val adapter = FilmeRecyclerAdapter(this)

        adapter.onItemClick = {it ->
            val intent = Intent(this@ListaFilmeActivity, NovoFilmeActivity::class.java)
            intent.putExtra(NovoFilmeActivity.EXTRA_REPLY, it)
            startActivityForResult(intent, requestCodeNote)
        }
        recyclerView.adapter = adapter
        //val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        // Get a new or existing ViewModel from the ViewModelProvider
        filmeViewModel = ViewModelProviders.of(this).get(FilmeViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        filmeViewModel.allFilmes.observe(this, Observer { filmes ->
            // Update the cached copy of the words in the adapter.
            filmes?.let { adapter.setFilmeList(it) }
        })

        fbtnAddFilme.setOnClickListener {
            val intent = Intent(this@ListaFilmeActivity, NovoFilmeActivity::class.java)
            startActivityForResult(intent, requestCodeNote)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeNote && resultCode == Activity.RESULT_OK) {
            data?.let { data ->
                try {
                    val filme: Filme?
                    filme = data.getSerializableExtra(NovoFilmeActivity.EXTRA_REPLY) as Filme
                    filme.let {
                        if(filme.fId > 0) filmeViewModel.update(filme)
                        else filmeViewModel.insert(filme)

                    }
                } catch (e: Exception){
                    val friend: Filme?  = data.getSerializableExtra(NovoFilmeActivity.EXTRA_DELETE) as Filme
                    friend.let {
                        filmeViewModel.delete(friend!!)
                    }
                }
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
            ).show()
        }
    }

}
