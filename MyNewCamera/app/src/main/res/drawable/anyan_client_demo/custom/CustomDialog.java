package drawable.anyan_client_demo.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anyan.client.anyan_client_demo.R;

/**
 * Created by flin on 2015/8/31.
 */
public class CustomDialog extends Dialog {
    public EditText mEdt_Line1, mEdt_Line2, mEdt_Line3;
    public Button positiveButton, negativeButton;
    public TextView mTxt_title;

    public CustomDialog(Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
        requestWindowFeature(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉黑色头部
        //getWindow().setBackgroundDrawableResource(android.R.color.transparent);//只有这样才能去掉黑色背景

        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.custdialog, null);
        mTxt_title = (TextView) mView.findViewById(R.id.txt_title);
        mEdt_Line1 = (EditText) mView.findViewById(R.id.edt_line1);
        mEdt_Line2 = (EditText) mView.findViewById(R.id.edt_line2);
        mEdt_Line3 = (EditText) mView.findViewById(R.id.edt_line3);
        positiveButton = (Button) mView.findViewById(R.id.btn_num1);
        positiveButton.setText("确 定");
        negativeButton = (Button) mView.findViewById(R.id.btn_num2);
        negativeButton.setText("取 消");
        super.setContentView(mView);
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        positiveButton.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener){
        negativeButton.setOnClickListener(listener);
    }
}