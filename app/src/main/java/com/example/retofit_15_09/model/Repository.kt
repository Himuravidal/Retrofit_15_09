package com.example.retofit_15_09.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.retofit_15_09.model.db.TerrainDao
import com.example.retofit_15_09.model.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val terrainDao: TerrainDao) {

    private val service = RetrofitClient.getRetrofitClient()
    val mLiveData = terrainDao.getAllTerrainsFromDB()


    fun obtainTerrainByID(id: String): LiveData<Terrain> {
       return terrainDao.getTerrainByID(id)
    }


    // La vieja confiable
    fun getDataFromServer() {
        val call = service.getDataFromApi()
        call.enqueue(object : Callback<List<Terrain>> {

            override fun onResponse(call: Call<List<Terrain>>, response: Response<List<Terrain>>) {
                when (response.code()) {
                    in 200..299 -> CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.let {
                            terrainDao.insertAllTerrains(it)
                        }
                    }
                    in 300..399 -> Log.d("ERROR 300", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<Terrain>>, t: Throwable) {
                Log.e("Repository", t.message.toString())
            }

        })
    }


    fun getDataFromServerWithCoroutines() = CoroutineScope(Dispatchers.IO).launch {
        val service = kotlin.runCatching { service.getDataFromApiCorutines() }
        service.onSuccess {
            when (it.code()) {
                in 200..299 -> it.body()?.let { it1 -> terrainDao.insertAllTerrains(it1) }
                in 300..399 -> Log.d("ERROR", "ERROR de Parametros ETC")
            }
        }
        service.onFailure {
            Log.e("REPO_ERROR", it.message.toString())
        }
    }

}