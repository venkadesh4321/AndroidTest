package com.venkad.androidtest.data.network.model

data class RegisterResponse(
    var success: Boolean,
    var message: String,
    var data: Data
)
data class Data(
    var token: String
)