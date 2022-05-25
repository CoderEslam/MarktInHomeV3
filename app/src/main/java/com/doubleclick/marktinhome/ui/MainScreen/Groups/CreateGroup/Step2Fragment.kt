package com.doubleclick.marktinhome.ui.MainScreen.Groups.CreateGroup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Step2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Step2Fragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val IMAGE_PICK_CODE = 100
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var selectImage: CircleImageView
    private lateinit var next: Button
    private lateinit var skip: TextView
    private lateinit var imageUri: Uri
    private val pushId by navArgs<Step2FragmentArgs>()
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_step_2, container, false)
        progressIndicator = view.findViewById(R.id.progressBar)
        selectImage = view.findViewById(R.id.selectImage)
        next = view.findViewById(R.id.next)
        skip = view.findViewById(R.id.skip)

        // todo upload data

        // todo upload data
        next.setOnClickListener(View.OnClickListener { v: View? ->
            progressIndicator.setVisibility(View.VISIBLE)
            uploadImage(imageUri)

        })
        // todo skip
        // todo skip
        skip.setOnClickListener(View.OnClickListener { v: View? -> })

        selectImage.setOnClickListener(View.OnClickListener { v: View? -> pickImage() })

        return view;
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            imageUri = Objects.requireNonNull(data).data!!
            selectImage!!.setImageURI(imageUri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uploadImage(image_uri: Uri) {
        val fileReference =
            FirebaseStorage.getInstance().reference.child("GroupsImages")
                .child("" + System.currentTimeMillis() + "." + getFileExtension(image_uri))
        val uploadTask: StorageTask<*>

        uploadTask = fileReference.putFile(image_uri);
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
                reference.child(Constantes.GROUPS).child(pushId.id.toString())
                    .updateChildren(map)
                    .addOnCompleteListener {
                        Snackbar.make(
                            requireView(),
                            "Profile photo updated",
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                        progressIndicator!!.visibility = View.GONE
                        findNavController().navigate(
                            Step2FragmentDirections.actionStepTwoFragmentToStepThreeFragment(
                                pushId.id.toString()
                            )
                        )
                    }
            }
        }
    }

    override fun getFileExtension(uri: Uri?): String? {
        val contentResolver = requireContext().contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Step2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Step2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}