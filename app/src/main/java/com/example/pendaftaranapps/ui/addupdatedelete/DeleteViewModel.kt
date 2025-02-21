package com.example.pendaftaranapps.ui.addupdatedelete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pendaftaranapps.data.respone.DeleteResponse
import com.example.pendaftaranapps.data.retrofit.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _deleteRespone = MutableLiveData<String>()
    val deleteRespone: LiveData<String> = _deleteRespone

    fun deleteSiswa(id: Int){
        _isLoading.value = true

        val client = APIConfig.getApiService().deleteSiswa(id)
        client.enqueue(object : Callback<DeleteResponse> {
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                _isLoading.value = false
                _deleteRespone.value = "onFailure"
            }

            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _deleteRespone.value = responseBody.message
                    }else{
                        _deleteRespone.value = "null"
                    }
                }else{
                    _deleteRespone.value = "Failed"
                }
            }
        })
    }
}