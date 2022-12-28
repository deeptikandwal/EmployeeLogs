package com.project.employeeLogs.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.employeeLogs.data.db.entity.AnimeEntity

@Dao
interface  AnimeDao {
    @Query("SELECT * FROM Animes")
    fun getAllAnimes(): List<AnimeEntity>

    @Query("SELECT * FROM Animes where anime like :name ORDER BY anime ASC")
    fun getAnimeByName(name:String?): AnimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimes(employees: List<AnimeEntity>)

    @Query("DELETE FROM Animes")
    suspend fun deleteAllAnimes()
}