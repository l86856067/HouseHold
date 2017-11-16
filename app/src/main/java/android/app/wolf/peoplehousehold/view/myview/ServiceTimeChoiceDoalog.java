package android.app.wolf.peoplehousehold.view.myview;

import android.app.Dialog;
import android.app.wolf.peoplehousehold.R;
import android.content.Context;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by lh on 2017/10/20.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class ServiceTimeChoiceDoalog extends Dialog {

    private Context context;
    private String cancelText;
    private String confirmText;

    String[] dateTime = {getDataString(0),getDataString(1),getDataString(2),getDataString(3),getDataString(4),getDataString(5),getDataString(6),};
    private NumberPicker dialog_servicetime_yearPick;
    private NumberPicker dialog_servicetime_hourPick;

    private OnCancelinterface onCancelinterface;
    private OnConfirminterface onConfirminterface;

    public interface OnCancelinterface{
        void cancel();
    }

    public interface OnConfirminterface{
        void confirm(String date);
    }


    public ServiceTimeChoiceDoalog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public ServiceTimeChoiceDoalog setOnCancelListener(String cancelText, OnCancelinterface onCancelinterface){
        this.cancelText = cancelText;
        this.onCancelinterface = onCancelinterface;
        return this;
    }

    public ServiceTimeChoiceDoalog setOnConfirmListener(String confirmText, OnConfirminterface onConfirminterface){
        this.confirmText = confirmText;
        this.onConfirminterface = onConfirminterface;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_servicetimechoice, null);
        setContentView(view);
        setCancelable(false);

        TextView dialog_time_btnCancel = (TextView) view.findViewById(R.id.dialog_servicetime_btnCancel);
        TextView dialog_time_btnConfim = (TextView) view.findViewById(R.id.dialog_servicetime_btnConfim);

        dialog_time_btnCancel.setText(cancelText);
        dialog_time_btnConfim.setText(confirmText);


        dialog_servicetime_yearPick = (NumberPicker) view.findViewById(R.id.dialog_servicetime_yearPick);
        dialog_servicetime_hourPick = (NumberPicker) view.findViewById(R.id.dialog_servicetime_hourPick);

        dialog_servicetime_yearPick.setDisplayedValues(dateTime);
        dialog_servicetime_yearPick.setMinValue(0);
        dialog_servicetime_yearPick.setMaxValue(dateTime.length - 1);
        dialog_servicetime_yearPick.setWrapSelectorWheel(false);

        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        final int hour = t.hour;

        if (hour > 19){

        }else{
            dialog_servicetime_hourPick.setMaxValue(20);
            dialog_servicetime_hourPick.setMinValue(hour+1);
            dialog_servicetime_hourPick.setValue(hour+1);
            dialog_servicetime_hourPick.setWrapSelectorWheel(false);
        }

        dialog_servicetime_yearPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 0){
                    if (hour > 19){

                    }else{
                        dialog_servicetime_hourPick.setMaxValue(20);
                        dialog_servicetime_hourPick.setMinValue(hour+1);
                        dialog_servicetime_hourPick.setValue(hour+1);
                        dialog_servicetime_hourPick.setWrapSelectorWheel(false);
                    }
                }else {
                    dialog_servicetime_hourPick.setMaxValue(20);
                    dialog_servicetime_hourPick.setMinValue(8);
                    dialog_servicetime_hourPick.setValue(8);
                    dialog_servicetime_hourPick.setWrapSelectorWheel(false);
                }
            }
        });



        dialog_time_btnCancel.setOnClickListener(new timepickerListener());
        dialog_time_btnConfim.setOnClickListener(new timepickerListener());

    }

    private class timepickerListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_servicetime_btnCancel:
                    onBackPressed();
                    if (onCancelinterface != null){
                        onCancelinterface.cancel();
                    }
                    break;
                case R.id.dialog_servicetime_btnConfim:
                    onBackPressed();
                    if (onConfirminterface != null){
                        onConfirminterface.confirm(dateTime[dialog_servicetime_yearPick.getValue()]
                                +" "+dialog_servicetime_hourPick.getValue()+":00");
                    }
                    break;
            }
        }
    }

    private String getDataString(int days){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay+days); // 延后3天
        int mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if(mNowDay>mNextDay){
            mMonth+=1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if(mMonth>12){
            mMonth = 1;
            mYear +=1 ;
        }
        String data = mYear+"-"+mMonth+"-"+mNextDay;
        return data;
    }

}
