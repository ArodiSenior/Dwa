package com.dwakenya.homecarehub.newdwa.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dwakenya.homecarehub.newdwa.R;

public class ViewerAdapter extends PagerAdapter {
    String[] image;
    Context context;
    LayoutInflater inflater;
    
    public ViewerAdapter(Context context, String[] image) {
        this.image = image;
        this.context = context;
    }
    
    @Override
    public int getCount() {
        return image.length;
    }
    
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
    
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fullitem, null);
    
        ImageView imageView = view.findViewById(R.id.ViewerImage);
    
        Glide.with(context).load(image[position]).into(imageView);
        ViewPager viewPager = (ViewPager)container;
        viewPager.addView(view, 0);
        return view;
    }
    
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    
        ViewPager viewPager = (ViewPager)container;
        View view = (View)object;
        viewPager.removeView(view);
    }
}
