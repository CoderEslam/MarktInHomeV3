package com.doubleclick.marktinhome.ui.Filter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.OnProduct
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ProductAdapter
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity

class FilterActivity : AppCompatActivity(), OnProduct {


    lateinit var FilterRecycler: RecyclerView;
    lateinit var productViewModel: ProductViewModel
    var id: String = ""
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        FilterRecycler = findViewById(R.id.FilterRecycler)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        id = intent.getStringExtra("id")!!.toString();
        type = intent.getStringExtra("type")!!.toString()

        if (type.equals("search")) {
            productViewModel.getSearch(id);
            productViewModel.searchLiveData.observe(this, Observer {
                var productAdapter = ProductAdapter(it, this);
                FilterRecycler.adapter = productAdapter

            })
        }
        if (type.equals("childId")) {
            productViewModel.getSearchByChild(id)
            productViewModel.FilterByChildLiveDate().observe(this, Observer {
                var productAdapter = ProductAdapter(it, this);
                FilterRecycler.adapter = productAdapter
            })
        }
        if (type.equals("ShareUrl")) {
            productViewModel.getSearchByIdProduct(id)
            productViewModel.searchByIdProductLiveData.observe(this) {
                Log.e("ShareUrl", it.toString());
                val intent= Intent(this,productActivity::class.java)
                intent.putExtra("product", it[0]);
                startActivity(intent)
                val productAdapter = ProductAdapter(it, this);
                FilterRecycler.adapter = productAdapter
            }
        }
    }


    override fun onItemProduct(p: Product?) {
        var intent = Intent(this@FilterActivity, productActivity::class.java)
        intent.putExtra("product", p)
        startActivity(intent)
    }
}