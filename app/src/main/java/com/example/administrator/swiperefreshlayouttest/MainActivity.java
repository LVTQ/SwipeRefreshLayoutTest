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
    private MyRecycleViewAdapter adapter;
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
            beanList.add("item" + i + 1);
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
        mRecyclerView.setAdapter(new MyRecycleViewAdapter(data, layout, this, true) {
            @Override
            protected void setPositionClick(int position) {
//                Intent intent = new Intent(ListActivity.this,DetailedInfoActivity.class);
//                intent.putExtra("positions",positions);
//                startActivity(intent);
                Toast.makeText(MainActivity.this, "positionClick:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void initData(MyViewHolder holder, int position) {
//                DetailsInfo info = (DetailsInfo) data.get(position);
//                setHolderData(data,info,holder,position);
            }
        });
        //滚动监听，在滚动监听里面去实现加载更多的功能
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
//                if (lastVisibleItemPosition + 1 == mRecyclerView.getAdapter().getItemCount()) {
//
//
////                    if (!isLoading) {//一个布尔的变量，默认是false
////                        isLoading = true;
////                        Log.e("TAG", "page==" + page);
////                        handler.postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                getDataFromeNet();
////                            }
////                        }, 2000);
//                    // } else
//                    if (page == 3) {
//                        //当没有更多的数据的时候去掉加载更多的布局
//                        MyRecycleViewAdapter adapter = (MyRecycleViewAdapter) mRecyclerView.getAdapter();
//                        adapter.setIsNeedMore(false);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
    }

    private int page = 0;

    /**
     * 上拉加载更多
     */
//    private void getDataFromeNet() {
//        for (int i = 0; i < 6; i++) {
//            DetailsInfo detailsInfo = new DetailsInfo("宝马4S店"+i+i,"小李","2016.2.3","合格");
//            data.add(detailsInfo);
//        }
//        page++;
//        if(page!=3) {
//            isLoading = false;
//        }else {
//            isLoading = true;
//        }
//        mRecyclerView.getAdapter().notifyDataSetChanged();
//    }

}
