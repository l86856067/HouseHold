package android.app.wolf.peoplehousehold.view.detailsactivity;

import android.app.wolf.peoplehousehold.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClernDetaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cleandeta_btnBack;
    private LinearLayout cleandeta_service;
    private TextView cleandeta_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clern_deta);

        initView();

        setListener();
    }

    private void setListener() {
        cleandeta_btnBack.setOnClickListener(this);
        cleandeta_service.setOnClickListener(this);
        cleandeta_place.setOnClickListener(this);
    }

    private void initView() {
        cleandeta_btnBack = (ImageView) findViewById(R.id.cleandeta_btnBack);
        cleandeta_service = (LinearLayout) findViewById(R.id.cleandeta_service);
        cleandeta_place = (TextView) findViewById(R.id.cleandeta_place);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cleandeta_btnBack:
                finish();
                break;
            case R.id.cleandeta_service:



                break;
            case R.id.cleandeta_place:



                break;
        }
    }
}
