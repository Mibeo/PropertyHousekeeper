package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class MySpendActivity extends AppCompatActivity {

//    private ListView listView=null;

    //listview的数据填充器
    private ArrayAdapter<String> adapter;
    //listview中数据的集合
    private List<String> data;
    //下一页初始化为0
    int nextpage = 0;
    //每一页记载多少数据
    private int number=10;
    //最多有几页
    private int maxpage=5;
    //用来判断是否加载完成
    private boolean loadfinish=true;
    private View v;
    private Handler handler;
    private ListView listView;
    private ListviewAdapter listviewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.left_to_right, R.anim.hold);
        setContentView(R.layout.acticity_spend_list);
        initView();
    }

    private void initView() {
        /*listView = findViewById(R.id.listview);
        ListviewAdapter listviewAdapter = new ListviewAdapter(this);
        listView.setAdapter(listviewAdapter);*/

        v=this.getLayoutInflater().inflate(R.layout.list_refresh, null);
        listView=(ListView) super.findViewById(R.id.listview);
        //得到数据
        data=DataServer.getData(0,10);
        //实习化ArrayAdapter对象
        listviewAdapter = new ListviewAdapter(this,DataServer.getData(1,10));

//        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);
        //添加listview的脚跟视图，这个方法必须在listview.setAdapter()方法之前，否则无法显示视图
        listView.addFooterView(v);
        //添加数据
        listView.setAdapter(listviewAdapter);
        //当下一页的数据加载完成之后移除改视图
        listView.removeFooterView(v);
        //当用户滑动listview到最后一项是，动态的加载第二页的数据
        listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                // TODO Auto-generated method stub
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, final int totalItemCount)
            {
                //得到listview最后一项的id
                int lastItemId=listView.getLastVisiblePosition();
                //判断用户是否滑动到最后一项，因为索引值从零开始所以要加上1
                if((lastItemId+1)==totalItemCount)
                {
                    /**
                     * 计算当前页，因为每一页只加载十条数据，所以总共加载的数据除以每一页的数据的个数
                     * 如果余数为零则当前页为currentPage=totalItemCount/number；
                     * 如果不能整除则当前页为(int)(totalItemCount/number)+1;
                     * 下一页则是当前页加1
                     */
                    int currentPage=totalItemCount%number;
                    if(currentPage==0)
                    {
                        currentPage=totalItemCount/number;
                    }
                    else
                    {
                        currentPage=(int)(totalItemCount/number)+1;
                    }
                    System.out.println("当前页为："+currentPage);
                    nextpage=currentPage+1;
                    //当总共的数据大于0是才加载数据
                    if(totalItemCount>0)
                    {
                        //判断当前页是否超过最大页，以及上一页的数据是否加载完成
                        if(nextpage<=maxpage && loadfinish )
                        {
                            //添加页脚视图
                            listView.addFooterView(v);
                            loadfinish=false;
                            new Thread(new Runnable()
                            {
                                public void run()
                                {
                                    try {

                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    //获取当前加载页的数据
                                    data=DataServer.getData(totalItemCount, 10);
                                    //通知listview改变UI中的数据
                                    handler.sendEmptyMessage(0);
                                }
                            }).start();
                            //还可以通过这样的方式实现
                            //AsyncTaskLoadData asynctask=new AsyncTaskLoadData(totalItemCount);
                            ///asynctask.execute();
                        }
                    }

                }
                //判断加载的数据的页数有没有超过最大页，并且是否已经记载完成

            }
        });
        handler=new Handler()
        {
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message msg)
            {
                if(msg.what==0)
                {
                    //通知listview中的数据已经改动
                    listviewAdapter.notifyDataSetChanged();
                    loadfinish=true;
                }
                super.handleMessage(msg);
                //判断listview中的页脚视图是否存在，如果存在在删除页脚视图
                if(listView.getFooterViewsCount()!=0)
                {
                    listView.removeFooterView(v);
                }
            }
        };
    }
    //异步加载数据
    @SuppressWarnings("unused")
    private final class AsyncTaskLoadData extends AsyncTask<Object, Object, Object>
    {
        private int count;
        private List<String> list;
        public AsyncTaskLoadData(int totalItemCount) {
            this.count=totalItemCount;
            list=new ArrayList<String>();
        }

        protected Object doInBackground(Object... params) {
            list=DataServer.getData(count, 10);
            return null;
        }

        @Override
        protected void onPostExecute(Object result)
        {
            try {
                data=list;
                listviewAdapter.notifyDataSetChanged();
                Thread.sleep(2000);
                loadfinish=true;
                if(listView.getFooterViewsCount()!=0)
                {
                    listView.removeFooterView(v);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }


    }
}
