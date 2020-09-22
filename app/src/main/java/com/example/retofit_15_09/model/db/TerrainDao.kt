package com.example.retofit_15_09.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retofit_15_09.model.Terrain

@Dao
interface TerrainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTerrains(mTerrainsList: List<Terrain>)

    @Query("SELECT * FROM terrain_table")
    fun getAllTerrainsFromDB(): LiveData<List<Terrain>>


    @Query("SELECT * FROM terrain_table WHERE id =:id")
    fun getTerrainByID(id: String ): LiveData<Terrain>

}