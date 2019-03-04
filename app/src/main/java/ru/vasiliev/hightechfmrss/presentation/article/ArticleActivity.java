package ru.vasiliev.hightechfmrss.presentation.article;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.domain.model.Enclosure;
import ru.vasiliev.hightechfmrss.utils.DateUtils;
import ru.vasiliev.hightechfmrss.utils.glide.GlideImageGetter;
import ru.vasiliev.hightechfmrss.viewstyle.recyclerviewindicator.RecyclerViewIndicator;
import timber.log.Timber;

public class ArticleActivity extends MvpAppCompatActivity implements ArticleView, Html.ImageGetter {

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

    private RequestManager mGlideRequestManager;

    private ViewPreloadSizeProvider<Enclosure> mPreloadSizeProvider;

    private EnclosureAdapter mEnclosureAdapter;

    private GlideImageGetter mGlideImageGetter;

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
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mArticlePresenter.extractArguments(getIntent());

        initUi();

        mArticlePresenter.loadArticle();
    }

    private void initUi() {
        mArticleTitle.setLinksClickable(true);
        mArticleTitle.setMovementMethod(LinkMovementMethod.getInstance());
        mArticleDescription.setLinksClickable(true);
        mArticleDescription.setMovementMethod(LinkMovementMethod.getInstance());
        mArticleBody.setLinksClickable(true);
        mArticleBody.setMovementMethod(LinkMovementMethod.getInstance());
        mGlideImageGetter = new GlideImageGetter(this, mArticleBody);
        initRssRecycler();
    }

    private void initRssRecycler() {
        mEnclosureRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        mEnclosureRecycler.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mEnclosureRecycler);

        mGlideRequestManager = Glide.with(this);
        mEnclosureAdapter = new EnclosureAdapter(mGlideRequestManager);

        mPreloadSizeProvider = new ViewPreloadSizeProvider<>(mEnclosureRecycler);

        RecyclerViewPreloader<Enclosure> preloader = new RecyclerViewPreloader<>(Glide.with(this),
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
    public Drawable getDrawable(String source) {
        source = source.replaceAll("\\\\\"", "");

        final LevelListDrawable levelListDrawable = new LevelListDrawable();
        Drawable empty = ContextCompat.getDrawable(this, R.drawable.article_image_placeholder);
        levelListDrawable.addLevel(0, 0, empty);
        levelListDrawable.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth16to9 = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight16to9 = (dpWidth16to9 * 9) / 16.0f;

        final int w = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth16to9, displayMetrics);
        final int h = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight16to9, displayMetrics);

        Glide.with(this).asBitmap().load(source).into(new SimpleTarget<Bitmap>(w, h) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                try {
                    levelListDrawable.addLevel(1, 1, new BitmapDrawable(getResources(), resource));
                    levelListDrawable.setLevel(1);
                    levelListDrawable
                            .setBounds(-10, 0, resource.getWidth() + 20, resource.getHeight());
                    mArticleBody.setEllipsize(null);
                } catch (Exception e) {
                    Timber.e("", e);
                }
            }
        });

        return levelListDrawable;
    }

    private void setupBodyHtml(String html) {
        mArticleBody.setText(Html.fromHtml(html, this, null));
    }

    @Override
    public void showArticle(Article article) {
        mArticleTitle.setText(article.title);
        mAuthorAndTime.setText(getString(R.string.author_and_time_format,
                TextUtils.isEmpty(article.author) ? "От редакции" : article.author,
                DateUtils.toHumanReadable(article.pubDate)));
        if (TextUtils.isEmpty(article.description)) {
            mArticleDescription.setVisibility(View.GONE);
        } else {
            mArticleDescription.setText(Html.fromHtml(article.description));
        }
        setupBodyHtml(article.encoded);
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
                disableMenuItem(R.id.menu_bookmark);
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
