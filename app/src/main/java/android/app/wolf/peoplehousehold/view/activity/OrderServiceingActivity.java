package android.app.wolf.peoplehousehold.view.activity;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.adapter.ServiceingOrderListViewAdapter;
import android.app.wolf.peoplehousehold.http.bean.ServiceingInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderServiceingActivity extends AppCompatActivity {

    private ImageView serviceing_btnBack;
    private SwipeRefreshLayout serviceing_refresh;
    private ListView serviceing_lsit;
    private List<ServiceingInfoBean.RowsBean> list;
    private ServiceingOrderListViewAdapter adapter;

    private SharedPreferences sharedPreferences;
    int userId = 0;    //用户id
    int initpage = 1;  //初始页码
    int rows = 15;     //每页个数
    int nowpage = 1;   //当前页面
    int maxpage = 0;   //最大页面
    int last_item = 0; //最后条目
    int max_item = 0;  //最大条目
    int now_item = 0;  //当前顶部条目

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:  //加载数据完成
                    serviceing_refresh.setRefreshing(false);
                    break;
                case 1:  //刷新数据完成
                    adapter.notifyDataSetChanged();
                    serviceing_refresh.setRefreshing(false);
                    break;
                case 2:  //上拉加载数据完成
                    serviceing_lsit.setSelection(now_item);
                    serviceing_refresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_serviceing);

        initView();

        loadData(initpage,0);

        serListener();

    }

    private void loadData(int page, final int sign) {

        serviceing_refresh.setRefreshing(true);
        HttpRequest getServiceingRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getServiceingRequest.postuserIdtogetServiceStatus(userId,page,rows).enqueue(new Callback<ServiceingInfoBean>() {
            @Override
            public void onResponse(Call<ServiceingInfoBean> call, Response<ServiceingInfoBean> response) {
                ServiceingInfoBean body = response.body();
                if (body != null){
                    maxpage = (body.getTotal() / rows) + 1;
                    nowpage = body.getPageNum();
                    List<ServiceingInfoBean.RowsBean> rows = body.getRows();
                    if (rows != null){
                        for (int i = 0; i < rows.size() ; i ++){
                            list.add(rows.get(i));
                        }
                        adapter = new ServiceingOrderListViewAdapter(list,OrderServiceingActivity.this);
                        serviceing_lsit.setAdapter(adapter);
                        handler.sendEmptyMessage(sign);
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceingInfoBean> call, Throwable t) {
                ToastUtils.showShortToast(t.getMessage());
                handler.sendEmptyMessage(sign);
            }
        });

    }

    private void serListener() {

        serviceing_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        serviceing_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });

        serviceing_lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(OrderServiceingActivity.this,OrderDetailsActivity.class);
                intent.putExtra("orderId",list.get(position).getId());
                startActivity(intent);

            }
        });

        serviceing_lsit.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.d(TAG,"onScrollStateChanged:"+scrollState);
                switch (scrollState){
                    case 0:
                        if (last_item == max_item){
                            if (nowpage < maxpage){
                                loadData(++nowpage , 2);
                            }else {
                                ToastUtils.showShortToast("到最底了");
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.d(TAG,"onScroll:"+firstVisibleItem+","+visibleItemCount+","+totalItemCount);
                last_item = firstVisibleItem + visibleItemCount;
                now_item = visibleItemCount;
                max_item = totalItemCount;
            }
        });

    }

    private void initView() {
        serviceing_btnBack = (ImageView) findViewById(R.id.serviceing_btnBack);
        serviceing_refresh = (SwipeRefreshLayout) findViewById(R.id.serviceing_refresh);
        serviceing_lsit = (ListView) findViewById(R.id.serviceing_lsit);
        list = new ArrayList<>();

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
    }
}
