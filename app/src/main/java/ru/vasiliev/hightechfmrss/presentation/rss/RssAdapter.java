package ru.vasiliev.hightechfmrss.presentation.rss;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import java.util.Collections;
import java.util.List;

import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 07/02/2018.
 */

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.ViewHolder> implements
        ListPreloader.PreloadModelProvider<Article> {
    private RequestManager mGlideRequestManager;
    private ListPreloader.PreloadSizeProvider<Article> mPreloadSizeProvider;
    private List<Article> mArticleList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView articleTitle;
        public ImageView articleCover;

        public ViewHolder(View v) {
            super(v);
            articleTitle = v.findViewById(R.id.article_title);
            articleCover = v.findViewById(R.id.article_cover);
        }
    }

    public RssAdapter(RequestManager glideRequestManager) {
        mGlideRequestManager = glideRequestManager;
    }

    public void setData(List<Article> articleList) {
        mArticleList = articleList;
    }

    @Override
    public RssAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.articleTitle.setText(mArticleList.get(position).title);
        mGlideRequestManager.load(mArticleList.get(position).enclosure.get(0).url)
                .into(holder.articleCover);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArticleList != null ? mArticleList.size() : 0;
    }

    @NonNull
    @Override
    public List<Article> getPreloadItems(int position) {
        if (mArticleList != null && mArticleList.size() > position) {
            return mArticleList.subList(position, position + 1);
        } else {
            return Collections.emptyList();
        }
    }

    @Nullable
    @Override
    public RequestBuilder getPreloadRequestBuilder(Article item) {
        // Article may have no enclosures
        boolean hasEnclosure = item.enclosure != null && item.enclosure.size() > 0;
        return mGlideRequestManager.load(
                hasEnclosure ? item.enclosure.get(0).url : R.drawable.article_cover_placeholder);
    }
}
