package com.example.crimeintent.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.crimeintent.Crime
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    fun getCrimes(): Flow<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): Flow<Crime>

    @Update
    fun updateCrime(crime: Crime)
}