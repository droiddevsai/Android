package com.sai.testclub.ui

import com.sai.testclub.model.Product

interface OnItemClickListener {
    fun onItemClicked(product: Product, position: Int)
}