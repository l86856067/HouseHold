package android.app.wolf.peoplehousehold.view.activity;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.UserInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isLogin = false;
    private int userId = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initData();

        judgeStateToNext();
    }

    private void judgeStateToNext() {

        if (isLogin){

            userId = sharedPreferences.getInt("userId",0);
            HttpRequest getInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
            getInfoRequest.postUserIdtoGetUserInfo(userId).enqueue(new Callback<UserInfoBean>() {
                @Override
                public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                    UserInfoBean body = response.body();
                    if (body != null){
                        if (body.getStatusCode().equals("200")){
                            UserInfoBean.DataBean data = body.getData();

                            editor.putInt("userId",data.getId());
                            editor.putString("userName",data.getUsername());
                            editor.putString("userAddress",data.getAddress());
                            editor.putString("userTele",data.getLoginTele());
                            editor.putFloat("userMoney", data.getAvailMoney());
                            editor.putString("userIcon",data.getUserIcon());

                            editor.commit();

                            startActivity(new Intent(StartActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserInfoBean> call, Throwable t) {
                    ToastUtils.showShortToast(t.getMessage());
                }
            });

        }else {

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }

    }

    private void initData() {
        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isLogin = sharedPreferences.getBoolean("isLogin",false);
    }

}
