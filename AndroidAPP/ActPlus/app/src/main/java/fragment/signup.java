package fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.actplus.Loginpage;
import com.example.dell.actplus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fragment.login;

public class signup extends Fragment {

    public signup() {
        // Required empty public constructor
    }
    private final int WRONG = 0;
    private final int SUCCESS=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        Button sign_up_butt = (Button)view.findViewById(R.id.sign_up_butt);
        sign_up_butt.setOnClickListener(sign_up_click);
        return view;
    }
    private View.OnClickListener sign_up_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText username_text, nickname_text, password_text, confirm_password_text, mailbox_text;
            String username, nickname, password, confirm_password, mailbox;
            username_text = (EditText)getView().findViewById(R.id.sign_up_username);
            nickname_text = (EditText)getView().findViewById(R.id.sign_up_nickname);
            confirm_password_text = (EditText)getView().findViewById(R.id.sign_up_password_confirm);
            password_text = (EditText)getView().findViewById(R.id.sign_up_password);
            mailbox_text = (EditText)getView().findViewById(R.id.sign_up_mailbox);
            username = username_text.getText().toString();
            nickname = nickname_text.getText().toString();
            password = password_text.getText().toString();
            confirm_password = confirm_password_text.getText().toString();
            mailbox = mailbox_text.getText().toString();
            if (username.equals("") || nickname.equals("")  || password.equals("")  || confirm_password.equals("")  || mailbox.equals("") ) {
                Toast.makeText(getActivity().getApplicationContext(), "不能有空的哦~", Toast.LENGTH_LONG).show();
            } else if (password.equals(confirm_password) == false) {
                Toast.makeText(getActivity().getApplicationContext(), "密码输入不一样哦~", Toast.LENGTH_LONG).show();
            } else {
                singUp(username, mailbox, nickname, password);
            }
        }
    };
    private void singUp(final String username, final String mailbox, final String nickname, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postMessToServe(username, mailbox, nickname, password);
            }
        }).start();
    }
    private void postMessToServe(final String username, final String mailbox, final String nickname, final String password) {
        HttpURLConnection connection = null;
        String url = "http://actplus.sysuactivity.com/api/user/registerMethod";
        String response = "";
        String responseCookie="";
        try {
            connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            //简书上看的post提交数据方法
            String data = "password=" + password + "&username=" + username+"&email="+mailbox+"&nickname="+nickname+"&sex=M";
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", data.length() + "");
            connection.setDoOutput(true);
            connection.setDoInput(true);//允许回传
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.close();
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                responseCookie = connection.getHeaderField("Set-Cookie");//取到所用的Cookie
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            }
        }catch (Exception e) {
            Log.e("postMessageToServe:", e.toString());
        }
        try {
            JSONObject ansJson = new JSONObject(response);
            if (ansJson.getInt("code") == 201) {
                Message message = new Message();
                message.what = SUCCESS;
                Map<String, String> login_info = new HashMap<>();
                login_info.put("password", password);
                login_info.put("username", username);
                message.obj = login_info;
                handler.sendMessage(message);
            } else {
                Message message = new Message();
                message.what = WRONG;
                handler.sendMessage(message);
            }
        } catch (JSONException e) {
            Log.e("signupjson:", e.toString());
        }
    }
    public void turnToLogin(final String username, final String password) {
        sendUserInfo(username, password);
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        login login_fragment = new login();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.login_fragment, login_fragment);
        ft.commit();
    }
    private void sendUserInfo(String username, String password) {
        ((Loginpage)getActivity()).setCurrentUserInfo(username, password);
    }
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WRONG:
                    Toast.makeText(getActivity().getApplicationContext(), "用户名或邮箱已存在" , Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS:
                    Map<String, String> login_info = (Map<String, String>) msg.obj;
                    turnToLogin(login_info.get("username"), login_info.get("password"));
                    Toast.makeText(getActivity().getApplicationContext(), "注册成功" , Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
}
