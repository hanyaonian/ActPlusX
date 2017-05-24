package com.example.dell.actplus;

import android.app.ProgressDialog;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class Index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //toolbar 设置空
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set up bottom navigation
        setUpBottomNavigate();
        //headerview(viewpager ,推荐最新活动)
        PullToRefreshListView PTF_listView = (PullToRefreshListView)findViewById(R.id.PTF_listview);
        ListView listView = PTF_listView.getRefreshableView();
        View headerView = View.inflate(getApplicationContext(), R.layout.viewpager, null);
        //设置仅可上拉刷新
        PTF_listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        PTF_listView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
        PTF_listView.getLoadingLayoutProxy().setPullLabel("下拉加载更多");
        PTF_listView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
        //设置上拉刷新监听
        PTF_listView.setOnRefreshListener(PullUpRefresh);
        //bug from pulltorefreshlistview. solve:http://blog.csdn.net/pk0071/article/details/50464247
        //add header view
        listView.addHeaderView(headerView);
        //初始化类
        listData = new ArrayList<>();
        tool = new NetTools();
        //获取初始数据
        try {
            currentPage = 0;
            currentType = "allList";
            dialog = ProgressDialog
                    .show(Index.this, "亲别急", "活动正在加载中", false);
            UpdateDataAndUI(currentPage, 5, currentType);
            first_start = true;
        } catch (Exception e) {
            Log.e("On Create", e.toString());
        }
    }
    private NetTools tool;
    private boolean first_start;
    private List<ActItem> listData;
    private int currentPage;
    private String currentType;
    private Myadpter myadpter;

    private PullToRefreshBase.OnRefreshListener PullUpRefresh = new PullToRefreshBase.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            new AsyncLoadData().execute();
        }
    };

    //设置正在加载,progress
    private ProgressDialog dialog;
    public void setUpViewPager(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(5);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(listData, this);
        viewPager.setAdapter(myPagerAdapter);
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
                if (position == 0) Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                if (position == 1) Toast.makeText(getApplicationContext(), "group", Toast.LENGTH_SHORT).show();
                if (position == 2) Toast.makeText(getApplicationContext(), "person", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }
    public void UpdateDataAndUI(final int startPage, final int pageSize, final String dataType) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<ActItem> actItems = tool.getList(startPage, pageSize, dataType);
                    Message message = new Message();
                    message.what = UPDATE_CONTENT;
                    message.obj = actItems;
                    handler.sendMessage(message);
                } catch(Exception e) {
                    Log.i("onCreate", e.toString());
                }
            }
        }).start();
    }
    private final int UPDATE_CONTENT = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_CONTENT:
                    List<ActItem> data = (List<ActItem>) msg.obj;
                    updateUI(data);
                    break;
                default:
                    break;
            }
        }
    };
    private void updateUI(final List<ActItem> data) {
        try {
            listData = data;
            PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.PTF_listview);
            if (first_start == true) {
                myadpter = new Myadpter(getApplicationContext(), listData);
                setUpViewPager();
                first_start = false;
                //若每次更新UI都是setAdapter就会不停地弹回顶部
                listView.setAdapter(myadpter);
                dialog.dismiss();
            } else {
                myadpter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Log.e("updateUI", e.toString());
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //右上角的菜单选项，暂时不要了
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class AsyncLoadData extends AsyncTask<Void, Void, List<ActItem>> {
        @Override
        protected List<ActItem> doInBackground(Void... params) {
            List<ActItem> list = null;
            try {
                currentPage += 1;
                list = tool.getList(currentPage, 5, currentType);
            } catch (Exception e) {
                Log.e("AsyncLoadData", e.toString());
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<ActItem> result) {
            //异步加载完成后
            Toast.makeText(getApplicationContext(), "加载完成O(∩_∩)O",Toast.LENGTH_LONG).show();
            listData.addAll(result);
            myadpter.notifyDataSetChanged();
            PullToRefreshListView PTF_listView = (PullToRefreshListView)findViewById(R.id.PTF_listview);
            PTF_listView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
}
