package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.RECENTORDER;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.marktinhome.Model.RecentOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;

/**
 * Created By Eslam Ghazy on 3/23/2022
 */
public class RecentOrdersForSellerRepository extends BaseRepository {


    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MM yyyy HH:mm:ss aaa");
    //    String date = simpleDateFormat.format(recentOrder.getDate());
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

    private ArrayList<RecentOrder> recentOrderArrayList = new ArrayList<>();
    private recentOrder recentOrder;

    public RecentOrdersForSellerRepository(recentOrder recentOrder) {
        this.recentOrder = recentOrder;
    }

    public void getRecentOrders() {
        reference.child(RECENTORDER).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                RecentOrder recentOrder = snapshot.getValue(RecentOrder.class);
                                if (Objects.requireNonNull(recentOrder).getSellerId().equals(myId)) {
                                    recentOrderArrayList.add(recentOrder);
                                }
                            }
                            recentOrder.recentOrder(recentOrderArrayList);
                            getDate(dataSnapshot);
                            getMyMoney(dataSnapshot);
                        }
                    } else {
                        ShowToast("No internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("RecentOrderException", e.getMessage());
                }

            }
        });
    }

    public interface recentOrder {
        void recentOrder(ArrayList<RecentOrder> recentOrderArrayList);

        void ListOfYear(ArrayList<ArrayList<ArrayList<Integer>>> years);

        void recentMoney(double money);
    }

    public void getDate(DataSnapshot dataSnapshot) {
        ArrayList<ArrayList<ArrayList<Integer>>> years = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            ArrayList<ArrayList<Integer>> monthList = new ArrayList<>();
            for (int day = 1; day <= 31; day++) {
                ArrayList<Integer> dayList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecentOrder recentOrder = snapshot.getValue(RecentOrder.class);
                    Log.e("recentOrder = ", recentOrder.toString());
//                    Log.e("TheDay", simpleDateFormat.format(recentOrder.getDate()).substring(5, 7));
//                    Log.e("currentYear", "" + currentYear);
//                    Log.e("test", ", day = "  + day + simpleDateFormat.format(recentOrder.getDate()).substring(5, 7).equals(String.valueOf(day)));
                    if (simpleDateFormat.format(Objects.requireNonNull(recentOrder).getDate()).contains(String.valueOf(currentYear)) && recentOrder.getSellerId().equals(myId) && Integer.parseInt(simpleDateFormat.format(recentOrder.getDate()).substring(5, 7)) == day && month == Integer.parseInt(simpleDateFormat.format(recentOrder.getDate()).substring(8, 10))) {
                        dayList.add(1);
                    }
                }
                monthList.add(dayList);
            }
            years.add(monthList);
        }
        recentOrder.ListOfYear(years);
    }


    public void getMyMoney(DataSnapshot snapshot) {
        double money = 0.0;
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            RecentOrder recentOrder = dataSnapshot.getValue(RecentOrder.class);
            if (simpleDateFormat.format(Objects.requireNonNull(recentOrder).getDate()).contains(String.valueOf(currentYear)) && recentOrder.getSellerId().equals(myId)) {
                money += recentOrder.getTotalPrice();
            }
        }
        recentOrder.recentMoney(money);
    }

}
