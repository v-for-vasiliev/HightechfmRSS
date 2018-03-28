package ru.vasiliev.hightechfmrss.presentation.article;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collections;
import java.util.List;

import ru.vasiliev.hightechfmrss.BuildConfig;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Enclosure;

/**
 * Created by vasiliev on 07/02/2018.
 */

public class EnclosureAdapter extends RecyclerView.Adapter<EnclosureAdapter.ViewHolder> implements
        ListPreloader.PreloadModelProvider<Enclosure> {
    private RequestManager mGlideRequestManager;
    private List<Enclosure> mEnclosureList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView enclosureImage;

        ViewHolder(View v) {
            super(v);
            enclosureImage = v.findViewById(R.id.enclosure_image);
        }
    }

    EnclosureAdapter(RequestManager glideRequestManager) {
        mGlideRequestManager = glideRequestManager;
    }

    public void setData(List<Enclosure> enclosureList) {
        mEnclosureList = enclosureList;
    }

    @Override
    public EnclosureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view, set the view's size, margins, paddings and layout parameters
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enclosure_cardview, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Replace the contents of the view with that element
        mGlideRequestManager.load(mEnclosureList.get(position).url)
                .apply(new RequestOptions().fitCenter().placeholder(
                        R.drawable.image_not_found_1210x600).error(
                        R.drawable.image_not_found_1210x600).override(
                        BuildConfig.ENCLOSURE_IMG_WIDTH,
                        BuildConfig.ENCLOSURE_IMG_HEIGHT).centerCrop())
                .into(holder.enclosureImage);
    }

    @Override
    public int getItemCount() {
        return mEnclosureList != null ? mEnclosureList.size() : 0;
    }

    @NonNull
    @Override
    public List<Enclosure> getPreloadItems(int position) {
        if (mEnclosureList != null && mEnclosureList.size() > position) {
            return mEnclosureList.subList(position, position + 1);
        } else {
            return Collections.emptyList();
        }
    }

    @Nullable
    @Override
    public RequestBuilder getPreloadRequestBuilder(Enclosure enclosure) {
        // Article may have no enclosures
        return mGlideRequestManager.load(enclosure.url).apply(
                new RequestOptions().fitCenter().placeholder(
                        R.drawable.image_not_found_1210x600).error(
                        R.drawable.image_not_found_1210x600).override(
                        BuildConfig.ENCLOSURE_IMG_WIDTH,
                        BuildConfig.ENCLOSURE_IMG_HEIGHT).centerCrop());
    }
}
