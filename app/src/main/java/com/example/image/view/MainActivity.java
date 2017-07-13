package com.example.image.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ViewPagerItem[] viewPagerItem = new ViewPagerItem[] {
            new ViewPagerItem(R.drawable.a, "0图片描述信息0"),
            new ViewPagerItem(R.drawable.b, "1图片描述信息1"),
            new ViewPagerItem(R.drawable.c, "2图片描述信息2"),
            new ViewPagerItem(R.drawable.d, "3图片描述信息3"),
            new ViewPagerItem(R.drawable.e, "4图片描述信息4"),
            new ViewPagerItem(R.drawable.e, "5图片描述信息5"),
    };

    private ViewPagerShow mViewPagerShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = (LinearLayout) findViewById(R.id.dot_container);
//        View dot_focus_View = getLayoutInflater().inflate(R.layout.dot_focus, container, false);
//        View dot_unfocus_View = getLayoutInflater().inflate(R.layout.dot_unfocus, container, false);
//        cloneView dot_focus_View = getLayoutInflater().inflate(R.layout.dot_focus, container, false);
//        cloneView dot_unfocus_View = getLayoutInflater().inflate(R.layout.dot_unfocus, container, false);
//        cloneView dot = (cloneView) dot_focus_View.clone();
        mViewPagerShow = new ViewPagerShow(viewPagerItem, this, (ViewPager)findViewById(R.id.vp), mHandler,
                (TextView)findViewById(R.id.title), container);

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
