package ru.vasiliev.hightechfmrss.presentation.bookmarks;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.github.ybq.android.spinkit.SpinKitView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.main.rss.ArticleListAdapter;
import ru.vasiliev.hightechfmrss.viewstyle.TopSnapLayoutManager;
import timber.log.Timber;

public class BookmarksActivity extends MvpAppCompatActivity
        implements BookmarksView, ArticleListAdapter.RssItemSelectedListener {

    private static final int PRELOAD_AHEAD_ITEMS = BuildConfig.PRELOAD_ARTICLES;

    @BindView(R.id.bookmarks_recycler)
    RecyclerView mBookmarksRecycler;

    @BindView(R.id.bookmarks_loader)
    SpinKitView mBookmarksLoader;

    Menu mMenu;

    private TopSnapLayoutManager mBookmarksRecyclerLayoutManager;

    private RequestManager mBookmarksRecyclerGlideRequestManager;

    private ViewPreloadSizeProvider<Article> mPreloadSizeProvider;

    private ArticleListAdapter mArticleListAdapter;

    @InjectPresenter
    BookmarksPresenter mBookmarksPresenter;

    public static void start(AppCompatActivity parentActivity) {
        Timber.d("Starting %s", BookmarksActivity.class.getSimpleName());
        Intent startIntent = new Intent(parentActivity, BookmarksActivity.class);
        parentActivity.startActivity(startIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initUi();

        mBookmarksPresenter.loadAllBookmarks();
    }

    private void initUi() {
        initRssRecycler();
    }

    private void initRssRecycler() {
        mBookmarksRecycler.setHasFixedSize(true);
        mBookmarksRecyclerLayoutManager = new TopSnapLayoutManager(this);
        mBookmarksRecycler.setLayoutManager(mBookmarksRecyclerLayoutManager);

        mBookmarksRecyclerGlideRequestManager = Glide.with(this);
        mArticleListAdapter = new ArticleListAdapter(mBookmarksRecyclerGlideRequestManager, false);
        mArticleListAdapter.setRssItemSelectedListener(this);

        mPreloadSizeProvider = new ViewPreloadSizeProvider<>();

        RecyclerViewPreloader<Article> preloader = new RecyclerViewPreloader<>(Glide.with(this),
                mArticleListAdapter, mPreloadSizeProvider, PRELOAD_AHEAD_ITEMS);

        mBookmarksRecycler.setAdapter(mArticleListAdapter);
        mBookmarksRecycler.addOnScrollListener(preloader);

        mBookmarksRecycler.setRecyclerListener(holder -> {
            ArticleListAdapter.ViewHolder vh = (ArticleListAdapter.ViewHolder) holder;
            mBookmarksRecyclerGlideRequestManager.clear(vh.articleCover);
        });
    }


    @Override
    public void onRssItemSelected(Article article) {
        mBookmarksPresenter.getRouter().openArticle((AppCompatActivity) this, article);
    }

    @Override
    public void showLoader() {
        toggleScreenLoader(true);
    }

    @Override
    public void showBookmarks(List<Article> articleList) {
        toggleScreenLoader(false);
        if (articleList != null && articleList.size() > 0) {
            mArticleListAdapter.setData(articleList);
            mArticleListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateMenu(boolean bookmarksFound) {
        if (mMenu == null) {
            return;
        }
        MenuItem bookmarkItem = mMenu.findItem(R.id.menu_bookmark);
        bookmarkItem.setIcon(bookmarksFound ? R.drawable.ic_menu_bookmark_white
                : R.drawable.ic_menu_bookmark_border_white);
        bookmarkItem.setVisible(true);
    }

    @Override
    public void enableMenuItem(int id) {
        MenuItem item = mMenu.findItem(R.id.menu_bookmark);
        if (item != null) {
            item.setEnabled(true);
        }
    }

    @Override
    public void disableMenuItem(int id) {
        MenuItem item = mMenu.findItem(R.id.menu_bookmark);
        if (item != null) {
            item.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(mMenu = menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_share:
                return true;
            case R.id.menu_bookmark:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleScreenLoader(boolean isLoading) {
        mBookmarksLoader.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mBookmarksRecycler.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}
