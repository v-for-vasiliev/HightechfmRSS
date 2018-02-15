package ru.vasiliev.hightechfmrss.presentation.article;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;

public class ArticleFragment extends MvpAppCompatFragment implements ArticleView {
    protected static final String ARG_ARTICLE = "article";

    @BindView(R.id.article_title)
    TextView mArticleTitle;

    @BindView(R.id.article_body)
    TextView mArticleBody;

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
        mArticlePresenter.showArticle();
    }

    @Override
    public void showArticle(Article article) {
        mArticleTitle.setText(article.description);
        mArticleBody.setText(article.content);
    }
}
