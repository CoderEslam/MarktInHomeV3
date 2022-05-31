package com.doubleclick.marktinhome.ui.MainScreen.Frgments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.doubleclick.*
import com.doubleclick.ViewModel.AdvertisementViewModel
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.ViewModel.RecentSearchViewModel
import com.doubleclick.ViewModel.TradmarkViewModel
import com.doubleclick.marktinhome.Adapters.HomeAdapter
import com.doubleclick.marktinhome.BaseApplication.isNetworkConnected
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.*
import com.doubleclick.marktinhome.Model.Constantes.PRODUCT
import com.doubleclick.marktinhome.Model.Constantes.USER
import com.doubleclick.marktinhome.Model.HomeModel.*
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Views.Animatoo
import com.doubleclick.marktinhome.ui.MainScreen.FilterParent.FilterParentActivity
import com.doubleclick.marktinhome.ui.MainScreen.ViewMore.ViewMoreCategoryActivity
import com.doubleclick.marktinhome.ui.MainScreen.ViewMore.ViewMoreTopDealsActivity
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity
import com.doubleclick.marktinhome.ui.Trademark.FilterTradmarkActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HomeFragment : BaseFragment(), OnItem, OnProduct, Tradmarkinterface, ViewMore, Categorical,
    SwipeRefreshLayout.OnRefreshListener {


    lateinit var MainRecyceler: RecyclerView
    private lateinit var homeModels: ArrayList<HomeModel>
    lateinit var homeAdapter: HomeAdapter
    lateinit var animationView: LottieAnimationView
    private var timer: Timer = Timer();
    private var idProduct: String = ""
    private lateinit var refresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idProduct = it.getString("idProduct", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment\
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        MainRecyceler = view.findViewById(R.id.MainRecyceler)
        animationView = view.findViewById(R.id.animationView);
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this)
        homeModels = ArrayList()
        homeAdapter = HomeAdapter(homeModels);
        MainRecyceler.adapter = homeAdapter
        if (idProduct != "") {
            reference.child(PRODUCT).child(idProduct)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        try {
                            if (isNetworkConnected()) {
                                if (snapshot.exists()) {
                                    var product: Product? = snapshot.getValue(Product::class.java)
                                    val intent =
                                        Intent(requireContext(), productActivity::class.java);
                                    intent.putExtra("product", product);
                                    startActivity(intent)
                                }
                            } else {
                                ShowToast("No Internet Connection")
                            }
                        } catch (e: Exception) {
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
        refresh.setColorScheme(
            R.color.blue,
            R.color.ripplecoloreffect,
            R.color.green,
            R.color.orange
        )
        loadHomePage();
        updateToken();
//        ReloadData();
        return view;
    }

    // filter by parentCategory
    override fun onItem(parentCategory: ParentCategory?) {
        val intent = Intent(requireContext(), FilterParentActivity::class.java)
        intent.putExtra("parentCategoryId", parentCategory!!.pushId);
        startActivity(intent)
    }

    override fun onItemLong(parentCategory: ParentCategory?) {}

    override fun onItemProduct(product: Product?) {
        val intent = Intent(requireContext(), productActivity::class.java);
        intent.putExtra("product", product);
        startActivity(intent)
    }

    override fun AllTradmark(tradmark: ArrayList<Trademark>) {

    }

    override fun AllNameTradmark(names: List<String>) {

    }


    override fun OnItemTradmark(tradmark: Trademark) {
        val intent = Intent(requireContext(), FilterTradmarkActivity::class.java)
        intent.putExtra("trademark", tradmark);
        startActivity(intent)
    }

    override fun onEditTradmark(tradmark: Trademark) {

    }

    override fun onDeleteTradmark(tradmark: Trademark) {

    }

    override fun getViewMore(products: ArrayList<Product>) {

    }

    override fun getViewMore(product: Product) {

    }

    private fun loadHomePage() {
        Loadproduct()
        Loadtrademark()
        Loadadvertisement()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun Loadproduct() {
        val productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productViewModel.categorical.observe(viewLifecycleOwner) {
            try {
                homeModels.add(HomeModel(it, this, this, Categorical));
                homeAdapter.notifyDataSetChanged()
            } catch (e: Exception) {

            }

        }
//        productViewModel.parent.observe(viewLifecycleOwner) {
//            if (it.size != 0) {
////                homeModels.add(0, HomeModel(it, HomeModel.TopCategory, this))
////                homeAdapter.notifyDataSetChanged()
////                homeAdapter.notifyItemInserted(0)
//                animationView.visibility = View.GONE
////                timer.cancel()
//            } else {
//                animationView.visibility = View.VISIBLE
//            }
//        };
        productViewModel.topDealsLiveData.observe(viewLifecycleOwner) { // get max as 50 items
            try {
                homeModels.add(HomeModel(it, TopDeal, this, this));
                homeAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
//
            }


        };
//        productViewModel.product.observe(viewLifecycleOwner) { // get max as 1000 items
//            try {
//                homeModels.add(HomeModel(it, Products, this))
//                homeAdapter.notifyDataSetChanged()
//            } catch (e: Exception) {
//
//            }
//        };
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun Loadtrademark() {
        val trademarkViewModel = TradmarkViewModel()
        trademarkViewModel.allMark.observe(viewLifecycleOwner) {
            homeModels.add(HomeModel(it, Trademarks, this))
            homeAdapter.notifyDataSetChanged();
        };
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun Loadadvertisement() {
        val advertisementViewModel = AdvertisementViewModel();
        advertisementViewModel.advAdd.observe(viewLifecycleOwner) {
            homeModels.add(0, HomeModel(it, Advertisement))
            homeAdapter.notifyItemInserted(0)
            homeAdapter.notifyItemInserted(0)
            homeAdapter.notifyDataSetChanged();
        };
    }


    override fun onRefresh() {
        Handler().postDelayed(Runnable {
            homeModels.clear()
            loadHomePage()
            refresh.isRefreshing = false;
        }, 2000)
    }

    private fun updateToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                val map: HashMap<String, Any> = HashMap();
                map["token"] = it.result.toString();
                reference.child(USER).child(myId).updateChildren(map);
            }
        }
    }

    private fun LoadrecentSearch() {
        val recentSearchViewModel = ViewModelProvider(this)[RecentSearchViewModel::class.java]
//        to get last Recent Search
        recentSearchViewModel.lastSearchListLiveDataOneTime.observe(viewLifecycleOwner, Observer {
            if (it.size != 0) {
                homeModels.add(HomeModel(HomeModel.RecentSearch, it, this, this, 0))
            }
        })
    }

    private fun ReloadData() {
        val handler = Handler()
        val runnable = Runnable {
            try {
                loadHomePage();
                Animatoo.animateSwipeLeft(requireContext());
            } catch (e: Exception) {
                Log.e("ExceptionHomeFrg", e.message.toString());
            }
        }
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, 2000, 2000)
    }

    override fun categorical(categoricalProduct: CategoricalProduct) {
        val intent = Intent(requireContext(), FilterParentActivity::class.java);
        intent.putExtra("parentCategoryId", categoricalProduct.id);
        startActivity(intent);
    }
}

