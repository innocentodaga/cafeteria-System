package com.example.cafe;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ExploreFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        // Initialize ViewPager and TabLayout
        viewPager2 = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        // Initialize the ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(this);

        // Set the adapter to the ViewPager
        viewPager2.setAdapter(viewPagerAdapter);

        // Attach TabLayout to ViewPager
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Breakfast");
                    break;
                case 1:
                    tab.setText("Lunch");
                    break;
                case 2:
                    tab.setText("Supper");
                    break;
                case 3:
                    tab.setText("Drinks");
                    break;
                default:
                    tab.setText("");
            }
        }).attach();

        // Set the selected tab color and indicator color
        int selectedTabColor = ContextCompat.getColor(requireContext(), R.color.orange);
        tabLayout.setSelectedTabIndicatorColor(selectedTabColor);
        tabLayout.setTabTextColors(ContextCompat.getColor(requireContext(), R.color.black),
                selectedTabColor);

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Handle page selection
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        return view;
    }

}