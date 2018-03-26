package com.example.bingjiazheng.propertyhousekeeper.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bingjiazheng.propertyhousekeeper.Adapter.TabLayoutFragmentAdapter;
import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2016/11/30.
 * Blog:http://blog.csdn.net/student9128
 * Description: Bottom Navigation Bar by TabLayout.
 */

public class TabLayoutFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTextView;
    private List<String> mTabList = new ArrayList<>();
    private TabLayoutFragmentAdapter mAdapter;
    private int[] mTabImgs = new int[]{R.drawable.home, R.drawable.location, R.drawable.like, R.drawable.person};
    private List<Fragment> mFragments = new ArrayList<>();

    public static TabLayoutFragment newInstance(String s) {
        TabLayoutFragment tabLayoutFragment = new TabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", s);
        tabLayoutFragment.setArguments(bundle);
        return tabLayoutFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        initTabList();
        mAdapter = new TabLayoutFragmentAdapter(getChildFragmentManager(), mTabList, getActivity(), mFragments, mTabImgs);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(mAdapter.getTabView(i));
        }
        mTabLayout.addOnTabSelectedListener(this);
//        mViewPager.setCurrentItem(0);
//        mTabLayout.getTabAt(0).setIcon(R.drawable.home);
//        mTabLayout.getTabAt(1).setIcon(R.drawable.location);
//        mTabLayout.getTabAt(2).setIcon(R.drawable.like);
//        mTabLayout.getTabAt(3).setIcon(R.drawable.person);
//        setDefaultFragment();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initFragmentList();
    }

    private void setDefaultFragment() {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.sub_content, HomeFragment.newInstance(getString(R.string.item_home)))
                .commit();
    }

    /**
     * add Fragment
     */
    public void initFragmentList() {
        mFragments.clear();
        mFragments.add(HomeFragment.newInstance(getString(R.string.item_home)));
        mFragments.add(LocationFragment.newInstance(getString(R.string.item_location)));
        mFragments.add(LikeFragment.newInstance(getString(R.string.item_like)));
        mFragments.add(PersonFragment.newInstance(getString(R.string.item_person)));

    }

    /**
     * init the tab list.
     */
    private void initTabList() {
        mTabList.clear();
        mTabList.add("Home");
        mTabList.add("Location");
        mTabList.add("Like");
        mTabList.add("Me");
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabSelectedState(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabUnSelectedState(tab);
    }


    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setTabSelectedState(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        TextView tabText = (TextView) customView.findViewById(R.id.tv_tab_text);
        ImageView tabIcon = (ImageView) customView.findViewById(R.id.iv_tab_icon);
        tabText.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        String s = tabText.getText().toString();
        if ("Home".equals(s)) {
            tabIcon.setImageResource(R.drawable.home_fill);
        } else if ("Location".equals(s)) {
            tabIcon.setImageResource(R.drawable.location_fill);
        } else if ("Like".equals(s)) {
            tabIcon.setImageResource(R.drawable.like_fill);
        } else if ("Me".equals(s)) {
            tabIcon.setImageResource(R.drawable.person_fill);
        }
    }

    private void setTabUnSelectedState(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        TextView tabText = (TextView) customView.findViewById(R.id.tv_tab_text);
        ImageView tabIcon = (ImageView) customView.findViewById(R.id.iv_tab_icon);
        tabText.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        String s = tabText.getText().toString();
        if ("Home".equals(s)) {
            tabIcon.setImageResource(R.drawable.home);
        } else if ("Location".equals(s)) {
            tabIcon.setImageResource(R.drawable.location);
        } else if ("Like".equals(s)) {
            tabIcon.setImageResource(R.drawable.like);
        } else if ("Me".equals(s)) {
            tabIcon.setImageResource(R.drawable.person);
        }
    }
}