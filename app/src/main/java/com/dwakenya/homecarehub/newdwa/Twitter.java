package com.dwakenya.homecarehub.newdwa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Twitter extends AppCompatActivity {
    Intent intent;
    WebView webView;
    ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        webView = findViewById(R.id.TwitterWeb);
    
        isConnected();
    
        progressBar = findViewById(R.id.ProgressTwitt);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        String facepath = "https://www.facebook.com/n/?Homecarehub";
    
        webView.loadUrl(facepath);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                
                } else if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                
                }
            }
        
        });
    }
    public  boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            final AlertDialog.Builder builder = new AlertDialog.Builder(Twitter.this);
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
