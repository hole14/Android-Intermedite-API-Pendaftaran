package com.example.pendaftaranapps.data.retrofit

import com.example.pendaftaranapps.data.respone.ListSiswaResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api-list-siswa.php")
    fun getAllSiswa(): Call<ListSiswaResponse>


}