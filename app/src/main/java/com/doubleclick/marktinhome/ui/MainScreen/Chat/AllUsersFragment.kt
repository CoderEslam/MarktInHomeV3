package com.doubleclick.marktinhome.ui.MainScreen.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doubleclick.UserInter
import com.doubleclick.ViewModel.UserViewModel
import com.doubleclick.marktinhome.Adapters.AllUserChatListAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Model.User
import com.doubleclick.marktinhome.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.todkars.shimmer.ShimmerRecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AllUsersFragment : BaseFragment(), UserInter {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var allUser: ShimmerRecyclerView
    private lateinit var userViewModel: UserViewModel
    private lateinit var search_users: AppCompatEditText

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

        val view = inflater.inflate(R.layout.fragment_all_users, container, false)
        allUser = view.findViewById(R.id.allUser);
        search_users = view.findViewById(R.id.search_users);
        allUser.showShimmer()
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java];
        userViewModel.allUsers.observe(viewLifecycleOwner, Observer {
            var allUserChatListAdapter = AllUserChatListAdapter(it, this);
            allUser.adapter = allUserChatListAdapter;
            allUser.hideShimmer()
        })
        search_users.addTextChangedListener {
            userViewModel.getUserByName(it.toString());
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
         * @return A new instance of fragment AllUsersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllUsersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun ItemUser(user: User?) {}

    override fun ItemUserInfoById(user: User?) {}

    override fun AllUser(user: ArrayList<User>?) {}

    override fun OnUserLisitner(user: User) {
        findNavController().navigate(AllUsersFragmentDirections.actionAllUsersFragmentToChatFragment(user.id))
    }
}