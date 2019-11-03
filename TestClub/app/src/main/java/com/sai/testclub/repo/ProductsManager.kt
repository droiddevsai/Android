package com.sai.testclub.repo

import com.sai.testclub.model.Product

/*
 * This call is supposed to be handling all things related to Products.
*/
object ProductsManager {

    //TODO: All the API call we are making ProductsListActivity need to be moved here.

    val products: MutableList<Product> = mutableListOf()

    fun updateInitialLoad(ps: MutableList<Product>){
        products.clear()
        products.addAll(ps)
    }

    fun addMoreProducts(moreProducts: MutableList<Product>) {
        products.addAll(moreProducts)
    }

}