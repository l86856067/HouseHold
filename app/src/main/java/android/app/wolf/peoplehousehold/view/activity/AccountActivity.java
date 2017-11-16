package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.RegisterResultBean;
import android.app.wolf.peoplehousehold.http.bean.UserInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.EditDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private ImageView account_back;
    private TextView account_edit;

    private TextView account_userName;
    private TextView account_userAddress;
    private TextView account_userPhone;
    private TextView account_userMoney;
    private RelativeLayout account_addressLayout;

    SharedPreferences sharedPreferences;
    int userId = 0;
    String userName = "";
    String userAddress = "";
    String userphone = "";
    float userMoney = 0;

    ProgressDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initView();

        getData();

        setListener();
    }

    private void getData() {
        loadDialog.show();
        HttpRequest getUserInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getUserInfoRequest.postUserIdtoGetUserInfo(userId).enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                loadDialog.dismiss();
                UserInfoBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        UserInfoBean.DataBean data = body.getData();
                        userName = data.getUsername();
                        userAddress = data.getAddress();
                        userphone = data.getLoginTele();
                        userMoney = data.getAvailMoney();

                        showViewandserListener();
                        
                    }else {
                        ToastUtils.showShortToast(body.getStatusDesc());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                loadDialog.dismiss();
                ToastUtils.showShortToast(t.getMessage());
            }
        });
    }

    private void showViewandserListener() {
        account_userName.setText(userName);
        account_userAddress.setText(userAddress);
        account_userPhone.setText(userphone);
        account_userMoney.setText(userMoney + " 元");


    }

    private void setListener() {
        account_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        account_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditDialog editDialog = new EditDialog(AccountActivity.this);
                editDialog.setOnCancelButtoninterface("取消", new EditDialog.onCancelButtoninterface() {
                    @Override
                    public void cancel() {
                        editDialog.dismiss();
                    }
                });
                editDialog.setOnNextButtoninterface("确定", new EditDialog.onNextButtoninterface() {
                    @Override
                    public void call(String name, String address) {

                        editDialog.dismiss();
                        loadDialog.show();

                        HttpRequest upInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        upInfoRequest.postIdandInfotoUpdataUserInfo(userId,name,address).enqueue(new Callback<RegisterResultBean>() {
                            @Override
                            public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                                loadDialog.dismiss();
                                RegisterResultBean body = response.body();
                                if (body != null){
                                    if (body.getStatusCode().equals("200")){
                                        ToastUtils.showShortToast("修改成功");
                                        getData();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterResultBean> call, Throwable t) {
                                loadDialog.dismiss();
                                ToastUtils.showShortToast(t.getMessage());
                            }
                        });
                    }
                });
                editDialog.show();
            }
        });
    }

    private void initView() {
        account_back = (ImageView) findViewById(R.id.account_back);
        account_edit = (TextView) findViewById(R.id.account_edit);

        account_userName = (TextView) findViewById(R.id.account_userName);
        account_userAddress = (TextView) findViewById(R.id.account_userAddress);
        account_userPhone = (TextView) findViewById(R.id.account_userPhone);
        account_userMoney = (TextView) findViewById(R.id.account_userMoney);

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("加载中");
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
//        userId = 5;
    }
}
