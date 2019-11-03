package com.sai.testclub.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sai.testclub.R
import com.sai.testclub.api.ApiClient
import com.sai.testclub.model.Product
import com.sai.testclub.model.ProductsResponse
import com.sai.testclub.repo.ProductsManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_products_list.*
import timber.log.Timber

class ProductsListActivity : AppCompatActivity(), OnItemClickListener, OnBottomReachedListener {

    companion object {
        private const val PAGE_SIZE = 30
    }

    private lateinit var productsListRecyclerView: RecyclerView
    private lateinit var adapter: ProductsAdapter
    private var currentPageNumber = 1
    private var totalProducts = -10
    private var loadingNext: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_products_list)

        currentPageNumber = 1

        productsListRecyclerView = findViewById(R.id.products_list)
        productsListRecyclerView.setHasFixedSize(false)
        productsListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        initialLoadProducts()

        // pull to refresh call.
        refresh_view.setOnRefreshListener {
            initialLoadProducts()
        }

        setupToolBar()
    }

    private fun setupToolBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        activity_title.text = getString(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun initialLoadProducts() {

        productsListRecyclerView.adapter = null

        refresh_view.isRefreshing = true

        ApiClient().gitApi.getProducts(1, PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                refresh_view.isRefreshing = false
                onResponse(result)
            }) { error ->
                refresh_view.isRefreshing = false
                displayError(true)
                Timber.d("Error while getting info $error")
            }
    }

    private fun loadMoreData(pageNumber: Int) {
        ApiClient().gitApi.getProducts(pageNumber, PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                loadingNext = false
                updateProductsList(result)
            }) { error ->
                loadingNext = false
                displayError(false)
                Timber.d("Error while getting info $error")
            }
    }

    private fun displayError(fromInitialLoad: Boolean){
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.error_dialog_title)

        if(fromInitialLoad) {
            builder.setMessage(R.string.error_dialog_Message)
                .setPositiveButton(R.string.error_positive_btn) { dialog, which -> initialLoadProducts() }
                .setNegativeButton(R.string.error_neagtive_btn) { dialog, which -> finish() }
        } else {
            builder.setMessage(R.string.error_dialog_Message_for_more)
                .setPositiveButton(R.string.error_positive_btn_for_more) { dialog, which -> dialog.dismiss() }

        }
        builder.show()
    }

    private fun onResponse(productsResponse: ProductsResponse?) {
        Timber.d("Initial load ---------")
        logResponseDetails(productsResponse)
        if (productsResponse?.statusCode == 200) {
            totalProducts = productsResponse?.totalProducts
        }
        if (productsResponse?.products != null && !productsResponse.products!!.isNullOrEmpty()) {
            setupAdapter(productsResponse.products!!.toMutableList())
            product_counter.text = getString(R.string.total_counter, adapter.itemCount, totalProducts)
            ProductsManager.updateInitialLoad(productsResponse.products!!.toMutableList())
        }
    }


    private fun setupAdapter(productsList: MutableList<Product>) {
        adapter = ProductsAdapter(productsList, this, this)
        productsListRecyclerView.adapter = adapter
    }

    private fun updateProductsList(productsResponse: ProductsResponse?) {

        Timber.d("updateProductsList() ---------")
        logResponseDetails(productsResponse)

        if (adapter != null && productsResponse?.products != null && !productsResponse.products!!.isNullOrEmpty()) {
            adapter.addProducts(productsResponse.products!!.toMutableList())
            product_counter.text = getString(R.string.total_counter, adapter.itemCount, totalProducts)
            ProductsManager.addMoreProducts(productsResponse.products!!.toMutableList())
        }
    }

    private fun logResponseDetails(productsResponse: ProductsResponse?){
        productsResponse?.let {
            Timber.d("Total items: ${it.totalProducts}")
            Timber.d("Page number: ${it.pageNumber}")
            Timber.d("Page size: ${it.pageSize}")
            Timber.d("Status Code: ${it.statusCode}")
        }
    }

    override fun onItemClicked(product: Product, position: Int) {
        if (product != null) {
            startActivity(ViewPagerDetailsActivity.createIntent(this, position))
        }
    }

    override fun onBottomReached() {
        if (adapter.products.size < totalProducts && !loadingNext) {
            loadingNext = true
            currentPageNumber++
            loadMoreData(currentPageNumber)
        }
    }
}
