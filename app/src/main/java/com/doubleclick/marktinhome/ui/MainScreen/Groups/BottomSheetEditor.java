package com.doubleclick.marktinhome.ui.MainScreen.Groups;

import static com.doubleclick.marktinhome.Model.Constantes.GROUPS;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.marktinhome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 4/23/2022
 */
public class BottomSheetEditor extends BottomSheetDialogFragment {

    private EditText et_user_input_bottom_sheet_fragment;
    private TextView btn_save_bottom_sheet, btn_cancel_bottom_sheet;
    private String id;
    private DatabaseReference reference;
    private String type;


    public BottomSheetEditor(String id, String type, DatabaseReference databaseReference) {
        this.id = id;
        this.type = type;
        this.reference = databaseReference;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_editor, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        et_user_input_bottom_sheet_fragment = view.findViewById(R.id.et_user_input_bottom_sheet_fragment);
        btn_save_bottom_sheet = view.findViewById(R.id.btn_save_bottom_sheet);
        btn_cancel_bottom_sheet = view.findViewById(R.id.btn_cancel_bottom_sheet);
        btn_save_bottom_sheet.setOnClickListener(v -> {
            updateUsernameAndBio();
        });

        btn_cancel_bottom_sheet.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }

    private void updateUsernameAndBio() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(type, et_user_input_bottom_sheet_fragment.getText().toString().trim());
        reference.child(GROUPS).child(id/* group id*/).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
                et_user_input_bottom_sheet_fragment.setText("");
            }
        });

    }
}
