package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.PlaceOrderResultBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.AddressEditDialog;
import android.app.wolf.peoplehousehold.view.myview.RemarkEditDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceLongOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView placelong_btnBack;
    private TextView placelong_title;
    private RelativeLayout placelong_number;
    private TextView placelong_number_text;
    private EditText placelong_editName;
    private EditText placelong_editTele;
    private EditText placelong_editAddre;
    private RelativeLayout placelong_remarks;
    private TextView placelong_remarks_remark;
    private TextView placelong_pay;

    ProgressDialog loadDialog;
    SharedPreferences sharedPreferences;
    String title = "";
    int userId = 0;
    int serviceItemId = 0;
    int serviceNotifyNum = 0;
    String serviceRemark = "";
    String orderContactName = "";
    String orderContactTel = "";
    String orderContactAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_long_order);

        getData();

        initView();

        showView();

        setListener();

    }

    private void showView() {

        placelong_title.setText(title);
        placelong_editName.setText(orderContactName);
        placelong_editTele.setText(orderContactTel);
        placelong_editAddre.setText(orderContactAddress);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.placelong_btnBack:
                finish();
                break;
            case R.id.placelong_number:

                getNotifyCustNumber();

                break;
            case R.id.placelong_remarks:

                inputRemarks();

                break;
            case R.id.placelong_pay:

                if (judgeInfo()){

                    toPlace();

                }
                break;
        }
    }

    private void toPlace() {

        loadDialog.show();
        HttpRequest placeRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        placeRequest.postInfotoPlaceLongOrder(userId,serviceItemId,serviceNotifyNum,orderContactAddress,serviceRemark,orderContactName,orderContactTel).enqueue(new Callback<PlaceOrderResultBean>() {
            @Override
            public void onResponse(Call<PlaceOrderResultBean> call, Response<PlaceOrderResultBean> response) {
                loadDialog.dismiss();
                PlaceOrderResultBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        ToastUtils.showShortToast("下单成功");
                        Intent intent = new Intent(PlaceLongOrderActivity.this,OrderDetailsActivity.class);
                        intent.putExtra("orderId",body.getData().getId());
                        intent.putExtra("type",0);
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtils.showShortToast(body.getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceOrderResultBean> call, Throwable t) {
                loadDialog.dismiss();
                ToastUtils.showShortToast(t.getMessage());
            }
        });

    }

    private void inputRemarks() {

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
                placelong_remarks_remark.setText(serviceRemark);
                remarkDialog.dismiss();
            }
        });
        remarkDialog.show();

    }

    private void getNotifyCustNumber() {

        final NumberChoiceDoalog numberChoiceDoalog = new NumberChoiceDoalog(this);
        numberChoiceDoalog.setVal(1,10,1);
        numberChoiceDoalog.setOnCancelListener("取消", new NumberChoiceDoalog.OnCancelinterface() {
            @Override
            public void cancel() {
                numberChoiceDoalog.dismiss();
            }
        });
        numberChoiceDoalog.setOnConfirmListener("确定", new NumberChoiceDoalog.OnConfirminterface() {
            @Override
            public void confirm(int date) {
                serviceNotifyNum = date;
                placelong_number_text.setText(serviceNotifyNum+"家");
                numberChoiceDoalog.dismiss();
            }
        });
        numberChoiceDoalog.show();

    }

    private void setListener() {
        placelong_btnBack.setOnClickListener(this);
        placelong_number.setOnClickListener(this);
        placelong_remarks.setOnClickListener(this);
        placelong_pay.setOnClickListener(this);
    }


    private void getData() {
        serviceItemId = getIntent().getIntExtra("serviceItemId",0);
        title = getIntent().getStringExtra("title");

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
        orderContactName = sharedPreferences.getString("userName","");
        orderContactTel = sharedPreferences.getString("userTele","");
        orderContactAddress = sharedPreferences.getString("userAddress","");

    }

    private void initView() {
        placelong_btnBack = (ImageView) findViewById(R.id.placelong_btnBack);
        placelong_title = (TextView) findViewById(R.id.placelong_title);
        placelong_number = (RelativeLayout) findViewById(R.id.placelong_number);
        placelong_remarks = (RelativeLayout) findViewById(R.id.placelong_remarks);

        placelong_editName = (EditText) findViewById(R.id.placelong_editName);
        placelong_editTele = (EditText) findViewById(R.id.placelong_editTele);
        placelong_editAddre = (EditText) findViewById(R.id.placelong_editAddre);

        placelong_number_text = (TextView) findViewById(R.id.placelong_number_text);
        placelong_remarks_remark = (TextView) findViewById(R.id.placelong_remarks_remark);
        placelong_pay = (TextView) findViewById(R.id.placelong_pay);

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("正在下单");
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    public boolean judgeInfo(){

        orderContactName = placelong_editName.getText().toString();
        orderContactTel = placelong_editTele.getText().toString();
        orderContactAddress = placelong_editAddre.getText().toString();

        if (serviceNotifyNum == 0){
            ToastUtils.showShortToast("请选择通知门店数量");
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
