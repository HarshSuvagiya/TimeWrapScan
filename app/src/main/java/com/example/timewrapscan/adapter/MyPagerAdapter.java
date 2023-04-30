package com.example.timewrapscan.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.timewrapscan.Fragment.ImageFragment;
import com.example.timewrapscan.Fragment.VideoFragment;


public class MyPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 2;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new VideoFragment();
        }
        return new ImageFragment();
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
