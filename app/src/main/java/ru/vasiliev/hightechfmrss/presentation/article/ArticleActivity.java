package ru.vasiliev.hightechfmrss.presentation.article;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.domain.model.Enclosure;
import ru.vasiliev.hightechfmrss.utils.DateUtils;
import ru.vasiliev.hightechfmrss.viewstyle.recyclerviewindicator.RecyclerViewIndicator;
import timber.log.Timber;

public class ArticleActivity extends MvpAppCompatActivity implements ArticleView {

    protected static final String KEY_ARTICLE = "article";

    private static final int PRELOAD_AHEAD_ITEMS = BuildConfig.PRELOAD_ENCLOSURES;

    @BindView(R.id.article_title)
    TextView mArticleTitle;

    @BindView(R.id.author_and_time)
    TextView mAuthorAndTime;

    @BindView(R.id.article_description)
    TextView mArticleDescription;

    @BindView(R.id.article_body)
    TextView mArticleBody;

    @BindView(R.id.enclosure_recycler)
    RecyclerView mEnclosureRecycler;

    @BindView(R.id.enclosure_dots_indicator)
    RecyclerViewIndicator mEnclosureViewIndicator;

    Menu mMenu;

    private RecyclerView.LayoutManager mLayoutManager;
    private RequestManager mGlideRequestManager;
    private ViewPreloadSizeProvider<Enclosure> mPreloadSizeProvider;
    private EnclosureAdapter mEnclosureAdapter;

    @InjectPresenter
    ArticlePresenter mArticlePresenter;

    public static class Builder {

        private Intent mStartIntent = new Intent();

        public Builder setArticle(Article article) {
            if (article != null) {
                mStartIntent.putExtra(KEY_ARTICLE, article);
            }
            return this;
        }

        public void start(AppCompatActivity parentActivity) {
            Timber.d("Starting %s", getClass().getSimpleName());
            mStartIntent.setClass(parentActivity, ArticleActivity.class);
            parentActivity.startActivity(mStartIntent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mArticlePresenter.extractArguments(getIntent());

        initUi();

        mArticlePresenter.loadArticle();
    }

    @Override
    public void showArticle(Article article) {
        mArticleTitle.setText(article.title);
        mAuthorAndTime.setText(getString(R.string.author_and_time_format, article.author,
                DateUtils.toHumanReadable(article.pubDate)));
        mArticleDescription.setText(Html.fromHtml(article.description));
        mArticleBody.setText(Html.fromHtml(article.encoded));

        if (article.enclosure != null && article.enclosure.size() > 0) {
            mEnclosureAdapter.setData(article.enclosure);
            mEnclosureAdapter.notifyDataSetChanged();
            if (article.enclosure.size() > 1) {
                mEnclosureViewIndicator.setVisibility(View.VISIBLE); // it's invisible by default
            }
        }
    }

    @Override
    public void updateMenu(boolean isBookmarked) {
        if (mMenu == null) {
            return;
        }
        MenuItem bookmarkItem = mMenu.findItem(R.id.menu_bookmark);
        bookmarkItem.setIcon(isBookmarked ? R.drawable.ic_menu_bookmark_white
                : R.drawable.ic_menu_bookmark_border_white);
        bookmarkItem.setVisible(true);
    }

    private void initUi() {
        mArticleTitle.setLinksClickable(true);
        mArticleTitle.setMovementMethod(LinkMovementMethod.getInstance());
        mArticleDescription.setLinksClickable(true);
        mArticleDescription.setMovementMethod(LinkMovementMethod.getInstance());
        mArticleBody.setLinksClickable(true);
        mArticleBody.setMovementMethod(LinkMovementMethod.getInstance());

        initRssRecycler();
    }

    private void initRssRecycler() {
        mEnclosureRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mEnclosureRecycler.setLayoutManager(mLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mEnclosureRecycler);

        mGlideRequestManager = Glide.with(this);
        mEnclosureAdapter = new EnclosureAdapter(mGlideRequestManager);

        mPreloadSizeProvider = new ViewPreloadSizeProvider<>();

        RecyclerViewPreloader<Enclosure> preloader = new RecyclerViewPreloader<>(
                Glide.with(this),
                mEnclosureAdapter, mPreloadSizeProvider, PRELOAD_AHEAD_ITEMS);

        mEnclosureRecycler.setAdapter(mEnclosureAdapter);
        mEnclosureRecycler.addOnScrollListener(preloader);

        mEnclosureRecycler.setRecyclerListener(holder -> {
            EnclosureAdapter.ViewHolder vh = (EnclosureAdapter.ViewHolder) holder;
            mGlideRequestManager.clear(vh.enclosureImage);
        });

        mEnclosureViewIndicator.setRecyclerView(mEnclosureRecycler);
        mEnclosureViewIndicator.forceUpdateItemCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem bookmarkItem = menu.findItem(R.id.menu_bookmark);
        bookmarkItem.setVisible(false);
        mArticlePresenter.checkIsBookmarked();
        return super.onPrepareOptionsMenu(mMenu = menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_share:
                startActivity(Intent.createChooser(mArticlePresenter.createShareIntent(),
                        getString(R.string.article_action_share_tooltip)));
                return true;
            case R.id.menu_bookmark:
                if (mArticlePresenter.isBookmarked()) {
                    mArticlePresenter.removeFromBookmarks();
                } else {
                    mArticlePresenter.bookmarkArticle();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
