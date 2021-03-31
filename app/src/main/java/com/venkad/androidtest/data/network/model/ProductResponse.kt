package com.venkad.androidtest.data.network.model

data class Product(
    var success: Boolean,
    var message: String,
    var data: ArrayList<Products>,
)
data class Products(
    var id: Int,
    var image: String,
    var name: String,
    var category_id: Int,
    var product_id: Int,
    var barcode: String,
    var qty: String,
    var price: String,
    var sell_price: String,
    var expiry_date: String
)