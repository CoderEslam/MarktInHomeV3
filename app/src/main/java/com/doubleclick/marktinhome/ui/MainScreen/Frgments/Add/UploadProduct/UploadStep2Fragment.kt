package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add.UploadProduct

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.marktinhome.Adapters.ImageAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Constantes.PRODUCT
import com.doubleclick.marktinhome.Model.Product
import com.doubleclick.marktinhome.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.util.*
import kotlin.collections.ArrayList


class UploadStep2Fragment : BaseFragment(), ImageAdapter.deleteImage {

    private lateinit var productImages: RecyclerView
    private lateinit var next: Button
    private lateinit var addImages: FloatingActionButton
    private var IMAGES_REQUEST: Int = 100
    private var uris: ArrayList<String> = ArrayList()
    private lateinit var imageAdapter: ImageAdapter
    val product by navArgs<UploadStep2FragmentArgs>()

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
        val view = inflater.inflate(R.layout.fragment_upload_step2, container, false)
        productImages = view.findViewById(R.id.productImages);
        next = view.findViewById(R.id.next);
        addImages = view.findViewById(R.id.addImages);

        addImages.setOnClickListener {
            openImage()
        }

        next.setOnClickListener {
            val productAllData = Product(
                product.product.productId,
                product.product.price,
                "",
                0,
                myId.toString(),
                product.product.productName.toString(),
                product.product.lastPrice,
                product.product.tradeMark.toString(),
                product.product.parentCategoryName.toString(),
                product.product.childCategoryName.toString(),
                product.product.parentCategoryId.toString(),
                product.product.childCategoryId.toString(),
                0,
                product.product.discount,
                product.product.keywords,
                uris.toString(),
                product.product.sizes.toString(),
                product.product.colors.toString(),
                product.product.colorsName.toString(),
                product.product.ratingSeller.toFloat()
            );
            findNavController().navigate(
                UploadStep2FragmentDirections.actionUploadStep2FragmentToRichFragment(
                    productAllData
                )
            )
        }


        return view;
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGES_REQUEST && resultCode == Activity.RESULT_OK) {
            val clip = data!!.clipData
            if (clip != null) {
                for (j in 0 until clip.itemCount) {
                    val item = clip.getItemAt(j)
                    uris.add(item.uri.toString())
                }
                imageAdapter = ImageAdapter(uris, this);
                productImages.adapter = imageAdapter
            }
        }
    }


    override fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, IMAGES_REQUEST)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun deleteImage(postion: Int) {
        uris.removeAt(postion)
        imageAdapter.notifyItemRemoved(postion)
        imageAdapter.notifyDataSetChanged()
    }
}