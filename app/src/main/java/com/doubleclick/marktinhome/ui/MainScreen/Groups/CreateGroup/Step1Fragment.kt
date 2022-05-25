package com.doubleclick.marktinhome.ui.MainScreen.Groups.CreateGroup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doubleclick.ViewModel.GroupViewModel
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.ui.MainScreen.MainScreenActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Step1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Step1Fragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var name: EditText
    private lateinit var details: EditText
    private lateinit var next: Button
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var back: ImageView
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
        val view = inflater.inflate(R.layout.fragment_step_1, container, false)
        name = view.findViewById(R.id.name)
        details = view.findViewById(R.id.details)
        next = view.findViewById(R.id.next)
        progressIndicator = view.findViewById(R.id.progressBar)
        back = view.findViewById(R.id.back)
        back.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    MainScreenActivity::class.java
                )
            )
        }

        next.setOnClickListener { v: View? ->
            if (name.text.toString() != "" && details.text.toString() != "") {
                progressIndicator.visibility = View.VISIBLE
                val time = Date().time
                val pushId = "$myId:$time"
                val map: MutableMap<String, Any> =
                    HashMap()
                map["name"] = name.text.toString()
                map["details"] = details.text.toString()
                map["time"] = time
                map["id"] = pushId
                map["createdBy"] = myId
                reference.child(Constantes.GROUPS).child(pushId).updateChildren(map)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressIndicator.visibility = View.GONE
                            findNavController().navigate(
                                Step1FragmentDirections.actionStepOneFragmentToStepTwoFragment(
                                    pushId
                                )
                            )
                        } else {
                            Snackbar.make(v!!, "Check your internet", Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
            } else if (name.text.toString() == "") {
                name.error = "required"
            }  else if (details.text.toString() == "") {
                details.error = "required"
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Step1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Step1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}