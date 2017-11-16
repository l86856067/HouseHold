package android.app.wolf.peoplehousehold.view.fragment;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.view.activity.AboutActivity;
import android.app.wolf.peoplehousehold.view.activity.AccountActivity;
import android.app.wolf.peoplehousehold.view.activity.LoginActivity;
import android.app.wolf.peoplehousehold.view.activity.PackageActivity;
import android.app.wolf.peoplehousehold.view.activity.RechargeActivity;
import android.app.wolf.peoplehousehold.view.myview.CallDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lh on 2017/10/20.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private ImageView userfragment_head;
    private TextView userfragment_userName;
    private RelativeLayout userfragment_account;
    private RelativeLayout userfragment_recharge;
    private RelativeLayout userfragment_package;
    private RelativeLayout userfragment_service;
    private RelativeLayout userfragment_about;
    private RelativeLayout userfragment_inspect;
    private Button userfragment_unlogin;

    SharedPreferences sharedPreferences;
    String userName = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user,null);

        initView(view);

        loadView();

        setListener();

        EventBus.getDefault().register(this);

        return view;
    }

    private void loadView() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userfragment_account:
                Intent intent1 = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.userfragment_recharge:
                Intent intent2 = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent2);
                break;
            case R.id.userfragment_package:
                Intent intent3 = new Intent(getActivity(), PackageActivity.class);
                startActivity(intent3);
                break;
            case R.id.userfragment_service:
                final CallDialog callDialog = new CallDialog(getActivity());
                callDialog.setMessage("400-2222-3333");
                callDialog.setOnCancelButtoninterface("取消", new CallDialog.onCancelButtoninterface() {
                    @Override
                    public void cancel() {
                        callDialog.dismiss();
                    }
                });
                callDialog.setOnNextButtoninterface("拨打", new CallDialog.onNextButtoninterface() {
                    @Override
                    public void call() {
                        callDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"10086"));
                        startActivity(intent);
                    }
                });
                callDialog.show();
                break;
            case R.id.userfragment_about:
                Intent intent5 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent5);
                break;
            case R.id.userfragment_inspect:
                break;
            case R.id.userfragment_unlogin:
                sharedPreferences.edit().putBoolean("isLogin",false).commit();
                Intent intent7 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent7);
                getActivity().finish();
                break;
        }
    }

    private void setListener() {
        userfragment_account.setOnClickListener(this);
        userfragment_recharge.setOnClickListener(this);
        userfragment_package.setOnClickListener(this);
        userfragment_service.setOnClickListener(this);
        userfragment_about.setOnClickListener(this);
        userfragment_inspect.setOnClickListener(this);
        userfragment_unlogin.setOnClickListener(this);
    }

    private void initView(View view) {
        userfragment_head = (ImageView) view.findViewById(R.id.userfragment_head);
        userfragment_userName = (TextView) view.findViewById(R.id.userfragment_userName);

        userfragment_account = (RelativeLayout) view.findViewById(R.id.userfragment_account);
        userfragment_recharge = (RelativeLayout) view.findViewById(R.id.userfragment_recharge);
        userfragment_package = (RelativeLayout) view.findViewById(R.id.userfragment_package);
        userfragment_service = (RelativeLayout) view.findViewById(R.id.userfragment_service);
        userfragment_about = (RelativeLayout) view.findViewById(R.id.userfragment_about);
        userfragment_inspect = (RelativeLayout) view.findViewById(R.id.userfragment_inspect);
        userfragment_unlogin = (Button) view.findViewById(R.id.userfragment_unlogin);

        sharedPreferences = getActivity().getSharedPreferences("peoplehousehold", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName","");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void flahView(String str){
        Log.d("userfragment",str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
