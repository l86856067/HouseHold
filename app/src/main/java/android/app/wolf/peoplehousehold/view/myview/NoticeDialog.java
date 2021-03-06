package android.app.wolf.peoplehousehold.view.myview;

import android.app.Dialog;
import android.app.wolf.peoplehousehold.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lh on 2017/10/11.
 * <p>
 * 功能作用：通知的Dialog
 * <p>
 * 修改记录：
 */
public class NoticeDialog extends Dialog {

    private Context context;
    private String message;
    private String postText;
    private OnpostButtonInterface postButtonInterface;

    public interface OnpostButtonInterface{
        void post();
    }

    public NoticeDialog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public NoticeDialog setMessage(String message){
        this.message = message;
        return this;
    }

    public NoticeDialog setPostButton(String postText,OnpostButtonInterface onpostButtonInterface){
        this.postText = postText;
        this.postButtonInterface = onpostButtonInterface;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_notice, null);
        setContentView(view);

        TextView textmessage = (TextView) view.findViewById(R.id.dialog_message);
        TextView btn = (TextView) view.findViewById(R.id.dialog_btn);
        btn.setOnClickListener(new postButtonListener());

        textmessage.setText(message);
        setCancelable(false);


//        Window dialogWindow = getWindow();
//        WindowManager windowManager = ((Activity)context).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.width = display.getWidth() * 7/10;
//        layoutParams.height = display.getHeight() * 7/10;

//        dialogWindow.setAttributes(layoutParams);

    }

    private class postButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_btn:
                    onBackPressed();
                    if (postButtonInterface != null){
                        postButtonInterface.post();
                    }
                    break;
            }
        }
    }
}
