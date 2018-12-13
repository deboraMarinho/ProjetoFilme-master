package com.example.debce.projetofilme.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.debce.projetofilme.model.Filme
import com.example.debce.projetofilme.model.FilmeDatabase
import com.example.debce.projetofilme.repository.FilmeRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class FilmeViewModel(application: Application):
        AndroidViewModel(application){

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
            get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: FilmeRepository
    val allFilmes: LiveData<List<Filme>>

    init {
        val filmeDAO = FilmeDatabase.
                getDatabase(application, scope).filmeDAO()
        repository = FilmeRepository(filmeDAO)
        allFilmes = repository.allFilmes
    }

    fun insert(filme: Filme) = scope.launch(Dispatchers.IO) {
        repository.insert(filme)
    }

    fun update(filme: Filme) = scope.launch(Dispatchers.IO){
        repository.update(filme)
    }

    fun delete(filme: Filme) = scope.launch(Dispatchers.IO){
        repository.delete(filme)
    }
}