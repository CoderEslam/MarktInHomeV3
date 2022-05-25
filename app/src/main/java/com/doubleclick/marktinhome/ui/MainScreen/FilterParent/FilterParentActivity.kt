package com.doubleclick.marktinhome.ui.MainScreen.FilterParent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.OnProduct
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ProductAdapter
import com.doubleclick.marktinhome.Model.ParentCategory
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity
import com.todkars.shimmer.ShimmerRecyclerView

class FilterParentActivity : AppCompatActivity(), OnProduct {

    lateinit var FilterParent: ShimmerRecyclerView
    lateinit var productViewModel: ProductViewModel
    lateinit var parentCategory: ParentCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_parent)
        FilterParent = findViewById(R.id.FilterParent);
        parentCategory = intent.getSerializableExtra("parentCategory") as ParentCategory;
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productViewModel.FilterByParent(parentCategory.pushId)
        productViewModel.FilterByParentLiveDate().observe(this, Observer {
            val productAdapter = ProductAdapter(it, this)
            FilterParent.adapter = productAdapter
        })

    }

    override fun onItemProduct(product: Product?) {
        val intent = Intent(this, productActivity::class.java)
        intent.putExtra("product", product);
        startActivity(intent)
    }
}