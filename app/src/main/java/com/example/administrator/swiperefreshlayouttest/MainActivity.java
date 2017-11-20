package com.example.administrator.swiperefreshlayouttest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.swiperefreshlayouttest.myswiprefreshlayout.CustomProgressDrawable;
import com.example.administrator.swiperefreshlayouttest.myswiprefreshlayout.CustomSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CustomSwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private View bottomLayout;
    //private ImageButton imgBtNew;
    //private TextInputView textInputView;
    private Button btSend;
    private ViewPager emojiPanel;
    //private MomentsListAdapter adapter;

    private ArrayList beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imgBtNew = (ImageButton) findViewById(R.id.imgBt_new);
        mRefreshLayout = (CustomSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        bottomLayout = findViewById(R.id.rlBottomBar);
        //textInputView = (TextInputView) findViewById(R.id.viewInputText);
        btSend = (Button) findViewById(R.id.btSend);
        emojiPanel = (ViewPager) findViewById(R.id.vpPanelEmoji);
        initView();


    }

    private void initView() {
        beanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            beanList.add("item" + (i + 1));
        }
        setRecyclerView(beanList, R.layout.support_simple_spinner_dropdown_item);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (bottomLayout.getVisibility() == View.VISIBLE) {
                        bottomLayout.setVisibility(View.GONE);

                        return true;
                    }
                }
                return false;
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CustomProgressDrawable drawable = new CustomProgressDrawable(this, mRefreshLayout);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        drawable.setBitmap(bitmap);
        mRefreshLayout.setProgressView(drawable);
        mRefreshLayout.setBackgroundColor(Color.BLACK);
        mRefreshLayout.setProgressBackgroundColorSchemeColor(Color.BLACK);
        mRefreshLayout.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mRefreshLayout.setRefreshing(false);
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });
    }

    private void setRecyclerView(final List data, int layout) {
        //线性布局管理器
        //false:不进行反转
        //true:进行反转
        final LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter(beanList);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * recycleview的适配器
     *第一个泛型是数据类型，第二个泛型是Viewholder
     */
    public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public MyAdapter(List<String> data) {
            //注意：布局需要自己手动设置，忘记设置布局会报出错误
            super(R.layout.support_simple_spinner_dropdown_item, data);
        }
        @Override
        protected void convert(final BaseViewHolder holder, String item) {
//            TextView textView = helper.getView(R.id.tv_outter_adapter_text);
//            textView.setText(item);
            //可以将将值直接设置给tv_outter_adapter_text控件，原理请自行阅读源代码
            holder.setText(android.R.id.text1, item)
                    .setTextColor(android.R.id.text1, Color.RED);
            //给条目设置点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "点击了" + holder.getLayoutPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
