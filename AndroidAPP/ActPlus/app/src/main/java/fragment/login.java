package fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.actplus.Index;
import com.example.dell.actplus.Loginpage;
import com.example.dell.actplus.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class login extends Fragment {


    public login() {
        // Required empty public constructor
    }

    private final int WRONG_INPUT = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button sign_in_butt = (Button)view.findViewById(R.id.sign_in_butt);
        sign_in_butt.setOnClickListener(signin_click);
        Button sign_up_butt = (Button)view.findViewById(R.id.go_sign_up_butt);
        sign_up_butt.setOnClickListener(signup_butt_click);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Map<String, String> userInfo = ((Loginpage)getActivity()).getCurrentUserInfo();
        Button sign_in_butt = (Button)getView().findViewById(R.id.sign_in_butt);
        if (userInfo.get("username").equals("") == false) {
            try {
                EditText password_text = (EditText)getView().findViewById(R.id.input_password);
                EditText username_text = (EditText)getView().findViewById(R.id.input_username);
                password_text.setText(userInfo.get("password"));
                username_text.setText(userInfo.get("username"));
                sign_in_butt.performClick();
            } catch (Exception e) {
                Log.e("onCreateView: ", e.toString());
            }
        }
    }
    private View.OnClickListener signin_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText password_text = (EditText)getView().findViewById(R.id.input_password);
            EditText username_text = (EditText)getView().findViewById(R.id.input_username);
            final String password = password_text.getText().toString();
            final String username = username_text.getText().toString();
            if (password.equals("") || username.equals("")) {
                Toast.makeText(getActivity().getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_LONG).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        signin(password, username);
                    }
                }).start();
            }
        }
    };
    public void signin(String password, String username) {
        HttpURLConnection connection = null;
        String url = "http://actplus.sysuactivity.com/api/user/loginMethod";
        String response = "";
        String responseCookie = "";
        try {
            connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            //简书上看的post提交数据方法
            String data = "password="+password+"&username="+username;
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", data.length()+"");
            connection.setDoOutput(true);
            connection.setDoInput(true);//允许回传
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.close();
            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                responseCookie = connection.getHeaderField("Set-Cookie");//取到所用的Cookie
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "网络状况似乎不好哦", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("sign in part:" , e.toString());
        }
        is_sign_in(response, responseCookie, password, username);
    }
    private void is_sign_in(String response, String reponseCookie, String password, String username) {
        try {
            JSONObject ans = new JSONObject(response);
            int code = ans.getInt("code");
            if ( code ==  202) {
                ((Loginpage)getActivity()).setCurrentUserInfo(username, password);
                ((Loginpage)getActivity()).saveData();
                turnToIndex(reponseCookie);
            } else {
                Message message = new Message();
                message.what = WRONG_INPUT;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            Log.i("is_sign_in:", e.toString());
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WRONG_INPUT:
                    Toast.makeText(getActivity().getApplicationContext(), "错误的用户名或者密码~", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    public void turnToIndex(String cookie) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), Index.class);
        intent.putExtra("cookie", cookie);
        //就不会返回到登录页面
        getActivity().finish();
        startActivity(intent);
    }
    private View.OnClickListener signup_butt_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            signup f_signup = new signup();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.login_fragment, f_signup);
            ft.commit();
        }
    };
}
