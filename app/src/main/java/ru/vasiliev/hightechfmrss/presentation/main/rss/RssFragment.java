package ru.vasiliev.hightechfmrss.presentation.main.rss;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.domain.model.ArticleCategory;
import ru.vasiliev.hightechfmrss.presentation.main.RssPagerMainActivity;
import ru.vasiliev.hightechfmrss.viewstyle.TopSnapLayoutManager;
import timber.log.Timber;

public class RssFragment extends MvpAppCompatFragment
        implements RssView, ArticleListAdapter.RssItemSelectedListener {

    private static final String PARAM_CATEGORY_ID = "PARAM_CATEGORY_ID";

    private static final int PRELOAD_AHEAD_ITEMS = BuildConfig.PRELOAD_ARTICLES;

    @BindView(R.id.rss_recycler)
    RecyclerView mRssRecycler;

    @BindView(R.id.rss_swipetorefresh)
    SwipeRefreshLayout mRssSwipe;

    @BindView(R.id.rss_loader)
    RelativeLayout mRssLoader;

    FloatingActionButton mFloatingButton;

    private TopSnapLayoutManager mRssRecyclerLayoutManager;

    private RequestManager mRssRecyclerGlideRequestManager;

    private ViewPreloadSizeProvider<Article> mPreloadSizeProvider;

    private ArticleListAdapter mArticleListAdapter;

    @InjectPresenter
    RssPresenter mRssPresenter;

    RecyclerView.OnScrollListener mFabOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mRssRecyclerLayoutManager.findFirstVisibleItemPosition() == 0) {
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
            mRssPresenter.setArticleCategory(ArticleCategory
                    .of(getArguments().getInt(PARAM_CATEGORY_ID, ArticleCategory.ALL.getId())));
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
        mRssSwipe.setOnRefreshListener(() -> mRssPresenter.loadFeed(false));

        mRssSwipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent));

        initRssRecycler();
    }

    private void initRssRecycler() {
        mRssRecycler.setHasFixedSize(true);
        mRssRecyclerLayoutManager = new TopSnapLayoutManager(getContext());
        mRssRecycler.setLayoutManager(mRssRecyclerLayoutManager);

        mRssRecyclerGlideRequestManager = Glide.with(this);
        mArticleListAdapter = new ArticleListAdapter(mRssRecyclerGlideRequestManager,
                mRssPresenter.getArticleCategory() == ArticleCategory.ALL);
        mArticleListAdapter.setRssItemSelectedListener(this);

        mPreloadSizeProvider = new ViewPreloadSizeProvider<>();

        RecyclerViewPreloader<Article> preloader = new RecyclerViewPreloader<>(Glide.with(this),
                mArticleListAdapter, mPreloadSizeProvider, PRELOAD_AHEAD_ITEMS);

        mRssRecycler.setAdapter(mArticleListAdapter);
        mRssRecycler.addOnScrollListener(preloader);

        mRssRecycler.setRecyclerListener(holder -> {
            ArticleListAdapter.ViewHolder vh = (ArticleListAdapter.ViewHolder) holder;
            mRssRecyclerGlideRequestManager.clear(vh.articleCover);
        });
    }

    @Override
    public void initGoUpButton() {
        mFloatingButton = getActivity().findViewById(R.id.fab);
        mFloatingButton.setOnClickListener(view -> mRssRecycler.smoothScrollToPosition(0));
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
            mArticleListAdapter.setData(articleList);
            mArticleListAdapter.notifyDataSetChanged();
            runLayoutAnimation(mRssRecycler);
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

    private void runLayoutAnimation(RecyclerView recyclerView) {
        recyclerView.scheduleLayoutAnimation();
        recyclerView.invalidate();
    }
}
