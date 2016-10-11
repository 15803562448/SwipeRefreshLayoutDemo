package com.sunny.zh.swiperefreshlayoutdemo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sunny.zh.swiperefreshlayoutdemo.adapter.MyAdapter;
import com.sunny.zh.swiperefreshlayoutdemo.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private List<String> datas = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);

        initData();
        initRecyclerView();
        initRefreshLayout();
    }

    private void initRecyclerView() {
        mRecyclerView.findViewById(R.id.recyclerview);
        mAdapter = new MyAdapter(datas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String city) {
                Toast.makeText(MainActivity.this,"city:"+city+",position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRefreshLayout(){
        mLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.darker_gray);//圈圈的颜色
        mLayout.setDistanceToTriggerSync(100);//下拉多少会触发刷新
        mLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.item_press));//圈圈背景色
        mLayout.setSize(SwipeRefreshLayout.LARGE);//圈圈的大小
        mLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0; i<10; i++){
                            mAdapter.addData(i,"new City:"+i);
                        }
                        mAdapter.notifyItemRangeChanged(0,10);
                        mRecyclerView.scrollToPosition(0);
                        mLayout.setRefreshing(false);
                    }
                },3000);
            }
        });




    }

    private void initData(){
        datas.add("fsh");
        datas.add("hjj");
        datas.add("tryer");
        datas.add("jnjhg");
        datas.add("ryytr");
        datas.add("kjkj");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_action_add:
                mAdapter.addData(0,"new City");
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                break;
            case R.id.id_action_girdciew:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.id_action_staview:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        return true;
    }
}
