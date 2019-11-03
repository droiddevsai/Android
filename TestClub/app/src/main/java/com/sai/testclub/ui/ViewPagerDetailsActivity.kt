package com.sai.testclub.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sai.testclub.R
import com.sai.testclub.repo.ProductsManager

class ViewPagerDetailsActivity: AppCompatActivity() {


    companion object {
        private val EXTRA_POSITION = "position"
        fun createIntent(context: Context, position:Int): Intent {
            val intent = Intent(context, ViewPagerDetailsActivity::class.java)
            intent.putExtra(EXTRA_POSITION , position)
            return intent
        }
    }

    private lateinit var mPager: ViewPager
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_details)
        position = intent.getIntExtra(EXTRA_POSITION, 0)

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager)


        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter
        mPager.currentItem = position
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = ProductsManager.products.size

        override fun getItem(position: Int): Fragment {
            return ProductsDetailsFragment.newInstance(ProductsManager.products[position])
        }
    }

}