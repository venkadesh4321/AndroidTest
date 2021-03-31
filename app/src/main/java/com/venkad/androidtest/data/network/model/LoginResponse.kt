package com.venkad.androidtest.data.network.model

data class LoginResponse(
    var success: Boolean,
    var message: String,
    var data: LoginData
)
data class LoginData(
    var token: String,
    var role: String,
    var role_id: Int,
    var branch: ArrayList<Int>,
    var shop_id: Int,
    var shop_name: String
)
