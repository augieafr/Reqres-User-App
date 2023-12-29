package com.augieafr.reqresapp.data.model.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val data: List<UserItem>,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,
)

data class UserItem(

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("email")
	val email: String
)
