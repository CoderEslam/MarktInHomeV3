package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.context;
import static com.doubleclick.marktinhome.Model.Constantes.RECENTSEARCH;

import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.doubleclick.ViewModel.RecentSearchViewModel;
import com.doubleclick.marktinhome.Model.RecentSearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created By Eslam Ghazy on 3/11/2022
 */
public class Sending extends BaseRepository {

    private static RecentSearchViewModel recentSearchViewModel;
    private static String allSearch = "";

    public static void Check(String query, ViewModelStoreOwner viewModelStoreOwner, LifecycleOwner lifecycleOwner) {
        recentSearchViewModel = new ViewModelProvider(viewModelStoreOwner).get(RecentSearchViewModel.class);
        recentSearchViewModel.getRecentSearchLiveData().observe(lifecycleOwner, new Observer<RecentSearch>() {
            @Override
            public void onChanged(RecentSearch recentSearch) {
                allSearch = recentSearch.getRecentSearch();
                if (!allSearch.contains(query)) {
                    allSearch = allSearch + "," + query;
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("recentSearch", allSearch);
                    reference.child(RECENTSEARCH).child(myId).updateChildren(map);
                }
            }
        });
        // for first time working
        if (allSearch.equals("")) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("recentSearch", query);
            reference.child(RECENTSEARCH).child(myId).updateChildren(map);
        }
    }

}
