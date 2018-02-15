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
    private List<Article> mArticleList;
    private RssItemSelectedListener mRssItemSelectedListener;

    public interface RssItemSelectedListener {
        void onRssItemSelected(Article article);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView articleTitle;
        ImageView articleCover;

        ViewHolder(View v) {
            super(v);
            articleTitle = v.findViewById(R.id.article_title);
            articleCover = v.findViewById(R.id.article_cover);
        }
    }

    RssAdapter(RequestManager glideRequestManager) {
        mGlideRequestManager = glideRequestManager;
    }

    public void setData(List<Article> articleList) {
        mArticleList = articleList;
    }

    public void setRssItemSelectedListener(RssItemSelectedListener rssItemSelectedListener) {
        mRssItemSelectedListener = rssItemSelectedListener;
    }

    @Override
    public RssAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view, set the view's size, margins, paddings and layout parameters
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Replace the contents of the view with that element
        holder.articleTitle.setText(mArticleList.get(position).title);
        holder.itemView.setOnClickListener(v -> {
            if (mRssItemSelectedListener != null) {
                mRssItemSelectedListener.onRssItemSelected(mArticleList.get(position));
            }
        });
        mGlideRequestManager.load(mArticleList.get(position).enclosure.get(0).url)
                .into(holder.articleCover);
    }

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
