package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.PlaceOrderResultBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.RemarkEditDialog;
import android.app.wolf.peoplehousehold.view.myview.ServiceTimeChoiceDoalog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceReclaimOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView placereclaim_btnBack;
    private RelativeLayout placereclaim_time;
    private TextView placereclaim_timeChoice;
    private RelativeLayout placereclaim_acreage;
    private EditText placereclaim_acreageInput;
    private EditText placereclaim_editName;
    private EditText placereclaim_editTele;
    private EditText placereclaim_editAddre;
    private RelativeLayout placereclaim_remarks;
    private TextView placereclaim_remarksText;
    private TextView placereclaim_money;
    private TextView placereclaim_place;

    private ProgressDialog placeDialog;

    private SharedPreferences sharedPreferences;
    private int userId = 0;
    private int serviceItemId = 0;
    private String serviceStarttime = "";
    private String serviceRemark = "";

    private String acreageText = "";
    private int acreage = 0;
    private float money = 0;
    private String userName = "";
    private String userTele = "";
    private String userAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_reclaim_order);

        getlastData();

        initView();

        showUserData();

        setListener();
    }

    private void setListener() {

        placereclaim_time.setOnClickListener(this);
        placereclaim_remarks.setOnClickListener(this);
        placereclaim_place.setOnClickListener(this);
        placereclaim_btnBack.setOnClickListener(this);

        placereclaim_acreageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showMoney();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.placereclaim_time:

                getServiceStartTime();

                break;
            case R.id.placereclaim_remarks:

                toInputRemarks();

                break;
            case R.id.placereclaim_place:

                if (judgeInputIsTrue()){
                    toPlace();
                }

                break;
            case R.id.placereclaim_btnBack:

                finish();

                break;
        }
    }

    private void toPlace() {

        placeDialog.show();

        HttpRequest placeReclaimRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        placeReclaimRequest.postInfotoPlaceReclaimOrder(userId,serviceStarttime,money,serviceItemId,acreage,serviceRemark,userName,userTele,userAddress).enqueue(new Callback<PlaceOrderResultBean>() {
            @Override
            public void onResponse(Call<PlaceOrderResultBean> call, Response<PlaceOrderResultBean> response) {
                placeDialog.dismiss();
                PlaceOrderResultBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        ToastUtils.showShortToast("下单成功");
                        Intent intent = new Intent(PlaceReclaimOrderActivity.this,OrderDetailsActivity.class);
                        intent.putExtra("orderId",body.getData().getId());
                        intent.putExtra("type",1);
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtils.showShortToast(body.getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceOrderResultBean> call, Throwable t) {
                placeDialog.dismiss();
                ToastUtils.showShortToast(t.getMessage());
            }
        });

    }


    private void showMoney() {

        acreageText = placereclaim_acreageInput.getText().toString();
        if (acreageText.isEmpty()){
            placereclaim_money.setText("付款：");
        }else {
            acreage = Integer.parseInt(acreageText);
            money = acreage * 10;
            placereclaim_money.setText("付款：" + money + "元");
        }

    }

    private boolean judgeInputIsTrue(){

        userName = placereclaim_editName.getText().toString();
        userTele = placereclaim_editTele.getText().toString();
        userAddress = placereclaim_editAddre.getText().toString();

        if (serviceStarttime.isEmpty()){
            ToastUtils.showShortToast("请选择服务开始时间");
            return false;
        }else if (acreageText.isEmpty()){
            ToastUtils.showShortToast("请输入房间面积");
            return false;
        }else if (userName.isEmpty()){
            ToastUtils.showShortToast("请输入客户名称");
            return false;
        }else if (userTele.length() != 11){
            ToastUtils.showShortToast("请输入正确的电话");
            return false;
        }else if (userAddress.isEmpty()){
            ToastUtils.showShortToast("请输入服务地址");
            return false;
        }else {
            return true;
        }

    }

    private void toInputRemarks() {

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
                placereclaim_remarksText.setText(serviceRemark);
                remarkDialog.dismiss();
            }
        });
        remarkDialog.show();

    }

    private void getServiceStartTime() {

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
                placereclaim_timeChoice.setText(serviceStarttime);
            }
        });
        serviceTimeChoiceDoalog.show();

    }

    private void showUserData() {

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
        userName = sharedPreferences.getString("userName","");
        userTele = sharedPreferences.getString("userTele","");
        userAddress = sharedPreferences.getString("userAddress","");

        placereclaim_editName.setText(userName);
        placereclaim_editTele.setText(userTele);
        placereclaim_editAddre.setText(userAddress);

    }

    private void getlastData() {
        serviceItemId = getIntent().getIntExtra("serviceItemId",0);
    }

    private void initView() {
        placereclaim_btnBack = (ImageView) findViewById(R.id.placereclaim_btnBack);
        placereclaim_time = (RelativeLayout) findViewById(R.id.placereclaim_time);
        placereclaim_timeChoice = (TextView) findViewById(R.id.placereclaim_timeChoice);
        placereclaim_acreage = (RelativeLayout) findViewById(R.id.placereclaim_acreage);
        placereclaim_acreageInput = (EditText) findViewById(R.id.placereclaim_acreageInput);
        placereclaim_editName = (EditText) findViewById(R.id.placereclaim_editName);
        placereclaim_editTele = (EditText) findViewById(R.id.placereclaim_editTele);
        placereclaim_editAddre = (EditText) findViewById(R.id.placereclaim_editAddre);
        placereclaim_remarks = (RelativeLayout) findViewById(R.id.placereclaim_remarks);
        placereclaim_remarksText = (TextView) findViewById(R.id.placereclaim_remarksText);
        placereclaim_money = (TextView) findViewById(R.id.placereclaim_money);
        placereclaim_place = (TextView) findViewById(R.id.placereclaim_place);

        placeDialog = new ProgressDialog(this);
        placeDialog.setMessage("下单中");
        placeDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }


}
