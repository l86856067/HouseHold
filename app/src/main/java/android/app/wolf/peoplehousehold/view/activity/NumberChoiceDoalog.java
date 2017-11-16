package android.app.wolf.peoplehousehold.view.activity;

import android.app.Dialog;
import android.app.wolf.peoplehousehold.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by lh on 2017/10/20.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class NumberChoiceDoalog extends Dialog {

    private Context context;
    private String cancelText;
    private String confirmText;
    private int minVal = 0;
    private int maxVal = 0;
    private int val = 0;

    private NumberPicker dialog_number_picker;

    private OnCancelinterface onCancelinterface;
    private OnConfirminterface onConfirminterface;

    public interface OnCancelinterface{
        void cancel();
    }

    public interface OnConfirminterface{
        void confirm(int date);
    }


    public NumberChoiceDoalog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public NumberChoiceDoalog setOnCancelListener(String cancelText, OnCancelinterface onCancelinterface){
        this.cancelText = cancelText;
        this.onCancelinterface = onCancelinterface;
        return this;
    }

    public NumberChoiceDoalog setOnConfirmListener(String confirmText, OnConfirminterface onConfirminterface){
        this.confirmText = confirmText;
        this.onConfirminterface = onConfirminterface;
        return this;
    }

    public NumberChoiceDoalog setVal(int minVal,int maxVal,int val){
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.val = val;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_numberchoice, null);
        setContentView(view);
        setCancelable(false);

        TextView dialog_time_btnCancel = (TextView) view.findViewById(R.id.dialog_number_btnCancel);
        TextView dialog_time_btnConfim = (TextView) view.findViewById(R.id.dialog_number_btnConfim);

        dialog_time_btnCancel.setText(cancelText);
        dialog_time_btnConfim.setText(confirmText);


        dialog_number_picker = (NumberPicker) view.findViewById(R.id.dialog_number_picker);

        dialog_number_picker.setMinValue(minVal);
        dialog_number_picker.setMaxValue(maxVal);
        dialog_number_picker.setValue(val);
        dialog_number_picker.setWrapSelectorWheel(false);

        dialog_time_btnCancel.setOnClickListener(new timepickerListener());
        dialog_time_btnConfim.setOnClickListener(new timepickerListener());

    }

    private class timepickerListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_number_btnCancel:
                    onBackPressed();
                    if (onCancelinterface != null){
                        onCancelinterface.cancel();
                    }
                    break;
                case R.id.dialog_number_btnConfim:
                    onBackPressed();
                    if (onConfirminterface != null){
                        onConfirminterface.confirm(dialog_number_picker.getValue());
                    }
                    break;
            }
        }
    }

}
