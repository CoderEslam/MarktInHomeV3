package com.doubleclick.marktinhome.ui.MainScreen.Chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doubleclick.UserInter
import com.doubleclick.ViewModel.ChatListViewModel
import com.doubleclick.ViewModel.UserViewModel
import com.doubleclick.marktinhome.Adapters.AllUserChatListAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListData
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListDatabaseRepository
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListViewModelDatabase
import com.doubleclick.marktinhome.Model.ChatList
import com.doubleclick.marktinhome.Model.User
import com.doubleclick.marktinhome.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.paypal.android.sdk.v
import com.todkars.shimmer.ShimmerRecyclerView


class ChatListFragment : BaseFragment(), UserInter {

    lateinit var allUser: ShimmerRecyclerView;
    lateinit var chatListViewModel: ChatListViewModel
    lateinit var chatUser: FloatingActionButton
    private var sharePost: String = "null"
    private lateinit var allUserChatListAdapter: AllUserChatListAdapter
    private lateinit var chatListViewModelDatabase: ChatListViewModelDatabase
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (!it.isEmpty) {
                sharePost = it.getString("sharePost").toString()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)
        allUser = view.findViewById(R.id.allUser);
        allUser.showShimmer();
        chatUser = view.findViewById(R.id.chatUser);
        chatListViewModel = ViewModelProvider(this)[ChatListViewModel::class.java];
        chatListViewModelDatabase = ViewModelProvider(this)[ChatListViewModelDatabase::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]



        chatListViewModelDatabase.chatListData.observe(viewLifecycleOwner) {
            allUserChatListAdapter = AllUserChatListAdapter(this, it);
            allUser.adapter = allUserChatListAdapter;
        }

        chatListViewModel.ChatListInserted().observe(viewLifecycleOwner) {
            try {
                chatListViewModelDatabase.insertChatList(it)
            } catch (e: Exception) {

            }
            try {
                insertUser(it.id);
                updateUser(it.id);
            } catch (e: Exception) {

            }
        }

        chatListViewModel.ChatListUpdate().observe(viewLifecycleOwner) {
            chatListViewModelDatabase.updateChatList(it)
            updateUser(it.id);
        }

        chatListViewModel.ChatListDeleted().observe(viewLifecycleOwner) {
            chatListViewModelDatabase.deleteChatList(it)
            deleteUser(it.id);
        }



        chatUser.setOnClickListener {
            findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToAllUsersFragment())
        }

        return view;
    }

    override fun ItemUser(user: User) {}

    override fun ItemUserChanged(user: User) {}

    override fun ItemUserDeleted(user: User) {}

    override fun ItemUserInfoById(user: User?) {}

    override fun AllUser(user: ArrayList<User>?) {}

    override fun OnUserLisitner(user: User?) {
        try {
            if (sharePost != "null") {
                val chatFragment = ChatFragment();
                val bundle = Bundle();
                bundle.putString("sharePost", sharePost);
                bundle.putString("userId", user!!.id);
                chatFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_Chat, chatFragment).commit()
            } else {
                findNavController().navigate(
                    ChatListFragmentDirections.actionChatListFragmentToChatFragment(
                        user!!.id
                    )
                )
            }
        } catch (e: Exception) {

        }
    }

    private fun insertUser(id: String) {
        userViewModel.getUserById(id)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            chatListViewModelDatabase.insertUser(it)
        }
    }

    private fun updateUser(id: String) {
        userViewModel.getUserById(id)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            chatListViewModelDatabase.updateUser(it)
        }
    }

    private fun deleteUser(id: String) {
        userViewModel.getUserById(id)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            chatListViewModelDatabase.deleteUser(it)
        }
    }

}