package com.sai.testclub.ui

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sai.testclub.R
import com.sai.testclub.api.ApiClient
import com.sai.testclub.model.Product
import com.squareup.picasso.Picasso

class ProductsAdapter(
    val products: MutableList<Product>,
    val onItemClickListener: OnItemClickListener,
    val onBottomReachedListener: OnBottomReachedListener
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    companion object {
        private val THRUSH_HOLD_FOR_REFRESH = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun addProducts(nextProductsList: MutableList<Product>) {
        products.addAll(nextProductsList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products[position]
        holder.name.text = product.productName
        product.shortDescription?.let { shortDescription ->
            holder.description.text = Html.fromHtml(shortDescription, Html.FROM_HTML_MODE_COMPACT)
        }
        Picasso.get()
            .load(ApiClient.getCompleteUrl(product.productImage))
            .into(holder.icon)

        holder.itemView.setOnClickListener { onItemClickListener.onItemClicked(product, position) }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - THRUSH_HOLD_FOR_REFRESH) {
            onBottomReachedListener.onBottomReached()
        }
        return super.getItemViewType(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView
        val name: TextView
        val description: TextView

        init {
            this.icon = itemView.findViewById(R.id.icon) as ImageView
            this.name = itemView.findViewById(R.id.name) as TextView
            this.description = itemView.findViewById(R.id.description) as TextView
        }
    }
}