package com.venkad.androidtest.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Products(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var image: String? = null,
    var name: String? = null,
    var category_id: Int? = null,
    var product_id: Int?= null,
    var barcode: String?= null,
    var qty: String?= null,
    var price: String?= null,
    var sell_price: String?= null,
    var sell_price_with_vat: String?= null,
    var expiry_date: String? = null
):Serializable{
}