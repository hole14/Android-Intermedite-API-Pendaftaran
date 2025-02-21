package com.example.pendaftaranapps.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pendaftaranapps.data.respone.DataItem
import com.example.pendaftaranapps.data.respone.ListSiswaResponse
import com.example.pendaftaranapps.data.retrofit.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _listSiswa = MutableLiveData<List<DataItem>>()
    val listSiswa: LiveData<List<DataItem>> = _listSiswa

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findAllSiswa()
    }

    private fun findAllSiswa() {
        _isLoading.value = true
        val client = APIConfig.getApiService().getAllSiswa()
        client.enqueue(object : Callback<ListSiswaResponse> {
            override fun onFailure(call: Call<ListSiswaResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<ListSiswaResponse>,
                response: Response<ListSiswaResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listSiswa.value = response.body()?.data
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

        })
    }

}