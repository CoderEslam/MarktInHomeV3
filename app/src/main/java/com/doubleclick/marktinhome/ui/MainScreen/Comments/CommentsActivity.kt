package com.doubleclick.marktinhome.ui.MainScreen.Comments

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.ViewModel.CommentsViewModel
import com.doubleclick.ViewModel.UserViewModel
import com.doubleclick.marktinhome.Adapters.CommentAdapter
import com.doubleclick.marktinhome.BaseApplication.context
import com.doubleclick.marktinhome.Model.CommentsProductData
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.Constantes.COMMENTS_PRODUCT
import com.doubleclick.marktinhome.Model.User
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Repository.BaseRepository.myId
import com.doubleclick.marktinhome.Repository.BaseRepository.reference
import com.doubleclick.marktinhome.Views.CircleImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CommentsActivity : AppCompatActivity(), CommentAdapter.OnDeleteComment {

    lateinit var send: ImageView
    lateinit var userViewModel: UserViewModel
    lateinit var user: User
    private lateinit var commentsViewModel: CommentsViewModel
    lateinit var commentsRecycler: RecyclerView;
    lateinit var idproduct: String
    lateinit var imageUser: CircleImageView
    lateinit var textComment: EditText;
    private var commentProductData: ArrayList<CommentsProductData> = ArrayList();
    private lateinit var commentAdapter: CommentAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        commentsViewModel = ViewModelProvider(this)[CommentsViewModel::class.java]
        commentsRecycler = findViewById(R.id.commentsRecycler);
        send = findViewById(R.id.send);
        textComment = findViewById(R.id.textComment);
        imageUser = findViewById(R.id.imageUser);
        idproduct = intent.getStringExtra("idproduct")!!.toString()
        commentsViewModel.getCommentsById(idproduct)
        commentAdapter = CommentAdapter(commentProductData, this);
        commentsRecycler.adapter = commentAdapter
        commentsViewModel.commentsLiveDate.observe(this) {
            commentProductData.add(it)
            commentAdapter.notifyItemInserted(commentProductData.size - 1)
            commentAdapter.notifyDataSetChanged()
        }
        commentsViewModel.commentsLiveDateChanged.observe(this) {
            val pos = commentProductData.indexOf(it)
            commentProductData[pos] = it;
            commentAdapter.notifyItemChanged(pos)
        }
        commentsViewModel.commentsLiveDateDeleted.observe(this) {
            val pos = commentProductData.indexOf(it)
            commentProductData.removeAt(pos);
            commentAdapter.notifyItemRemoved(pos);
        }
        userViewModel.user.observe(this) {
            user = it
            Glide.with(this).load(user.image).into(imageUser)
        }

        send.setOnClickListener {
            SendRate(textComment.text.toString());
            textComment.setText("");
        }
    }

    private fun SendRate(comment: String) {
        val map: HashMap<String, Any> = HashMap();
        val id = "$myId:$idproduct"
        map["comment"] = comment
        map["id"] = id
        map["date"] = Date().time
        map["userId"] = user.id
        reference.child(COMMENTS_PRODUCT).child(idproduct).child(id).updateChildren(map)
            .addOnCanceledListener {
                textComment.setText("")
            }
    }

    override fun DeleteComment(commentsProductData: CommentsProductData?) {
        reference.child(COMMENTS_PRODUCT).child(idproduct).child(commentsProductData!!.comments.id)
            .removeValue()
            .addOnCompleteListener {
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            }
    }
}