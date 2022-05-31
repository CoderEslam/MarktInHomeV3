package com.doubleclick.marktinhome.ui.MainScreen.ViewMore

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.OnProduct
import com.doubleclick.ViewModel.LoadMoreViewModel
import com.doubleclick.marktinhome.Adapters.ProductAdapterSmall
import com.doubleclick.marktinhome.Model.CategoricalProduct
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity

class ViewMoreTopDealsActivity : AppCompatActivity(), OnProduct {

    private lateinit var viewMoreRecyclerView: RecyclerView
    private lateinit var loadMoreViewModel: LoadMoreViewModel
    private var products: ArrayList<Product> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_more_top_deals)
        viewMoreRecyclerView = findViewById(R.id.ViewMoreRecyclerView)
        loadMoreViewModel = ViewModelProvider(this)[LoadMoreViewModel::class.java]
        val productAdapterSmall = ProductAdapterSmall(products, this)
        viewMoreRecyclerView.adapter = productAdapterSmall
        loadMoreViewModel.LoadMoreLive().observe(this) { // load as max 1000
            if (it != null) {
                products.add(it)
                productAdapterSmall.notifyItemInserted(products.size - 1)
                productAdapterSmall.notifyDataSetChanged()
            }

        }

    }

    override fun onItemProduct(product: Product?) {
        val intent = Intent(this, productActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}