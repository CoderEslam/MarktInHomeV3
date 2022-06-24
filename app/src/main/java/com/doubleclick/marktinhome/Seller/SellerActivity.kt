package com.doubleclick.marktinhome.Seller

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.OnOrder
import com.doubleclick.ViewModel.CartViewModel
import com.doubleclick.ViewModel.OrderViewModel
import com.doubleclick.marktinhome.Adapters.OrderAdapter
import com.doubleclick.marktinhome.BaseApplication
import com.doubleclick.marktinhome.BaseApplication.ShowToast
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.Constantes.ORDERS
import com.doubleclick.marktinhome.Model.Constantes.RECENTORDER
import com.doubleclick.marktinhome.Model.OrdersDate
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Repository.BaseRepository.myId
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

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
            Log.e("OrderViewModelSeller", it.toString());
            if (it.size == 0) {
                Toast.makeText(
                    this,
                    "you don't have an orders",
                    Toast.LENGTH_LONG
                ).show()
            }
            val orderAdapter = OrderAdapter(it, this)
            MyOrder.adapter = orderAdapter;
        }
    }


    override fun OnOKItemOrder(orders: OrdersDate?) {
        val time = Date().time;
        val pushId = myId + ":" + orders!!.orders.productId + ":" + time
        val map: HashMap<String, Any> = HashMap();
        map["productId"] = orders.orders.productId;
        map["buyerId"] = orders.orders.buyerId;
        map["sellerId"] = myId;
        map["quantity"] = orders.orders.quantity;
        map["id"] = pushId;
        map["date"] = time;
        map["address"] = orders.orders.address;
        map["phone"] = orders.orders.phone;
        map["toggleItemColor"] = orders.orders.toggleItemColor;
        map["toggleItemSize"] = orders.orders.toggleItemSize;
        map["anotherPhone"] = orders.orders.anotherPhone;
        try {
            if (!TextUtils.isEmpty(orders.orders.locationUri)) {
                map["locationUri"] = orders.orders.locationUri;
            }
        } catch (e: NullPointerException) {

        } finally {
            reference.child(RECENTORDER).child(pushId).updateChildren(map);
            reference.child(ORDERS).child(orders.orders.id)
                .removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        ShowToast("Delivered")
                    }
                }
        }
    }

    override fun OnCancelItemOrder(orders: OrdersDate?) {
        reference.child(Constantes.ORDERS).child(orders!!.orders.id)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ShowToast("Deleted")
                }
            }
    }
}