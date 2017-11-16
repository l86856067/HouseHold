package android.app.wolf.peoplehousehold.view.activity;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.adapter.SearchOrderListViewAdapter;
import android.app.wolf.peoplehousehold.http.bean.SearchOrderInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.TimeChoiceDoalog;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView search_back;
    private TextView search_startBtn;
    private TextView search_endBtn;
    private TextView search_search;
    private SwipeRefreshLayout search_refre;
    private ListView search_list;
    private List<SearchOrderInfoBean.RowsBean> list;
    private SearchOrderListViewAdapter adapter;

    String startDate = "";
    String endDate = "";

    private SharedPreferences sharedPreferences;
    int userId = 0;    //商户id
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
                    search_refre.setRefreshing(false);
                    break;
                case 1:  //刷新数据完成
                    adapter.notifyDataSetChanged();
                    search_refre.setRefreshing(false);
                    break;
                case 2:  //上拉加载数据完成
                    search_list.setSelection(now_item);
                    search_refre.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        setListener();
    }

    private void setListener() {

        search_back.setOnClickListener(this);
        search_startBtn.setOnClickListener(this);
        search_endBtn.setOnClickListener(this);
        search_search.setOnClickListener(this);

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchOrderInfoBean.RowsBean rowsBean = list.get(position);
                Intent intent = new Intent(SearchActivity.this, OrderDetailsActivity.class);

                intent.putExtra("orderId",list.get(position).getId());

                startActivity(intent);
            }
        });

        search_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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


        search_refre.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_back:
                finish();
                break;
            case R.id.search_startBtn:

                final TimeChoiceDoalog startdialog = new TimeChoiceDoalog(this);
                startdialog.setOnCancelListener("取消", new TimeChoiceDoalog.OnCancelinterface() {
                    @Override
                    public void cancel() {
                        startdialog.dismiss();
                    }
                });
                startdialog.setOnConfirmListener("确定", new TimeChoiceDoalog.OnConfirminterface() {
                    @Override
                    public void confirm(String date) {
                        startDate = date;
                        search_startBtn.setText(date);
                        startdialog.dismiss();
                    }
                });
                startdialog.show();

                break;
            case R.id.search_endBtn:

                final TimeChoiceDoalog enddialog = new TimeChoiceDoalog(this);
                enddialog.setOnCancelListener("取消", new TimeChoiceDoalog.OnCancelinterface() {
                    @Override
                    public void cancel() {
                        enddialog.dismiss();
                    }
                });
                enddialog.setOnConfirmListener("确定", new TimeChoiceDoalog.OnConfirminterface() {
                    @Override
                    public void confirm(String date) {
                        endDate = date;
                        search_endBtn.setText(date);
                        enddialog.dismiss();
                    }
                });
                enddialog.show();

                break;
            case R.id.search_search:

                if (dateIsOk()){

                    list.clear();
                    loadData(initpage,0);

                }

                break;
        }
    }

    private void loadData(int page, final int sign) {

        search_refre.setRefreshing(true);
        HttpRequest loadHisData = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        loadHisData.postIdtoGetHisOrderList(userId,startDate,endDate,page,rows).enqueue(new Callback<SearchOrderInfoBean>() {
            @Override
            public void onResponse(Call<SearchOrderInfoBean> call, Response<SearchOrderInfoBean> response) {
                SearchOrderInfoBean body = response.body();
                if (body != null){
                    maxpage = (body.getTotal() / rows) + 1;
                    nowpage = body.getPageNum();
                    List<SearchOrderInfoBean.RowsBean> rows = body.getRows();
                    if (rows != null){
                        for (int i = 0; i < rows.size() ; i ++){
                            list.add(rows.get(i));
                        }
                        adapter = new SearchOrderListViewAdapter(list,SearchActivity.this);
                        search_list.setAdapter(adapter);
                        handler.sendEmptyMessage(sign);
                    }
                }

            }

            @Override
            public void onFailure(Call<SearchOrderInfoBean> call, Throwable t) {
                ToastUtils.showShortToast(t.getMessage());
                handler.sendEmptyMessage(sign);
            }
        });

    }

    private void initView() {
        search_back = (ImageView) findViewById(R.id.search_back);
        search_startBtn = (TextView) findViewById(R.id.search_startBtn);
        search_endBtn = (TextView) findViewById(R.id.search_endBtn);
        search_search = (TextView) findViewById(R.id.search_search);
        search_refre = (SwipeRefreshLayout) findViewById(R.id.search_refre);
        search_list = (ListView) findViewById(R.id.search_list);
        search_list = (ListView) findViewById(R.id.search_list);
        list = new ArrayList<>();
        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
    }

    public boolean dateIsOk(){
        if (startDate.isEmpty()){
            ToastUtils.showShortToast("请选择开始日期");
            return false;
        }else if (endDate.isEmpty()){
            ToastUtils.showShortToast("请选择结束日期");
            return false;
        }else {
            return true;
        }
    }


}
