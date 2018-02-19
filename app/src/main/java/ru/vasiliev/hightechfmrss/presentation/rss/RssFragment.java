package ru.vasiliev.hightechfmrss.presentation.rss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.domain.model.ArticleCategory;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;
import ru.vasiliev.hightechfmrss.presentation.article.ArticleFragment;
import ru.vasiliev.hightechfmrss.presentation.home.HomeActivity;
import ru.vasiliev.hightechfmrss.utils.FragmentUtils;

public class RssFragment extends MvpAppCompatFragment implements RssView,
        RssAdapter.RssItemSelectedListener {

    private static final int PRELOAD_AHEAD_ITEMS = 5;
    private static final int VERTICAL_ITEM_SPACE = 48;

    @BindView(R.id.rss_recycler)
    RecyclerView mRssRecycler;

    @BindView(R.id.rss_category_tabs)
    TabLayout mRssCategoryTabs;

    private RecyclerView.LayoutManager mLayoutManager;
    private RequestManager mGlideRequestManager;
    private ViewPreloadSizeProvider<Article> mPreloadSizeProvider;
    private RssAdapter mRssAdapter;

    @InjectPresenter
    RssPresenter mRssPresenter;

    private int mCategoryId = ArticleCategory.ALL.getId();

    public RssFragment() {
    }

    public static RssFragment newInstance() {
        return new RssFragment();
    }

    public RssFragment setCategory(ArticleCategory category) {
        mCategoryId = category.getId();
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecycler();

        initTabs();

        mRssPresenter.getFeed();
    }

    private void initRecycler() {
        mRssRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRssRecycler.setLayoutManager(mLayoutManager);

        mGlideRequestManager = Glide.with(this);
        mRssAdapter = new RssAdapter(mGlideRequestManager);
        mRssAdapter.setRssItemSelectedListener(this);

        mPreloadSizeProvider = new ViewPreloadSizeProvider<>();

        RecyclerViewPreloader<Article> preloader = new RecyclerViewPreloader<>(Glide.with(this),
                mRssAdapter, mPreloadSizeProvider, PRELOAD_AHEAD_ITEMS);

        mRssRecycler.setAdapter(mRssAdapter);
        mRssRecycler.addOnScrollListener(preloader);

        mRssRecycler.setRecyclerListener(holder -> {
            RssAdapter.ViewHolder vh = (RssAdapter.ViewHolder) holder;
            mGlideRequestManager.clear(vh.articleCover);
        });

        // This returns lost enertia to recycler
        // mRssRecycler.setNestedScrollingEnabled(false);

        /*
        mRssRecycler.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        mRssRecycler.addItemDecoration(
                new DividerItemDecoration(R.drawable.rss_recycler_divider, getContext(), 1));
        */
    }

    private void initTabs() {
        mRssCategoryTabs.addTab(mRssCategoryTabs.newTab().setText(ArticleCategory.ALL.getTitle()));
        mRssCategoryTabs.addTab(
                mRssCategoryTabs.newTab().setText(ArticleCategory.CASES.getTitle()));
        mRssCategoryTabs.addTab(
                mRssCategoryTabs.newTab().setText(ArticleCategory.IDEAS.getTitle()));
        mRssCategoryTabs.addTab(
                mRssCategoryTabs.newTab().setText(ArticleCategory.BLOCKCHAIN.getTitle()));
        mRssCategoryTabs.addTab(
                mRssCategoryTabs.newTab().setText(ArticleCategory.OPINIONS.getTitle()));
        mRssCategoryTabs.addTab(
                mRssCategoryTabs.newTab().setText(ArticleCategory.TRENDS.getTitle()));

        mRssCategoryTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                } else if (tab.getPosition() == 1) {
                } else {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void showFeed(RssFeed feed) {
        if (feed != null) {
            mRssAdapter.setData(feed.articleList);
            mRssAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoader() {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRssItemSelected(Article article) {
        FragmentUtils.replaceWithHistory((HomeActivity) getActivity(),
                ArticleFragment.newInstance(article),
                R.id.fragment_container);
    }
}
