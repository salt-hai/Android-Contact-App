package com.salthai.a17376042_khb_finaltest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.salthai.a17376042_khb_finaltest.Bean.User;
import com.salthai.a17376042_khb_finaltest.Db.DBHelper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    Button bt_reg;
    EditText ed_add_username, ed_add_password1, ed_add_password2;
    private static final String URL_REG = "http://47.101.63.227:8080/FianlTestServer/reg";
    private DBHelper helper = null;
    private SQLiteDatabase sd = null;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        init();
        inEvent();
    }

    private void init() {
        bt_reg = (Button) findViewById(R.id.bt_reg);

        ed_add_password1 = (EditText) findViewById(R.id.et_add_password1);
        ed_add_password2 = (EditText) findViewById(R.id.et_add_password2);
        ed_add_username = (EditText) findViewById(R.id.et_add_username);
    }

    private void inEvent() {
//        注册操作
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_add_username.getText().toString();
                String password1 = ed_add_password1.getText().toString();
                String password2 = ed_add_password2.getText().toString();
                if (password1.equals(password2)) {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("username", username)
                            .add("password", password1)
                            .build();
//                    发送请求需要request对象
                    Request request = new Request.Builder()
                            .url(URL_REG)
                            .post(body)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            Log.d("news", result);
                            doreg(result);
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "两次输入密码不一致请重新输入",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void doreg(String result) {
        if (result.equals("0")) {
            Looper.prepare();
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } else {
            Gson gson = new Gson();
            User user = gson.fromJson(result, User.class);
            String userName = user.getUsername();
            String userPassword = user.getPassword();
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
            Intent in = new Intent(RegisterActivity.this, LoginActivity.class);
            in.putExtra("addname",userName);
            startActivity(in);

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
