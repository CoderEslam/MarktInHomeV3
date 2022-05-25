package com.doubleclick.DashBoard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubleclick.marktinhome.R;

import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthFragment extends Fragment {

    private ColumnChartView chart_bottom_Column;
    private ColumnChartData columnData;
    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",};
    MonthFragmentListener monthFragmentListener;

    public MonthFragment() {
        // Required empty public constructor
    }

    public interface MonthFragmentListener {
        void onInputASent(CharSequence input);
    }

    public static MonthFragment newInstance(String param1, String param2) {
        MonthFragment fragment = new MonthFragment();
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
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        chart_bottom_Column = view.findViewById(R.id.chart_bottom_Column);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MonthFragmentListener) {
            monthFragmentListener = (MonthFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MonthFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        monthFragmentListener = null;
    }
}