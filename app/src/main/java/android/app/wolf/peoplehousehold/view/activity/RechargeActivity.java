package android.app.wolf.peoplehousehold.view.activity;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.adapter.MoneyFlowAdapter;
import android.app.wolf.peoplehousehold.http.bean.AliPayBean;
import android.app.wolf.peoplehousehold.http.bean.MoneyFlowBean;
import android.app.wolf.peoplehousehold.http.bean.UserInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.PayBottomDialog;
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

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private ImageView recharge_back;
    private TextView recharge_money;
    private RadioGroup recharge_radiogroup1;
    private RadioGroup recharge_radiogroup2;
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
    private float rechargeMoney = 0;

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
                        handler.sendEmptyMessage(2);
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

        setListener();
    }


    private void setListener() {
        recharge_back.setOnClickListener(this);
        recharge_btnPay.setOnClickListener(this);

        recharge_radiogroup1.setOnCheckedChangeListener(this);
        recharge_radiogroup2.setOnCheckedChangeListener(this);

    }

    private void initView() {
        recharge_back = (ImageView) findViewById(R.id.recharge_back);
        recharge_money = (TextView) findViewById(R.id.recharge_money);
        recharge_radiogroup1 = (RadioGroup) findViewById(R.id.recharge_radiogroup1);
        recharge_radiogroup2 = (RadioGroup) findViewById(R.id.recharge_radiogroup2);
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

        PayBottomDialog dialog = new PayBottomDialog();
        dialog.setMoneyText(rechargeMoney+"元");
        dialog.setOnPayListener(new PayBottomDialog.OnPayinterface() {
            @Override
            public void pay(int type) {
                switch (type){
                    case 0:
                        ToastUtils.showShortToast("请选择支付方式");
                        break;
                    case 1:

                        aliPay();

                        break;
                    case 2:
                        ToastUtils.showShortToast("微信支付暂未开通");
                        break;
                }
            }
        });
        dialog.show(getSupportFragmentManager(),"RechargeActivity");

    }

    private void aliPay(){
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.recharge_radioBtn1:
                recharge_radiogroup2.clearCheck();
                recharge_radiogroup1.check(R.id.recharge_radioBtn1);
                rechargeMoney = (float) 0.01;
                break;
            case R.id.recharge_radioBtn2:
                recharge_radiogroup2.clearCheck();
                recharge_radiogroup1.check(R.id.recharge_radioBtn2);
                rechargeMoney = (float) 0.01;
                break;
            case R.id.recharge_radioBtn3:
                recharge_radiogroup2.clearCheck();
                recharge_radiogroup1.check(R.id.recharge_radioBtn3);
                rechargeMoney = (float) 0.01;
                break;
            case R.id.recharge_radioBtn4:
                recharge_radiogroup1.clearCheck();
                recharge_radiogroup2.check(R.id.recharge_radioBtn4);
                rechargeMoney = (float) 0.01;
                break;
            case R.id.recharge_radioBtn5:
                recharge_radiogroup1.clearCheck();
                recharge_radiogroup2.check(R.id.recharge_radioBtn5);
                rechargeMoney = (float) 0.02;
                break;
            case R.id.recharge_radioBtn6:
                recharge_radiogroup1.clearCheck();
                recharge_radiogroup2.check(R.id.recharge_radioBtn6);
                rechargeMoney = (float) 0.03;
                break;
        }
    }
}
