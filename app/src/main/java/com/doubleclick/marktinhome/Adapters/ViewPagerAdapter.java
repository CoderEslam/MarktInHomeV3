package com.doubleclick.marktinhome.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.doubleclick.marktinhome.MainLoginFragment;
import com.doubleclick.marktinhome.ui.Splash.Splash1Fragment;
import com.doubleclick.marktinhome.ui.Splash.Splash2Fragment;
import com.doubleclick.marktinhome.ui.Splash.Splash3Fragment;

/**
 * Created By Eslam Ghazy on 2/28/2022
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Splash1Fragment();
            case 1:
                return new Splash2Fragment();
            case 2:
                return  new Splash3Fragment();
            case 3:
                return  new MainLoginFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
