package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.AliPayBean;
import android.app.wolf.peoplehousehold.http.bean.OrderInfoBean;
import android.app.wolf.peoplehousehold.http.bean.RegisterResultBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.AllPayDialog;
import android.media.Rating;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    private ImageView orderdetails_btnBack;
    private TextView orderdetails_serviceName;
    private TextView orderdetails_serviceAddress;
    private TextView orderdetails_customerInfo;
    private TextView orderdetails_serviceTime;
    private TextView orderdetails_serviceLength;
    private TextView orderdetails_serviceNumber;
    private TextView orderdetails_serviceMoney;
    private TextView orderdetails_remarks;
    private RatingBar orderdetails_ratingStar;

    private RelativeLayout orderdetails_serviceInfoLayout;
    private TextView orderdetails_servicepeopleinfo;
    private LinearLayout orderdetails_evaluateLayout;
    private EditText orderdetails_evaluateEdit;
    private TextView orderdetails_servicestatus;

    private TextView orderdetails_btnbotton;

    private int orderId = 0;

    int orderStatus = 0;
    String serviceName = "";
    String serviceAddress = "";
    String userInfo = "";
    String serviceTime = "";
    int serviceLength = 0;
    int serviceNumber = 0;
    String serviceRemarks = "";
    float serviceMoney = 0;

    String peopleName = "";
    String peopleTele = "";

    String serviceEvaluation = "";
    float evaluationScor = 0;
    ProgressDialog progressDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Map<String, String> stringStringMap = (Map<String, String>) msg.obj;
                    Log.d("orderAliPay",stringStringMap.toString() );
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(1000);
                                handler.sendEmptyMessage(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case 2:
                    getData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        initView();

        getData();

        setListener();
    }

    private void setListener() {
        orderdetails_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        HttpRequest getOrderInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getOrderInfoRequest.postOrderIdtoGetOrderInfo(orderId).enqueue(new Callback<OrderInfoBean>() {
            @Override
            public void onResponse(Call<OrderInfoBean> call, Response<OrderInfoBean> response) {
                OrderInfoBean body = response.body();
                Log.d("orderDetaisl",body.getStatusCode()+";"+body.getStatusDesc());
                if (body != null){
                    if (body.getStatusCode().equals("200")){

                        OrderInfoBean.DataBean data = body.getData();
                        orderStatus = data.getOrderStatus();
                        serviceName = data.getServiceName();
                        serviceAddress = data.getAddress();
                        userInfo = data.getOrderContactName()+"    "+data.getOrderContactTel();
                        serviceTime = data.getServiceStarttime();
                        serviceLength = data.getServiceTime();
                        serviceNumber = data.getCustEmplNum();
                        serviceRemarks = data.getServiceDesc();
                        serviceMoney = data.getMoney();

                        peopleName = data.getCustName();
                        peopleTele = data.getCustTele();

                        evaluationScor = data.getServiceScore();
                        serviceEvaluation = data.getServiceEvaluation();

                        showView();

                    }
                }
            }

            @Override
            public void onFailure(Call<OrderInfoBean> call, Throwable t) {
                Log.d("orderDetaisl",t.getMessage());
            }
        });
    }

    private void showView() {

        orderdetails_serviceName.setText(serviceName);
        orderdetails_serviceAddress.setText(serviceAddress);
        orderdetails_customerInfo.setText(userInfo);
        orderdetails_serviceTime.setText(serviceTime);
        orderdetails_serviceLength.setText(serviceLength+"个小时");
        orderdetails_serviceNumber.setText(serviceNumber+"个");
        orderdetails_serviceMoney.setText(serviceMoney+"元");
        orderdetails_remarks.setText(serviceRemarks);

        switch (orderStatus){
            case 0:
                orderdetails_btnbotton.setVisibility(View.VISIBLE);
                orderdetails_btnbotton.setText("支付订单");
                orderdetails_btnbotton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toPay();
                    }
                });
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                orderdetails_serviceInfoLayout.setVisibility(View.VISIBLE);
                orderdetails_servicepeopleinfo.setText(peopleName+"    "+peopleTele);
                orderdetails_servicestatus.setVisibility(View.VISIBLE);
                orderdetails_servicestatus.setText("已接单");
                break;
            case 10:
                orderdetails_serviceInfoLayout.setVisibility(View.VISIBLE);
                orderdetails_servicepeopleinfo.setText(peopleName+"    "+peopleTele);
                orderdetails_servicestatus.setVisibility(View.VISIBLE);
                orderdetails_servicestatus.setText("服务中......");
                orderdetails_btnbotton.setVisibility(View.VISIBLE);
                orderdetails_btnbotton.setText("服务完成");
                orderdetails_btnbotton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        HttpRequest endorderRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        endorderRequest.postIdtoEndOrder(orderId).enqueue(new Callback<RegisterResultBean>() {
                            @Override
                            public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                                progressDialog.dismiss();
                                RegisterResultBean body = response.body();
                                if (body != null){
                                    if (body.getStatusCode().equals("200")){
                                        ToastUtils.showShortToast("服务已完成");
                                        getData();
                                    }else {
                                        ToastUtils.showShortToast(body.getStatusDesc());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterResultBean> call, Throwable t) {
                                progressDialog.dismiss();
                                ToastUtils.showShortToast(t.getMessage());
                            }
                        });
                    }
                });
                break;
            case 11:
                orderdetails_serviceInfoLayout.setVisibility(View.VISIBLE);
                orderdetails_servicepeopleinfo.setText(peopleName+"    "+peopleTele);

                orderdetails_evaluateLayout.setVisibility(View.VISIBLE);

                orderdetails_btnbotton.setVisibility(View.VISIBLE);
                orderdetails_btnbotton.setText("立即评价");

                orderdetails_btnbotton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        evaluationScor = orderdetails_ratingStar.getRating();

                        HttpRequest evaluateRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        evaluateRequest.postIdandInfotoEvaluateOrder(orderId,evaluationScor,orderdetails_evaluateEdit.getText().toString()).enqueue(new Callback<RegisterResultBean>() {
                            @Override
                            public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                                RegisterResultBean body = response.body();
                                if (body != null){
                                    if (body.getStatusCode().equals("200")){
                                        ToastUtils.showShortToast(body.getStatusDesc());
                                        getData();
                                    }else {
                                        ToastUtils.showShortToast(body.getStatusDesc());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterResultBean> call, Throwable t) {
                                ToastUtils.showShortToast(t.getMessage());
                            }
                        });


                    }
                });
                break;
            case 12:
                orderdetails_serviceInfoLayout.setVisibility(View.VISIBLE);
                orderdetails_servicepeopleinfo.setText(peopleName+"    "+peopleTele);
                orderdetails_evaluateLayout.setVisibility(View.VISIBLE);
                orderdetails_evaluateEdit.setText(serviceEvaluation);
                orderdetails_ratingStar.setRating(evaluationScor);
                orderdetails_ratingStar.setIsIndicator(true);
                orderdetails_evaluateEdit.setFocusable(false);
                orderdetails_evaluateEdit.setFocusableInTouchMode(false);
                break;
        }

        if (getIntent().getIntExtra("type",0) == 1){
            toPay();
        }

    }

    private void toPay(){

        AllPayDialog payDialog = new AllPayDialog();
        payDialog.setMoney(serviceMoney);
        payDialog.setOnPayListener(new AllPayDialog.OnPayinterface() {
            @Override
            public void pay(int type) {

                switch (type){
                    case 0:
                        ToastUtils.showShortToast("请选择支付方式");
                        break;
                    case 1:


                        HttpRequest AliPayRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        AliPayRequest.toAliPay(orderId,0.01).enqueue(new Callback<AliPayBean>() {
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

                                                PayTask aliPay = new PayTask(OrderDetailsActivity.this);
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

                        break;
                    case 2:
                        ToastUtils.showShortToast("暂未开通微信支付");
                        break;
                    case 3:
                        break;
                }

            }
        });
        payDialog.show(getSupportFragmentManager(),"orderpay");
    }

    private void initView() {
        orderdetails_btnBack = (ImageView) findViewById(R.id.orderdetails_btnBack);
        orderdetails_serviceName = (TextView) findViewById(R.id.orderdetails_serviceName);
        orderdetails_serviceAddress = (TextView) findViewById(R.id.orderdetails_serviceAddress);
        orderdetails_customerInfo = (TextView) findViewById(R.id.orderdetails_customerInfo);
        orderdetails_serviceTime = (TextView) findViewById(R.id.orderdetails_serviceTime);
        orderdetails_serviceLength = (TextView) findViewById(R.id.orderdetails_serviceLength);
        orderdetails_serviceNumber = (TextView) findViewById(R.id.orderdetails_serviceNumber);
        orderdetails_serviceMoney = (TextView) findViewById(R.id.orderdetails_serviceMoney);
        orderdetails_remarks = (TextView) findViewById(R.id.orderdetails_remarks);
        orderdetails_ratingStar = (RatingBar) findViewById(R.id.orderdetails_ratingStar);

        orderdetails_serviceInfoLayout = (RelativeLayout) findViewById(R.id.orderdetails_serviceInfoLayout);
        orderdetails_servicepeopleinfo = (TextView) findViewById(R.id.orderdetails_servicepeopleinfo);
        orderdetails_evaluateLayout = (LinearLayout) findViewById(R.id.orderdetails_evaluateLayout);
        orderdetails_evaluateEdit = (EditText) findViewById(R.id.orderdetails_evaluateEdit);
        orderdetails_servicestatus = (TextView) findViewById(R.id.orderdetails_servicestatus);

        orderdetails_btnbotton = (TextView) findViewById(R.id.orderdetails_btnbotton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("请稍候");

        orderId = getIntent().getIntExtra("orderId",0);

    }
}
