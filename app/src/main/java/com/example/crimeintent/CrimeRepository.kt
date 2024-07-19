package com.example.crimeintent

import android.content.Context
import androidx.room.Room
import com.example.crimeintent.database.CrimeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database: CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    suspend fun getCrimes(): List<Crime> {
        return withContext(Dispatchers.IO) {
            database.crimeDao().getCrimes()
        }
    }

    suspend fun getCrime(id: UUID) : Crime {
        return withContext(Dispatchers.IO) {
            database.crimeDao().getCrime(id)
        }
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE
                ?: throw IllegalArgumentException("CrimeRepository must be initialized!")
        }
    }
}