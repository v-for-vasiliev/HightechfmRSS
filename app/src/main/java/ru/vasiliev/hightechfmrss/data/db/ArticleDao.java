package ru.vasiliev.hightechfmrss.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
