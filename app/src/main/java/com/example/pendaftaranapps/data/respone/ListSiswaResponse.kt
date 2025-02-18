package com.example.pendaftaranapps.data.respone

import com.google.gson.annotations.SerializedName

data class ListSiswaResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("response")
	val response: Int,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("agama")
	val agama: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("sekolah_asal")
	val sekolahAsal: String,

	@field:SerializedName("alamat")
	val alamat: String
)
