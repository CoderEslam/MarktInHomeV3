package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.CHATS;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private List<ChatListData> chatListData;

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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyUserChatListAdapter.MyUserViewHolder holder, int position) {
        if (chatListData.size() != 0) {
            holder.name.setText(chatListData.get(holder.getBindingAdapterPosition()).getUser().getName());
            Glide.with(holder.itemView.getContext()).load(chatListData.get(holder.getBindingAdapterPosition()).getUser().getImage()).into(holder.image);
            holder.itemView.setOnClickListener(v -> {
                if (chatListData.get(holder.getBindingAdapterPosition()) != null) {
                    onUser.OnUserLisitner(chatListData.get(holder.getBindingAdapterPosition()).getUser());
                }
            });
            if (chatListData.get(holder.getBindingAdapterPosition()).getUser().getId() != null) {
                holder.Messageunread(chatListData.get(holder.getBindingAdapterPosition()).getUser().getId());
            }
            holder.image.setOnClickListener(v -> {
                onUser.OnImageListnerLoad(chatListData.get(holder.getBindingAdapterPosition()).getUser(), holder.image);
            });
            holder.delete.setOnClickListener(view -> {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this chat")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onUser.ItemUserDeleted(chatListData.get(holder.getBindingAdapterPosition()).getUser(), chatListData.get(holder.getBindingAdapterPosition()).getChatList().get(0));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setIcon(holder.itemView.getContext().getResources().getDrawable(R.drawable.delete))
                        .show();
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
        private ImageView delete;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            countMessage = itemView.findViewById(R.id.countMessage);
            delete = itemView.findViewById(R.id.delete);
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
