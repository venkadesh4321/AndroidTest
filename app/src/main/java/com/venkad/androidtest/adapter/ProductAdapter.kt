package com.venkad.androidtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.venkad.androidtest.R
import com.venkad.androidtest.data.network.model.Products
import com.venkad.androidtest.databinding.ItemProductBinding
import java.util.*

class ProductAdapter(
    private val details: ArrayList<Products>,
) : RecyclerView.Adapter<ProductAdapter.ProductViewsHolder>() {

    override fun getItemCount() = details.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewsHolder(
            DataBindingUtil.inflate<ItemProductBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_product,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductViewsHolder, position: Int) {
        holder.itemProductBinding.product = details[position]

        holder.itemProductBinding.priceTextView.text = "Actual Price: "+details[position].price
        holder.itemProductBinding.sellPriceTextView.text = "Sell Price: "+details[position].sell_price
    }

    inner class ProductViewsHolder(
        val itemProductBinding: ItemProductBinding
    ) : RecyclerView.ViewHolder(itemProductBinding.root)
}