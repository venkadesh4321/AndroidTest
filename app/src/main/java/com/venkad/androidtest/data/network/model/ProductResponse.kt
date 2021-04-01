package com.venkad.androidtest.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Product(
    var success: Boolean,
    var message: String,
    var data: ArrayList<Products>,
)