package com.example.dell.actplus;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by DELL on 2017/5/22.
 */
//Learn fragmentPagerAdapter from :http://www.jianshu.com/p/e5abbda4a71c
public class MyPagerAdapter extends PagerAdapter {
    private List<ActItem> ActList;
    private Context context;
    public MyPagerAdapter(List<ActItem> list, Context appContext) {
        this.ActList = list;
        this.context = appContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(context);
            view.setImageBitmap(ActList.get(position).getImage());
            container.addView(view);
            return view;
    }

    @Override
    public int getCount() {
        return ActList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //内存问题
        container.removeView((View) object);
    }
}
