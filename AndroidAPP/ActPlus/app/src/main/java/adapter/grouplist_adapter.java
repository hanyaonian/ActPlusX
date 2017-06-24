package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.actplus.R;
import entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/6/3.
 */

public class grouplist_adapter extends BaseAdapter {
    private List<Map<String, String>> listData;
    private List<UserInfo> userInfoList;
    //app context
    private static LayoutInflater inflater=null;
    private Context appContext;
    @Override
    public int getCount() {
        return listData.size();
    }
    public grouplist_adapter(Context context, List<Map<String, String>> data) {
        appContext = context;
        listData = data;
        //set inflater, why? see next
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(appContext);
        convertView= (LinearLayout) inflater.inflate(R.layout.group_carditem, null);
        TextView group_title, group_pub, group_time, group_detail, group_contact, act_type;
        group_title = (TextView)convertView.findViewById(R.id.group_title);
        group_pub = (TextView)convertView.findViewById(R.id.group_pub);
        group_time = (TextView)convertView.findViewById(R.id.group_time);
        group_detail = (TextView)convertView.findViewById(R.id.group_detail);
        group_contact = (TextView)convertView.findViewById(R.id.group_contact);
        act_type = (TextView)convertView.findViewById(R.id.act_type);
        ImageView Head_img = (ImageView)convertView.findViewById(R.id.group_pub_img);
        try {
            Map<String, String> temp = listData.get(position);
            //get userid
            group_contact.setText("联系方式："+temp.get("contact"));
            group_time.setText(temp.get("pubTime"));
            group_title.setText(temp.get("title"));
            act_type.setText(temp.get("actType"));
            group_detail.setText(temp.get("mainText"));
            group_pub.setText(temp.get("sponsorId")+"发布于:");
            //Glide.with(appContext).load("http://actplus.sysuactivity.com/imgBase/headImg/"+temp.get("head_img")).into(Head_img);
        } catch (Exception e) {
            Log.e("group_list error:", e.toString());
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(listData.get(position).get("groupId"));
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

}
