package ru.vasiliev.hightechfmrss.presentation.main.rss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.domain.model.ArticleCategory;
import ru.vasiliev.hightechfmrss.presentation.main.RssPagerMainActivity;
import timber.log.Timber;

public class RssFragment extends MvpAppCompatFragment implements RssView,
        RssAdapter.RssItemSelectedListener {

    private static final String PARAM_CATEGORY_ID = "PARAM_CATEGORY_ID";

    private static final int PRELOAD_AHEAD_ITEMS = BuildConfig.PRELOAD_ARTICLES;

    @BindView(R.id.rss_recycler)
    RecyclerView mRssRecycler;

    @BindView(R.id.rss_swipetorefresh)
    SwipeRefreshLayout mRssSwipe;

    /*
    @BindView(R.id.rss_loader)
    ProgressBar mRssLoader;
    */

    @BindView(R.id.rss_loader)
    SpinKitView mRssLoader;

    FloatingActionButton mFloatingButton;

    private RecyclerView.LayoutManager mRssRecyclerLayoutManager;
    private RequestManager mRssRecyclerGlideRequestManager;
    private ViewPreloadSizeProvider<Article> mPreloadSizeProvider;
    private RssAdapter mRssAdapter;

    @InjectPresenter
    RssPresenter mRssPresenter;

    RecyclerView.OnScrollListener mFabOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (((LinearLayoutManager) mRssRecyclerLayoutManager).findFirstVisibleItemPosition()
                    == 0) {
                mFloatingButton.hide();
            } else {
                mFloatingButton.show();
            }
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    /*
    @ProvidePresenter
    RssPresenter providePresenter() {
        RssPresenter presenter = new RssPresenter();
        presenter.setArticleCategory(ArticleCategory.of(
                getArguments().getInt(PARAM_CATEGORY_ID, ArticleCategory.ALL.getId())));
        return presenter;
    }
    */

    public RssFragment() {
    }

    public static RssFragment newInstance(int articleCategoryId) {
        RssFragment fragment = new RssFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM_CATEGORY_ID, articleCategoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRssPresenter.setArticleCategory(
                    ArticleCategory.of(
                            getArguments().getInt(PARAM_CATEGORY_ID, ArticleCategory.ALL.getId())));
        }
        Timber.d("Fragment(%s) - onCreate()", mRssPresenter.getArticleCategory());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss, container, false);
        ButterKnife.bind(this, view);
        Timber.d("Fragment(%s) - onCreateView()", mRssPresenter.getArticleCategory());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUi();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mRssSwipe != null) {
            Timber.d("Fragment(%s) - VISIBLE", mRssPresenter.getArticleCategory());
            if (mRssPresenter.isFeedLoaded()) {
                // User triggers SwipeToReshresh in one fragment and switched to this fragment
                mRssSwipe.setRefreshing(mRssPresenter.isLoading());
            }
            initGoUpButton();
        } else {
            if (mRssRecycler != null) {
                mRssRecycler.removeOnScrollListener(mFabOnScrollListener);
            }
        }
    }

    private void initUi() {
        mRssSwipe.setOnRefreshListener(() -> mRssPresenter.getFeed(false));

        mRssSwipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent));

        initRssRecycler();
    }

    private void initRssRecycler() {
        mRssRecycler.setHasFixedSize(true);
        mRssRecyclerLayoutManager = new LinearLayoutManager(getContext());
        mRssRecycler.setLayoutManager(mRssRecyclerLayoutManager);

        mRssRecyclerGlideRequestManager = Glide.with(this);
        mRssAdapter = new RssAdapter(mRssRecyclerGlideRequestManager,
                mRssPresenter.getArticleCategory() == ArticleCategory.ALL);
        mRssAdapter.setRssItemSelectedListener(this);

        mPreloadSizeProvider = new ViewPreloadSizeProvider<>();

        RecyclerViewPreloader<Article> preloader = new RecyclerViewPreloader<>(Glide.with(this),
                mRssAdapter, mPreloadSizeProvider, PRELOAD_AHEAD_ITEMS);

        mRssRecycler.setAdapter(mRssAdapter);
        mRssRecycler.addOnScrollListener(preloader);

        mRssRecycler.setRecyclerListener(holder -> {
            RssAdapter.ViewHolder vh = (RssAdapter.ViewHolder) holder;
            mRssRecyclerGlideRequestManager.clear(vh.articleCover);
        });
    }

    @Override
    public void initGoUpButton() {
        mFloatingButton = getActivity().findViewById(R.id.fab);
        mFloatingButton.setOnClickListener(view -> mRssRecyclerLayoutManager.scrollToPosition(0));
        mFloatingButton.hide();
        mRssRecycler.addOnScrollListener(mFabOnScrollListener);
    }

    @Override
    public void showFeed(List<Article> articleList) {
        Timber.d("Fragment(%s) - showFeed()", mRssPresenter.getArticleCategory());
        ((RssPagerMainActivity) getActivity()).togglePager(true);
        mRssSwipe.setRefreshing(false);
        toggleScreenLoader(false);
        toggleTabs(true);
        if (articleList != null && articleList.size() > 0) {
            mRssAdapter.setData(articleList);
            mRssAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoader(boolean feedLoaded) {
        Timber.d("Fragment(%s) - showLoader(feedLoaded = %b)", mRssPresenter.getArticleCategory(),
                feedLoaded);
        if (feedLoaded) {
            mRssSwipe.setRefreshing(true);
        } else {
            mRssSwipe.setRefreshing(false);
            toggleScreenLoader(true);
        }
    }

    @Override
    public void showError(String error) {
        Timber.d("Fragment(%s) - showError(%s)", mRssPresenter.getArticleCategory(), error);
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRssItemSelected(Article article) {
        mRssPresenter.getRouter().openArticle((AppCompatActivity) getActivity(), article);
    }

    private void toggleScreenLoader(boolean isLoading) {
        mRssLoader.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mRssRecycler.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void toggleTabs(boolean show) {
        TabLayout tabs = getActivity().findViewById(R.id.rss_tabs);
        if (tabs != null) {
            tabs.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
