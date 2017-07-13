package com.example.image.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * Created by image on 2017/7/14.
 */

class ViewPagerItem {
    public int iMageId;
    public String iMageTitle;

    public ViewPagerItem(int iMageId, String iMageTitle) {
        this.iMageId = iMageId;
        this.iMageTitle = iMageTitle;
    }
}

class ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> iMageViews;

    public ViewPagerAdapter(List<ImageView> iMageViews) {
        this.iMageViews = iMageViews;
    }

    @Override
    public int getCount() {
        return iMageViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
// TODO Auto-generated method stub
//      super.destroyItem(container, position, object);
//      view.removeView(view.getChildAt(position));
//      view.removeViewAt(position);
        view.removeView(iMageViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        // TODO Auto-generated method stub
        view.addView(iMageViews.get(position));
        return iMageViews.get(position);
    }
}

class cloneView extends View {

    public cloneView(Context context) {
        super(context);
    }

    public cloneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public cloneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public cloneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public Object clone(){
        Object o=null;
        try {
            o=super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}

class ViewPagerShow {
    private ArrayList<View> dots;
    private ViewPagerItem[] viewPagerItem;
    private List<ImageView> iMagesList;
    private Context context;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private ScheduledExecutorService scheduledExecutorService;
    private Handler mHandler;
    private int currentItem = 0;
    private TextView titleText;
    private LinearLayout container;
    private View dot_focus, dot_unfocus;

    private class ViewPageTask implements Runnable{
        @Override
        public void run() {
            currentItem = (currentItem + 1) % viewPagerItem.length;
            mHandler.sendEmptyMessage(0);
        };
    }

    public void dotUpdate() {
        container.removeAllViews();
        View view;
        for (int i = 0; i < this.viewPagerItem.length; i++) {
            if (i == currentItem) {
                view = LayoutInflater.from(context).inflate(R.layout.dot_focus, null);
                container.addView(view);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.dot_unfocus, null);
                container.addView(view);
            }
        }
    }

    public ViewPagerShow(final ViewPagerItem[] viewPagerItem, Context context, ViewPager mViewPager, Handler mHandler,
                         final TextView titleText, LinearLayout container) {
        this.viewPagerItem = viewPagerItem;
        this.context = context;
        this.mViewPager = mViewPager;
        this.mHandler = mHandler;
        this.titleText = titleText;
        this.container = container;
        this.dot_focus = dot_focus;
        this.dot_unfocus = dot_unfocus;

        /* show first pic title */
        titleText.setText(viewPagerItem[0].iMageTitle);

        iMagesList = new ArrayList<ImageView>();
        for (int i = 0; i < this.viewPagerItem.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(viewPagerItem[i].iMageId);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.d(TAG, "onClick: onClick");;
                }
            });
            iMagesList.add(imageView);
        }

//        final View view = LayoutInflater.from(context).inflate(R.layout.dot_focus, null);
//        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view1 = li.inflate(R.layout.dot_unfocus, null);
//        container.addView(view);
//        container.addView(view1);

        //dots.get(position).setBackgroundResource(R.drawable.dot_focused);
        dotUpdate();
//        ArrayList<View> dotViews = new ArrayList<View>();
//        View view;
//        for (int i = 0; i < this.viewPagerItem.length; i++) {
//            view = LayoutInflater.from(context).inflate(R.layout.dot_unfocus, null);
//            dotViews.add(view);
//            container.addView(view);
//        }
//        dotViews.get(3).setBackgroundResource(R.drawable.dot_focused);

        mViewPagerAdapter = new ViewPagerAdapter(iMagesList);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                titleText.setText(viewPagerItem[position].iMageTitle);
                currentItem = position;
                dotUpdate();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 5, 5, TimeUnit.SECONDS);
    }

    public void update() {
        mViewPager.setCurrentItem(currentItem);
        titleText.setText(viewPagerItem[currentItem].iMageTitle);
        dotUpdate();
        //update_dot();
    }
}
