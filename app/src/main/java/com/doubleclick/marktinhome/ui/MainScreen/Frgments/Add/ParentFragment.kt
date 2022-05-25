package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.OnItem
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ParentAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.ParentCategory
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class ParentFragment : BaseFragment(), OnItem {


    private lateinit var productViewModel: ProductViewModel
    private lateinit var ParentRecyceler: RecyclerView;
    private lateinit var addParent: FloatingActionButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_parent, container, false)
        productViewModel =ViewModelProvider(this)[ProductViewModel::class.java]
        ParentRecyceler = view.findViewById(R.id.ParentRecyceler);
        addParent = view.findViewById(R.id.addParent);
        productViewModel.parent.observe(viewLifecycleOwner, Observer {
            var parentAdapter = ParentAdapter(it, this)
            ParentRecyceler.adapter = parentAdapter;
        })
        addParent.setOnClickListener {
            findNavController().navigate(
                ParentFragmentDirections.actionParentFragmentToEditFragment(
                    "",
                    "addParent",
                    "",
                    "",
                    ""
                )
            )
        }
        return view;
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ParentFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItem(parentCategory: ParentCategory?) {
        if (parentCategory != null) {
            findNavController().navigate(
                ParentFragmentDirections.actionParentFragmentToChildFragment(
                    parentCategory
                )
            )
        }
    }

    override fun onItemLong(parentCategory: ParentCategory?) {
        findNavController().navigate(
            ParentFragmentDirections.actionParentFragmentToEditFragment(
                parentCategory!!.pushId,
                "parent",
                parentCategory.image,
                parentCategory.name,
                parentCategory.order
            )
        )
//        builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.edit_on_product, null, false)
//        val editnameProduct: TextInputEditText = view.findViewById(R.id.editProduct)
//        val editorder: TextInputEditText = view.findViewById(R.id.editorder)
//        val ok: TextView = view.findViewById(R.id.ok)
//        ok.setOnClickListener {
//            val map = HashMap<String, Any>()
//            if (!editorder.text.toString().equals("")) {
//                map.put("order", "-" + editorder.text.toString());
//                editorder.setText("")
//            }
//            if (!editnameProduct.text.toString().equals("")) {
//                map.put("name", editnameProduct.text.toString());
//                editnameProduct.setText("")
//            }
//            if (!editnameProduct.text.toString().equals("") || !editorder.text.toString().equals("")
//            ) {
//                reference.child(PARENTS).child(parentCategory!!.pushId).updateChildren(map);
//                view.visibility = View.GONE
//                ShowToast(context, "Done")
//            }
//
//        }
//        builder.setView(view)
//        builder.show()

    }
}