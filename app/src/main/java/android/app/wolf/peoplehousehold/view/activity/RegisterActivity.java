package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.RegisterCodeBean;
import android.app.wolf.peoplehousehold.http.bean.RegisterResultBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView register_back;
    private EditText register_editPhone;
    private EditText register_editCode;
    private TextView register_getCode;
    private EditText register_editPass;
    private CheckBox register_check;
    private TextView register_check_text;
    private Button register_btnRegister;
    private ProgressDialog registerDialog;

    String phoneNumber = "";
    String code = "";
    String password = "";
    int time = 60;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    register_getCode.setClickable(false);
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            while (time > 0){
                                handler.sendEmptyMessage(2);
                                time --;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            time = 60;
                            handler.sendEmptyMessage(1);
                        }
                    }.start();
                    break;
                case 1:
                    register_getCode.setClickable(true);
                    register_getCode.setText("重新发送");
                    break;
                case 2:
                    register_getCode.setText(time + " 秒后重试");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        setListener();
    }

    private void setListener() {
        register_back.setOnClickListener(this);
        register_getCode.setOnClickListener(this);
        register_check_text.setOnClickListener(this);
        register_btnRegister.setOnClickListener(this);
    }

    private void initView() {
        register_back = (ImageView) findViewById(R.id.register_back);
        register_editPhone = (EditText) findViewById(R.id.register_editPhone);
        register_editCode = (EditText) findViewById(R.id.register_editCode);
        register_getCode = (TextView) findViewById(R.id.register_getCode);
        register_editPass = (EditText) findViewById(R.id.register_editPass);
        register_check = (CheckBox) findViewById(R.id.register_check);
        register_check_text = (TextView) findViewById(R.id.register_check_text);
        register_btnRegister = (Button) findViewById(R.id.register_btnRegister);

        registerDialog = new ProgressDialog(this);
        registerDialog.setMessage("注册中");
        registerDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_back:
                finish();
                break;
            case R.id.register_getCode:
                if (phoneNumberisRight()){
                    HttpRequest getCodeRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                    getCodeRequest.postTeletoGetRegisterCode(phoneNumber).enqueue(new Callback<RegisterCodeBean>() {
                        @Override
                        public void onResponse(Call<RegisterCodeBean> call, Response<RegisterCodeBean> response) {
                            RegisterCodeBean body = response.body();
                            if (body != null){
                                if (body.getStatusCode().equals("200")){
                                    ToastUtils.showShortToast(body.getStatusDesc());
                                    handler.sendEmptyMessage(0);
                                }else {
                                    ToastUtils.showShortToast(body.getStatusDesc());
                                    handler.sendEmptyMessage(1);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterCodeBean> call, Throwable t) {
                            ToastUtils.showShortToast(t.getMessage());
                            handler.sendEmptyMessage(1);
                        }
                    });
                }
                break;
            case R.id.register_check_text:
                break;
            case R.id.register_btnRegister:
                if (editisRight()){

                    if (checkIsChoice()){

                        registerDialog.show();

                        HttpRequest registerRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        registerRequest.postTeleandPasstoRegister(phoneNumber,password,code).enqueue(new Callback<RegisterResultBean>() {
                            @Override
                            public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                                registerDialog.dismiss();
                                RegisterResultBean body = response.body();
                                if (body != null){
                                    if (body.getStatusCode().equals("200")){
                                        ToastUtils.showShortToast(body.getStatusDesc());
                                        finish();
                                    }else {
                                        ToastUtils.showShortToast(body.getStatusDesc());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterResultBean> call, Throwable t) {
                                registerDialog.dismiss();
                                ToastUtils.showShortToast(t.getMessage());
                            }
                        });

                    }

                }
                break;
        }
    }

    public boolean phoneNumberisRight(){
        phoneNumber = register_editPhone.getText().toString();
        if (phoneNumber.isEmpty()){
            ToastUtils.showShortToast("请输入手机号码");
            return false;
        }else if (phoneNumber.length() != 11){
            ToastUtils.showShortToast("请输入正确的手机号码");
            return false;
        }else {
            return true;
        }
    }

    public boolean editisRight(){
        phoneNumber = register_editPhone.getText().toString();
        code = register_editCode.getText().toString();
        password = register_editPass.getText().toString();

        if (phoneNumber.isEmpty()){
            ToastUtils.showShortToast("请输入手机号码");
            return false;
        }else if (phoneNumber.length() != 11){
            ToastUtils.showShortToast("请输入正确的手机号码");
            return false;
        }else if (code.isEmpty()){
            ToastUtils.showShortToast("请输入验证码");
            return false;
        }else if (password.isEmpty()){
            ToastUtils.showShortToast("请输入密码");
            return false;
        }else {
            return true;
        }
    }

    private boolean checkIsChoice(){
        boolean checked = register_check.isChecked();
        if (checked){
            return true;
        }else {
            ToastUtils.showShortToast("请同意家菲猫用户协议");
            return false;
        }
    }

}
