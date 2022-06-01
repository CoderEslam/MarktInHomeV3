package com.doubleclick.marktinhome.ui.MainScreen.Frgments.Add;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
public class KeywordBottomSheet extends BottomSheetDialogFragment {


    private TextInputEditText keyword;
    private Keywords keywords;

    public KeywordBottomSheet(Keywords keywords) {
        this.keywords = keywords;
    }

    public KeywordBottomSheet() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_keywords, container, false);
        keyword = view.findViewById(R.id.keyword);
        keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (!keyword.getText().toString().equals("")) {
                        keywords.ItemsKeyword(keyword.getText().toString());
                        keyword.setText("");
                    }
                }
                return false;
            }
        });
        return view;
    }

    public interface Keywords {
        void ItemsKeyword(String item);
    }
}
