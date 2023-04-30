package com.example.timewrapscan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.timewrapscan.Activity.Utl;
import com.example.timewrapscan.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ImageDisplayAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> imageArrayList=new ArrayList<>();
    public ImageDisplayAdapter(Context context, ArrayList<String> imageArrayList){
        this.context=context;
        this.imageArrayList=imageArrayList;
    }

    @Override
    public int getCount() {
        return imageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mPage = inflater.inflate(R.layout.view_image,null);

        RoundedImageView img=(RoundedImageView)mPage.findViewById(R.id.imageview);
        Utl.SetUILinear(context,img,1000,1500);
        Log.i("ImageAdapter","ImagePosition"+imageArrayList.get(position));

        Glide.with(context).load(imageArrayList.get(position)).into(img);
        collection.addView(mPage );

        return mPage;
    }
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
