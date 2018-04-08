package ru.vasiliev.hightechfmrss.presentation.main;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.presentation.bookmarks.BookmarksActivity;
import ru.vasiliev.hightechfmrss.viewstyle.RssViewPager;

public class MainActivity extends MvpAppCompatActivity
        implements MainView, RssPagerMainActivity, NavigationView.OnNavigationItemSelectedListener {

    public static final int OFFSCREEN_PAGE_LIMIT = BuildConfig.PRELOAD_PAGER_FRAGMENTS;

    @BindView(R.id.rss_pager)
    RssViewPager mRssViewPager;

    @BindView(R.id.rss_tabs)
    TabLayout mRssTabs;

    @InjectPresenter
    MainPresenter mMainPresenter;

    RssPagerAdapter mRssPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this);

        initUi();
    }

    @Override
    @SuppressWarnings("all")
    public void togglePager(boolean enabled) {
        mRssViewPager.togglePaging(enabled);
    }

    private void initUi() {
        mRssPagerAdapter = new RssPagerAdapter(getSupportFragmentManager());
        mRssViewPager.setAdapter(mRssPagerAdapter);
        mRssViewPager.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
        mRssTabs.setupWithViewPager(mRssViewPager);
        togglePager(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.article_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_bookmarks:
                BookmarksActivity.start(this);
                break;
            case R.id.nav_about:
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
