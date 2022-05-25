package com.doubleclick.marktinhome.ui.MainScreen.Chat

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import com.doubleclick.marktinhome.Model.User
import com.doubleclick.marktinhome.R

class ChatActivity : AppCompatActivity() {


    private var userId: String = "null"
    private var sharePost: String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        try {
            // todo recive userId from GroupAdapter and menu_profileFragment
            userId = intent.getStringExtra("userId").toString()
//            Toast.makeText(this, userId, Toast.LENGTH_LONG).show()
            sharePost = intent.getStringExtra("sharePost").toString()
            if (userId != "null") {
                val chatFragment = ChatFragment()
                val bundle = Bundle();
                bundle.putString("userId", userId)
                chatFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_Chat, chatFragment).commit()
            } else if (sharePost != "null") {
                val chatListFragment = ChatListFragment();
                val bundle = Bundle();
                bundle.putString("sharePost", sharePost);
                chatListFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_Chat, chatListFragment).commit()
            }

        } catch (e: Exception) {
            Log.e("userException", e.message.toString())
        }
    }
}