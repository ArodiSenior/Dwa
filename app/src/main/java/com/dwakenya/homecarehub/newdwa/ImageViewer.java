package com.dwakenya.homecarehub.newdwa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ImageViewer extends AppCompatActivity {
    ImageView imageView;
    ProgressBar progrss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        
    
        progrss = findViewById(R.id.progrsssss);

        imageView = findViewById(R.id.ViewPagerId);
        Intent intent = getIntent();
        String videoPath = intent.getStringExtra("ImageURL");
        Glide.with(ImageViewer.this).load(videoPath).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progrss.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);
    }
    
    
}
