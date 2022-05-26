package com.doubleclick.marktinhome.ui.Trademark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.OnProduct
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ProductAdapter
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.Model.Trademark
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity
import com.todkars.shimmer.ShimmerRecyclerView

class FilterTradmarkActivity : AppCompatActivity(), OnProduct {

    lateinit var trademarkRecycler: ShimmerRecyclerView
    lateinit var productViewModel: ProductViewModel
    lateinit var trademark: Trademark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tradmark)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        trademark = intent.getSerializableExtra("trademark") as Trademark
        productViewModel.ProductWithTrademark(trademark.name)
        trademarkRecycler = findViewById(R.id.trademarkRecycler);
        trademarkRecycler.showShimmer()
        productViewModel.ProductWithTrademarkLiveDate().observe(this, Observer {
            if (it.size != 0) {
                trademarkRecycler.hideShimmer()
                val productAdapter = ProductAdapter(it, this);
                trademarkRecycler.adapter = productAdapter
            }
        })
    }

    override fun onItemProduct(product: Product?) {
        val intent = Intent(this, productActivity::class.java)
        intent.putExtra("product", product);
        startActivity(intent)

    }
}