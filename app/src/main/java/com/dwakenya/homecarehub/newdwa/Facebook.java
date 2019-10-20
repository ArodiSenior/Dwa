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

public class Facebook extends AppCompatActivity {
    Intent intent;
    WebView FaceWeb;
    ProgressBar ProgressFace;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        FaceWeb = findViewById(R.id.FaceWeb);
    
    
        ProgressFace = findViewById(R.id.ProgressFace);
        FaceWeb.setWebViewClient(new myWebClient());
        FaceWeb.getSettings().setJavaScriptEnabled(true);
        FaceWeb.getSettings().setSupportZoom(true);
        String facepath = "https://m.facebook.com/Homecarehub-1912065879045843/";
    
        FaceWeb.loadUrl(facepath);
    
        FaceWeb.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }
            
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            
                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(Facebook.this).create();
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
    
        FaceWeb.setWebChromeClient(new WebChromeClient() {
        
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    ProgressFace.setVisibility(View.VISIBLE);
                
                } else if (newProgress == 100) {
                    ProgressFace.setVisibility(View.GONE);
                
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
