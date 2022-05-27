package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.CHATS;
import static com.doubleclick.marktinhome.Model.Constantes.LIKES;

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
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/18/2022
 */
public class AllUserChatListAdapter extends RecyclerView.Adapter<AllUserChatListAdapter.AllUserViewHolder> {

    private ArrayList<User> userArrayList;
    private UserInter onUser;
    private DatabaseReference reference;
    private String myId;

    public AllUserChatListAdapter(ArrayList<User> userArrayList, UserInter onUser) {
        this.userArrayList = userArrayList;
        this.onUser = onUser;
    }

    @NonNull
    @Override
    public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        reference = FirebaseDatabase.getInstance().getReference().child(CHATS);
        myId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().toString();
        return new AllUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserViewHolder holder, int position) {
        if (userArrayList.size() != 0) {
            holder.name.setText(userArrayList.get(position).getName());
            Glide.with(holder.itemView.getContext()).load(userArrayList.get(position).getImage()).into(holder.image);
            holder.itemView.setOnClickListener(v -> {
                if (userArrayList.get(position)!=null){
                    onUser.OnUserLisitner(userArrayList.get(position));
                }
            });
        }
        if (userArrayList.get(holder.getAdapterPosition()).getId() != null) {
            holder.Messageunread(userArrayList.get(holder.getAdapterPosition()).getId());
        }
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class AllUserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private TextView name, countMessage;


        public AllUserViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            countMessage = itemView.findViewById(R.id.countMessage);
        }

        private void Messageunread(String id) {
            try {
                if (!id.equals("")) {
                    reference.child(myId).child(id).addValueEventListener(new ValueEventListener() {
                        @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                int i = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Chat chat = snapshot.getValue(Chat.class);
                                    assert chat != null;
                                    if (chat.getStatusMessage().equals("Uploaded") && !chat.getSender().equals(myId) && !chat.isSeen()) {
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
