package com.example.duan1_application.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.duan1_application.R;
import com.example.duan1_application.model.Photo;

import java.util.ArrayList;

public class PhotoAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Photo>list;

    public PhotoAdapter(Context context, ArrayList<Photo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo,container,false);
        ImageView ivslideshow=view.findViewById(R.id.ivslideshow);

        Photo photo=list.get(position);
        if (photo!=null){
            Glide.with(context).load(photo.getResourceID()).into(ivslideshow);
        }

        container.addView(view);


        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
