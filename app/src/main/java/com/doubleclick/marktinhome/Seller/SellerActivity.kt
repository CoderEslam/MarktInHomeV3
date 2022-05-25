package com.doubleclick.marktinhome.Seller

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.OnOrder
import com.doubleclick.ViewModel.CartViewModel
import com.doubleclick.ViewModel.OrderViewModel
import com.doubleclick.marktinhome.Adapters.OrderAdapter
import com.doubleclick.marktinhome.BaseApplication.ShowToast
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.Constantes.ORDERS
import com.doubleclick.marktinhome.Model.Constantes.RECENTORDER
import com.doubleclick.marktinhome.Model.Orders
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Repository.BaseRepository.myId
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class SellerActivity : AppCompatActivity(), OnOrder {

    lateinit var MyOrder: RecyclerView;
    lateinit var cartViewModel: CartViewModel
    lateinit var orderViewModel: OrderViewModel
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)
        MyOrder = findViewById(R.id.MyOrder);
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        reference =
            FirebaseDatabase.getInstance("https://marketinhome-99d25-default-rtdb.firebaseio.com").reference
        orderViewModel.myOrderLiveData.observe(this) {
            var orderAdapter = OrderAdapter(it, this)
            MyOrder.adapter = orderAdapter;
        }
    }

    override fun OnOKItemOrder(orders: Orders?) {
        var time = Date().time;
        val pushId = myId + ":" + orders!!.productId + ":" + time
        val map: HashMap<String, Any> = HashMap();
        map["productId"] = orders.productId;
        map["buyerId"] = orders.buyerId;
        map["sellerId"] = myId;
        map["totalPrice"] = orders.totalPrice;
        map["quantity"] = orders.quantity;
        map["price"] = orders.price;
        map["images"] = orders.images;
        map["productName"] = orders.productName;
        map["id"] = pushId;
        map["date"] = time;
        map["address"] = orders.address;
        map["phone"] = orders.phone;
        map["toggleItem"] = orders.toggleItem;
        map["anotherPhone"] = orders.anotherPhone;
        try {
            if (!TextUtils.isEmpty(orders.locationUri)) {
                map["locationUri"] = orders.locationUri;
            }
        } catch (e: NullPointerException) {

        } finally {
            reference.child(RECENTORDER).child(pushId).updateChildren(map);
            reference.child(ORDERS).child(orders.id)
                .removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        ShowToast("Delivered")
                    }
                }
        }
    }

    override fun OnCancelItemOrder(orders: Orders?) {
        reference.child(Constantes.ORDERS).child(orders!!.id)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ShowToast("Deleted")
                }
            }
    }
}