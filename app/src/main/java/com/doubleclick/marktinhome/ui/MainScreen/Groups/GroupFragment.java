package com.doubleclick.marktinhome.ui.MainScreen.Groups;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.doubleclick.ViewModel.GroupViewModel;
import com.doubleclick.marktinhome.Adapters.ItemGroupsAdapter;
import com.doubleclick.marktinhome.Model.Group;
import com.doubleclick.marktinhome.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    private FloatingActionButton addGroup;
    private EditText search;
    private GroupViewModel groupViewModel;
    private ShimmerRecyclerView allGroupsRecycler;

    public GroupFragment() {
        // Required empty public constructor
    }

    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        addGroup = view.findViewById(R.id.addGroup);
        search = view.findViewById(R.id.search);
        allGroupsRecycler = view.findViewById(R.id.allGroupsRecycler);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.AllGroups().observe(getViewLifecycleOwner(), groups -> {
            ItemGroupsAdapter itemGroupsAdapter = new ItemGroupsAdapter(groups);
            allGroupsRecycler.setAdapter(itemGroupsAdapter);
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addGroup.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CreateGroupActivity.class));
        });
        return view;
    }

}