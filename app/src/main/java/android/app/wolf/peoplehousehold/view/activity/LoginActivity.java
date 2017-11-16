package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.LoginResultBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login_edit_username;
    private EditText login_edit_password;
    private TextView login_textRegister;
    private TextView login_forget;
    private Button login_btnLogin;
    private ProgressDialog loginDialog;

    private String tele = "";         // 电话号，用户登录时候的用户名
    private String password = "";     // 密码，用户登录时候的密码

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        setListener();
    }

    private void setListener() {
        login_textRegister.setOnClickListener(this);
        login_forget.setOnClickListener(this);
        login_btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_textRegister:
                Intent intent1 = new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.login_forget:
                break;
            case R.id.login_btnLogin:
                tologin();
                break;
        }
    }

    /*
    *  点击登录按钮时调用的方法
    * */
    private void tologin() {
        if (teleIsRight()){

            loginDialog.show();
            HttpRequest loginRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
            loginRequest.postTeleandPasstoLogin(tele,password).enqueue(new Callback<LoginResultBean>() {
                @Override
                public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                    loginDialog.dismiss();
                    LoginResultBean body = response.body();
                    if (body != null){
                        if (body.getStatusCode().equals("200")){
                            LoginResultBean.DataBean data = body.getData();
                            editor.putInt("userId",data.getId());
                            editor.putString("userName",data.getUsername());
                            editor.putString("userAddress",data.getAddress());
                            editor.putString("userTele",data.getLoginTele());
                            editor.putFloat("userMoney", data.getAvailMoney());
                            editor.putString("userIcon",data.getUserIcon());
                            editor.putBoolean("isLogin",true);
                            editor.commit();

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtils.showShortToast(body.getStatusDesc());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResultBean> call, Throwable t) {
                    loginDialog.dismiss();
                    ToastUtils.showShortToast(t.getMessage());
                }
            });

        }
    }

    private boolean teleIsRight() {
        tele = login_edit_username.getText().toString();
        password = login_edit_password.getText().toString();

        if (tele.isEmpty()){
            ToastUtils.showShortToast("请输入手机号");
            return false;
        }else if (password.isEmpty()){

            ToastUtils.showShortToast("请输入密码");
            return false;
        }else if (tele.length() != 11){
            ToastUtils.showShortToast("手机号输入有误");
            return false;
        }else {
            return true;
        }
    }


    private void initView() {
        login_edit_username = (EditText) findViewById(R.id.login_edit_username);
        login_edit_password = (EditText) findViewById(R.id.login_edit_password);
        login_textRegister = (TextView) findViewById(R.id.login_textRegister);
        login_forget = (TextView) findViewById(R.id.login_forget);
        login_btnLogin = (Button) findViewById(R.id.login_btnLogin);
        loginDialog = new ProgressDialog(this);
        loginDialog.setMessage("登录中");
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

}
