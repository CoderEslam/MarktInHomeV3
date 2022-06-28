/*
 * MIT License
 *
 * Copyright (c) 2018 AmrDeveloper (Amr Hesham)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.doubleclick.marktinhome.Views.reactbutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom ArrayAdapter used to handle the reactions list inside the reactions dialog layout
 */
public class ReactionAdapter extends RecyclerView.Adapter<ReactionAdapter.ReactionViewHolder> {

    private ArrayList<Reaction> reactions = new ArrayList<>();
    private GetReaction getReaction;
    private int pos;
    private RecyclerView recyclerView;

    public ReactionAdapter(ArrayList<Reaction> reactions, GetReaction getReaction) {
        this.reactions = reactions;
        this.getReaction = getReaction;
    }

    public ReactionAdapter(ArrayList<Reaction> reactions, GetReaction getReaction, int pos) {
        this.reactions = reactions;
        this.getReaction = getReaction;
        this.pos = pos;
    }

    public ReactionAdapter(ArrayList<Reaction> reactions, GetReaction getReaction, int pos, RecyclerView recyclerView) {
        this.reactions = reactions;
        this.getReaction = getReaction;
        this.pos = pos;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public ReactionAdapter.ReactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.react_dialog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReactionAdapter.ReactionViewHolder holder, int position) {
        holder.reaction_image.setImageResource(reactions.get(position).getReactIconId());
        holder.reaction_image.setOnClickListener(view -> {
            getReaction.getReact(reactions.get(position), recyclerView, pos);
        });
    }

    @Override
    public int getItemCount() {
        return reactions.size();
    }

    public class ReactionViewHolder extends RecyclerView.ViewHolder {
        private ImageView reaction_image;

        public ReactionViewHolder(@NonNull View itemView) {
            super(itemView);
            reaction_image = itemView.findViewById(R.id.reaction_image);
        }
    }

    public interface GetReaction {
        void getReact(Reaction reaction, RecyclerView recyclerView, int pos);
    }
}
