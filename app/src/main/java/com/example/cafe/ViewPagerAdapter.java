package com.example.cafe;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull ExploreFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new BreakFastFragment();
            case 1: return new LunchFragment();
            case 2: return new SupperFragment();
            case 3: return new DrinksFragment();
            default: return new BreakFastFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
