package com.doubleclick.marktinhome.ui.ProductActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.Rating
import android.os.Build
import android.os.Bundle
import android.util.Half.toFloat
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.doubleclick.ViewModel.FavoriteViewModel
import com.doubleclick.ViewModel.RateViewModel
import com.doubleclick.marktinhome.Adapters.ProductSliderAdapter
import com.doubleclick.marktinhome.BaseApplication.ShowToast
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.Constantes.*
import com.doubleclick.marktinhome.Model.Favorite
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.Model.Rate
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Repository.BaseRepository.myId
import com.doubleclick.marktinhome.Repository.BaseRepository.reference
import com.doubleclick.marktinhome.Views.shinebuttonlib.ShineButton
import com.doubleclick.marktinhome.Views.togglebuttongroup.SingleSelectToggleGroup
import com.doubleclick.marktinhome.Views.togglebuttongroup.button.CircularToggle
import com.doubleclick.marktinhome.Views.viewmoretextview.ViewMoreTextView
import com.doubleclick.marktinhome.ui.MainScreen.Comments.CommentsActivity
import com.github.anastr.speedviewlib.AwesomeSpeedometer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.fragment_upload.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class productActivity : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var banner_slier_view_pager: ViewPager
    private lateinit var productName: ViewMoreTextView
    private lateinit var trarmark: TextView
    private lateinit var price: TextView
    private lateinit var lastPrice: TextView
    private lateinit var totalPercentage: TextView
    private lateinit var yourRate: RatingBar;
    private lateinit var rateViewModel: RateViewModel;
    lateinit var plus: ImageView
    lateinit var mins: ImageView
    lateinit var quantity: TextView
    var qNumber: Int = 1
    lateinit var share: ImageView
    lateinit var pieChartView: PieChartView
    private var toggleItemColor: String = ""
    private var toggleItemSize: String = ""
    lateinit var comments: TextView;
    lateinit var product: Product
    lateinit var toggleSizes: SingleSelectToggleGroup
    lateinit var toggleColors: SingleSelectToggleGroup
    lateinit var webView: WebView
    private lateinit var speedView: AwesomeSpeedometer
    private lateinit var nestedScrollColor: NestedScrollView
    private lateinit var nestedScrollSize: NestedScrollView
    private lateinit var favorite: ShineButton;
    private lateinit var favoriteViewModel: FavoriteViewModel;


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        rateViewModel = ViewModelProvider(this)[RateViewModel::class.java]
        fab = findViewById(R.id.fab)
        banner_slier_view_pager = findViewById(R.id.banner_slier_view_pager)
        productName = findViewById(R.id.productName)
        nestedScrollColor = findViewById(R.id.nestedScrollColor);
        nestedScrollSize = findViewById(R.id.nestedScrollSize);
        trarmark = findViewById(R.id.trarmark)
        price = findViewById(R.id.price)
        webView = findViewById(R.id.webView);
        lastPrice = findViewById(R.id.lastPrice)
        totalPercentage = findViewById(R.id.totalPercentage);
        yourRate = findViewById(R.id.yourRate);
        favorite = findViewById(R.id.favorite);
        comments = findViewById(R.id.comments);
        speedView = findViewById(R.id.speedView);
        toggleColors = findViewById(R.id.toggleColors);
        plus = findViewById(R.id.plus)
        quantity = findViewById(R.id.quantity)
        mins = findViewById(R.id.mins)
        share = findViewById(R.id.share);
        toggleSizes = findViewById(R.id.toggleSizes)
        pieChartView = findViewById(R.id.pieChartView);
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        product = intent.getParcelableExtra("product")!!
        Log.e("productproducts", product.toString());
        productName.text = product.productName
        trarmark.text = product.tradeMark
        price.text = product.price.toString()
        lastPrice.text = product.lastPrice.toString()
        webView.loadDataWithBaseURL(
            null,
            product.description,
            "text/html",
            "utf-8",
            null
        );

        favorite.setOnCheckStateChangeListener { view, checked ->
            if (checked) {
                val map: HashMap<String, Any> = HashMap<String, Any>();
                map["id"] = product.productId;
                reference.child(FAVORITE).child(myId).child(product.productId).updateChildren(map);
            } else {
                reference.child(FAVORITE).child(myId).child(product.productId).removeValue();
            }
        }

        favoriteViewModel.isFavorite(product.productId).observe(this) {
            favorite.setChecked(it, it);
            favorite.setBtnFillColor(resources.getColor(R.color.red));
        }

        productName.setAnimationDuration(500)
            .setEllipsizedText("View More")
            .setVisibleLines(2)
            .setIsExpanded(false)
            .setEllipsizedTextColor(ContextCompat.getColor(this, R.color.blueDark))

        productName.setOnClickListener {
            productName.toggle();
        }

        val spliterSizes =
            product.sizes.toString().replace("[", "").replace("]", "").replace(" ", "").split(",")
        if (!product.sizes.equals("[]")) {
            for (i in spliterSizes.indices) {
                val circularToggle = CircularToggle(this)
                circularToggle.text = spliterSizes[i]
                if (i == 0) {
                    circularToggle.id = 1234567890; /* to chek at first element in toggle*/
                    toggleItemSize = spliterSizes[i];
                }
                circularToggle.setOnClickListener {
                    toggleItemSize = spliterSizes[i]
                }
                toggleSizes.addView(circularToggle)
                toggleSizes.check(1234567890)
            }
        } else {
            nestedScrollSize.visibility = View.GONE
        }

        val spliterColors =
            product.colors.toString().replace("[", "").replace("]", "").replace(" ", "").split(",")
        val spliterColorsName =
            product.colorsName.toString().replace("[", "").replace("]", "").replace(" ", "")
                .split(",")
        if (!product.colors.equals("[]") && !product.colorsName.equals("[]")) {
            for (i in spliterColors.indices) {
                val circularToggle = CircularToggle(this)
                circularToggle.text = spliterColorsName[i]
                try {
                    circularToggle.markerColor = Integer.parseInt(spliterColors[i])
                } catch (e: NumberFormatException) {
                }
                if (i == 0) {
                    circularToggle.id = 1234567890; /* to chek at first element in toggle*/
                    toggleItemColor = spliterColorsName[i]
                }
                circularToggle.setOnClickListener {
                    toggleItemColor = spliterColorsName[i]
                }
                toggleColors.addView(circularToggle)
                toggleColors.check(1234567890)
            }
        } else {
            nestedScrollColor.visibility = View.GONE
        }
        setBannerSliderViewPager(product.images)
        rateViewModel.getMyRate(myId, product.productId)
        rateViewModel.myRateing.observe(this) {
            if (it != null) {
                yourRate.rating = it.rate.toFloat();
            }
        }

        rateViewModel.getAllRate(product.productId)
        rateViewModel.allRateing.observe(this) {
            CalculatRate(it)
            var r1 = 0f;
            var r2 = 0f;
            var r3 = 0f;
            var r4 = 0f;
            var r5 = 0f
            val list: MutableList<SliceValue> = ArrayList();
            for (i in it) {
                if (0.0 < i.rate.toFloat() && i.rate.toFloat() <= 1.0) {
                    r1 += 1
                    list.add(SliceValue(r1, resources.getColor(R.color.red)))
                }
                if (1.0 < i.rate.toFloat() && i.rate.toFloat() <= 2.0) {
                    r2 += 1
                    list.add(SliceValue(r2, resources.getColor(R.color.orange)))
                }
                if (2.0 < i.rate.toFloat() && i.rate.toFloat() <= 3.0) {
                    r3 += 1
                    list.add(SliceValue(r3, resources.getColor(R.color.yellow)))
                }
                if (3.0 < i.rate.toFloat() && i.rate.toFloat() <= 4.0) {
                    r4 += 1
                    list.add(SliceValue(r4, resources.getColor(R.color.yellowgreen)))
                }
                if (4.0 < i.rate.toFloat() && i.rate.toFloat() <= 5.0) {
                    r5 += 1
                    list.add(SliceValue(r5, resources.getColor(R.color.green)))
                }
            }
            val data = PieChartData(list);
            data.setHasCenterCircle(true)
            data.setHasLabels(true)
            data.centerText1 = "Rating"
            data.centerText1FontSize = 18
            data.centerText1Color = Color.BLUE
            pieChartView.pieChartData = data;
        }

        /**
         * for order product
         */
        fab.setOnClickListener {
            if (qNumber != 0) {
                val id = myId + ":" + product.productId
                val map: HashMap<String, Any> = HashMap();
                map["productId"] = product.productId;
                map["buyerId"] = myId;
                map["sellerId"] = product.adminId;
                map["totalPrice"] = (qNumber.toDouble() * product.price.toDouble()).toLong();
                map["quantity"] = qNumber.toLong();
                map["price"] = product.price.toLong();
                map["images"] = product.images;
                map["productName"] = product.productName;
                map["lastPrice"] = product.lastPrice
                map["id"] = id;
                map["toggleItemColor"] = toggleItemColor!!
                map["toggleItemSize"] = toggleItemSize!!
                reference.child(CART).child(myId).child(id).setValue(map);
                ShowToast("check your cart");
            } else {
                ShowToast("you can't order less than one!");
            }


        }
        /**
         * put rate for product
         * */
        yourRate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            val id = myId + ":" + product.productId
            val map: HashMap<String, Any> = HashMap();
            map["id"] = id
            map["rate"] = rating.toString()
            map["myId"] = myId
            reference.child(RATE).child(product.productId).child(id)
                .updateChildren(map);
        }

        plus.setOnClickListener {
            qNumber += 1
            quantity.text = qNumber.toString()
        }

        mins.setOnClickListener {
            if (qNumber <= 1) {
                quantity.text = qNumber.toString()
                ShowToast("you can't order less than one!");
                return@setOnClickListener
            } else {
                qNumber -= 1
                quantity.text = qNumber.toString()
            }

        }

        share.setOnClickListener {
            ShareProduct()
        }

        comments.setOnClickListener {
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("idproduct", product.productId)
            startActivity(intent)
        }

    }

    private fun CalculatRate(rate: ArrayList<Rate>) {
        speedView.maxSpeed = rate.size.toFloat()
        var count = 0;
        for (r in rate) {
            if (r.rate.toFloat() > 2.0f) {
                count++;
            }
        }
        // for indicate NeedleIndicator to good rating
        speedView.speedTo(count.toFloat());
        CaculatePercentageTotalRate(count.toLong(), rate.size.toLong());
    }

    private fun CaculatePercentageTotalRate(c: Long, all: Long) {
        val unit: Double = (all / 5.0)
        val percentage: Double = (c / unit)
        totalPercentage.text = percentage.toString()
        val map: HashMap<String, Any> = HashMap();
        map["totalPercentage"] = percentage.toString();
        reference.child(PRODUCT).child(product.productId).updateChildren(map);
    }


    fun setBannerSliderViewPager(list: String?) {
        val sliderAdapter = ProductSliderAdapter(list)
        banner_slier_view_pager.adapter = sliderAdapter
        banner_slier_view_pager.clipToPadding = false
        banner_slier_view_pager.pageMargin = 20
    }


    fun ShareProduct() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "https://www.market.doubleclick.com/product/" + product.productId
            )
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}