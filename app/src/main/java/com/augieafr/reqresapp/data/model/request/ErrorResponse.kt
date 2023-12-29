package com.augieafr.reqresapp.data.model.request

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("error")
	val error: String
)
