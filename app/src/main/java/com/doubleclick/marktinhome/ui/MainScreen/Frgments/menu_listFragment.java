package com.doubleclick.marktinhome.ui.MainScreen.Frgments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.doubleclick.ViewModel.ProductViewModel;
import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.R;



public class menu_listFragment extends BaseFragment {


    public menu_listFragment() {
        // Required empty public constructor
    }

    private ProductViewModel productViewModel;


    public static menu_listFragment newInstance(String param1, String param2) {
        menu_listFragment fragment = new menu_listFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        RecyclerView rr = view.findViewById(R.id.rr);



        return view;
    }
}