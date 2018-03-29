package ru.vasiliev.hightechfmrss.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.vasiliev.hightechfmrss.domain.model.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}