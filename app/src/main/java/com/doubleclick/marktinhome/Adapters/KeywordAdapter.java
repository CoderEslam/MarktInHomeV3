package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/29/2022
 */
public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.KeyViewHolder> {

    private ArrayList<String> texts = new ArrayList<>();
    private OnDelete onDelete;

    public KeywordAdapter(ArrayList<String> texts, OnDelete onDelete) {
        this.texts = texts;
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public KeyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new KeyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KeyViewHolder holder, int position) {

        holder.text.setText(texts.get(position));
        holder.delete.setOnClickListener(v -> {
            onDelete.onItemDelete(position);
        });

    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    public class KeyViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView delete;

        public KeyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            delete = itemView.findViewById(R.id.delete);
        }

    }

    public interface OnDelete {
        void onItemDelete(int pos);
    }
}
