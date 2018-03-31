package ru.vasiliev.hightechfmrss.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by k.vasilev on 29/03/2018.
 */

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles")
    Maybe<List<Article>> getAll();

    @Query("SELECT * FROM articles WHERE link= :link LIMIT 1")
    Maybe<Article> findByLink(String link);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Article... articles);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM articles")
    void deleteAll();
}
