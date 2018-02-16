package ru.vasiliev.hightechfmrss.presentation.article;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import ru.vasiliev.hightechfmrss.domain.model.Enclosure;

public class ArticleFragment extends MvpAppCompatFragment implements ArticleView {

    protected static final String ARG_ARTICLE = "article";

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

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance(Article article) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ARTICLE, article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticlePresenter.extractArguments(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
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
}
