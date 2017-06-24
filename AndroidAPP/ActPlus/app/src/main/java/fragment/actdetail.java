package fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import entity.ActItem;
import com.example.dell.actplus.Index;
import net.NetTools;
import com.example.dell.actplus.R;

import java.util.Map;

public class actdetail extends Fragment {
    private ActItem showed_item;
    private NetTools tool;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actdetail, container, false);
        //get selected item
        showed_item = ((Index)getActivity()).getSelected_item();
        //set up nettool
        tool = new NetTools();
        // Inflate the layout for this fragment
        UpdateDataAndUI(showed_item.getId());
        return view;
    }
    private final int UPDATE_CONTENT = 0;
    public void UpdateDataAndUI(final int actId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取详情需要的数据
                    Map<String, Object> data_detail = tool.getActOriginDetail(actId);
                    Map<String, Object> act_detail = tool.getActDetail(actId);
                    data_detail.putAll(act_detail);
                    Message message = new Message();
                    message.what = UPDATE_CONTENT;
                    message.obj = data_detail;
                    handler.sendMessage(message);
                } catch(Exception e) {
                    Log.i("onCreate", e.toString());
                }
            }
        }).start();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_CONTENT:
                    Map<String, Object> data = (Map<String, Object>) msg.obj;
                    updateUI(data);
                    break;
                default:
                    break;
            }
        }
    };
    //这个函数是真的蠢。
    private void updateUI(final Map<String, Object>data) {
        try {
            TextView act_title, act_pub, act_time, act_for, act_Join, act_place, act_detail;
            ImageView poster;
            act_title = (TextView)getView().findViewById(R.id.act_detail_title);
            act_pub = (TextView)getView().findViewById(R.id.act_detail_pub);
            act_time = (TextView)getView().findViewById(R.id.act_detail_time);
            act_Join = (TextView)getView().findViewById(R.id.act_detail_Join);
            act_for = (TextView)getView().findViewById(R.id.act_detail_actFor);
            act_place = (TextView)getView().findViewById(R.id.act_detail_place);
            act_detail = (TextView)getView().findViewById(R.id.act_detail_detail);
            poster = (ImageView)getView().findViewById(R.id.act_detail_poster);
            act_title.setText((String)data.get("actName"));
            act_Join.setText("报名方式： "+ (String)data.get("actJoin"));
            act_for.setText("面向对象： "+(String)data.get("actFor"));
            act_place.setText("活动地点： "+(String)data.get("actLoc"));
            act_time.setText("活动时间： "+(String)data.get("actTime"));
            act_pub.setText("活动发布： "+(String)data.get("actPub"));
            act_detail.setText("活动详情： "+(String)data.get("actDetail"));
            ImageView qr_img = (ImageView)getView().findViewById(R.id.qr_img);
            if (data.get("posterName") != null) {
                Glide.with(getActivity().getApplicationContext())
                        .load("http://actplus.sysuactivity.com/imgBase/poster/" + data.get("posterName"))
                        .into(poster);
            }
            if (data.get("qrName") != null) {
                Glide.with(getActivity().getApplicationContext())
                        .load("http://actplus.sysuactivity.com/imgBase/qrImg/" + data.get("qrName"))
                        .into(qr_img);
            }
        } catch (Exception e) {
            Log.e("updateUI", e.toString());
        }
    }
}
