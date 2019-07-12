package com.salthai.a17376042_khb_finaltest;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.salthai.a17376042_khb_finaltest.Bean.ContactsBean;
import com.salthai.a17376042_khb_finaltest.Adapter.ContactsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    private List<ContactsBean> contactsList = new ArrayList<>();
    private ContactsAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;
    TextView tv_name;
    private ContactsBean contactsBean;
    Context context = MyApplication.getContext();
    FloatingActionButton fab;
    Animation rotate2, rotate1;
    ImageButton ib_save;


    //        定义一个联系人数组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);


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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //开始写适配读取联系人
        recyclerView = findViewById(R.id.contacts_view);
        //添加Android自带的分割线
        // recyclerView.addItemDecoration(new RecyclerView(adapter, LinearLayoutManager.VERTICAL));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ContactsAdapter(contactsList);

        recyclerView.setAdapter(adapter);

        /*读取联系人所需要的权限*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readComtacts();
        }
        //下拉刷新
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContact();
            }
        });
        init();
        inEvent();
    }

    public void init() {
        ib_save = (ImageButton) findViewById(R.id.ib_save);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void inEvent() {
        final Intent intent = getIntent();
        final String username = intent.getStringExtra("name");
        if (username == null) {
            Toast.makeText(this, "您还没登录，请先登录", Toast.LENGTH_SHORT).show();
        } else

            Toast.makeText(this, username + "您已登录", Toast.LENGTH_SHORT).show();
//     tv_name.setText("username");
    /*    recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contactsBean.getNumber()));
                startActivity(intent);
            }
        });*/
//        浮动按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, AddCotactActivity.class);
                startActivity(intent1);
                //addMan();
            }
        });
    }

    private void refreshContact() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /*--------------------------------------*/
    private void readComtacts() {
        Cursor cursor = null;
        ContactsBean contactsBean = null;
        try {
            //查询联系人
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //获取姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取电话
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsBean = new ContactsBean(displayName, number);
                    contactsList.add(contactsBean);
                }
                adapter.notifyDataSetChanged();//通知刷新RecyclerView
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();//关闭cursor
            }
        }
    }

    /*-------------------------------------*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*菜单栏点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent intent = getIntent();
        final String username = intent.getStringExtra("name");
        if (username == null) {
            Toast.makeText(this, "您没有登录", Toast.LENGTH_SHORT).show();
        } else
            switch (item.getItemId()) {
                case R.id.it_upload:
                    NotificationManager manager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification1 = new NotificationCompat.Builder(this)
                            .setContentTitle("上传")
                            .setContentText("上传成功")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.up)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.up))
                            .build();
                    manager1.notify(1, notification1);
                    Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.it_download:

                    NotificationManager manager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification2 = new NotificationCompat.Builder(this)
                            .setContentTitle("下载")
                            .setContentText("下载成功")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.dow)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dow))
                            .build();
                    manager2.notify(1, notification2);
                    Toast.makeText(this, "下载成功请刷新", Toast.LENGTH_SHORT).show();
            }


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuitem) {
        // Handle navigation view item clicks here.
        switch (menuitem.getItemId()) {
            case R.id.nav_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
