package android.app.wolf.peoplehousehold.view.myview;

import android.app.Dialog;
import android.app.wolf.peoplehousehold.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lh on 2017/11/3.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class AllPayDialog extends DialogFragment {

    private ImageView allpaydialog_closeBtn;
    private CheckBox allpaydialog_aliPay;
    private CheckBox allpaydialog_wechatPay;
    private CheckBox allpaydialog_yuePay;
    private TextView allpaydialog_money;
    private TextView allpaydialog_yueText;
    private float money;
    private Button allpaydialog_pay;
    private SharedPreferences sharedPreferences;

    private OnPayinterface payinterface;
    private int payType = 0;

    public interface OnPayinterface{
        void pay(int type);
    }

    public AllPayDialog setOnPayListener(OnPayinterface payinterface){
        this.payinterface = payinterface;
        return this;
    }

    public AllPayDialog setMoney(float money){
        this.money = money;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("peoplehousehold", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_allpay);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 5 / 10;
        window.setAttributes(lp);
        allpaydialog_closeBtn = (ImageView) dialog.findViewById(R.id.allpaydialog_closeBtn);
        allpaydialog_aliPay = (CheckBox) dialog.findViewById(R.id.allpaydialog_aliPay);
        allpaydialog_wechatPay = (CheckBox) dialog.findViewById(R.id.allpaydialog_wechatPay);
        allpaydialog_yuePay = (CheckBox) dialog.findViewById(R.id.allpaydialog_yuePay);
        allpaydialog_money = (TextView) dialog.findViewById(R.id.allpaydialog_money);
        allpaydialog_yueText = (TextView) dialog.findViewById(R.id.allpaydialog_yueText);
        allpaydialog_pay = (Button) dialog.findViewById(R.id.allpaydialog_pay);

        allpaydialog_money.setText(money+"元");

        allpaydialog_aliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    allpaydialog_wechatPay.setChecked(false);
                    allpaydialog_yuePay.setChecked(false);
                }
            }
        });

        allpaydialog_wechatPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    allpaydialog_aliPay.setChecked(false);
                    allpaydialog_yuePay.setChecked(false);
                }
            }
        });

        allpaydialog_yuePay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    allpaydialog_aliPay.setChecked(false);
                    allpaydialog_wechatPay.setChecked(false);
                }
            }
        });

        if (sharedPreferences.getFloat("userMoney",0) < money){
            allpaydialog_yueText.setText("账户余额不足");
            allpaydialog_yueText.setTextColor(getResources().getColor(R.color.app_texthui));
            allpaydialog_yuePay.setClickable(false);
        }

        allpaydialog_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payinterface != null){
                    if (allpaydialog_aliPay.isChecked()){
                        payType = 1;
                    }else if (allpaydialog_wechatPay.isChecked()){
                        payType = 2;
                    }else if (allpaydialog_yuePay.isChecked()){
                        payType = 3;
                    }else {
                        payType = 0;
                    }
                    payinterface.pay(payType);
                }
            }
        });

        allpaydialog_closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;

    }


}
