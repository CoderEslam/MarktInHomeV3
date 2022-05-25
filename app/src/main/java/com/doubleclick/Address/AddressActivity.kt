package com.doubleclick.Address;

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.doubleclick.Api.APIService
import com.doubleclick.ViewModel.CartViewModel
import com.doubleclick.marktinhome.Model.*
import com.doubleclick.marktinhome.Model.Constantes.CART
import com.doubleclick.marktinhome.Model.Constantes.ORDERS
import com.doubleclick.marktinhome.Notifications.Client
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Repository.BaseRepository.myId
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddressActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var imagePerson: CircleImageView;
    lateinit var name: TextInputEditText
    lateinit var phone: TextInputEditText
    lateinit var anotherPhone: TextInputEditText
    lateinit var address: TextInputEditText
    lateinit var confirmFinalOrderBtn: Button
    private lateinit var cartViewModel: CartViewModel
    private var carts: ArrayList<Cart> = ArrayList()
    var client: FusedLocationProviderClient? = null
    var googleMap: GoogleMap? = null
    var mLocationRequest: LocationRequest? = null
    var uri: String? = null
    lateinit var myLocation: SwitchCompat
    private lateinit var apiService: APIService
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address);
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        imagePerson = findViewById(R.id.imagePerson);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        anotherPhone = findViewById(R.id.anotherPhone);
        address = findViewById(R.id.address);
        confirmFinalOrderBtn = findViewById(R.id.confirmFinalOrderBtn)
        myLocation = findViewById(R.id.myLocation);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(
            APIService::class.java
        )
        reference =
            FirebaseDatabase.getInstance("https://marketinhome-99d25-default-rtdb.firebaseio.com/").reference

        client = LocationServices.getFusedLocationProviderClient(this)
        confirmFinalOrderBtn.setOnClickListener {
            confirmOrder(
                name.text.toString(),
                phone.text.toString(),
                anotherPhone.text.toString(),
                address.text.toString()
            )
        }

        cartViewModel.CartLiveData().observe(this) {
            if (!it.id.equals("")) {
                carts.add(it)
                confirmFinalOrderBtn.isEnabled = true
            }
        }

        myLocation.setOnClickListener {
            getMyLocation()
        }


    }

    private fun confirmOrder(name: String, phone: String, AnotherPhone: String, Address: String) {
        for (i in carts.indices) {
            val time: Long = Date().time
            val id = myId + ":" + carts[i].productId + ":" + time;
            val map: HashMap<String, Any> = HashMap();
            map["productId"] = carts[i].productId
            map["price"] = carts[i].price
            map["quantity"] = carts[i].quantity
            map["lastPrice"] = carts[i].lastPrice
            map["productName"] = carts[i].productName
            map["images"] = carts[i].images
            map["id"] = id
            map["buyerId"] = carts[i].buyerId
            map["sellerId"] = carts[i].sellerId
            map["totalPrice"] = carts[i].totalPrice
            map["phone"] = phone
            map["anotherPhone"] = AnotherPhone
            map["address"] = Address
            map["name"] = name
            map["date"] = time
            map["toggleItem"] = carts[i].toggleItem
            if (myLocation.isChecked) {
                if (uri.toString() != "") {
                    map["locationUri"] = uri!!
                    sendNotifiaction(this, carts[i].sellerId, carts[i].productName);
                    reference.child(ORDERS).child(id).updateChildren(map)
                    reference.child(CART).child(carts[i].id).removeValue()
                } else {
                    Toast.makeText(this, "Open your location", Toast.LENGTH_SHORT).show()
                }
            } else {
                sendNotifiaction(this, carts[i].sellerId, carts[i].productName);
                reference.child(ORDERS).child(id).updateChildren(map)
                reference.child(CART).child(carts[i].id).removeValue()
            }


        }
    }

    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val task = client!!.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
//                    supportMapFragment!!.getMapAsync {
                    val latLng = LatLng(location.latitude, location.longitude)
                    uri = "[" + location.latitude + "," + location.longitude + "]"
//                    }
                } else {
                    Toast.makeText(this, "Open your location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun sendNotifiaction(context: Context?, receiver: String, Productname: String) {
        reference.child(Constantes.USER).child(receiver).get()
            .addOnCompleteListener(OnCompleteListener<DataSnapshot> { task ->
                val dataSnapshot = task.result
                val user = dataSnapshot.getValue(
                    User::class.java
                )
                val data = Data(
                    myId, R.drawable.icon_app,
                    "Product name: $Productname", "New Order", receiver
                )
                val sender = Sender(data, user!!.token)
                apiService.sendNotification(sender)
                    .enqueue(object : Callback<MyResponse> {
                        override fun onResponse(
                            call: Call<MyResponse>,
                            response: Response<MyResponse>
                        ) {
                            if (response.code() == 200) {
                                if (response.body()!!.success != 1) {
                                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
                    })
            })
    }

}