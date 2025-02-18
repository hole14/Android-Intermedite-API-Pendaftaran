package com.example.pendaftaranapps.data.respone

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("response")
	val response: Int,

	@field:SerializedName("message")
	val message: String
)