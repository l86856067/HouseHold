package android.app.wolf.peoplehousehold.view.activity;

import android.Manifest;
import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.http.bean.ServiceingInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.FragmentUtils;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.utils.ToastUtils;
import android.app.wolf.peoplehousehold.view.fragment.HomeFragment;
import android.app.wolf.peoplehousehold.view.fragment.OrderFragment;
import android.app.wolf.peoplehousehold.view.fragment.UserFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout main_btnHome;
    private RelativeLayout main_btnOrder;
    private RelativeLayout main_btnUser;

    private ImageView main_btnHome_image;
    private ImageView main_btnOrder_image;
    private ImageView main_btnUser_image;
    private TextView main_btnHome_text;
    private TextView main_btnOrder_text;
    private TextView main_btnUser_text;

    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private UserFragment userFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private SharedPreferences sharedPreferences;
    private int userId = 0;
    private int page = 1;
    private int rows = 15;
    private int total = 0;
    private int orderId = 0;

    String[] persission = new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getPermission();

        setListener();

    }

    private void getPermission() {
        List<String> mpersission = new ArrayList<>();

        for (int i = 0 ; i < persission.length ; i ++){
            if (ContextCompat.checkSelfPermission(this, persission[i]) != PackageManager.PERMISSION_GRANTED){
                mpersission.add(persission[i]);
            }
        }
        if (!mpersission.isEmpty()){
            String[] needPersisssion = mpersission.toArray(new String[mpersission.size()]);
            ActivityCompat.requestPermissions(this,needPersisssion,1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btnHome:

                main_btnHome_image.setImageResource(R.drawable.main_homeyes);
                main_btnOrder_image.setImageResource(R.drawable.main_orderno);
                main_btnUser_image.setImageResource(R.drawable.main_userno);
                main_btnHome_text.setTextColor(getResources().getColor(R.color.app_theme));
                main_btnOrder_text.setTextColor(getResources().getColor(R.color.app_black));
                main_btnUser_text.setTextColor(getResources().getColor(R.color.app_black));

                FragmentUtils.judgeToShow(fragmentManager,homeFragment);
                break;
            case R.id.main_btnOrder:

                main_btnHome_image.setImageResource(R.drawable.main_homeno);
                main_btnOrder_image.setImageResource(R.drawable.main_orderyes);
                main_btnUser_image.setImageResource(R.drawable.main_userno);
                main_btnHome_text.setTextColor(getResources().getColor(R.color.app_black));
                main_btnOrder_text.setTextColor(getResources().getColor(R.color.app_theme));
                main_btnUser_text.setTextColor(getResources().getColor(R.color.app_black));

                FragmentUtils.judgeToShow(fragmentManager,orderFragment);
                break;
            case R.id.main_btnUser:

                main_btnHome_image.setImageResource(R.drawable.main_homeno);
                main_btnOrder_image.setImageResource(R.drawable.main_orderno);
                main_btnUser_image.setImageResource(R.drawable.main_useryes);
                main_btnHome_text.setTextColor(getResources().getColor(R.color.app_black));
                main_btnOrder_text.setTextColor(getResources().getColor(R.color.app_black));
                main_btnUser_text.setTextColor(getResources().getColor(R.color.app_theme));

                FragmentUtils.judgeToShow(fragmentManager,userFragment);
                break;
        }
    }


    private void setListener() {

        main_btnHome.setOnClickListener(this);
        main_btnOrder.setOnClickListener(this);
        main_btnUser.setOnClickListener(this);

    }



    private void initView() {

        main_btnHome = (RelativeLayout) findViewById(R.id.main_btnHome);
        main_btnOrder = (RelativeLayout) findViewById(R.id.main_btnOrder);
        main_btnUser = (RelativeLayout) findViewById(R.id.main_btnUser);

        main_btnHome_image = (ImageView) findViewById(R.id.main_btnHome_image);
        main_btnOrder_image = (ImageView) findViewById(R.id.main_btnOrder_image);
        main_btnUser_image = (ImageView) findViewById(R.id.main_btnUser_image);
        main_btnHome_text = (TextView) findViewById(R.id.main_btnHome_text);
        main_btnOrder_text = (TextView) findViewById(R.id.main_btnOrder_text);
        main_btnUser_text = (TextView) findViewById(R.id.main_btnUser_text);

        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        userFragment = new UserFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_fragmentlayout,homeFragment)
                .add(R.id.main_fragmentlayout,orderFragment).hide(orderFragment)
                .add(R.id.main_fragmentlayout,userFragment).hide(userFragment)
                .commit();

        sharedPreferences = getSharedPreferences("peoplehousehold",MODE_PRIVATE);

    }

}
