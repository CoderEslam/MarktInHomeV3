package com.doubleclick.marktinhome.ui.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.doubleclick.ViewModel.RecentOrderViewModel;
import com.doubleclick.marktinhome.Adapters.RecentOrderAdapter;
import com.doubleclick.marktinhome.Model.RecentOrder;
import com.doubleclick.marktinhome.Model.RecentOrderData;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

public class RecentOrderActivity extends AppCompatActivity {


    private RecentOrderViewModel recentOrderViewModel;
    private RecyclerView myRecentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_order);
        recentOrderViewModel = new ViewModelProvider(this).get(RecentOrderViewModel.class);
        myRecentOrder = findViewById(R.id.myRecentOrder);
        recentOrderViewModel.getMyRecentOrderLiveData().observe(this, recentOrderData -> myRecentOrder.setAdapter(new RecentOrderAdapter(recentOrderData)));
    }
}