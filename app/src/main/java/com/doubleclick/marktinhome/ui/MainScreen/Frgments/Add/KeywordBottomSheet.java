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
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.Adapters.KeywordAdapter;
import com.doubleclick.marktinhome.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;
import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/29/2022
 */
public class KeywordBottomSheet extends DialogFragment {


    private TextInputEditText keyword;
    private ImageView send;
    private Keywords keywords;

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
        send.setOnClickListener(v -> {
            if (!keyword.getText().toString().equals("")) {
                keywords.ItemsKeyword(keyword.getText().toString());
            }
        });
        return view;
    }

    public interface Keywords {
        void ItemsKeyword(String item);
    }
}
