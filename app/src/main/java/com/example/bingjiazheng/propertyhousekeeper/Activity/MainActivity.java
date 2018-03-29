package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Fragment.TabLayoutFragment;
import com.example.bingjiazheng.propertyhousekeeper.Fragment.*;
import com.example.bingjiazheng.propertyhousekeeper.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    public static NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mRadioBadge;//the badge for radioGroup menu
    private TextView mRadioMsg;
    private TabLayoutFragment mTabLayoutFragment;
    private NightModeHelper mNightModeHelper;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor editor;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.left_to_right, R.anim.hold);
        user = getIntent().getStringExtra("user");
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mSettings.edit();
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        mNightModeHelper = new NightModeHelper(this, R.style.BaseTheme);
        setContentView(R.layout.layout_test);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("财产管家");
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //创建抽屉的开关，将mToolbar和mDrawerLayout作为参数传递给它
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_layout_open, R.string.drawer_layout_close);
        //实现toolbar和Drawer的联动
        mDrawerToggle.syncState();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        //给mDrawerLayout设置开关的监听
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

//        View headerView = mNavigationView.getHeaderView(0);
//        headerView.setOnClickListener(this);
        mNavigationView.setItemIconTintList(null);
        //设置mNavigationView的menu监听
        mNavigationView.setNavigationItemSelectedListener(this);
        //设置非统一菜单文字颜色
        mNavigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.bg_drawer_navigation));
        //设置非统一菜单图标颜色
        mNavigationView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.bg_drawer_navigation));
//        mRadioBadge = (LinearLayout) mNavigationView.getMenu().findItem(R.id.menu_radio_group).getActionView();
//        mRadioMsg = (TextView) mRadioBadge.findViewById(R.id.msg);
//        mRadioMsg.setText("8");
        //默认选择第一项
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mTabLayoutFragment == null) {
            mTabLayoutFragment = TabLayoutFragment.newInstance(getString(R.string.navigation_tab_layout),user);
        }
        transaction.replace(R.id.frame_content, mTabLayoutFragment);
        transaction.commit();
//        setNavigationViewChecked(3);
//        Snackbar.make(mDrawerLayout, "TabLayout + ViewPager", Snackbar.LENGTH_SHORT).show();

//        setNavigationViewChecked(3);

//        setCurrentFragment();
        setNavigationViewChecked(mSettings.getInt("selected_life", 0));
//        editor.putInt("selected_life_dely",mSettings.getInt("selected_life", 0));
//        editor.commit();

    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setNavigationViewChecked(int position) {
        mNavigationView.getMenu().getItem(position).setChecked(true);
        Log.i("Kevin", "the count of menu item is--->" + mNavigationView.getMenu().size() + "");
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            if (i != position) {
                mNavigationView.getMenu().getItem(i).setChecked(false);
            }
        }
    }

    /*private void setCurrentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mNavigationFragment = NavigationFragment.newInstance(getString(R.string.navigation_navigation_bar));
        transaction.replace(R.id.frame_content, mNavigationFragment).commit();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.unselected:
                HomeFragment.handler.sendEmptyMessage(0);
                setNavigationViewChecked(0);
                break;
            case R.id.student:
                HomeFragment.handler.sendEmptyMessage(1);
                setNavigationViewChecked(1);
                break;
            case R.id.work_unmarried:
                HomeFragment.handler.sendEmptyMessage(2);
                setNavigationViewChecked(2);
                break;
            case R.id.work_married:
                HomeFragment.handler.sendEmptyMessage(3);
                setNavigationViewChecked(3);
                break;
            case R.id.retire:
                HomeFragment.handler.sendEmptyMessage(4);
                setNavigationViewChecked(4);
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.settings:
                Snackbar.make(mDrawerLayout, "Settings", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                mNightModeHelper.toggle();
//                Configuration newConfig = new Configuration(getResources().getConfiguration());
//                newConfig.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
//                newConfig.uiMode |= uiNightMode;
//                getResources().updateConfiguration(newConfig, null);
//                recreate();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

}
