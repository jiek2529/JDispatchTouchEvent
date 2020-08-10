package com.jiek.jdispatchtouchevent;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jiek.jlogger.JLog;
import com.jiek.jlogger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.jiek.jdispatchtouchevent.Utils.actionToString;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ViewPager mViewPager;
    private List<View> viewList = new ArrayList<>();
    //    private List<ListView> listViews = new ArrayList<>();
    private List<String> list_data = new ArrayList<>();
    private List<ListView> listViews = new ArrayList<>();
    private List<ListAdapter> listViewAdapters = new ArrayList<>();


    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            d("onItemClick: " + position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.setDebug();
        Logger.registerTag(TAG);
        Logger.registerTag("MyViewPager");
        Logger.registerTag("MyListView");

        for (int i = 0; i < 30; i++) {
            list_data.add("data: " + i);
        }

        mViewPager = findViewById(R.id.view_pager);
        for (int i = 0; i < 10; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.plv, null);

            ListAdapter adapter = new MyListViewAdapter(list_data, this);//new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_data);
            ListView listView = inflate.findViewById(R.id.lv);
            listViews.add(listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listener);

            viewList.add(inflate);
        }
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(adapter);
    }

    PagerAdapter adapter = new MyPagerAdapter(viewList);

    public void enableLV(View view) {
        Toast.makeText(this, "enable", Toast.LENGTH_SHORT).show();
    }

    private static class MyPagerAdapter extends androidx.viewpager.widget.PagerAdapter {

        private List<View> mViewList;

        public MyPagerAdapter(List<View> viewList) {
            this.mViewList = viewList;
        }

        @Override
        public int getCount() {//页数
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//实例化当前页 View
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//移除不用的页
            container.removeView(mViewList.get(position));
        }
    }

    private static class MyListViewAdapter extends BaseAdapter {
        List<String> list;
        Context mContext;

        public MyListViewAdapter(List<String> list_data, Context context) {
            list = list_data;
            mContext = context;
//            Logger.registerTag("MyListViewAdapter");
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                d(" listview getView: 加载layout " + position);
                convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_item, parent, false);
                TextView text = convertView.findViewById(R.id.item_tv);
                holder = new ViewHolder();
                holder.text = text;
                convertView.setTag(holder);
            } else {
                d(" listview getView: 复用： " + position);
                holder = (ViewHolder) convertView.getTag();
            }
            holder.text.setText(list.get(position));
            return convertView;
        }

        private void d(String msg, MotionEvent ev) {
            JLog.e("MyListViewAdapter", msg + actionToString(ev.getAction()));
        }

        public void d(String msg) {
            JLog.e("MyListViewAdapter", msg);
        }

        private static class ViewHolder {
            public TextView text;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean flag = super.dispatchTouchEvent(ev);
        d("dispatchTouchEvent: " + flag + "   ", ev);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        d("onTouchEvent: " + flag + "   ", event);
        return flag;
    }

    private void d(String msg, MotionEvent ev) {
        JLog.e(TAG, msg + actionToString(ev.getAction()));
    }

    public void d(String msg) {
        JLog.e(TAG, msg);
    }
}
