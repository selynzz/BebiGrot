package com.pukimen.babygrowth.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.pukimen.babygrowth.data.model.UserModel
import com.pukimen.babygrowth.data.remote.response.LoginResponse
import com.pukimen.babygrowth.data.remote.response.PredictResponse
import com.pukimen.babygrowth.data.remote.retrofit.ApiService
import com.pukimen.babygrowth.utils.Results
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ScanRepository private constructor(private val apiService: ApiService) {

//    suspend fun uploadImage(file: MultipartBody.Part): Results<PredictResponse> {
//        return try {
//            val response = apiService.uploadImage(file)
//            Results.Success(response)
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
//            Results.Error(errorResponse.message.toString())
//        } catch (e: Exception) {
//            Results.Error(e.message.toString())
//        }
//    }
private val results = MediatorLiveData<Results<PredictResponse>>()


  fun uploadImage(image: MultipartBody.Part): LiveData<Results<PredictResponse>> {

        results.value = Results.Loading
        val client = apiService.uploadImage(image)
        client.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                Log.e(ContentValues.TAG, "response")
                if (response.isSuccessful) {
                    val response = response.body()
                    Log.e(ContentValues.TAG, "onSuccess: ${response}")

                    results.value = Results.Success(response!!)
                }
                else {
                    Log.e(ContentValues.TAG, "onFailurel: ${response.errorBody().toString()}")
                    Log.e(ContentValues.TAG, "onFailurel: ${response.code()}")
                    results.value = Results.Error(response.message())
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                results.value = Results.Error(t.message.toString())
                Log.e(ContentValues.TAG, t.message.toString())
            }
        })
        return results
    }

    companion object {
        @Volatile
        private var instance: ScanRepository? = null

        fun getInstance(apiService: ApiService): ScanRepository =
            instance ?: synchronized(this) {
                instance ?: ScanRepository(apiService)
            }.also { instance = it }
    }
}