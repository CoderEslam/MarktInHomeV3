package com.doubleclick.marktinhome.ui.MainScreen.Chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doubleclick.UserInter
import com.doubleclick.ViewModel.ChatListViewModel
import com.doubleclick.marktinhome.Adapters.AllUserChatListAdapter
import com.doubleclick.marktinhome.BaseFragment
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListDatabaseRepository
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListViewModelDatabase
import com.doubleclick.marktinhome.Database.UserDatabase.UserViewModelDatabase
import com.doubleclick.marktinhome.Model.User
import com.doubleclick.marktinhome.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.todkars.shimmer.ShimmerRecyclerView


class ChatListFragment : BaseFragment(), UserInter {

    lateinit var allUser: ShimmerRecyclerView;
    lateinit var chatListViewModel: ChatListViewModel
    lateinit var chatUser: FloatingActionButton
    private var sharePost: String = "null"
    private lateinit var userViewModelDatabase: UserViewModelDatabase;
    private var allUsers: ArrayList<User> = ArrayList();
    private lateinit var allUserChatListAdapter: AllUserChatListAdapter
    private lateinit var chatListViewModelDatabase: ChatListViewModelDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (!it.isEmpty) {
                sharePost = it.getString("sharePost").toString()
            }
        }
    }

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
        userViewModelDatabase = ViewModelProvider(this)[UserViewModelDatabase::class.java]
        chatListViewModelDatabase = ViewModelProvider(this)[ChatListViewModelDatabase::class.java]
        allUserChatListAdapter = AllUserChatListAdapter(allUsers, this);
        allUser.adapter = allUserChatListAdapter;
        allUsers.clear()
        allUsers.addAll(userViewModelDatabase.allUsers);
        chatListViewModel.UserInserted().observe(viewLifecycleOwner) {
            Log.e("USER", it.toString());
            if (!allUsers.contains(it)) {
                Log.e("USER", it.toString());
                userViewModelDatabase.insert(it)
                allUsers.add(it);
                allUser.hideShimmer()
            }
        }

        chatListViewModel.UserDeleted().observe(viewLifecycleOwner) {
            allUserChatListAdapter.notifyItemRemoved(allUsers.indexOf(it))
            userViewModelDatabase.delete(it)
            allUsers.remove(it)
        }
        chatListViewModel.UserChanged().observe(viewLifecycleOwner) {
            allUsers[allUsers.indexOf(it)] = it
            userViewModelDatabase.update(it)
            allUserChatListAdapter.notifyItemChanged(allUsers.indexOf(it))
        }

        Log.e("CHATLIST", chatListViewModelDatabase.allUsers.toString());
        chatListViewModel.ChatListInserted().observe(viewLifecycleOwner) {
            chatListViewModelDatabase.insert(it)
        }

        chatListViewModel.ChatListUpdate().observe(viewLifecycleOwner) {
            chatListViewModelDatabase.update(it)

        }

        chatListViewModel.ChatListDeleted().observe(viewLifecycleOwner) {
            chatListViewModelDatabase.delete(it)
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

}