package ru.vasiliev.hightechfmrss.presentation.rss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

public class RssFragment extends MvpAppCompatFragment implements RssView {

    private static final int PRELOAD_AHEAD_ITEMS = 5;

    @BindView(R.id.rss_recycler)
    RecyclerView mRssRecycler;

    private RecyclerView.LayoutManager mLayoutManager;
    private RequestManager mGlideRequestManager;
    private ViewPreloadSizeProvider<Article> mPreloadSizeProvider;
    private RssAdapter mRssAdapter;

    @InjectPresenter
    RssPresenter mRssPresenter;

    public RssFragment() {
    }

    public static RssFragment newInstance() {
        return new RssFragment();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mRssPresenter.getFeed();
    }

    private void initRecycler() {
        mRssRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRssRecycler.setLayoutManager(mLayoutManager);

        mGlideRequestManager = Glide.with(this);
        mRssAdapter = new RssAdapter(mGlideRequestManager);

        mPreloadSizeProvider = new ViewPreloadSizeProvider<>();

        RecyclerViewPreloader<Article> preloader = new RecyclerViewPreloader<>(Glide.with(this),
                mRssAdapter, mPreloadSizeProvider, PRELOAD_AHEAD_ITEMS);

        mRssRecycler.setAdapter(mRssAdapter);
        mRssRecycler.addOnScrollListener(preloader);

        mRssRecycler.setRecyclerListener(holder -> {
            RssAdapter.ViewHolder vh = (RssAdapter.ViewHolder) holder;
            mGlideRequestManager.clear(vh.articleCover);
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

    }
}
