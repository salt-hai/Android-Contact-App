package com.salthai.a17376042_khb_finaltest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.salthai.a17376042_khb_finaltest.Bean.User;
import com.salthai.a17376042_khb_finaltest.Db.DBHelper;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {
    Button bt_login, bt_register;
    private EditText et_username, et_password;
    private static final String URL_LOGIN = "http://47.101.63.227:8080/FianlTestServer/login";
    private DBHelper helper = null;
    private SQLiteDatabase sd = null;
    private Button btn_login;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
      /*  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        init();
        inEvent();
    }

    private void closeActivity() {
        this.finish();
    }

    private void init() {
//        登录按钮
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
    }

    private void inEvent() {


        final Intent intent = getIntent();
        String username = intent.getStringExtra("addname");
        if (username == null) {
        } else

            Toast.makeText(this, username + "您已注册", Toast.LENGTH_SHORT).show();

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
//        登录操作
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build();
//                发送请求需要 request对象
                Request request = new Request.Builder()
                        .url(URL_LOGIN)
                        .post(body)
                        .build();
//
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call arg0, Response res) throws IOException {
                        //Looper.prepare();
                        String result = res.body().string();
                        Log.d("news", result);
                        doLogin(result);
                    }

                    @Override
                    public void onFailure(Call arg0, IOException arg1) {
                        // TODO Auto-generated method stub

                    }
                });
            }
        });
    }
//登录逻辑判断有误
    private void doLogin(String result) {
        if (result.equals("0")) {
            Looper.prepare();
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } else {
            Gson gson = new Gson();
            User user = gson.fromJson(result, User.class);
            String userName = user.getUsername();
            String userPassword= user.getPassword();
            int id = user.getId();
            Log.d("news", id + "");
            Log.d("news", userName);
            Log.d("news", userPassword);

            sp = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", userName);
            editor.putString("password", userPassword);
            editor.putInt("id", id);
            editor.commit();
            Intent in = new Intent(LoginActivity.this, MainActivity.class);
            in.putExtra("name",userName);
            startActivity(in);
            closeActivity();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

