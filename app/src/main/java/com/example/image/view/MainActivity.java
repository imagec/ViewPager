package com.example.image.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
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

class ViewPagerItem {
    public int iMageId;
    public String iMageTitle;

    public ViewPagerItem(int iMageId, String iMageTitle) {
        this.iMageId = iMageId;
        this.iMageTitle = iMageTitle;
    }
}

class ViewPagerAdapter extends PagerAdapter{
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
//			super.destroyItem(container, position, object);
//			view.removeView(view.getChildAt(position));
//			view.removeViewAt(position);
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
    private cloneView dot_focus, dot_unfocus;

    private class ViewPageTask implements Runnable{
        @Override
        public void run() {
            currentItem = (currentItem + 1) % viewPagerItem.length;
            mHandler.sendEmptyMessage(0);
        };
    }

//    private void addView1() {
//        LinearLayout container = (LinearLayout) context.findViewById(R.id.dot_container);
//        View dot_focus_View = context.getLayoutInflater().inflate(R.layout.dot_focus, container, false);
//        View dot_unfocus_View = context.getLayoutInflater().inflate(R.layout.dot_unfocus, container, false);
//    }

//
//    				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
//				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
//    private void update_dot() {
//        container.addView(dot_focus);
//        container.addView(dot_unfocus);
//        dot_focus.get()
//    }

    public ViewPagerShow(final ViewPagerItem[] viewPagerItem, Context context, ViewPager mViewPager, Handler mHandler,
                         final TextView titleText, LinearLayout container, cloneView dot_focus, cloneView dot_unfocus) {
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

        container.addView(dot_unfocus);
        container.addView(dot_focus);
        //container.addView(dot_focus);
        View tmp = (View)dot_focus.clone();

//        for (int i = 0; i < this.viewPagerItem.length; i++) {
//
//            container.addView(dot_unfocus);
//        }

        mViewPagerAdapter = new ViewPagerAdapter(iMagesList);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                titleText.setText(viewPagerItem[position].iMageTitle);
                currentItem = position;
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
        //update_dot();
    }
}

public class MainActivity extends Activity {
    private ViewPagerItem[] viewPagerItem = new ViewPagerItem[] {
            new ViewPagerItem(R.drawable.a, "0巩俐不低俗，我就不能低俗"),
            new ViewPagerItem(R.drawable.b, "1扑树又回来啦！再唱经典老歌引万人大合唱"),
            new ViewPagerItem(R.drawable.c, "2巩俐不低俗，我就不能低俗"),
            new ViewPagerItem(R.drawable.d, "3巩俐不低俗，我就不能低俗"),
            new ViewPagerItem(R.drawable.e, "4巩俐不低俗，我就不能低俗"),
    };

    private ViewPagerShow mViewPagerShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = (LinearLayout) findViewById(R.id.dot_container);
        View dot_focus_View = getLayoutInflater().inflate(R.layout.dot_focus, container, false);
        View dot_unfocus_View = getLayoutInflater().inflate(R.layout.dot_unfocus, container, false);
        //cloneView dot_focus_View = getLayoutInflater().inflate(R.layout.dot_focus, container, false);
        //cloneView dot_unfocus_View = getLayoutInflater().inflate(R.layout.dot_unfocus, container, false);
        //cloneView dot = (cloneView) dot_focus_View.clone();
        mViewPagerShow = new ViewPagerShow(viewPagerItem, this, (ViewPager)findViewById(R.id.vp), mHandler,
                (TextView)findViewById(R.id.title), container, (cloneView) dot_focus_View, (cloneView) dot_unfocus_View);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPagerShow.update();
        };
    };

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }
}
