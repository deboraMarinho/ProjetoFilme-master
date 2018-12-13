package com.example.debce.projetofilme.bd

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.debce.projetofilme.model.Filme

@Dao
interface FilmeDAO {

    @Insert
    fun insert(filme: Filme)

    @Query("DELETE FROM filme_table")
    fun deleteAll()

    @Delete
    fun delete(filme: Filme)

    @Query("SELECT * from filme_table ORDER BY fNome ASC")
    fun getAll(): LiveData<List<Filme>>

    @Query("SELECT * from filme_table WHERE fId = :id")
    fun getFilme(id: Long): LiveData<Filme>

    @Update
    fun update(filme: Filme)
}