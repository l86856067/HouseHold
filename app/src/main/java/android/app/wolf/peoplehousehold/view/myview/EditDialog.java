package android.app.wolf.peoplehousehold.view.myview;

import android.app.Dialog;
import android.app.wolf.peoplehousehold.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by lh on 2017/10/14.
 * <p>
 * 功能作用：联系人的Dialog
 * <p>
 * 修改记录：
 */
public class EditDialog extends Dialog {

    private Context context;
    private String cancleText;
    private String nextText;
    private EditText userName;
    private EditText userAddress;
    private onCancelButtoninterface onCancelButtoninterface;
    private onNextButtoninterface onNextButtoninterface;

    public interface onCancelButtoninterface{
        void cancel();
    }

    public interface onNextButtoninterface{
        void call(String name,String address);
    }

    public EditDialog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public EditDialog setOnCancelButtoninterface(String cancleText, onCancelButtoninterface onCancelButtoninterface) {
        this.cancleText = cancleText;
        this.onCancelButtoninterface = onCancelButtoninterface;
        return this;
    }

    public EditDialog setOnNextButtoninterface(String nextText, onNextButtoninterface onNextButtoninterface) {
        this.nextText = nextText;
        this.onNextButtoninterface = onNextButtoninterface;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_edit, null);
        setContentView(view);
        setCancelable(false);

        TextView dialog_edit_cancelBtn = (TextView) view.findViewById(R.id.dialog_edit_cancelBtn);
        TextView dialog_edit_callBtn = (TextView) view.findViewById(R.id.dialog_edit_callBtn);
        userName = (EditText) view.findViewById(R.id.dialog_edit_userName);
        userAddress = (EditText) view.findViewById(R.id.dialog_edit_userAddress);


        dialog_edit_cancelBtn.setOnClickListener(new CallDialogListener());
        dialog_edit_callBtn.setOnClickListener(new CallDialogListener());


    }

    private class CallDialogListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_edit_cancelBtn:
                    onBackPressed();
                    if (onCancelButtoninterface != null){
                        onCancelButtoninterface.cancel();
                    }
                    break;
                case R.id.dialog_edit_callBtn:
                    onBackPressed();
                    if (onNextButtoninterface != null){
                        onNextButtoninterface.call(userName.getText().toString(),userAddress.getText().toString());
                    }
                    break;
            }
        }
    }

}
