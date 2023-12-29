package com.augieafr.reqresapp.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("token")
	val token: String
)
