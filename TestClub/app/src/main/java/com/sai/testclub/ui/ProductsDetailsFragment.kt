package com.sai.testclub.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sai.testclub.R
import com.sai.testclub.api.ApiClient
import com.sai.testclub.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_product_details.*

class ProductsDetailsFragment : Fragment() {

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product): ProductsDetailsFragment {
            val frag = ProductsDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCT, product)
            frag.arguments = args
            return frag
        }
    }

    private lateinit var product: Product

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_product_details, container, false)

        arguments?.let {
            product = it.getParcelable(ARG_PRODUCT)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(product == null){
            empty_container.visibility = View.VISIBLE
            content_container.visibility = View.GONE
        } else {
            empty_container.visibility = View.GONE
            content_container.visibility = View.VISIBLE

            Picasso.get()
                .load(ApiClient.getCompleteUrl(product.productImage))
                .into(image)
            name.text = product.productName
            price.text = product.price

            product.shortDescription?.let { shortDescription ->
                short_description.text =
                    Html.fromHtml(shortDescription, Html.FROM_HTML_MODE_COMPACT)
            }

            product.longDescription?.let { shortDescription ->
                long_description.text = Html.fromHtml(shortDescription, Html.FROM_HTML_MODE_COMPACT)
            }
            if( product.reviewCount > 0 && product.reviewRating >0.0f){
                review.text = getString(R.string.reviews, product.reviewRating, product.reviewCount)
            } else {
                review.text = getString(R.string.no_reviews)
            }

            if(product.inStock == true){
                action_btn.text = getString(R.string.add_to_cart)
                action_btn.isEnabled = true
            } else {
                action_btn.text = getString(R.string.not_available)
                action_btn.isEnabled = false
            }
            action_btn.setOnClickListener { v -> Toast.makeText(context,R.string.need_to_implement_cart, Toast.LENGTH_LONG).show()  }

        }
    }

}