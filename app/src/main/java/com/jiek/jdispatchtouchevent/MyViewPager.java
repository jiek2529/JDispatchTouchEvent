package com.jiek.jdispatchtouchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.jiek.jlogger.JLog;

import static com.jiek.jdispatchtouchevent.Utils.actionToString;

public class MyViewPager extends ViewPager {
    private static final String TAG = "MyViewPager";

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean flag = super.dispatchTouchEvent(ev);
        d("dispatchTouchEvent: " + flag + "   ", ev);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        d("onInterceptTouchEvent: ", ev);
//        boolean intercept = super.onInterceptTouchEvent(ev);
//        d("onInterceptTouchEvent: " + intercept + "   ", ev);
//        return intercept;
//        d("onInterceptTouchEvent: ", ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://让事件传递给子 View，可让需要拦截的 View能消费事件，才能让事件链能完成。子 View 才能使用 requestDisallowInterceptTouchEnvent 能会使用得到。
                super.onInterceptTouchEvent(ev);//此处需要将 Down 事件传递给子控件，否则事件无法传递至子控件
                return false;//不拦截 Down 事件
        }
        return false;//默认拦截除 down 之外的事件。子控件可控件可通过 getParent().requestDisallowInterceptTouchEvent(true); 拦截所有事件，此行不执行
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        d("onTouchEvent: " + flag + "   ", event);
        return flag;
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        d("canvas : " + canvas);
//    }

    private void d(String msg, MotionEvent ev) {
        JLog.e(TAG, msg + actionToString(ev.getAction()));
    }

    private void d(String msg) {
        JLog.e(TAG, msg);
    }

}
