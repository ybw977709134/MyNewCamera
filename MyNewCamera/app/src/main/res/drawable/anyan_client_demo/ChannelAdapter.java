package drawable.anyan_client_demo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 *
 */
public class ChannelAdapter  extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<String> mChannels;
    private String mChannelState;

    public ChannelAdapter(Context context, List<String> channels, String channelstate)
    {
        this.mContext = context;
        this.mChannels = channels;
        this.mInflater = LayoutInflater.from(context);
        this.mChannelState = channelstate;
    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.deviceitem, null);
            holder.channelName = (TextView)convertView.findViewById(R.id.txt_devicename);
            holder.deviceOwner = (TextView)convertView.findViewById(R.id.txt_devicesowner);
            holder.deviceOwner.setVisibility(View.GONE);
            holder.deviceOnline = (TextView)convertView.findViewById(R.id.txt_devicesonline);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.channelName.setText(mChannels.get(position));

        if(mChannelState.substring(position, position + 1).equals("0")) {
            holder.deviceOnline.setText("Offline");
            holder.deviceOnline.setTextColor(Color.RED);
        } else {
            holder.deviceOnline.setText("Online");
            holder.deviceOnline.setTextColor(Color.GREEN);
        }

        return convertView;
    }

    public static class ViewHolder
    {
        public TextView channelName;
        public TextView deviceOwner;
        public TextView deviceOnline;
    }
}
