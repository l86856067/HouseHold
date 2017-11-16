package android.app.wolf.peoplehousehold.view.fragment;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.adapter.SearchOrderListViewAdapter;
import android.app.wolf.peoplehousehold.http.bean.SearchOrderInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.activity.OrderDetailsActivity;
import android.app.wolf.peoplehousehold.view.activity.SearchActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lh on 2017/10/20.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class OrderFragment extends Fragment {

    private RelativeLayout hisfragment_search_btn;
    private SwipeRefreshLayout orderfragment_refresh;
    private ListView orderfragment_list;
    private List<SearchOrderInfoBean.RowsBean> list;
    private SearchOrderListViewAdapter adapter;

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
                    orderfragment_refresh.setRefreshing(false);
                    break;
                case 1:  //刷新数据完成
                    adapter.notifyDataSetChanged();
                    orderfragment_refresh.setRefreshing(false);
                    break;
                case 2:  //上拉加载数据完成
                    orderfragment_list.setSelection(now_item);
                    orderfragment_refresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order,null);

        initView(view);

        loadData(initpage,0);

        setListener();

        return view;
    }

    private void loadData(int page, final int sign) {

        orderfragment_refresh.setRefreshing(true);
        HttpRequest loadHisData = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        loadHisData.postIdtoGetHisOrderList(userId,"","",page,rows).enqueue(new Callback<SearchOrderInfoBean>() {
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
                        adapter = new SearchOrderListViewAdapter(list,getActivity());
                        orderfragment_list.setAdapter(adapter);
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

    private void setListener() {
        hisfragment_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        orderfragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchOrderInfoBean.RowsBean rowsBean = list.get(position);
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);

                intent.putExtra("orderId",list.get(position).getId());

                startActivity(intent);
            }
        });

        orderfragment_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                now_item = totalItemCount;
                max_item = totalItemCount;
            }
        });

        orderfragment_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });

    }

    private void initView(View view) {
        hisfragment_search_btn = (RelativeLayout) view.findViewById(R.id.hisfragment_search_btn);
        orderfragment_refresh = (SwipeRefreshLayout) view.findViewById(R.id.orderfragment_refresh);
        orderfragment_list = (ListView) view.findViewById(R.id.orderfragment_list);
        list = new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences("peoplehousehold", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
    }
}
