package ru.vasiliev.hightechfmrss.presentation.article;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.domain.model.Enclosure;
import timber.log.Timber;

public class ArticleActivity extends MvpAppCompatActivity implements ArticleView {

    protected static final String KEY_ARTICLE = "article";

    private static final int PRELOAD_AHEAD_ITEMS = 5;

    @BindView(R.id.article_title)
    TextView mArticleTitle;

    @BindView(R.id.article_body)
    TextView mArticleBody;

    @BindView(R.id.enclosure_recycler)
    RecyclerView mEnclosureRecycler;

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

        mArticlePresenter.showArticle();
    }

    @Override
    public void showArticle(Article article) {
        mArticleTitle.setText(Html.fromHtml(article.description));
        mArticleBody.setText(Html.fromHtml(article.encoded));

        if (article.enclosure != null && article.enclosure.size() > 0) {
            mEnclosureAdapter.setData(article.enclosure);
            mEnclosureAdapter.notifyDataSetChanged();
        }
    }

    private void initUi() {
        mArticleTitle.setLinksClickable(true);
        mArticleTitle.setMovementMethod(LinkMovementMethod.getInstance());
        mArticleBody.setLinksClickable(true);
        mArticleBody.setMovementMethod(LinkMovementMethod.getInstance());

        initRecycler();
    }

    private void initRecycler() {
        mEnclosureRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mEnclosureRecycler.setLayoutManager(mLayoutManager);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
