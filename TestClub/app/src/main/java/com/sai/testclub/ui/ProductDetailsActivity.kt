package com.sai.testclub.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sai.testclub.R
import com.sai.testclub.model.Product


class ProductDetailsActivity: AppCompatActivity() {

    companion object{
        private const val EXTRA_PRODUCT= "product"

        fun createIntent(context: Context, product: Product): Intent {
            val intent =  Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_details)

        val product = intent.getParcelableExtra(EXTRA_PRODUCT) as Product

        val frag = ProductsDetailsFragment.newInstance(product)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, frag)
            .commit()

    }

}