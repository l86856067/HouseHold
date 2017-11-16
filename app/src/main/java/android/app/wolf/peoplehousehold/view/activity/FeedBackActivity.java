package android.app.wolf.peoplehousehold.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.RegisterResultBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.myview.CallDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView feedback_btnBack;
    private LinearLayout feedback_contact;
    private LinearLayout feedback_opinion;
    private LinearLayout feedback_editLayout;
    private EditText feedback_edit;
    private Button feedback_commit;

    SharedPreferences sharedPreferences;
    int userId = 0;
    private String feedbackStr = "";
    ProgressDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        initView();

        setListener();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feedback_btnBack:
                finish();
                break;
            case R.id.feedback_contact:
                final CallDialog dialog = new CallDialog(this);
                dialog.setMessage("400-5558-8454");
                dialog.setOnCancelButtoninterface("取消", new CallDialog.onCancelButtoninterface() {
                    @Override
                    public void cancel() {
                        ToastUtils.showShortToast("取消");
                        dialog.dismiss();
                    }
                });
                dialog.setOnNextButtoninterface("拨打", new CallDialog.onNextButtoninterface() {
                    @Override
                    public void call() {
                        ToastUtils.showShortToast("拨打");
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.feedback_opinion:
                feedback_editLayout.setVisibility(View.VISIBLE);
                feedback_commit.setVisibility(View.VISIBLE);
                break;
            case R.id.feedback_commit:
                feedbackStr = feedback_edit.getText().toString();
                loadDialog.show();

                HttpRequest feedbackRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                feedbackRequest.postIdandInfotoFeedback(userId,feedbackStr).enqueue(new Callback<RegisterResultBean>() {
                    @Override
                    public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                        loadDialog.dismiss();
                        RegisterResultBean body = response.body();
                        if (body != null){
                            if (body.getStatusCode().equals("200")){
                                ToastUtils.showShortToast(body.getStatusDesc());
                                feedback_edit.setText("");
                            }else {
                                ToastUtils.showShortToast(body.getStatusDesc());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResultBean> call, Throwable t) {
                        loadDialog.dismiss();
                        ToastUtils.showShortToast(t.getMessage());
                    }
                });
                break;
        }
    }

    private void setListener() {
        feedback_btnBack.setOnClickListener(this);
        feedback_contact.setOnClickListener(this);
        feedback_opinion .setOnClickListener(this);
        feedback_commit.setOnClickListener(this);
    }

    private void initView() {
        feedback_btnBack = (ImageView) findViewById(R.id.feedback_btnBack);
        feedback_contact = (LinearLayout) findViewById(R.id.feedback_contact);
        feedback_opinion = (LinearLayout) findViewById(R.id.feedback_opinion);
        feedback_editLayout = (LinearLayout) findViewById(R.id.feedback_editLayout);
        feedback_edit = (EditText) findViewById(R.id.feedback_edit);
        feedback_commit = (Button) findViewById(R.id.feedback_commit);

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("反馈中");
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
    }

}
