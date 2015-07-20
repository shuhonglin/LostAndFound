package com.moonlight.nasa.lostandfound;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by NaSa on 2015/7/12.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentTabHost fragmentTabHost = null;
    private View indicator = null;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        setUpViews();
    }

    private void setUpViews() {

        toolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("失物招领");

        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        indicator = getIndicatorView("首页",R.layout.tabhost_indicator);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("firstPage").setIndicator(indicator), FirstFragment.class, null);

        indicator = getIndicatorView("第二页",R.layout.tabhost_indicator);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("secondPage").setIndicator(indicator), SecondFragment.class, null);
        indicator = getIndicatorView("第三页",R.layout.tabhost_indicator);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("thirdPage").setIndicator(indicator), ThirdFragment.class, null);
        //设置tabs之间的分隔线不显示
        //fragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        fragmentTabHost.setCurrentTabByTag("secondPage");

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                System.out.println("tabId: "+tabId);
            }
        });


        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.kya);
        resideMenu.setShadowVisible(false);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);

        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "Profile");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Calendar");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");


        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        //禁用右边菜单
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
    }


    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(getApplicationContext(), "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(getApplicationContext(), "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    private View getIndicatorView(String name, int layoutId) {
        View v = getLayoutInflater().inflate(layoutId,null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(name);
        return v;
    }

    public ResideMenu getResideMenu(){
        return resideMenu;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentTabHost = null;
    }

    @Override
    public void onClick(View view) {
        if (view == itemHome){
            fragmentTabHost.setCurrentTab(0);
        }else if (view == itemProfile){
            fragmentTabHost.setCurrentTab(1);
            //changeFragment(new SecondFragment());
            //changeFragment(getSupportFragmentManager().findFragmentByTag("secondPage"));
        }else if (view == itemCalendar){
            fragmentTabHost.setCurrentTab(2);
            //changeFragment(new ThirdFragment());
            //changeFragment(getSupportFragmentManager().findFragmentByTag("thirdPage"));
        }else if (view == itemSettings){
            fragmentTabHost.setCurrentTab(0);
            //changeFragment(new FirstFragment());
        }

        resideMenu.closeMenu();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if (resideMenu.isOpened()) resideMenu.closeMenu();
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.realtabcontent, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
