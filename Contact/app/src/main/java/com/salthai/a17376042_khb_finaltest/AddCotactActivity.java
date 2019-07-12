package com.salthai.a17376042_khb_finaltest;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddCotactActivity extends AppCompatActivity {
    ImageButton ib_save;
    EditText et_add_name, et_add_number;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        init();
        inEvent();
    }

    public void init() {
        ib_save = (ImageButton) findViewById(R.id.ib_save);
        et_add_name = (EditText) findViewById(R.id.et_add_name);
        et_add_number = (EditText) findViewById(R.id.et_add_number);
    }

    /*private void addMan() {
        final AlertDialog.Builder di = new AlertDialog.Builder(AddCotactActivity.this);
        di.setCancelable(true);
        //布局转view
        LayoutInflater inflater = LayoutInflater.from(AddCotactActivity.this);
        View layout = inflater.inflate(R.layout.add_content, null);

        di.setView(layout);
        di.create();
        final Dialog dialog = di.show();
      *//*  ib_save.setOnClickListener(new View.OnClickListener() {
            //向通讯录添加联系人
            @Override
            public void onClick(View v) {
                //保存数据
                final String name = et_add_name.getText().toString();
                final String number = et_add_number.getText().toString();
                addMan_into_mobile(name, number, null);
            }
        });*//*
        //完

        Toast.makeText(getApplicationContext(), "已保存", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }*/
    public void inEvent() {
        final String name = et_add_name.getText().toString();
        final String number = et_add_number.getText().toString();
        if (name == null || number == null) {
            Toast.makeText(this, "请输入联系人信息", Toast.LENGTH_SHORT).show();
        } else
            ib_save.setOnClickListener(new View.OnClickListener() {
                //向通讯录添加联系人
                @Override
                public void onClick(View v) {
                    //保存数据
                    addMan_into_mobile(name, number, null);
                    Intent intent = new Intent(AddCotactActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(AddCotactActivity.this, "联系人已添加", Toast.LENGTH_SHORT).show();
                }
            });
    }

    //添加联系人方法
    private void addMan_into_mobile(String name, String number, byte[] by) {
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        //添加名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        //设置号码
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        //设置添加电话类型
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();
    }
}

