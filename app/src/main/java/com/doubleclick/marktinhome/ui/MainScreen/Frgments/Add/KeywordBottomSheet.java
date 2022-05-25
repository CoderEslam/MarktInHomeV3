package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.Adapters.KeywordAdapter;
import com.doubleclick.marktinhome.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/29/2022
 */
public class KeywordBottomSheet extends BottomSheetDialogFragment implements KeywordAdapter.OnDelete {


    private EditText keyword;
    private ImageView send;
    ArrayList<String> texts = new ArrayList<>();
    private Keywords keywords;
    private KeywordAdapter keywordAdapter;

    public KeywordBottomSheet(Keywords keywords) {
        this.keywords = keywords;

    }

    public KeywordBottomSheet() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_keyword, container, false);
        keyword = view.findViewById(R.id.keyword);
        send = view.findViewById(R.id.send);
        TextView ok = view.findViewById(R.id.ok);
        RecyclerView keys = view.findViewById(R.id.keys);
        keywordAdapter = new KeywordAdapter(texts, this);
        keys.setAdapter(keywordAdapter);
        send.setOnClickListener(v -> {
            if (!keyword.getText().toString().equals("")) {
                texts.add(keyword.getText().toString());
                keywordAdapter.notifyItemInserted(texts.size() - 1);
                keyword.setText("");
            }
        });
        ok.setOnClickListener(v -> {
            keywords.ItemsKeyword(texts);
            keys.setVisibility(View.GONE);
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemDelete(int pos) {
        try {
            texts.remove(pos);
            keywordAdapter.notifyItemRemoved(pos);
            keywordAdapter.notifyDataSetChanged();
        } catch (IndexOutOfBoundsException ignored) {

        }
    }


    public interface Keywords {
        void ItemsKeyword(ArrayList<String> items);
    }
}
