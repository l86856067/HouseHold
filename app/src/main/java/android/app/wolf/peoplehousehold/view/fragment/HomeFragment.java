package android.app.wolf.peoplehousehold.view.fragment;

import android.app.wolf.peoplehousehold.R;
import android.app.wolf.peoplehousehold.adapter.HomeViewPagerAdapter;
import android.app.wolf.peoplehousehold.http.bean.ServiceingInfoBean;
import android.app.wolf.peoplehousehold.http.httpinterface.HttpRequest;
import android.app.wolf.peoplehousehold.utils.RetrofitUtils;
import android.app.wolf.peoplehousehold.view.activity.OrderDetailsActivity;
import android.app.wolf.peoplehousehold.view.activity.OrderServiceingActivity;
import android.app.wolf.peoplehousehold.view.activity.PlaceLongOrderActivity;
import android.app.wolf.peoplehousehold.view.activity.PlaceOtherOrderActivity;
import android.app.wolf.peoplehousehold.view.activity.PlaceReclaimOrderActivity;
import android.app.wolf.peoplehousehold.view.activity.PlaceShortOrderActivity;
import android.app.wolf.peoplehousehold.view.detailsactivity.ClernDetaActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lh on 2017/10/20.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView homefragment_serviceing;
    private ViewPager homefragment_viewPager;
    private List<ImageView> list;
    HomeViewPagerAdapter adapter;

    private RelativeLayout homefragment_cleanTop;
    private RelativeLayout homefragment_hourlyTop;
    private RelativeLayout homefragment_materTop;
    private RelativeLayout homefragment_parentTop;
    private RelativeLayout homefragment_nannyTop;
    private RelativeLayout homefragment_oldTop;
    private RelativeLayout homefragment_glassTop;
    private RelativeLayout homefragment_reclaimTop;

    private SharedPreferences sharedPreferences;
    private int userId;
    private int total = 0;
    private int orderId = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,null);

        initView(view);

        loadViewPageImage();

        inspectService();

        setListener();

        return view;
    }

    private void inspectService() {


        Log.d("homefragment","检查"+total);
        HttpRequest getServiceRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getServiceRequest.postuserIdtogetServiceStatus(userId,1,15).enqueue(new Callback<ServiceingInfoBean>() {
            @Override
            public void onResponse(Call<ServiceingInfoBean> call, Response<ServiceingInfoBean> response) {
                ServiceingInfoBean body = response.body();

                if (body != null){
                    Log.d("homefragment","!null"+total);
                    if (body.getTotal() != 0){
                        Log.d("homefragment","total"+total);
                        total = body.getTotal();
                        orderId = body.getRows().get(0).getId();
                        homefragment_serviceing.setVisibility(View.VISIBLE);
                    }else {
                        Log.d("homefragment","total"+body.getTotal());
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceingInfoBean> call, Throwable t) {

            }
        });
    }

    private void loadViewPageImage() {
        ImageView imageView1 = new ImageView(getActivity());
        imageView1.setImageResource(R.drawable.home_viewpage1);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        list.add(imageView1);
        ImageView imageView2 = new ImageView(getActivity());
        imageView2.setImageResource(R.drawable.home_viewpager2);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        list.add(imageView2);
        ImageView imageView3 = new ImageView(getActivity());
        imageView3.setImageResource(R.drawable.home_viewpager3);
        imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        list.add(imageView3);
        adapter = new HomeViewPagerAdapter(list);
        homefragment_viewPager.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homefragment_serviceing:

                if (total == 1){
                    Intent orderIntent = new Intent(getActivity(),OrderDetailsActivity.class);
                    orderIntent.putExtra("orderId",orderId);
                    startActivity(orderIntent);
                }else {
                    Intent serviceIntent = new Intent(getActivity(),OrderServiceingActivity.class);
                    startActivity(serviceIntent);
                }

                break;
            case R.id.homefragment_cleanTop:

//                Intent cleanIntent = new Intent(getActivity(),PlaceShortOrderActivity.class);
//                cleanIntent.putExtra("serviceItemId",1);
//                cleanIntent.putExtra("title","临时保洁");
//                startActivity(cleanIntent);

                Intent cleanIntent = new Intent(getActivity(), ClernDetaActivity.class);
                startActivity(cleanIntent);

                break;
            case R.id.homefragment_hourlyTop:
                break;
            case R.id.homefragment_materTop:

                Intent materIntent = new Intent(getActivity(),PlaceLongOrderActivity.class);
                materIntent.putExtra("serviceItemId",4);
                materIntent.putExtra("title","月嫂");
                startActivity(materIntent);

                break;
            case R.id.homefragment_parentTop:

                Intent parentIntent = new Intent(getActivity(),PlaceLongOrderActivity.class);
                parentIntent.putExtra("serviceItemId",8);
                parentIntent.putExtra("title","育儿嫂");
                startActivity(parentIntent);

                break;
            case R.id.homefragment_nannyTop:
                break;
            case R.id.homefragment_oldTop:

                Intent oldIntent = new Intent(getActivity(),PlaceLongOrderActivity.class);
                oldIntent.putExtra("serviceItemId",7);
                oldIntent.putExtra("title","老人看护");
                startActivity(oldIntent);

                break;
            case R.id.homefragment_glassTop:
                break;
            case R.id.homefragment_reclaimTop:

                Intent reclaimIntent2 = new Intent(getActivity(),PlaceReclaimOrderActivity.class);
                reclaimIntent2.putExtra("serviceItemId",10);
                startActivity(reclaimIntent2);

                break;
        }
    }

    private void initView(View view) {
        list = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("peoplehousehold", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);

        homefragment_serviceing = (TextView) view.findViewById(R.id.homefragment_serviceing);
        homefragment_viewPager = (ViewPager) view.findViewById(R.id.homefragment_viewPager);

        homefragment_cleanTop = (RelativeLayout) view.findViewById(R.id.homefragment_cleanTop);
        homefragment_hourlyTop = (RelativeLayout) view.findViewById(R.id.homefragment_hourlyTop);
        homefragment_materTop = (RelativeLayout) view.findViewById(R.id.homefragment_materTop);
        homefragment_parentTop = (RelativeLayout) view.findViewById(R.id.homefragment_parentTop);
        homefragment_nannyTop = (RelativeLayout) view.findViewById(R.id.homefragment_nannyTop);
        homefragment_oldTop = (RelativeLayout) view.findViewById(R.id.homefragment_oldTop);
        homefragment_glassTop = (RelativeLayout) view.findViewById(R.id.homefragment_glassTop);
        homefragment_reclaimTop = (RelativeLayout) view.findViewById(R.id.homefragment_reclaimTop);

    }


    private void setListener() {
        homefragment_serviceing.setOnClickListener(this);
        homefragment_cleanTop.setOnClickListener(this);
        homefragment_hourlyTop.setOnClickListener(this);
        homefragment_materTop.setOnClickListener(this);
        homefragment_parentTop.setOnClickListener(this);
        homefragment_nannyTop.setOnClickListener(this);
        homefragment_oldTop.setOnClickListener(this);
        homefragment_glassTop.setOnClickListener(this);
        homefragment_reclaimTop.setOnClickListener(this);
    }

}
