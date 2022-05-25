package com.doubleclick.marktinhome.ui.MainScreen.Parents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.OnProduct
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ImageProductSliderAdapter
import com.doubleclick.marktinhome.Adapters.ProductAdapter
import com.doubleclick.marktinhome.Adapters.ViewPagerAdapter
import com.doubleclick.marktinhome.Adapters.ViewPagerParentAdapter
import com.doubleclick.marktinhome.Model.ClassificationPC
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.databinding.ActivityParentBinding
import com.doubleclick.marktinhome.ui.Filter.FilterActivity
import com.doubleclick.marktinhome.ui.ProductActivity.productActivity
import java.util.*

class ParentActivity : AppCompatActivity(), OnProduct {


    lateinit var binding: ActivityParentBinding
    lateinit var classificationPC: ClassificationPC
    lateinit var productViewModel: ProductViewModel;
    private var currentPage = 0
    private lateinit var timer: Timer
    private val DELAY_TIME: Long = 2000
    private val PERIOD_TIME: Long = 2000
    lateinit var viewPagerParentAdapter: ViewPagerParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        classificationPC = intent.getSerializableExtra("classificationPC") as ClassificationPC
        binding.toolbarCollapsinglayout.title = (classificationPC.name)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        var image = classificationPC.image
        setupPagerFragment()
        // if you want array of image
//            "[https://firebasestorage.googleapis.com/v0/b/marketinhome-99d25.appspot.com/o/Uploads%2F1647360547441.jpg?alt=media&token=c1a3825b-d835-4ced-9dc6-932c131d1209, https://firebasestorage.googleapis.com/v0/b/marketinhome-99d25.appspot.com/o/Uploads%2F1647360547530.jpg?alt=media&token=04ce6ade-70b0-4155-8991-5d82181199ed, https://firebasestorage.googleapis.com/v0/b/marketinhome-99d25.appspot.com/o/Uploads%2F1647360547568.jpg?alt=media&token=a4f4d674-09b3-4ed5-a0b5-6a46561586c4]"
        var l: List<String> = image.replace("[", "").replace("]", "").replace(" ", "").split(",")
        setBannerSliderViewPager(l)
        productViewModel.FilterByParent(classificationPC.parentPushId)
        productViewModel.FilterByParentLiveDate().observe(this, androidx.lifecycle.Observer {
            val productAdapter = ProductAdapter(it, this);
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // to update UpdateRecentSearch
//                UpdateRecentSearch.Check(query, this@ParentActivity, this@ParentActivity)
                val intent = Intent(this@ParentActivity, FilterActivity::class.java)
                intent.putExtra("id", query.trim())
                intent.putExtra("type", "search")
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        });

    }


    fun setBannerSliderViewPager(list: List<String>) {
        val sliderAdapter = ImageProductSliderAdapter(list)
        binding.viewPager.adapter = sliderAdapter
        binding.viewPager.clipToPadding = false
        binding.viewPager.pageMargin = 20
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        StartbannerSlideShow(list)
        binding.viewPager.setOnTouchListener(View.OnTouchListener { v, event ->
            StopBannerSlideShow()
            if (event.action == MotionEvent.ACTION_UP) {
                StartbannerSlideShow(list)
            }
            false
        });
    }

    private fun StartbannerSlideShow(list: List<String>) {
        val handler = Handler()
        val runnable = Runnable {
            if (currentPage >= list.size) {
                currentPage = 0
            }
            binding.viewPager.currentItem = currentPage++
        }
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, DELAY_TIME, PERIOD_TIME)
    }

    private fun StopBannerSlideShow() {
        timer.cancel()
    }

    private fun setupPagerFragment() {
        viewPagerParentAdapter =
            ViewPagerParentAdapter(supportFragmentManager, ViewPagerAdapter.POSITION_UNCHANGED)
        for (i in 0 until classificationPC.childCategory.size) {
            viewPagerParentAdapter.addFragment(
                PagerFragment(classificationPC.childCategory.get(i).pushId),
                classificationPC.childCategory[i].name
            )
        }
        binding.viewPagerParent.adapter = viewPagerParentAdapter
        binding.tabLayoutParent.setupWithViewPager(binding.viewPagerParent)
    }

    override fun onItemProduct(product: Product?) {
        val intent = Intent(this@ParentActivity, productActivity::class.java)
        intent.putExtra("product", product);
        startActivity(intent);
    }
}