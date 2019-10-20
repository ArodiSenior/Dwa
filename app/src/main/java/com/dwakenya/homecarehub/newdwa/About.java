package com.dwakenya.homecarehub.newdwa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity {
    Intent intent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        ImageView FacebookIcon = findViewById(R.id.FacebookIcon);
        FacebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(About.this, Facebook.class));
                
            }
        });
        ImageView TwitterIcon = findViewById(R.id.TwitterIcon);
        TwitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(About.this, Twitter.class));
        
            }
        });
        ImageView WebsiteIcon = findViewById(R.id.WebsiteIcon);
        WebsiteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(About.this, Website.class));
        
            }
        });
    }
}
