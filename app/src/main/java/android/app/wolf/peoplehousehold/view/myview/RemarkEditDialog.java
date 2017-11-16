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
public class RemarkEditDialog extends Dialog {

    private Context context;
    private String cancleText;
    private String nextText;
    private EditText remark;
    private String remarkText;
    private onCancelButtoninterface onCancelButtoninterface;
    private onNextButtoninterface onNextButtoninterface;

    public interface onCancelButtoninterface{
        void cancel();
    }

    public interface onNextButtoninterface{
        void call(String remark);
    }

    public RemarkEditDialog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public RemarkEditDialog setRemark(String remarkText){
        this.remarkText = remarkText;
        return this;
    }

    public RemarkEditDialog setOnCancelButtoninterface(String cancleText, onCancelButtoninterface onCancelButtoninterface) {
        this.cancleText = cancleText;
        this.onCancelButtoninterface = onCancelButtoninterface;
        return this;
    }

    public RemarkEditDialog setOnNextButtoninterface(String nextText, onNextButtoninterface onNextButtoninterface) {
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
        View view = inflater.inflate(R.layout.dialog_remarkedit, null);
        setContentView(view);
        setCancelable(false);

        TextView dialog_remark_cancelBtn = (TextView) view.findViewById(R.id.dialog_remark_cancelBtn);
        TextView dialog_remark_callBtn = (TextView) view.findViewById(R.id.dialog_remark_callBtn);
        remark = (EditText) view.findViewById(R.id.dialog_remark_edit);
        remark.setText(remarkText);

        dialog_remark_cancelBtn.setOnClickListener(new CallDialogListener());
        dialog_remark_callBtn.setOnClickListener(new CallDialogListener());


    }

    private class CallDialogListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_remark_cancelBtn:
                    onBackPressed();
                    if (onCancelButtoninterface != null){
                        onCancelButtoninterface.cancel();
                    }
                    break;
                case R.id.dialog_remark_callBtn:
                    onBackPressed();
                    if (onNextButtoninterface != null){
                        onNextButtoninterface.call(remark.getText().toString());
                    }
                    break;
            }
        }
    }

}
