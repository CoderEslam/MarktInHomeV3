package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.CHATS;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListData;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 6/8/2022
 */
public class MyUserChatListAdapter extends RecyclerView.Adapter<MyUserChatListAdapter.MyUserViewHolder> {

    private UserInter onUser;
    private DatabaseReference reference;
    private List<ChatListData> chatListData = new ArrayList<>();

    public MyUserChatListAdapter(UserInter onUser, List<ChatListData> chatListData) {
        this.onUser = onUser;
        this.chatListData = chatListData;
    }

    @NonNull
    @Override
    public MyUserChatListAdapter.MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        reference = FirebaseDatabase.getInstance().getReference().child(CHATS);
        return new MyUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserChatListAdapter.MyUserViewHolder holder, int position) {
        if (chatListData.size() != 0) {
            holder.name.setText(chatListData.get(position).getUser().getName());
            Glide.with(holder.itemView.getContext()).load(chatListData.get(position).getUser().getImage()).into(holder.image);
            holder.itemView.setOnClickListener(v -> {
                if (chatListData.get(position) != null) {
                    onUser.OnUserLisitner(chatListData.get(position).getUser());
                }
            });
            if (chatListData.get(holder.getAdapterPosition()).getUser().getId() != null) {
                holder.Messageunread(chatListData.get(holder.getAdapterPosition()).getUser().getId());
            }
            holder.image.setOnClickListener(v -> {
                onUser.OnImageListnerLoad(chatListData.get(position).getUser(), holder.image);
            });
        }
    }

    @Override
    public int getItemCount() {
        return chatListData.size();
    }

    public class MyUserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private TextView name, countMessage;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            countMessage = itemView.findViewById(R.id.countMessage);
        }

        private void Messageunread(String id /* friend id*/) {
            try {
                if (!id.equals("")) {
                    reference.child(BaseFragment.myId).child(id).addValueEventListener(new ValueEventListener() {
                        @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                int i = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Chat chat = snapshot.getValue(Chat.class);
                                    assert chat != null;
                                    if (chat.getStatusMessage().equals("Uploaded") && !chat.getSender().equals(BaseFragment.myId) && !chat.isSeen()) {
                                        i++;
                                        countMessage.setText(String.format("%d", i));
                                        countMessage.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.bg_green));
                                    }
                                }
                            } catch (Exception e) {

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            } catch (NullPointerException e) {
                Log.e("NullPointerException", e.getMessage());
            }

        }
    }
}
