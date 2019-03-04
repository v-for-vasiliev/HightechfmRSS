package ru.vasiliev.hightechfmrss.presentation.bookmarks;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
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

    @BindView(R.id.rss_loader)
    RelativeLayout mBookmarksLoader;

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
        mArticleListAdapter = new ArticleListAdapter(mBookmarksRecyclerGlideRequestManager, true);
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
        mBookmarksPresenter.getRouter().openArticle(this, article);
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
            runLayoutAnimation(mBookmarksRecycler);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.bookmarks_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleScreenLoader(boolean isLoading) {
        mBookmarksLoader.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mBookmarksRecycler.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void runLayoutAnimation(RecyclerView recyclerView) {
        recyclerView.scheduleLayoutAnimation();
        recyclerView.invalidate();
    }
}
