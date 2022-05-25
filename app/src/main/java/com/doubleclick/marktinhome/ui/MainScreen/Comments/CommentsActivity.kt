package com.doubleclick.marktinhome.ui.MainScreen.Comments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.ViewModel.CommentsViewModel
import com.doubleclick.ViewModel.UserViewModel
import com.doubleclick.marktinhome.Adapters.CommentAdapter
import com.doubleclick.marktinhome.BaseApplication.context
import com.doubleclick.marktinhome.Model.Constantes
import com.doubleclick.marktinhome.Model.User
import com.doubleclick.marktinhome.R
import com.doubleclick.marktinhome.Repository.BaseRepository.myId
import com.doubleclick.marktinhome.Repository.BaseRepository.reference
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.HashMap

class CommentsActivity : AppCompatActivity() {

    lateinit var cartRate: CardView
    lateinit var imageUser: CircleImageView;
    lateinit var yourRateText: EditText;
    lateinit var send: ImageView
    lateinit var starRate: RatingBar
    lateinit var userViewModel: UserViewModel
    lateinit var user: User
    lateinit var putRate: FloatingActionButton
    private var open = false
    private var rating: Float = 0.0f
    private lateinit var commentsViewModel: CommentsViewModel
    lateinit var commentsRecycler: RecyclerView;
    lateinit var idproduct: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        commentsViewModel = ViewModelProvider(this)[CommentsViewModel::class.java]
        commentsRecycler = findViewById(R.id.commentsRecycler);
        cartRate = findViewById(R.id.cartRate);
        imageUser = findViewById(R.id.imageUser);
        putRate = findViewById(R.id.putRate);
        yourRateText = findViewById(R.id.yourRateText);
        send = findViewById(R.id.send);
        starRate = findViewById(R.id.starRate);
        idproduct = intent.getStringExtra("idproduct")!!.toString()
        commentsViewModel.getCommentsById(idproduct)
        commentsViewModel.commentsLiveDate.observe(this, Observer {
            var commentAdapter = CommentAdapter(it);
            commentsRecycler.adapter = commentAdapter
        })
        userViewModel.user.observe(this, Observer {
            user = it
            Glide.with(this).load(user.image).into(imageUser)
        })

        starRate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            this.rating = rating;
        }
        send.setOnClickListener {
            SendRate(yourRateText.text.toString(), rating);
            var animation = AnimationUtils.loadAnimation(context, R.anim.righttoleft)
            cartRate.animation = animation
            cartRate.visibility = View.GONE
            open = true
        }
        putRate.setOnClickListener {
            if (!open) {
                cartRate.visibility = View.VISIBLE
                var animation = AnimationUtils.loadAnimation(context, R.anim.lefttoright)
                cartRate.animation = animation
                open = true
            } else {
                var animation = AnimationUtils.loadAnimation(context, R.anim.righttoleft)
                cartRate.animation = animation
                cartRate.visibility = View.GONE
                open = false
            }
        }
    }

    private fun SendRate(comment: String, rating: Float) {
        var map: HashMap<String, Any> = HashMap();
        var id = myId + ":" + idproduct
        map["rateStar"] = rating
        map["comment"] = comment
        map["id"] = id
        map["image"] = user.image
        map["date"] = Date().time
        map["userName"] = user.name
        map["idProduct"] = idproduct
        reference.child(Constantes.COMMENTS).child(id).updateChildren(map)
    }
}