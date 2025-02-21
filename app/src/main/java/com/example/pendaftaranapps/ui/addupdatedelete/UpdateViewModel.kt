package com.example.pendaftaranapps.ui.addupdatedelete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pendaftaranapps.data.respone.AddUpdateResponse
import com.example.pendaftaranapps.data.retrofit.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _updateRespone = MutableLiveData<String>()
    val updateRespone: LiveData<String> = _updateRespone

    fun updateSiswa(id: Int, nama: String, alamat: String, jenisKelamin: String, agama: String, sekolahAsal: String){
        _isLoading.value = true

        val client = APIConfig.getApiService().updateSiswa(id, nama, alamat, jenisKelamin, agama, sekolahAsal)
        client.enqueue(object : Callback<AddUpdateResponse> {
            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                _isLoading.value = false
                _updateRespone.value = "onFailure"
            }

            override fun onResponse(call: Call<AddUpdateResponse>, response: Response<AddUpdateResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _updateRespone.value = responseBody.message
                    }else{
                        _updateRespone.value = "null"
                    }
                }else{
                    _updateRespone.value = "Failed"
                }
            }

        })
    }
}