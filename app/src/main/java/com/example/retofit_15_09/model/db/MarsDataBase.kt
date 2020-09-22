package com.example.retofit_15_09.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retofit_15_09.model.Terrain


private const val DATA_BASE_NAME = "mars_db"

@Database(entities = [Terrain::class], version = 1)
abstract class MarsDataBase : RoomDatabase() {

    abstract fun getTerrainDao(): TerrainDao

    companion object {
        @Volatile
        private var INSTANCE: MarsDataBase? = null

        fun getDatabase(context: Context): MarsDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return  tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context,
                    MarsDataBase::class.java,
                    DATA_BASE_NAME
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}