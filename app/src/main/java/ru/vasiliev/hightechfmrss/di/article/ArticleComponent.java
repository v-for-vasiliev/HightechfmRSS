package ru.vasiliev.hightechfmrss.di.article;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.di.scope.ArticleScope;
import ru.vasiliev.hightechfmrss.presentation.article.ArticlePresenter;

@ArticleScope
@Subcomponent
public interface ArticleComponent {

    void inject(ArticlePresenter presenter);
}