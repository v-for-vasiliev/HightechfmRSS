package ru.vasiliev.hightechfmrss.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by k.vasilev on 29/03/2018.
 */

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles")
    List<Article> getAll();
}
