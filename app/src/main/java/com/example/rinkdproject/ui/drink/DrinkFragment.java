package com.example.rinkdproject.ui.drink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toolbar;

import com.example.rinkdproject.R;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;


public class DrinkFragment extends Fragment {

    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drink, container, false);

        mViewPager  =(ViewPager)view.findViewById(R.id.viewPager);

        //tablayout을 설정해준다
        // ICE와 HOT 두가지 탭을 생성해주었다.
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ICE"));
        tabLayout.addTab(tabLayout.newTab().setText("HOT"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Viewpager객체를 만들어서 xml파일의 viewpager 레이아웃을 가져온다.
        //Adapter 객체를 만들어서 viewpager 와 연결해준다.
        final ViewPager viewpager =(ViewPager)view.findViewById(R.id.viewPager);
        final PagerAdapter adapter = new PagerAdapter
                (getChildFragmentManager(),tabLayout.getTabCount());
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public  void onTabUnselected(TabLayout.Tab tab){

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        return view;
    }




    //내부클래스로 Adapter클래스를 생성해준다.
    private class PagerAdapter extends FragmentPagerAdapter{
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs){
            super(fm);
            this.mNumOfTabs=NumOfTabs;
        }
        //getItem메서드를 생성하여 해당 프래그먼트를 각각의 tab화면에 표시하도록 해준다.
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FirstFragment tab1 = new FirstFragment();
                    return tab1;
                case 1:
                    SecondFragment tab2 = new SecondFragment();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}