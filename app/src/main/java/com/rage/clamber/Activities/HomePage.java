package com.rage.clamber.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.rage.clamber.Data.User;
import com.rage.clamber.Fragments.HomeActivity.Home.HomeFragment;
import com.rage.clamber.Fragments.HomeActivity.Projects.ProjectsFragment;
import com.rage.clamber.Fragments.HomeActivity.UserInfo.UserInfoFragment;
import com.rage.clamber.Fragments.HomeActivity.Walls.WallsFragment;
import com.rage.clamber.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * The HomePage is the first page of the app. It contains an action bar for navigation to each
 * of the activities.
 */
public class HomePage extends AppCompatActivity {

    public static final String ARG_USER = "main user";
    public User user;
    @Bind(R.id.home_page_toolbar)
    Toolbar toolbar;
    protected String[] actionBarTabs;
    @Bind(R.id.home_page_tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        user=getIntent().getParcelableExtra(LoginActivity.ARG_USER);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.clamber_title_text);
        toolbar.setTitleTextColor(Color.WHITE);

        //tabLayout setup to handle launching fragments based on the tabs that are selected.
        actionBarTabs = getResources().getStringArray(R.array.main_action_tabs);
        tabLayout.setTabTextColors(Color.WHITE, Color.BLACK);
        for (String actionBarTab : actionBarTabs) {
            tabLayout.addTab(tabLayout.newTab().setText(actionBarTab));
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabSelected(tab);
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame_layout, HomeFragment.newInstance(user));
        transaction.commit();

    }

    /**
     * Called when onTabSelected and onTabReselcted are called to handle which fragment to launch
     * when each tab is selected.
     * @param tab - the tab that was selected.
     */
    public void tabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0){
            clearBackstack();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_page_frame_layout, HomeFragment.newInstance(user));
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (tab.getPosition() == 1){
            clearBackstack();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_page_frame_layout, WallsFragment.newInstance(user));
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (tab.getPosition() == 2){
            clearBackstack();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_page_frame_layout, ProjectsFragment.newInstance(user));
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (tab.getPosition() == 3) {
            clearBackstack();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_page_frame_layout, UserInfoFragment.newInstance(user));
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /**
     * On back pressed method - created to exit application instead of hitting the login page. Checks
     * to see if there are no items in the backstack (indicitive of being back at the Home Activity).
     * If that is the case, it exits the application. It also sets the tab selected to the Home tab
     * if the backstack is at one.
     */
    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else if (getSupportFragmentManager().getBackStackEntryCount()==1){
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            getSupportFragmentManager().popBackStack();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * Method to clear the backstack. Used to ensure fragments are not building up. Called each time
     * a tab is changed.
     */
    private void clearBackstack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Adds the menu item that holds the exit icon to the toolbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home_menu, menu);
        return true;
    }

    /**
     * When the exit icon is selected, launch the login activity.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.activity_home_logout_icon){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
