package com.example.dell.actplus;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;

import net.NetTools;

import java.util.List;

import entity.ActItem;
import entity.UserInfo;
import fragment.actdetail;
import fragment.grouplist;
import fragment.list_fragment;
import fragment.personcenter;

public class Index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActItem selected_item;
    private String userCookie;
    private final int UPDATE_USERINFO = 1;
    private final int UPDATE_USERGROUP = 0;
    private grouplist groupList;
    private actdetail actDetail;
    private list_fragment listFragment;
    private personcenter personCenter;
    private List<String> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //toolbar 设置空
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set up bottom navigation
        setUpBottomNavigate();
        //setAllFragment
        setUpFragment();
        //set up default fragment
        setUpDefaultFragment();
        //get cookie
        setCookie(getIntent().getStringExtra("cookie"));
        Update_UserInfo();
    }
    private void Update_UserGroups() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NetTools tool = new NetTools();
                    List<String> list_group = tool.getUserGroup(getUserCookie());
                    Message message = new Message();
                    message.what = UPDATE_USERGROUP;
                    message.obj = list_group;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Log.e("usergroup:" , e.toString());
                }
            }
        }).start();
    }
    private void setUpFragment() {
        groupList = new grouplist();
        actDetail = new actdetail();
        listFragment = new list_fragment();
        personCenter = new personcenter();
    }
    private void Update_UserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetTools tool = new NetTools();
                UserInfo userInfo = tool.getUserInfo(getUserCookie());
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
                case UPDATE_USERGROUP:
                    groups = (List<String>)msg.obj;
                    showGroupDialog();
                default:
                    break;
            }
        }
    };
    private void update_UserInfo(UserInfo info) {
        if (info != null) {
            TextView userName = (TextView) findViewById(R.id.userName);
            userName.setText("你好，" + info.getUserName() + " !");
            ImageView head_img = (ImageView) findViewById(R.id.user_head_img);
            Glide.with(getApplicationContext()).load("http://actplus.sysuactivity.com/imgBase/headImg/" + info.getHeadImg()).into(head_img);
        }
    }

    public void setUpDefaultFragment() {
        FragmentManager fragmentManager = this.getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment, listFragment).commit();
    }
    //开源项目 https://github.com/Ashok-Varma/BottomNavigation/wiki/Usage
    public void setUpBottomNavigate() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.group, "组队"))
                .addItem(new BottomNavigationItem(R.drawable.person, "我的"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                if (position == 0) {
                    ft.replace(R.id.main_fragment, listFragment);
                    ft.commit();
                }
                if (position == 1) {
                    ft.replace(R.id.main_fragment, groupList);
                    ft.commit();
                }
                if (position == 2) {
                    ft.replace(R.id.main_fragment, personCenter);
                    ft.commit();
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                if (position == 0) {
                    ft.replace(R.id.main_fragment, listFragment);
                    ft.commit();
                }
            }
        });
    }
    public void setCookie(String cookie) {
        this.userCookie = cookie;
    }
    public String getUserCookie() {
        return this.userCookie;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            exitApp();
        }
    }
    private long[] mHits = new long[2];
    //定义一个所需的数组
    private void exitApp() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 2000)) {// 2000代表设定的间隔时间
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "再次返回退出", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_start) {
            Toast.makeText(getApplicationContext(), "还在做~", Toast.LENGTH_LONG).show();
        } else if (id == R.id.my_group) {
            Update_UserGroups();
        } else if (id == R.id.log_out) {
            Intent intent = new Intent(Index.this, Loginpage.class);
            intent.putExtra("auto_login", false);
            finish();
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setSelected_item(ActItem actitem){
        this.selected_item = actitem;
    }
    public ActItem getSelected_item() {
        return this.selected_item;
    }


    public void showGroupDialog() {
        if (groups == null) {
            return;
        } else if (groups.size() == 0) {
            return;
        }
        String items[] = new String[groups.size()];
        items = groups.toArray(items);
            //dialog参数设置
            AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
            builder.setTitle("我的组队"); //设置标题

            builder.setIcon(R.drawable.group);//设置图标，图片id即可
            //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
            builder.setItems(items,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int position) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "确定", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
        }
}
