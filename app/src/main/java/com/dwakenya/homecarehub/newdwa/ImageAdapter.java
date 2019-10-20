package com.dwakenya.homecarehub.newdwa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VideoInfoHolder>{
    
        Context ctx;
        List<ImageModel> imageModels;
    
    public ImageAdapter(Context ctx, List<ImageModel> imageModels) {
        this.ctx = ctx;
        this.imageModels = imageModels;
    }
    
    @Override
        public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageraw, parent, false);
            return new VideoInfoHolder(itemView);
        }
    
    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        holder.VideoName.setText(imageModels.get(position).getVname());
    
        Glide.with(ctx).load(imageModels.get(position).getVimage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }
    
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.VideoImage);
        
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ImageViewer.class);
                intent.putExtra("ImageURL", imageModels.get(position).getVimage());
                ctx.startActivity(intent);
            }
        });
        
   
    }
    
    @Override
    public int getItemCount() {
        return imageModels.size();
    }
    
    public class VideoInfoHolder extends RecyclerView.ViewHolder {
        ImageView VideoImage;
        LinearLayout linearLayout;
        TextView VideoName;
        ProgressBar progressBar;
        
        public VideoInfoHolder(View itemView) {
            super(itemView);
            VideoImage = itemView.findViewById(R.id.VideoImage);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            VideoName = itemView.findViewById(R.id.VideoName);
            progressBar = itemView.findViewById(R.id.Imageprogrss);
        }
    
    }
}
