package com.doubleclick.marktinhome.ui.MainScreen.Groups;

import static com.doubleclick.marktinhome.BaseFragment.myId;
import static com.doubleclick.marktinhome.Model.Constantes.COMMENTS_GROUP;
import static com.doubleclick.marktinhome.Model.Constantes.GROUPS;
import static com.doubleclick.marktinhome.Model.Constantes.LIKES;
import static com.doubleclick.marktinhome.Model.Constantes.POSTS;
import static com.doubleclick.marktinhome.Views.reactbutton.FbReactions.Reactions;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.ANGRY;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.LIKE;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.LOVE;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.SAD;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.SMILE;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.WOW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doubleclick.ViewModel.CommentsGroupViewModel;
import com.doubleclick.marktinhome.Adapters.CommentGroupAdapter;
import com.doubleclick.marktinhome.Adapters.GroupsAdapter;
import com.doubleclick.marktinhome.Adapters.ImagesGroupAdapter;
import com.doubleclick.marktinhome.Model.CommentsGroupData;
import com.doubleclick.marktinhome.Model.PostData;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.carouselrecyclerviewReflaction.CarouselRecyclerview;
import com.doubleclick.marktinhome.Views.reactbutton.Reaction;
import com.doubleclick.marktinhome.Views.reactbutton.ReactionAdapter;
import com.doubleclick.marktinhome.Views.socialtextview.SocialTextView;
import com.doubleclick.marktinhome.ui.MainScreen.Chat.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewPostActivity extends AppCompatActivity implements ReactionAdapter.GetReaction {

    private PostData postData;
    private String postId, groupId, myId;
    private CarouselRecyclerview images;
    private ConstraintLayout ConstraintLayoutimage_name;
    private ImageView option, like_img, playVideo;
    private TextView namePublisher, like_text;
    private LinearLayout likeButton, comment, share;
    private CircleImageView imagePublisher;
    private ImageView video;
    private SocialTextView caption;
    private DatabaseReference reference;
    private ShimmerRecyclerView Comments;
    private EditText textComment;
    private ImageView send;
    private CommentsGroupViewModel commentsGroupViewModel;
    private ArrayList<CommentsGroupData> commentsGroupDataArrayList = new ArrayList<>();
    private CommentGroupAdapter commentGroupAdapter;
    private boolean LikeChecker = true;
    private RecyclerView reactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        reference = FirebaseDatabase.getInstance().getReference();
        postData = (PostData) getIntent().getSerializableExtra("post");
        postId = getIntent().getStringExtra("postId"/* post id*/);
        groupId = getIntent().getStringExtra("groupId" /* group id*/);
        myId = getIntent().getStringExtra("myId");
        ConstraintLayoutimage_name = findViewById(R.id.ConstraintLayoutimage_name);
        option = findViewById(R.id.option);
        Comments = findViewById(R.id.Comments);
        send = findViewById(R.id.send);
        reactions = findViewById(R.id.reactions);
        images = findViewById(R.id.images);
        images.getCarouselLayoutManager();
        images.set3DItem(true);
        images.setInfinite(true);
        images.setAlpha(true);
        textComment = findViewById(R.id.textComment);
        likeButton = findViewById(R.id.likeButton);
        comment = findViewById(R.id.comment);
        namePublisher = findViewById(R.id.namePublisher);
        imagePublisher = findViewById(R.id.imagePublisher);
        share = findViewById(R.id.share);
        caption = findViewById(R.id.caption);
        like_img = findViewById(R.id.like_img);
        like_text = findViewById(R.id.like_text);
        video = findViewById(R.id.video);
        playVideo = findViewById(R.id.playVideo);
        namePublisher.setText(postData.getUser().getName());
        Glide.with(this).load(postData.getUser().getImage()).into(imagePublisher);
        caption.setText(postData.getPostsGroup().getText());
        images.setAdapter(new ImagesGroupAdapter(Arrays.asList(postData.getPostsGroup().getMeme().replace("[", "").replace("]", "").replace(" ", "").split(","))));
        commentsGroupViewModel = new ViewModelProvider(this).get(CommentsGroupViewModel.class);
        postId = getIntent().getStringExtra("postId"/* post id*/);
        groupId = getIntent().getStringExtra("groupId" /* group id*/);
        commentsGroupViewModel.GetComments(groupId, postId);
        commentGroupAdapter = new CommentGroupAdapter(commentsGroupDataArrayList);
        Comments.setAdapter(commentGroupAdapter);
        commentsGroupViewModel.getCommentsLiveData().observe(this, new Observer<CommentsGroupData>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(CommentsGroupData commentsGroupData) {
                if (commentsGroupData != null) {
                    commentsGroupDataArrayList.add(commentsGroupData);
                    commentGroupAdapter.notifyItemInserted(commentsGroupDataArrayList.size() - 1);
                    commentGroupAdapter.notifyDataSetChanged();
                    Comments.hideShimmer();
                }
            }
        });

        caption.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(ViewPostActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.copy, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.copy) {
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setText(caption.getText());
                        Toast.makeText(ViewPostActivity.this, getResources().getString(R.string.text_copied), Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
            popupMenu.show();
        });
        send.setOnClickListener(v -> {
            if (!textComment.getText().toString().equals("")) {
                HashMap<String, Object> map = new HashMap<>();
                long time = new Date().getTime();
                String pushid = reference.push().getKey() + ":" + time;
                map.put("id", pushid);
                map.put("time", time);
                map.put("comment", textComment.getText().toString());
                map.put("userId", myId);
                map.put("groupId", groupId);
                reference.child(COMMENTS_GROUP).child(groupId /*group id*/).child(postId/* post id*/).child(pushid/*comment id*/).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            textComment.setText("");
                        }
                    }
                });
            } else {
                Snackbar.make(v, "Empty comment", Snackbar.LENGTH_LONG).show();
            }

        });
        setLike(postId);

        likeButton.setOnClickListener(v -> {
            LikeChecker = true;
            reference.child(LIKES).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //CHECKING IF THE POST IS LIKED OR NOT.....
                    if (LikeChecker == true) {
                        if (dataSnapshot.child(postId).hasChild(myId)) {
                            reference.child(LIKES).child(postId).child(myId).removeValue();
                            LikeChecker = false;
                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put("" + myId, "Like");
                            reference.child(LIKES).child(postId).updateChildren(map);
                            LikeChecker = false;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        });
        likeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                reactions.setVisibility(View.VISIBLE);
                reactions.setAdapter(new ReactionAdapter(Reactions(), ViewPostActivity.this, 0, reactions));
                return true;
            }
        });
        share.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.share_option, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.Apps) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "https://www.market.doublethink.com/" + groupId + "/" + postId);
                        Intent shareIntent = Intent.createChooser(intent, null);
                        startActivity(shareIntent);
                    }
                    if (id == R.id.Chat) {
                        Intent intent = new Intent(ViewPostActivity.this, ChatActivity.class);
                        intent.putExtra("sharePost", "https://www.market.doublethink.com/" + groupId + "/" + postId);
                        startActivity(intent);
                    }
                    return false;
                }
            });
            popupMenu.show();
        });

        option.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.option_group, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.deleteOption) {
                        reference.child(GROUPS).child(groupId/* group id */).child(POSTS).child(postId).removeValue();
                    }
                    if (id == R.id.editOption) {
                        Intent intent = new Intent(ViewPostActivity.this, EditPostActivity.class);
                        intent.putExtra("idGroup", groupId);
                        intent.putExtra("idPost", postId);
                        startActivity(intent);
                    }
                    return true;
                }
            });
            popupMenu.show();
        });
    }

    private void setLike(String PostKey) {
        reference.child(LIKES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.child(PostKey).hasChild(myId)) {
                        Object react = dataSnapshot.child(PostKey).child(myId).getValue().toString();
                        if (react.toString().equals(LIKE)) {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                            like_img.setImageResource(R.drawable.ic_like_circle);
                        } else if (react.toString().equals(LOVE)) {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                            like_img.setImageResource(R.drawable.ic_heart);
                        } else if (react.toString().equals(WOW)) {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                            like_img.setImageResource(R.drawable.ic_surprise);
                        } else if (react.toString().equals(ANGRY)) {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                            like_img.setImageResource(R.drawable.ic_angry);
                        } else if (react.toString().equals(SMILE)) {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                            like_img.setImageResource(R.drawable.ic_happy);
                        } else if (react.toString().equals(SAD)) {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                            like_img.setImageResource(R.drawable.ic_sad);
                        } else {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                            like_img.setImageResource(R.drawable.ic_like);
                        }
                    } else {
                        like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postId).getChildrenCount())));
                        like_img.setImageResource(R.drawable.ic_like);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getReact(Reaction reaction, RecyclerView recyclerView, int pos) {
        LikeChecker = true;
        reference.child(LIKES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CHECKING IF THE POST IS LIKED OR NOT.....
                if (LikeChecker == true) {
                    if (dataSnapshot.child(postId).hasChild(myId)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("" + myId, reaction.getReactText());
                        reference.child(LIKES).child(postId).updateChildren(map);
                        LikeChecker = false;
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("" + myId, reaction.getReactText());
                        reference.child(LIKES).child(postId).updateChildren(map);
                        LikeChecker = false;
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}