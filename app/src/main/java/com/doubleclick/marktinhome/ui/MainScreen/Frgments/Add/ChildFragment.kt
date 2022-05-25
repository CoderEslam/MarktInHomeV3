package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.ViewModel.ProductViewModel
import com.doubleclick.marktinhome.Adapters.ChildAdapter
import com.doubleclick.marktinhome.BaseFragment
import android.annotation.SuppressLint
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.doubleclick.marktinhome.Model.ChildCategory
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.Constantes.CHILDREN
import com.doubleclick.marktinhome.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class ChildFragment : BaseFragment(), ChildAdapter.OnChild {


    private lateinit var ChildRecycler: RecyclerView;
    private lateinit var productViewModel: ProductViewModel;
    val parentCategory by navArgs<ChildFragmentArgs>()
    lateinit var addChild: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_child, container, false)
        setHasOptionsMenu(true);
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productViewModel.getChildren(parentCategory.parent!!.pushId)
        ChildRecycler = view.findViewById(R.id.ChildRecycler);
        addChild = view.findViewById(R.id.addChild)
        productViewModel.children.observe(viewLifecycleOwner, Observer {
            var childAdapter = ChildAdapter(it, this)
            ChildRecycler.adapter = childAdapter;
        })

        addChild.setOnClickListener {
            UplaodChild()
        }
        return view;
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChildFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    fun UplaodChild() {
        findNavController().navigate(
            ChildFragmentDirections.actionChildFragmentToEditFragment(
                parentCategory.parent!!.pushId, // PushIdParents
                "addChild",
                "",
                "",
                ""
            )
        )
    }

    override fun onChild(childCategory: ChildCategory?) {
        if (childCategory != null) {
            findNavController().navigate(
                ChildFragmentDirections.actionChildFragmentToUploadFragment(
                    childCategory,
                    parentCategory.parent,
                )
            );
        }
    }

    override fun onChildLongClickListner(childCategory: ChildCategory?) {
        findNavController().navigate(
            ChildFragmentDirections.actionChildFragmentToEditFragment(
                childCategory!!.pushId,
                "child",
                childCategory.image,
                childCategory.name,
                ""
            )
        )
//        builder = AlertDialog.Builder(requireContext())
//        val view = LayoutInflater.from(context).inflate(R.layout.edit_on_product, null, false)
//        val editnameProduct: TextInputEditText = view.findViewById(R.id.editProduct)
//        val editorder: TextInputEditText = view.findViewById(R.id.editorder)
//        editorder.visibility = View.GONE
//        val ok: TextView = view.findViewById(R.id.ok)
//        ok.setOnClickListener {
//            val map = HashMap<String, Any>()
//            if (!editnameProduct.text.toString().equals("")) {
//                map.put("name", editnameProduct.text.toString());
//                reference.child(CHILDREN).child(childCategory!!.pushId).updateChildren(map);
//                editnameProduct.setText("")
//                view.visibility = View.GONE
//                ShowToast(context, "Done")
//            } else {
//                ShowToast(context, "field are required")
//            }
//
//        }
//        builder.setView(view)
//        builder.show()

    }
}