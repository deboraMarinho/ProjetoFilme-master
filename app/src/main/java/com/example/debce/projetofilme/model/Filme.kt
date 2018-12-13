package com.example.debce.projetofilme.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "filme_table")
data class Filme(
        @ColumnInfo(name = "fNome")
        @NotNull
        val fNome: String,
        @ColumnInfo(name = "fAno")
        var fAno: String= "",
        @ColumnInfo(name = "fEpisodio")
        var fEpisodio: String= "",
        @ColumnInfo(name = "fCategoria")
        var fCategoria: String= ""
):Serializable {
    @PrimaryKey(autoGenerate = true)
    var fId: Long = 0
}