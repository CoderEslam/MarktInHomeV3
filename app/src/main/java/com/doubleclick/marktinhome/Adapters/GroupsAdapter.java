package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.BaseFragment.myId;
import static com.doubleclick.marktinhome.Model.Constantes.LIKES;
import static com.doubleclick.marktinhome.Views.reactbutton.FbReactions.Reactions;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.ANGRY;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.LIKE;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.LOVE;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.SAD;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.SMILE;
import static com.doubleclick.marktinhome.Views.reactbutton.ReactConstants.WOW;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.PostData;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.carouselrecyclerviewReflaction.CarouselRecyclerview;

import com.doubleclick.marktinhome.Views.reactbutton.Reaction;
import com.doubleclick.marktinhome.Views.reactbutton.ReactionAdapter;
import com.doubleclick.marktinhome.Views.socialtextview.SocialTextView;
import com.doubleclick.marktinhome.ui.MainScreen.Chat.ChatActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Groups.ViewActivity;
import com.doubleclick.marktinhome.ui.MainScreen.Groups.ViewPostActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 4/20/2022
 */
public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder> implements ReactionAdapter.GetReaction {

    private ArrayList<PostData> postsData;
    private DatabaseReference reference;
    private boolean LikeChecker = false;
    private Loadmore loadmore;
    private OptionPost optionPost;

    public GroupsAdapter(ArrayList<PostData> postsData) {
        this.postsData = postsData;
    }

    public GroupsAdapter(ArrayList<PostData> postsData, Loadmore loadmore) {
        this.postsData = postsData;
        this.loadmore = loadmore;
    }

    public GroupsAdapter(ArrayList<PostData> postsData, Loadmore loadmore, OptionPost optionPost) {
        this.postsData = postsData;
        this.loadmore = loadmore;
        this.optionPost = optionPost;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        reference = FirebaseDatabase.getInstance().getReference();
        return new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        /**
         * todo if publisher of post is equal myId
         * */
        if (postsData.get(position).getUser().getId().equals(myId)) {
            holder.option.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.option_group, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.deleteOption) {
                            optionPost.delete(postsData.get(holder.getAdapterPosition()).getPostsGroup().getId(), holder.getAdapterPosition());
                        }
                        if (id == R.id.editOption) {
                            optionPost.edit(postsData.get(holder.getAdapterPosition()).getPostsGroup().getId(), holder.getAdapterPosition());
                        }
                        return true;
                    }
                });
                popupMenu.show();
            });
        } else {
            holder.option.setVisibility(View.GONE);
        }

        if (postsData.get(holder.getAdapterPosition()).getPostsGroup().getType().equals("image")) {
            List<String> image = Arrays.asList(postsData.get(holder.getAdapterPosition()).getPostsGroup().getMeme().replace("[", "").replace("]", "").replace(" ", "").split(","));
            holder.images.setAdapter(new ImagesGroupAdapter(image));
            holder.video.setVisibility(View.GONE);
            holder.playVideo.setVisibility(View.GONE);
        } else if (postsData.get(holder.getAdapterPosition()).getPostsGroup().getType().equals("video")) {
            holder.video.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(postsData.get(holder.getAdapterPosition()).getPostsGroup().getMeme()).into(holder.video);
            holder.images.setVisibility(View.GONE);
            holder.playVideo.setVisibility(View.VISIBLE);
        } else {
            holder.images.setVisibility(View.GONE);
            holder.video.setVisibility(View.GONE);
        }
        holder.namePublisher.setText(postsData.get(holder.getAdapterPosition()).getUser().getName());
        Glide.with(holder.itemView.getContext()).load(postsData.get(holder.getAdapterPosition()).getUser().getImage()).into(holder.imagePublisher);
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeChecker = true;
                reference.child(LIKES).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //CHECKING IF THE POST IS LIKED OR NOT.....
                        if (LikeChecker == true) {
                            if (dataSnapshot.child(postsData.get(holder.getAdapterPosition()).getPostsGroup().getId()).hasChild(myId)) {
                                reference.child(LIKES).child(postsData.get(holder.getAdapterPosition()).getPostsGroup().getId()).child(myId).removeValue();
                                LikeChecker = false;
                            } else {
                                Map<String, Object> map = new HashMap<>();
                                map.put("" + myId, "Like");
                                reference.child(LIKES).child(postsData.get(holder.getAdapterPosition()).getPostsGroup().getId()).updateChildren(map);
                                LikeChecker = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        holder.likeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.reactions.setVisibility(View.VISIBLE);
                holder.reactions.setAdapter(new ReactionAdapter(Reactions(), GroupsAdapter.this, holder.getAdapterPosition(), holder.reactions));
                return true;
            }
        });
        holder.setLike(postsData.get(holder.getAdapterPosition()).getPostsGroup().getId());
        holder.caption.setText(postsData.get(holder.getAdapterPosition()).getPostsGroup().getText());
        holder.caption.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.copy, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.copy) {
                        ClipboardManager clipboardManager = (ClipboardManager) holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setText(holder.caption.getText());
                        Toast.makeText(holder.itemView.getContext(), holder.itemView.getResources().getString(R.string.text_copied), Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
            popupMenu.show();
        });
        holder.ConstraintLayoutimage_name.setOnClickListener(v -> {
            if (!postsData.get(position).getUser().getId().equals(myId)) {
                Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
                intent.putExtra("userId", postsData.get(holder.getAdapterPosition()).getUser().getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.video.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ViewActivity.class);
            intent.putExtra("url", postsData.get(holder.getAdapterPosition()).getPostsGroup().getMeme());
            intent.putExtra("type", "video");
            holder.itemView.getContext().startActivity(intent);
        });

        holder.share.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.share_option, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.Apps) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "https://www.market.doubleclick.com/" + postsData.get(holder.getAdapterPosition()).getPostsGroup().getGroupId() + "/" + postsData.get(holder.getAdapterPosition()).getPostsGroup().getId());
                        Intent shareIntent = Intent.createChooser(intent, null);
                        holder.itemView.getContext().startActivity(shareIntent);
                    }
                    if (id == R.id.Chat) {
                        Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
                        intent.putExtra("sharePost", "https://www.market.doubleclick.com/" + postsData.get(holder.getAdapterPosition()).getPostsGroup().getGroupId() + "/" + postsData.get(holder.getAdapterPosition()).getPostsGroup().getId());
                        holder.itemView.getContext().startActivity(intent);
                    }
                    return true;
                }
            });
            popupMenu.show();
        });

        if (0 == position) {
            holder.loadmore.setVisibility(View.VISIBLE);
            holder.loadmore.setOnClickListener(v -> {
                loadmore.loadmore(1000);
            });
        }

        holder.comment.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ViewPostActivity.class);
            intent.putExtra("post", postsData.get(holder.getAdapterPosition()));
            intent.putExtra("postId", postsData.get(holder.getAdapterPosition()).getPostsGroup().getId());
            intent.putExtra("groupId", postsData.get(holder.getAdapterPosition()).getPostsGroup().getGroupId());
            intent.putExtra("myId", myId);
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return postsData.size();
    }


    @Override
    public void getReact(Reaction reaction, RecyclerView recyclerView, int pos) {
        LikeChecker = true;
        reference.child(LIKES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CHECKING IF THE POST IS LIKED OR NOT.....
                if (LikeChecker == true) {
                    if (dataSnapshot.child(postsData.get(pos).getPostsGroup().getId()).hasChild(myId)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("" + myId, reaction.getReactText());
                        reference.child(LIKES).child(postsData.get(pos).getPostsGroup().getId()).updateChildren(map);
                        LikeChecker = false;
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("" + myId, reaction.getReactText());
                        reference.child(LIKES).child(postsData.get(pos).getPostsGroup().getId()).updateChildren(map);
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


    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private CarouselRecyclerview images;
        private ConstraintLayout ConstraintLayoutimage_name;
        private ImageView option, like_img, playVideo;
        private TextView namePublisher, like_text, loadmore;
        private LinearLayout likeButton, comment, share;
        private CircleImageView imagePublisher;
        private ImageView video;
        private SocialTextView caption;
        private RecyclerView reactions;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.images);
            images.getCarouselLayoutManager();
            images.set3DItem(true);
            images.setInfinite(true);
            images.setAlpha(true);
            ConstraintLayoutimage_name = itemView.findViewById(R.id.ConstraintLayoutimage_name);
            option = itemView.findViewById(R.id.option);
            likeButton = itemView.findViewById(R.id.likeButton);
            comment = itemView.findViewById(R.id.comment);
            namePublisher = itemView.findViewById(R.id.namePublisher);
            imagePublisher = itemView.findViewById(R.id.imagePublisher);
            share = itemView.findViewById(R.id.share);
            caption = itemView.findViewById(R.id.caption);
            like_img = itemView.findViewById(R.id.like_img);
            like_text = itemView.findViewById(R.id.like_text);
            loadmore = itemView.findViewById(R.id.loadmore);
            video = itemView.findViewById(R.id.video);
            playVideo = itemView.findViewById(R.id.playVideo);
            reactions = itemView.findViewById(R.id.reactions);
//            myCarouselLayoutManager layoutManager = new myCarouselLayoutManager(myCarouselLayoutManager.HORIZONTAL);
//            images.setLayoutManager(layoutManager);
//            images.setHasFixedSize(true);
//            images.addOnScrollListener(new CenterScrollListener());
//            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        }

        private void setLike(String PostKey) {

            reference.child(LIKES).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.child(PostKey).hasChild(myId)) {
                            Object react = dataSnapshot.child(PostKey).child(myId).getValue().toString();
                            if (react.toString().equals(LIKE)) {
                                like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
                                like_img.setImageResource(R.drawable.ic_like_circle);
                            } else if (react.toString().equals(LOVE)) {
                                like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
                                like_img.setImageResource(R.drawable.ic_heart);
                            } else if (react.toString().equals(WOW)) {
                                like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
                                like_img.setImageResource(R.drawable.ic_surprise);
                            } else if (react.toString().equals(ANGRY)) {
                                like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
                                like_img.setImageResource(R.drawable.ic_angry);
                            } else if (react.toString().equals(SMILE)) {
                                like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
                                like_img.setImageResource(R.drawable.ic_happy);
                            } else if (react.toString().equals(SAD)) {
                                like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
                                like_img.setImageResource(R.drawable.ic_sad);
                            } else {
                                like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
                                like_img.setImageResource(R.drawable.ic_like);
                            }
                        } else {
                            like_text.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(postsData.get(getAdapterPosition()).getPostsGroup().getId()).getChildrenCount())));
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

    }


    public interface Loadmore {
        void loadmore(int num);
    }

    public interface OptionPost {
        void delete(String id, int pos);

        void edit(String id, int pos);
    }

}
