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


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoInfoHolder>{
    
        Context ctx;
        List<VideoModel> videoModel;
    
    public VideoAdapter(Context ctx, List<VideoModel> videoModel) {
        this.ctx = ctx;
        this.videoModel = videoModel;
    }
    
    @Override
        public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.videoraw, parent, false);
            return new VideoInfoHolder(itemView);
        }
    
    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        holder.VideoName.setText(videoModel.get(position).getVname());
    
        Glide.with(ctx).load(videoModel.get(position).getVimage()).listener(new RequestListener<String, GlideDrawable>() {
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
                Intent intent = new Intent(ctx, VideoView.class);
                intent.putExtra("VideoURL", videoModel.get(position).getVideo());
                ctx.startActivity(intent);
            }
        });
        
   
    }
    
    @Override
    public int getItemCount() {
        return videoModel.size();
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
            progressBar = itemView.findViewById(R.id.Videoprogrss);
        }
        
    }
}
