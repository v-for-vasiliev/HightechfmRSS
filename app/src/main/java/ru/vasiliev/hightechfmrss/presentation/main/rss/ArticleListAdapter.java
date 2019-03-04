package ru.vasiliev.hightechfmrss.presentation.main.rss;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.utils.DateUtils;

/**
 * Created by vasiliev on 07/02/2018.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder>
        implements ListPreloader.PreloadModelProvider<Article> {

    private RequestManager mGlideRequestManager;

    private boolean mShowCategory;

    private List<Article> mArticleList;

    private RssItemSelectedListener mRssItemSelectedListener;

    public interface RssItemSelectedListener {

        void onRssItemSelected(Article article);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView articleCover;

        public TextView articleTitle;

        public TextView articleCategory;

        public TextView articlePubTime;

        ViewHolder(View v, boolean showCategory) {
            super(v);
            articleCover = v.findViewById(R.id.article_cover);
            articleTitle = v.findViewById(R.id.article_title);
            articleCategory = v.findViewById(R.id.article_category);
            articleCategory.setVisibility(showCategory ? View.VISIBLE : View.GONE);
            articlePubTime = v.findViewById(R.id.article_pub_time);
        }
    }

    public ArticleListAdapter(RequestManager glideRequestManager, boolean showCategory) {
        mGlideRequestManager = glideRequestManager;
        mShowCategory = showCategory;

    }

    public void setData(List<Article> articleList) {
        mArticleList = articleList;
        Collections.sort(mArticleList, Collections.reverseOrder());
    }

    public void setRssItemSelectedListener(RssItemSelectedListener rssItemSelectedListener) {
        mRssItemSelectedListener = rssItemSelectedListener;
    }

    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view, set the view's size, margins, paddings and layout parameters
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_cardview, parent, false);
        return new ViewHolder(v, mShowCategory);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Replace the contents of the view with that element
        holder.articleTitle.setText(mArticleList.get(position).title);
        holder.articleCategory.setText(mArticleList.get(position).category);
        String pubDate = mArticleList.get(position).pubDate;

        if (DateUtils.withinLatestHours(pubDate)) {
            holder.articlePubTime
                    .setBackgroundResource(R.drawable.fresh_article_pubdate_background);
            holder.articlePubTime
                    .setTextAppearance(holder.itemView.getContext(), R.style.FreshArticlePubDate);
            holder.articlePubTime.setPadding(15, 5, 15, 5);
        } else {
            holder.articlePubTime.setBackgroundColor(Color.TRANSPARENT);
            holder.articlePubTime
                    .setTextAppearance(holder.itemView.getContext(), R.style.RegularArticlePubDate);
            holder.articlePubTime.setPadding(0, 0, 0, 0);
        }

        holder.articlePubTime
                .setText(DateUtils.toHumanReadable(mArticleList.get(position).pubDate));

        holder.itemView.setOnClickListener(v -> {
            if (mRssItemSelectedListener != null) {
                mRssItemSelectedListener.onRssItemSelected(mArticleList.get(position));
            }
        });
        mGlideRequestManager.load(mArticleList.get(position).enclosure.get(0).url)
                .apply(new RequestOptions().placeholder(R.drawable.image_not_found_1210x600)
                        .error(R.drawable.image_not_found_1210x600)
                        .override(BuildConfig.ENCLOSURE_IMG_WIDTH, BuildConfig.ENCLOSURE_IMG_HEIGHT)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.articleCover);
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
        return mGlideRequestManager.load(hasEnclosure ? item.enclosure.get(0).url
                : R.drawable.image_not_found_1210x600)
                .apply(new RequestOptions().placeholder(R.drawable.image_not_found_1210x600)
                        .error(R.drawable.image_not_found_1210x600)
                        .override(BuildConfig.ENCLOSURE_IMG_WIDTH, BuildConfig.ENCLOSURE_IMG_HEIGHT)
                        .diskCacheStrategy(DiskCacheStrategy.ALL));
    }
}
