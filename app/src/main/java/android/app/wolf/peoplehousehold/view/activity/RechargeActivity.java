package android.app.wolf.peoplehousehold.view.activity;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.adapter.MoneyFlowAdapter;
import android.app.wolf.peoplehousehold.http.bean.AliPayBean;
import android.app.wolf.peoplehousehold.http.bean.MoneyFlowBean;
import android.app.wolf.peoplehousehold.http.bean.UserInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView recharge_back;
    private TextView recharge_money;
    private RecyclerView recharge_recycler;
    private MoneyFlowAdapter adapter;
    private RadioGroup recharge_radiogroup;
    private TextView recharge_btnPay;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int userId = 0;
    int initpage = 1;  //初始页码
    int rows = 15;     //每页个数
    int nowpage = 1;   //当前页面
    int maxpage = 0;   //最大页面
    int last_item = 0; //最后条目
    int max_item = 0;  //最大条目
    int now_item = 0;  //当前顶部条目
    List<MoneyFlowBean.RowsBean> list;

    private float money = 0;
    private double rechargeMoney = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Map<String, String> stringStringMap = (Map<String, String>) msg.obj;
                    Log.d("rechargeactivity",stringStringMap.toString() );
                    String result = stringStringMap.get("resultStatus");

                    if (result.equals("9000")){
                        ToastUtils.showShortToast("充值成功");

//                        new Thread(){
//                            @Override
//                            public void run() {
//                                super.run();
//                                try {
//                                    Thread.sleep(1000);
                                    handler.sendEmptyMessage(2);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }.start();
                    }
                    break;
                case 2:
                    Log.d("rechargeActivity","开始刷新用户数据");
                    HttpRequest getInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                    getInfoRequest.postUserIdtoGetUserInfo(userId).enqueue(new retrofit2.Callback<UserInfoBean>() {
                        @Override
                        public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                            UserInfoBean body = response.body();
                            if (body != null){
                                if (body.getStatusCode().equals("200")){
                                    Log.d("rechargeActivity","userMoney"+body.getData().getAvailMoney());
                                    sharedPreferences.edit().putFloat("userMoney",body.getData().getAvailMoney()).commit();
                                    EventBus.getDefault().post("success");
                                    showMoney();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserInfoBean> call, Throwable t) {

                        }
                    });

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        initView();

        getMoneyFlow(initpage,3);

        setListener();
    }

    private void getMoneyFlow(int page,int sign) {
        HttpRequest getFlowRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getFlowRequest.postIdtoUserMoneyFlowList(userId,1,15).enqueue(new Callback<MoneyFlowBean>() {
            @Override
            public void onResponse(Call<MoneyFlowBean> call, Response<MoneyFlowBean> response) {
                MoneyFlowBean body = response.body();
                maxpage = (body.getTotal() / rows) + 1;
                nowpage = body.getPageNum();
                List<MoneyFlowBean.RowsBean> rows = body.getRows();
                if (rows != null){
                    for (int i = 0 ; i < rows.size() ; i ++){
                        list.add(rows.get(i));
                    }
                adapter = new MoneyFlowAdapter(RechargeActivity.this,list);
                recharge_recycler.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<MoneyFlowBean> call, Throwable t) {

            }
        });
    }

    private void setListener() {
        recharge_back.setOnClickListener(this);
        recharge_btnPay.setOnClickListener(this);

        recharge_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int itemNum = layoutManager.getItemCount();
//                layoutManager.findl

            }
        });

        recharge_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.recharge_radioBtn1:
//                        rechargeMoney = 50;
                        rechargeMoney = 0.01;
                        break;
                    case R.id.recharge_radioBtn2:
//                        rechargeMoney = 100;
                        rechargeMoney = 0.02;
                        break;
                    case R.id.recharge_radioBtn3:
//                        rechargeMoney = 200;
                        rechargeMoney = 0.03;
                        break;
                    case R.id.recharge_radioBtn4:
//                        rechargeMoney = 500;
                        rechargeMoney = 0.04;
                        break;
                }
            }
        });

    }

    private void initView() {
        recharge_back = (ImageView) findViewById(R.id.recharge_back);
        recharge_money = (TextView) findViewById(R.id.recharge_money);
        recharge_recycler = (RecyclerView) findViewById(R.id.recharge_recycler);
        recharge_recycler.setLayoutManager(new LinearLayoutManager(this));
        recharge_radiogroup = (RadioGroup) findViewById(R.id.recharge_radiogroup);
        recharge_btnPay = (TextView) findViewById(R.id.recharge_btnPay);

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
        list = new ArrayList<>();

        showMoney();
    }

    private void showMoney() {
        money = sharedPreferences.getFloat("userMoney",0);
        recharge_money.setText(money+"元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_back:
                finish();
                break;
            case R.id.recharge_btnPay:

                if (rechargeMoney == 0){
                    ToastUtils.showShortToast("请选择充值金额");
                }else {

                    toRecharge();

                }

                break;
        }
    }

    private void toRecharge() {
        HttpRequest rechargeRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        rechargeRequest.postIdandMoneytoRecharge(rechargeMoney,userId).enqueue(new Callback<AliPayBean>() {
            @Override
            public void onResponse(Call<AliPayBean> call, Response<AliPayBean> response) {
                AliPayBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        AliPayBean.DataBean data = body.getData();
                        final String body1 = data.getBody();
                        Log.d("orderAliPay",body1);

                        new Thread(){
                            @Override
                            public void run() {
                                super.run();

                                PayTask aliPay = new PayTask(RechargeActivity.this);
                                Map<String, String> stringStringMap = aliPay.payV2(body1, true);

                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = stringStringMap;
                                handler.sendMessage(msg);

                            }
                        }.start();

                    }
                }
            }

            @Override
            public void onFailure(Call<AliPayBean> call, Throwable t) {

            }
        });
    }
}
