package com.example.dell.actplus;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class personcenter extends Fragment {

    private final int UPDATE_USERINFO = 1;
    public personcenter() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personcenter, container, false);
        Update_UserInfo();
        Button logout_butt = (Button)view.findViewById(R.id.center_log_out);
        logout_butt.setOnClickListener(logout_click);
        return view;
    }
    private void Update_UserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetTools tool = new NetTools();
                UserInfo userInfo = tool.getUserInfo(((Index)getActivity()).getUserCookie());
                Message message = new Message();
                message.what = UPDATE_USERINFO;
                message.obj = userInfo;
                handler.sendMessage(message);
            }
        }).start();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_USERINFO:
                    UserInfo info = (UserInfo)msg.obj;
                    update_UserInfo(info);
                    break;
                default:
                    break;
            }
        }
    };
    private void update_UserInfo(UserInfo info) {
        if (info != null) {
            TextView userName = (TextView) getView().findViewById(R.id.center_user_name);
            userName.setText("你好，" + info.getUserName() + " !");
            ImageView head_img = (ImageView) getView().findViewById(R.id.center_head_img);
            Glide.with(getActivity().getApplicationContext()).load("http://actplus.sysuactivity.com/imgBase/headImg/" + info.getHeadImg()).into(head_img);
        }
    }
    View.OnClickListener logout_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), Loginpage.class);
            intent.putExtra("auto_login", false);
            getActivity().finish();
            startActivity(intent);
        }
    };
}
