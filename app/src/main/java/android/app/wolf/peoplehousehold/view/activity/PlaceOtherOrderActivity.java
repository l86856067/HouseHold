package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.PlaceOtherOrderBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.NoticeDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOtherOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView placeother_btnBack;
    private EditText placeother_edit;
    private Button placeother_btnCommit;

    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    int userId = 0;
    int serviceItemId = 6;
    String serviceDesc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_other_order);

        initView();

        setListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.placeother_btnBack:
                finish();
                break;
            case R.id.placeother_btnCommit:
                    if (editTextIsRight()){

                        dialog.show();

                        HttpRequest placeOtherRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        placeOtherRequest.postIdandOtherInfotoPlace(userId,serviceDesc,serviceItemId).enqueue(new Callback<PlaceOtherOrderBean>() {
                            @Override
                            public void onResponse(Call<PlaceOtherOrderBean> call, Response<PlaceOtherOrderBean> response) {
                                dialog.dismiss();
                                PlaceOtherOrderBean body = response.body();
                                if (body != null){
                                    if (body.getStatusCode().equals("200")){
                                        placeother_edit.setText("");

                                        final NoticeDialog noticeDialog = new NoticeDialog(PlaceOtherOrderActivity.this);
                                        noticeDialog.setMessage("下单成功，我们会尽快为您安排处理，请稍候！");
                                        noticeDialog.setPostButton("知道了", new NoticeDialog.OnpostButtonInterface() {
                                            @Override
                                            public void post() {
                                                noticeDialog.dismiss();
                                            }
                                        });
                                        noticeDialog.show();

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PlaceOtherOrderBean> call, Throwable t) {
                                dialog.dismiss();
                                ToastUtils.showShortToast(t.getMessage());
                            }
                        });

                    }
                break;
        }
    }

    private void setListener() {
        placeother_btnBack.setOnClickListener(this);
        placeother_btnCommit.setOnClickListener(this);
    }

    private void initView() {
        placeother_btnBack = (ImageView) findViewById(R.id.placeother_btnBack);
        placeother_edit = (EditText) findViewById(R.id.placeother_edit);
        placeother_btnCommit = (Button) findViewById(R.id.placeother_btnCommit);

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在下单");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);

    }

    public boolean editTextIsRight(){
        serviceDesc = placeother_edit.getText().toString();
        if (serviceDesc.isEmpty()){
            ToastUtils.showShortToast("请输入您的需求");
            return false;
        }else {
            return true;
        }
    }

}
