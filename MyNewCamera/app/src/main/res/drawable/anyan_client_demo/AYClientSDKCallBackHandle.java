package drawable.anyan_client_demo;

import android.util.Log;

import com.anyan.client.sdk.AYClientSDKCallBack;

/**
 * Created by flin on 2015/8/27.
 */
public class AYClientSDKCallBackHandle implements AYClientSDKCallBack {
    public void OnStatusMsg(int status_code,String msg)
    {

    }

    public void OnPlaystateChange(String device_id,int idx,int rate,int state,String msg)
    {
        Log.w("java_CALLBACK", "statechanged:"+state + ",msg=" + msg);
    }

    @Override
    public void OnNvrHistoryList(String device_id, int idx, int rate, int start_time, int[] start_history, int[] end_history) {

    }

    @Override
    public void OnRecvOEMData(byte[] data, int data_len) {

    }


}
