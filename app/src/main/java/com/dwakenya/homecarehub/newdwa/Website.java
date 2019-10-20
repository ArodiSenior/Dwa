package com.dwakenya.homecarehub.newdwa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Website extends AppCompatActivity {
    Intent intent;
    WebView webView;
    ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        webView = findViewById(R.id.Website);
    
        progressBar = findViewById(R.id.ProgressWeb);
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        String facepath = "http://www.cdtd.org/";
    
        webView.loadUrl(facepath);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }
            
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            
                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(Website.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });
            
                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
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
    
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            
            view.loadUrl(url);
            return true;
            
        }
    }
    
}
