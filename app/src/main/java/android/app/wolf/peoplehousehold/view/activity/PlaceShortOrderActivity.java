package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.AliPayBean;
import android.app.wolf.peoplehousehold.http.bean.PlaceOrderResultBean;
import android.app.wolf.peoplehousehold.http.bean.PlaceShortOrderResult;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.AddressEditDialog;
import android.app.wolf.peoplehousehold.view.myview.AllPayDialog;
import android.app.wolf.peoplehousehold.view.myview.RemarkEditDialog;
import android.app.wolf.peoplehousehold.view.myview.ServiceTimeChoiceDoalog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceShortOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView placeshort_btnBack;
    private TextView placeshort_title;
    private RelativeLayout placeshort_time;
    private RelativeLayout placeshort_duration;
    private RelativeLayout placeshort_number;

    private EditText placeshort_editName;
    private EditText placeshort_editTele;
    private EditText placeshort_editAddre;

    private RelativeLayout placeshort_remarks;
    private TextView placeshort_money;
    private TextView placeshort_pay;
    private TextView placeshort_time_choice;
    private TextView placeshort_duration_choice;
    private TextView placeshort_number_choice;
    private TextView placeshort_remarks_text;

    ProgressDialog loadDialog;

    String title = "";
    SharedPreferences sharedPreferences;
    int userId = 0;  //用户id
    String serviceStarttime = "";  //服务开始时间
    int serviceTime = 2;  //服务时间
    float initMoney = 50;  //服务金额
    float money = 100;  //服务金额
    int custEmplNum = 1;  //阿姨数量
    int serviceItemId = 0;  //服务项目id
    String serviceRemark = "";  //服务说明
    String orderContactName = "";  //订单联系人姓名
    String orderContactTel = "";  //订单联系人电话
    String orderContactAddress = "";  //订单联系人地址

    private int orderId = 0;  // 订单id

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 1:
//                    Map<String, String> stringStringMap = (Map<String, String>) msg.obj;
//                    if (stringStringMap.get("resultStatus").equals("9000")){
//                        ToastUtils.showShortToast("下单成功");
//                        Intent intent = new Intent(PlaceShortOrderActivity.this,OrderDetailsActivity.class);
//                        intent.putExtra("orderId",orderId);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    break;
//            }
//        }
//    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_short_order);

        initView();

        showView();

        setListener();
    }

    private void showView() {

        money = initMoney * serviceTime * custEmplNum;
        placeshort_money.setText("付款："+money+"元");

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.placeshort_btnBack:
                finish();
                break;
            case R.id.placeshort_time:
                final ServiceTimeChoiceDoalog serviceTimeChoiceDoalog = new ServiceTimeChoiceDoalog(this);
                serviceTimeChoiceDoalog.setOnCancelListener("取消", new ServiceTimeChoiceDoalog.OnCancelinterface() {
                    @Override
                    public void cancel() {
                        serviceTimeChoiceDoalog.dismiss();
                    }
                });
                serviceTimeChoiceDoalog.setOnConfirmListener("确定", new ServiceTimeChoiceDoalog.OnConfirminterface() {
                    @Override
                    public void confirm(String date) {
                        serviceTimeChoiceDoalog.dismiss();
                        serviceStarttime = date;
                        placeshort_time_choice.setText(serviceStarttime);
                    }
                });
                serviceTimeChoiceDoalog.show();
                break;
            case R.id.placeshort_duration:

                final NumberChoiceDoalog numberChoiceDoalog = new NumberChoiceDoalog(this);
                numberChoiceDoalog.setVal(2,5,2);
                numberChoiceDoalog.setOnConfirmListener("确定", new NumberChoiceDoalog.OnConfirminterface() {
                    @Override
                    public void confirm(int date) {
                        numberChoiceDoalog.dismiss();
                        serviceTime = date;
                        placeshort_duration_choice.setText(serviceTime+"个小时");
                        showView();
                    }
                });
                numberChoiceDoalog.setOnCancelListener("取消", new NumberChoiceDoalog.OnCancelinterface() {
                    @Override
                    public void cancel() {
                        numberChoiceDoalog.dismiss();
                    }
                });
                numberChoiceDoalog.show();

                break;
            case R.id.placeshort_number:

                final NumberChoiceDoalog emplNumChoiceDoalog = new NumberChoiceDoalog(this);
                emplNumChoiceDoalog.setVal(1,5,1);
                emplNumChoiceDoalog.setOnConfirmListener("确定", new NumberChoiceDoalog.OnConfirminterface() {
                    @Override
                    public void confirm(int date) {
                        emplNumChoiceDoalog.dismiss();
                        custEmplNum = date;
                        placeshort_number_choice.setText(custEmplNum+"个");
                        showView();
                    }
                });
                emplNumChoiceDoalog.setOnCancelListener("取消", new NumberChoiceDoalog.OnCancelinterface() {
                    @Override
                    public void cancel() {
                        emplNumChoiceDoalog.dismiss();
                    }
                });
                emplNumChoiceDoalog.show();

                break;
            case R.id.placeshort_remarks:
                final RemarkEditDialog remarkDialog = new RemarkEditDialog(this);
                remarkDialog.setRemark(serviceRemark);
                remarkDialog.setOnCancelButtoninterface("取消", new RemarkEditDialog.onCancelButtoninterface() {
                    @Override
                    public void cancel() {
                        remarkDialog.dismiss();
                    }
                });
                remarkDialog.setOnNextButtoninterface("确定", new RemarkEditDialog.onNextButtoninterface() {
                    @Override
                    public void call(String remark) {
                        serviceRemark = remark;
                        placeshort_remarks_text.setText(serviceRemark);
                        remarkDialog.dismiss();
                    }
                });
                remarkDialog.show();
                break;
            case R.id.placeshort_pay:

                if (judgeInfo()){

                    toPlace();

                }
                break;
        }
    }


    public void toPlace(){

        loadDialog.show();

        HttpRequest placeOrderRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        placeOrderRequest.postInfotoPlaceOrder(userId,serviceStarttime,serviceTime,money,custEmplNum,serviceItemId,serviceRemark,orderContactName,orderContactTel,orderContactAddress).enqueue(new Callback<PlaceShortOrderResult>() {
            @Override
            public void onResponse(Call<PlaceShortOrderResult> call, Response<PlaceShortOrderResult> response) {
                loadDialog.dismiss();
                PlaceShortOrderResult body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        ToastUtils.showShortToast(body.getStatusDesc());
                        orderId = body.getData().getOrderInfo().getId();
                        Intent intent = new Intent(PlaceShortOrderActivity.this,OrderDetailsActivity.class);
                        intent.putExtra("orderId",orderId);
                        intent.putExtra("type",1);
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtils.showShortToast(body.getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceShortOrderResult> call, Throwable t) {
                loadDialog.dismiss();
                ToastUtils.showShortToast(t.getMessage());
            }
        });

    }

//    public void toPay(){
//
//        AllPayDialog payDialog = new AllPayDialog();
//        payDialog.setMoney(money);
//        payDialog.setOnPayListener(new AllPayDialog.OnPayinterface() {
//            @Override
//            public void pay(int type) {
//
//                switch (type){
//                    case 0:
//                        ToastUtils.showShortToast("请选择支付方式");
//                        break;
//                    case 1:
//
//
//                        HttpRequest payRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
//                        payRequest.toAliPay(orderId,0.01).enqueue(new Callback<AliPayBean>() {
//                            @Override
//                            public void onResponse(Call<AliPayBean> call, Response<AliPayBean> response) {
//                                AliPayBean body = response.body();
//                                if (body != null){
//                                    if (body.getStatusCode().equals("200")){
//                                        AliPayBean.DataBean data = body.getData();
//                                        final String body1 = data.getBody();
//                                        Log.d("orderAliPay",body1);
//
//                                        new Thread(){
//                                            @Override
//                                            public void run() {
//                                                super.run();
//
//                                                PayTask aliPay = new PayTask(PlaceShortOrderActivity.this);
//                                                Map<String, String> stringStringMap = aliPay.payV2(body1, true);
//
//                                                Message msg = new Message();
//                                                msg.what = 1;
//                                                msg.obj = stringStringMap;
//                                                handler.sendMessage(msg);
//
//                                            }
//                                        }.start();
//
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<AliPayBean> call, Throwable t) {
//
//                            }
//                        });
//
//                        break;
//                    case 2:
//                        ToastUtils.showShortToast("暂未开通微信支付");
//                        break;
//                    case 3:
//                        break;
//                }
//
//            }
//        });
//        payDialog.show(getSupportFragmentManager(),"shortpay");
//
//    }

    private void setListener() {
        placeshort_btnBack.setOnClickListener(this);
        placeshort_time.setOnClickListener(this);
        placeshort_duration.setOnClickListener(this);
        placeshort_number.setOnClickListener(this);
        placeshort_remarks.setOnClickListener(this);
        placeshort_pay.setOnClickListener(this);
    }

    private void initView() {
        placeshort_btnBack = (ImageView) findViewById(R.id.placeshort_btnBack);
        placeshort_title = (TextView) findViewById(R.id.placeshort_title);
        placeshort_time = (RelativeLayout) findViewById(R.id.placeshort_time);
        placeshort_duration = (RelativeLayout) findViewById(R.id.placeshort_duration);
        placeshort_number = (RelativeLayout) findViewById(R.id.placeshort_number);

        placeshort_editName = (EditText) findViewById(R.id.placeshort_editName);
        placeshort_editTele = (EditText) findViewById(R.id.placeshort_editTele);
        placeshort_editAddre = (EditText) findViewById(R.id.placeshort_editAddre);

        placeshort_remarks = (RelativeLayout) findViewById(R.id.placeshort_remarks);
        placeshort_money = (TextView) findViewById(R.id.placeshort_money);
        placeshort_pay = (TextView) findViewById(R.id.placeshort_pay);
        placeshort_time_choice = (TextView) findViewById(R.id.placeshort_time_choice);
        placeshort_duration_choice = (TextView) findViewById(R.id.placeshort_duration_choice);
        placeshort_number_choice = (TextView) findViewById(R.id.placeshort_number_choice);
        placeshort_remarks_text = (TextView) findViewById(R.id.placeshort_remarks_text);

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("下单中.");
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        serviceItemId = getIntent().getIntExtra("serviceItemId",0);
        title = getIntent().getStringExtra("title");

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
        orderContactName = sharedPreferences.getString("userName","");
        orderContactTel = sharedPreferences.getString("userTele","");
        orderContactAddress = sharedPreferences.getString("userAddress","");

        placeshort_title.setText(title);
        placeshort_editName.setText(orderContactName);
        placeshort_editTele.setText(orderContactTel);
        placeshort_editAddre.setText(orderContactAddress);

    }



    public boolean judgeInfo(){

        orderContactName = placeshort_editName.getText().toString();
        orderContactTel = placeshort_editTele.getText().toString();
        orderContactAddress = placeshort_editAddre.getText().toString();

        if (serviceStarttime.isEmpty()){
            ToastUtils.showShortToast("请选择上门时间");
            return false;
        }else if (orderContactName.isEmpty()){
            ToastUtils.showShortToast("请输入客户姓名");
            return false;
        }else if (orderContactTel.isEmpty()){
            ToastUtils.showShortToast("请输入客户电话");
            return false;
        }else if (orderContactTel.length() != 11){
            ToastUtils.showShortToast("请检查电话格式");
            return false;
        }else if (orderContactAddress.isEmpty()){
            ToastUtils.showShortToast("请输入客户地址");
            return false;
        }else {
            return true;
        }
    }

}
