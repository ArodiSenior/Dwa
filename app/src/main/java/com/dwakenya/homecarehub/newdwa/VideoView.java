package com.dwakenya.homecarehub.newdwa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;

public class VideoView extends AppCompatActivity {
    android.widget.VideoView VideoViewID;
    MediaController mediaController;
    ProgressBar progrss;
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        isConnected();
    
        progrss = findViewById(R.id.progrss);
        progrss.setVisibility(View.VISIBLE);
    
        VideoViewID = findViewById(R.id.VideoViewID);
        mediaController = new MediaController(this);
        Intent intent = getIntent();
        String videoPath = intent.getStringExtra("VideoURL");
        Uri uri = Uri.parse(videoPath);
        VideoViewID.setVideoURI(uri);
        VideoViewID.start();
        VideoViewID.setMediaController(mediaController);
        mediaController.setAnchorView(VideoViewID);
        VideoViewID.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                progrss.setVisibility(View.VISIBLE);
                    VideoViewID.start();
            }
        });
        VideoViewID.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progrss.setVisibility(View.GONE);
            }
        });
        
        
    }
    
    public  boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            final AlertDialog.Builder builder = new AlertDialog.Builder(VideoView.this);
            builder.setMessage("No internet connection...");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();
                }
            });
            
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }
        return true;
    }
}
