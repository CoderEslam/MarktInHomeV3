package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.Constantes.CHILDREN
import com.doubleclick.marktinhome.Model.Constantes.PARENTS
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.MainScreen.Groups.CreateGroup.Step2FragmentDirections
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var image: ImageView
    private lateinit var name: TextInputEditText
    private lateinit var editorder: TextInputEditText
    private lateinit var textInputLayoutEditOrder: TextInputLayout
    private var IMAGE_REQUEST: Int = 100;
    private lateinit var imageUri: Uri;
    private lateinit var progressBar: ProgressBar
    private lateinit var ok: TextView


    private val data by navArgs<EditFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment\
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        editorder = view.findViewById(R.id.editorder);
        progressBar = view.findViewById(R.id.progressBar);
        ok = view.findViewById(R.id.ok);
        textInputLayoutEditOrder = view.findViewById(R.id.textInputLayoutEditOrder);
        if (!data.image.equals("")) {
            Glide.with(requireContext()).load(data.image).into(image)
        }
        name.setText(data.name.toString())
        //TODO update Child
        if (data.type.equals("child")) {
            textInputLayoutEditOrder.visibility = View.GONE
        }
        //TODO update parent
        if (data.type.equals("parent")) {
            textInputLayoutEditOrder.visibility = View.VISIBLE
            editorder.setText(data.order.toString())
        }
        // TODO add Parent
        if (data.type.equals("addParent")) {
            textInputLayoutEditOrder.visibility = View.VISIBLE
        }
        //TODO add Child
        if (data.type.equals("addChild")) {
            textInputLayoutEditOrder.visibility = View.GONE
        }

        image.setOnClickListener {
            openImage();
        }
        ok.setOnClickListener {
            UploadData();
            progressBar.visibility = View.VISIBLE
        }

        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!;
            image.setImageURI(imageUri)
        }

    }

    override fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    private fun UploadData() {
        //TODO updata child
        if (data.type.equals("child")) {
            // update to name
            if (name.text!!.toString() != data.name.toString()) {
                val map = HashMap<String, Any>()
                map["name"] = name.text.toString();
                reference.child(CHILDREN).child(data.id.toString()).updateChildren(map)
                    .addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful) {
                            ShowToast("Done")
                            progressBar.visibility = View.GONE
                        }
                    })
            }
            if (!imageUri.equals("")) {
                val fileReference = FirebaseStorage.getInstance().reference.child("Uploads")
                    .child("" + System.currentTimeMillis() + "." + getFileExtension(imageUri))
                val uploadTask: StorageTask<*>
                uploadTask = fileReference.putFile(imageUri);
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot?, Task<Uri?>?> { task ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }
                    return@Continuation fileReference.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val map = HashMap<String, Any>()
                        val downloadUri: Uri = task.result!!
                        map["image"] = downloadUri.toString()
                        if (name.text!!.toString() != data.name.toString()) {
                            map["name"] = name.text.toString();
                        }
                        reference.child(CHILDREN).child(data.id.toString()).updateChildren(map)
                            .addOnCompleteListener(OnCompleteListener {
                                if (it.isSuccessful) {
                                    ShowToast("Done")
                                    progressBar.visibility = View.GONE
                                }
                            })
                    }
                }
            }
            // TODO update parent
        } else if (data.type.equals("parent")) {
            // update to name
            if (name.text!!.toString() != data.name.toString()) {
                val map = HashMap<String, Any>()
                map["name"] = name.text.toString();
                reference.child(PARENTS).child(data.id.toString()).updateChildren(map)
                    .addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful) {
                            ShowToast("Done")
                            progressBar.visibility = View.GONE
                        }
                    })
            }
            // update order
            if (editorder.text!!.toString() != data.order.toString()) {
                val map = HashMap<String, Any>()
                map["order"] = editorder.text.toString();
                reference.child(PARENTS).child(data.id.toString()).updateChildren(map)
                    .addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful) {
                            ShowToast( "Done")
                            progressBar.visibility = View.GONE
                        }
                    })
            }
            try {
                if (imageUri.toString() != "") {
                    val fileReference = FirebaseStorage.getInstance().reference.child("Uploads")
                        .child("" + System.currentTimeMillis() + "." + getFileExtension(imageUri))
                    val uploadTask: StorageTask<*>
                    uploadTask = fileReference.putFile(imageUri);
                    uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot?, Task<Uri?>?> { task ->
                        if (!task.isSuccessful) {
                            throw task.exception!!
                        }
                        progressBar.visibility = View.GONE
                        return@Continuation fileReference.downloadUrl
                    }).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val map = HashMap<String, Any>()
                            val downloadUri: Uri = task.result!!
                            map["image"] = downloadUri.toString()
                            // update name
                            if (!name.text!!.toString().equals(data.name.toString())) {
                                map["name"] = name.text.toString();
                            }
                            // update order
                            if (!editorder.text!!.toString().equals(data.order.toString())) {
                                map["order"] = editorder.text.toString();
                            }
                            reference.child(PARENTS).child(data.id.toString()).updateChildren(map)
                                .addOnCompleteListener(OnCompleteListener {
                                    if (it.isSuccessful) {
                                        ShowToast( "Done")
                                    }
                                })
                        }
                    }
                }
            } catch (e: Exception) {

            }

            // TODO add parent
        } else if (data.type.equals("addParent")) {
            val fileReference = FirebaseStorage.getInstance().reference.child("Uploads")
                .child("" + System.currentTimeMillis() + "." + getFileExtension(imageUri))
            val uploadTask: StorageTask<*>
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot?, Task<Uri?>?> { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                return@Continuation fileReference.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val map = HashMap<String, Any>()
                    val downloadUri: Uri = task.result!!
                    val pushId = reference.push().key.toString();
                    map["image"] = downloadUri.toString()
                    map["name"] = name.text.toString();
                    map["pushId"] = pushId;
                    map["order"] = editorder.text.toString();
                    reference.child(PARENTS).child(pushId).updateChildren(map)
                        .addOnCompleteListener(OnCompleteListener {
                            if (it.isSuccessful) {
                                ShowToast( "Done")
                                progressBar.visibility = View.GONE
                            }
                        })
                }
            }
            // TODO add child
        } else if (data.type.equals("addChild")) {
            val fileReference = FirebaseStorage.getInstance().reference.child("Uploads")
                .child("" + System.currentTimeMillis() + "." + getFileExtension(imageUri))
            val uploadTask: StorageTask<*>
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot?, Task<Uri?>?> { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                return@Continuation fileReference.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val map = HashMap<String, Any>()
                    val downloadUri: Uri = task.result!!
                    val pushId = reference.push().key.toString();
                    map["image"] = downloadUri.toString()
                    map["name"] = name.text.toString();
                    map["pushId"] = pushId;
                    map["PushIdParents"] = data.id.toString(); // PushIdParents
                    reference.child(CHILDREN).child(pushId).updateChildren(map)
                        .addOnCompleteListener(OnCompleteListener {
                            if (it.isSuccessful) {
                                ShowToast( "Done")
                                progressBar.visibility = View.GONE
                            }
                        })
                }
            }
        }
    }

    override fun getFileExtension(uri: Uri?): String? {
        val contentResolver = requireContext().contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

}