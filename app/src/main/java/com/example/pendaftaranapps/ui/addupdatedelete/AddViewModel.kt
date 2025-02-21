package com.example.pendaftaranapps.ui.addupdatedelete

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pendaftaranapps.data.respone.AddUpdateResponse
import com.example.pendaftaranapps.data.respone.DataItem
import com.example.pendaftaranapps.data.retrofit.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _addRespone = MutableLiveData<String>()
    val addRespone: LiveData<String> = _addRespone

    fun insertSiswa(nama: String, alamat: String, jenisKelamin: String, agama: String, sekolahAsal: String){
        _isLoading.value = true
        val client = APIConfig.getApiService().addSiswa(nama, alamat, jenisKelamin, agama, sekolahAsal)
        client.enqueue(object : Callback<AddUpdateResponse> {
            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                _isLoading.value = false
                _addRespone.value = "onFailure"
            }

            override fun onResponse(call: Call<AddUpdateResponse>, response: Response<AddUpdateResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _addRespone.value = responseBody.message
                    }else{
                        _addRespone.value = "null"
                    }
                }else{
                    _addRespone.value = "Failed"
                }
            }
        })
    }
}