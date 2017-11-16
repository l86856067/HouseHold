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
public class AddressEditDialog extends Dialog {

    private Context context;
    private String cancleText;
    private String nextText;

    public AddressEditDialog setName(String name) {
        this.name = name;
        return this;
    }

    public AddressEditDialog setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public AddressEditDialog setAddress(String address) {
        this.address = address;
        return this;
    }

    private String name;
    private String phone;
    private String address;
    private EditText userName;
    private EditText userPhone;
    private EditText userAddress;
    private onCancelButtoninterface onCancelButtoninterface;
    private onNextButtoninterface onNextButtoninterface;

    public interface onCancelButtoninterface{
        void cancel();
    }

    public interface onNextButtoninterface{
        void call(String name,String phone, String address);
    }

    public AddressEditDialog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public AddressEditDialog setOnCancelButtoninterface(String cancleText, onCancelButtoninterface onCancelButtoninterface) {
        this.cancleText = cancleText;
        this.onCancelButtoninterface = onCancelButtoninterface;
        return this;
    }

    public AddressEditDialog setOnNextButtoninterface(String nextText, onNextButtoninterface onNextButtoninterface) {
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
        View view = inflater.inflate(R.layout.dialog_address_edit, null);
        setContentView(view);
        setCancelable(false);

        TextView dialog_addedit_cancelBtn = (TextView) view.findViewById(R.id.dialog_addedit_cancelBtn);
        TextView dialog_addedit_confimBtn = (TextView) view.findViewById(R.id.dialog_addedit_confimBtn);

        userName = (EditText) view.findViewById(R.id.dialog_addedit_userName);
        userName.setText(name);
        userPhone = (EditText) view.findViewById(R.id.dialog_addedit_userPhone);
        userPhone.setText(phone);
        userAddress = (EditText) view.findViewById(R.id.dialog_addedit_userAddress);
        userAddress.setText(address);


        dialog_addedit_cancelBtn.setOnClickListener(new CallDialogListener());
        dialog_addedit_confimBtn.setOnClickListener(new CallDialogListener());


    }

    private class CallDialogListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_addedit_cancelBtn:
                    onBackPressed();
                    if (onCancelButtoninterface != null){
                        onCancelButtoninterface.cancel();
                    }
                    break;
                case R.id.dialog_addedit_confimBtn:
                    onBackPressed();
                    if (onNextButtoninterface != null){
                        onNextButtoninterface.call(userName.getText().toString(),userPhone.getText().toString(),userAddress.getText().toString());
                    }
                    break;
            }
        }
    }

}
