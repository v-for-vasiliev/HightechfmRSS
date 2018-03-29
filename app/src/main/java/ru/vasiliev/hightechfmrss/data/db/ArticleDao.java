package ru.vasiliev.hightechfmrss.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
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

    @Query("SELECT * FROM articles WHERE link= :link LIMIT 1")
    Article findByLink(String link);

    @Insert
    void insertAll(Article... articles);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM articles")
    void deleteAll();
}
