package com.doubleclick.marktinhome.ui.MainScreen.Frgments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.Address.AddressActivity
import com.doubleclick.OnCartLisnter
import com.doubleclick.ViewModel.CartViewModel
import com.doubleclick.marktinhome.Adapters.CartAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Cart
import com.doubleclick.marktinhome.Model.Constantes.CART
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Seller.SellerActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : BaseFragment(), OnCartLisnter {


    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartRecycler: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var Continue: TextView
    private lateinit var totalPrice: TextView
    private var total = 0.0
    lateinit var myOrder: TextView
    private var carts: ArrayList<Cart> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartRecycler = view.findViewById(R.id.cartRecycler)
        Continue = view.findViewById(R.id.Continue)
        totalPrice = view.findViewById(R.id.totalPrice)
        myOrder = view.findViewById(R.id.MyOrder);
        cartAdapter = CartAdapter(carts, reference, this)
        cartRecycler.adapter = cartAdapter

        cartViewModel.CartLiveData().observe(viewLifecycleOwner) {
            if (!it.id.equals("")) {
                carts.add(it)
                cartAdapter.notifyItemInserted(carts.size - 1);
                cartAdapter.notifyDataSetChanged()
                total += it.price.toDouble() * it.quantity.toDouble()
                totalPrice.text = total.toString()
                Continue.setOnClickListener {
                    try {
                        startActivity(Intent(requireContext(), AddressActivity::class.java))
                    } catch (e: Exception) {

                    }
                }
            } else {
                ShowToast("you don't have orders")
            }
        }

        myOrder.setOnClickListener {
            startActivity(Intent(context, SellerActivity::class.java))
        }

        cartViewModel.CartAddLiveData().observe(viewLifecycleOwner) {
            carts[carts.indexOf(it)] = it
            cartAdapter.notifyItemChanged(carts.indexOf(it))
            cartAdapter.notifyDataSetChanged()
            calculatePrice()
        }
        cartViewModel.CartMinsLiveData().observe(viewLifecycleOwner) {
            carts[carts.indexOf(it)] = it
            cartAdapter.notifyItemChanged(carts.indexOf(it))
            cartAdapter.notifyDataSetChanged()
            calculatePrice()
        }
        cartViewModel.CartDeleteLiveData().observe(viewLifecycleOwner) {
            carts.remove(it)
            cartAdapter.notifyItemRemoved(carts.indexOf(it))
            cartAdapter.notifyDataSetChanged()
            calculatePrice()
        }
        return view
    }

    fun calculatePrice() {
        total = 0.0
        for (c in carts) {
            total += c.price * c.quantity
            totalPrice.text = total.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        total = 0.0
        totalPrice.text = total.toString()

    }

    override fun getCart(cart: Cart) {}

    @SuppressLint("NotifyDataSetChanged")
    override fun OnAddItemOrder(cart: Cart, pos: Int) {
        var quantity: Int = cart.quantity.toInt()
        val map: HashMap<String, Any> = HashMap();
        map["quantity"] = ++quantity
        map["totalPrice"] = (cart.price.toInt() * quantity).toLong()
        reference.child(CART).child(cart.id).updateChildren(map);
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun OnMinsItemOrder(cart: Cart, pos: Int) {
        var quantity: Int = cart.quantity.toInt();
        val map: HashMap<String, Any> = HashMap();
        map["quantity"] = --quantity
        map["totalPrice"] = (cart.price.toInt() * quantity).toLong()
        reference.child(CART).child(cart.id).updateChildren(map);
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun OnDeleteItemOrder(cart: Cart, pos: Int) {
        reference.child(CART).child(cart.id).removeValue()
        carts.removeAt(pos)
        cartAdapter.notifyItemRemoved(pos)
        cartAdapter.notifyDataSetChanged()
    }

}