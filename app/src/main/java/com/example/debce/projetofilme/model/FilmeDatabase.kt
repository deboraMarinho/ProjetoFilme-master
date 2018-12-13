package com.example.debce.projetofilme.model

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.debce.projetofilme.bd.FilmeDAO
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

@Database(entities = [Filme::class], version = 3)
abstract class FilmeDatabase: RoomDatabase() {

    abstract fun filmeDAO(): FilmeDAO

    companion object {
        @Volatile
        private var INSTANCE: FilmeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FilmeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FilmeDatabase::class.java,
                        "filme-database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(FilmeDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class FilmeDatabaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.filmeDAO())
                }
            }
        }
        fun populateDatabase(filmeDAO: FilmeDAO) {
            filmeDAO.insert(Filme("FILME 01"))

        }

    }

}