package com.example.debce.projetofilme.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.debce.projetofilme.bd.FilmeDAO
import com.example.debce.projetofilme.model.Filme

class FilmeRepository(private val filmeDAO: FilmeDAO) {
    val allFilmes: LiveData<List<Filme>> = filmeDAO.getAll()

    @Suppress
    @WorkerThread
    suspend fun insert(filme: Filme){
        filmeDAO.insert(filme)
    }

    fun delete(filme: Filme){
        filmeDAO.delete(filme)
    }

    fun update(filme: Filme){
        filmeDAO.update(filme)
    }
}