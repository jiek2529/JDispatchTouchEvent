package com.jiek.jdispatchtouchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import com.jiek.jlogger.JLog;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static com.jiek.jdispatchtouchevent.Utils.actionToString;

public class MyListView extends ListView {
    private static final String TAG = "MyListView";

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float mlastx, mlasty;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        d("dispatchTouchEvent: ", event);
        parentInterceptControl(event);
        boolean flag = super.dispatchTouchEvent(event);
        d("dispatchTouchEvent: " + flag + "  ", event);
        if (event.getAction() == ACTION_CANCEL) {
            Log.e(TAG, "dispatchTouchEvent: ", new Throwable());
        }
        return flag;
    }

    /**
     * 根据当前滑动变化，拦截权剥夺父控件权限的控制
     * 此方法不能写在 interceptTouchEvent 方法中，因为拦截后同级与次级事件不会再进入interceptTouchEvent()方法
     *
     * @param event
     */
    private void parentInterceptControl(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        switch (event.getAction()) {
            case ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);//如果父控件拦截的 Down 之外的事件后，此句可让父控件不拦截其它事件
                break;
            case MotionEvent.ACTION_MOVE:
                float deltax = x - mlastx;
                float deltay = y - mlasty;
                if (Math.abs(deltax) > Math.abs(deltay)) {//横向滑动
//                    当父 View 拦截到 MOVE 事件后，当前 View 会立即获取到 Cancel 事件的
                    getParent().requestDisallowInterceptTouchEvent(false);//需要父 View 拦截事件，且父 View 的 onInterceptTouchEvent的 Move 事件必须拦截才真生效
                }
                break;

            case ACTION_CANCEL:
                Log.d(TAG, "dispatchTouchEvent: ", new Throwable());
                break;
        }
        mlastx = x;
        mlasty = y;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(event);
        d("onInterceptTouchEvent: " + intercept + "  ", event);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean flag = super.onTouchEvent(ev);
        d("onTouchEvent: " + flag + "   ", ev);
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
