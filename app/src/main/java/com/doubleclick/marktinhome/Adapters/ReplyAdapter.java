package com.doubleclick.marktinhome.Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.CommentsReply;
import com.doubleclick.marktinhome.Model.CommentsReplyData;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.socialtextview.SocialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 4/28/2022
 */
public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {


    private ArrayList<CommentsReplyData> commentsReplyData = new ArrayList<>();

    public ReplyAdapter(ArrayList<CommentsReplyData> commentsReplyData) {
        this.commentsReplyData = commentsReplyData;
    }


    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReplyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_reply_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        holder.commentReplay.setText(commentsReplyData.get(position).getCommentsReply().getReplyComment());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm aa");
        holder.time.setText(String.format("%s ", simpleDateFormat.format(commentsReplyData.get(holder.getAdapterPosition()).getCommentsReply().getTime())));
        Log.e("commentsReplyDataAd", commentsReplyData.toString());
        Glide.with(holder.itemView.getContext()).load(commentsReplyData.get(holder.getAdapterPosition()).getUser().getImage()).into(holder.imageUser);
        holder.userName.setText(commentsReplyData.get(holder.getAdapterPosition()).getUser().getName());
    }

    @Override
    public int getItemCount() {
        return commentsReplyData.size();
    }

    public class ReplyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageUser;
        private TextView userName, time;
        private SocialTextView commentReplay;

        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            commentReplay = itemView.findViewById(R.id.commentReplay);
            userName = itemView.findViewById(R.id.userName);
            imageUser = itemView.findViewById(R.id.imageUser);
            time = itemView.findViewById(R.id.time);

        }
    }
}
