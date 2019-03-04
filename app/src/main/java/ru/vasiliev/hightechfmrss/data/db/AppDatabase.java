package ru.vasiliev.hightechfmrss.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.vasiliev.hightechfmrss.domain.model.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}