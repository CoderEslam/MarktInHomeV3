package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.CHATS;
import static com.doubleclick.marktinhome.Model.Constantes.LIKES;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/18/2022
 */
public class AllUserChatListAdapter extends RecyclerView.Adapter<AllUserChatListAdapter.AllUserViewHolder> {

    private List<User> userArrayList = new ArrayList<>();
    private UserInter onUser;

    public AllUserChatListAdapter(List<User> userArrayList, UserInter onUser) {
        this.userArrayList = userArrayList;
        this.onUser = onUser;
    }

    @NonNull
    @Override
    public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserViewHolder holder, int position) {
        if (userArrayList.size() != 0) {
            holder.name.setText(userArrayList.get(position).getName());
            Glide.with(holder.itemView.getContext()).load(userArrayList.get(position).getImage()).into(holder.image);
            holder.itemView.setOnClickListener(v -> {
                if (userArrayList.get(position) != null) {
                    onUser.OnUserLisitner(userArrayList.get(position));
                }
            });
            holder.delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class AllUserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private TextView name;
        private ImageView delete;

        public AllUserViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            delete = itemView.findViewById(R.id.delete);
        }
    }


}
