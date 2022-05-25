package com.doubleclick.marktinhome.ui.MainScreen.Parents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.OnProduct
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ProductAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity


class PagerFragment : Fragment, OnProduct {


    lateinit var id: String
    lateinit var productsRecyclerView: RecyclerView
    lateinit var productViewModel: ProductViewModel

    constructor(id: String)  {
        this.id = id
        Log.e("idOfParent", id);
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_pager, container, false)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productsRecyclerView = view.findViewById(R.id.products)
        productViewModel.getSearchByChild(id)
        productViewModel.FilterByChildLiveDate().observe(viewLifecycleOwner, Observer {
            var productAdapter = ProductAdapter(it, this);
            productsRecyclerView.adapter = productAdapter;
        })
        return view;
    }


    override fun onItemProduct(product: Product?) {
        var intent = Intent(context, productActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}