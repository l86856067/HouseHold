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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView account_back;
    private TextView account_edit;

    private EditText account_userName;
    private ImageView account_nameImage;
    private EditText account_userAddre;
    private ImageView account_addreImage;
    private TextView account_userPhone;

    private Button account_commit;

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
        account_userAddre.setText(userAddress);
        account_userPhone.setText(userphone);


    }

    private void setListener() {
        account_back.setOnClickListener(this);
        account_nameImage.setOnClickListener(this);
        account_addreImage.setOnClickListener(this);
        account_commit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_back:
                finish();
                break;
            case R.id.account_nameImage:

                account_commit.setVisibility(View.VISIBLE);
                account_userName.setFocusableInTouchMode(true);
                account_userName.setFocusable(true);
                account_userName.setSelection(account_userName.getText().toString().length());

                break;
            case R.id.account_addreImage:

                account_commit.setVisibility(View.VISIBLE);
                account_userAddre.setFocusableInTouchMode(true);
                account_userAddre.setFocusable(true);
                account_userAddre.setSelection(account_userAddre.getText().toString().length());

                break;
            case R.id.account_commit:

                if (infoIsRight()){
                    toCommitInfo();
                }

                break;
        }
    }

    private void toCommitInfo(){
        loadDialog.show();

        HttpRequest upInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        upInfoRequest.postIdandInfotoUpdataUserInfo(userId,userName,userAddress).enqueue(new Callback<RegisterResultBean>() {
            @Override
            public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                loadDialog.dismiss();
                RegisterResultBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        ToastUtils.showShortToast("修改成功");
                        account_userName.setFocusable(false);
                        account_userAddre.setFocusable(false);
                        account_userName.setFocusableInTouchMode(false);
                        account_userAddre.setFocusableInTouchMode(false);
                        account_commit.setVisibility(View.GONE);
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

    private boolean infoIsRight(){

        userName = account_userName.getText().toString();
        userAddress = account_userAddre.getText().toString();

        if (userName.isEmpty()){
            ToastUtils.showShortToast("姓名不能为空");
            return false;
        }else if (userAddress.isEmpty()){
            ToastUtils.showShortToast("姓名不能为空");
            return false;
        }else {
            return true;
        }

    }

    private void initView() {
        account_back = (ImageView) findViewById(R.id.account_back);

        account_userName = (EditText) findViewById(R.id.account_userName);
        account_nameImage = (ImageView) findViewById(R.id.account_nameImage);
        account_userAddre = (EditText) findViewById(R.id.account_userAddre);
        account_addreImage = (ImageView) findViewById(R.id.account_addreImage);
        account_userPhone = (TextView) findViewById(R.id.account_userPhone);

        account_commit = (Button) findViewById(R.id.account_commit);

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("加载中");
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
    }

}
