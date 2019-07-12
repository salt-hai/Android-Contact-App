package com.salthai.a17376042_khb_finaltest.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.salthai.a17376042_khb_finaltest.CallActivity;
import com.salthai.a17376042_khb_finaltest.LoginActivity;
import com.salthai.a17376042_khb_finaltest.MainActivity;
import com.salthai.a17376042_khb_finaltest.MyApplication;
import com.salthai.a17376042_khb_finaltest.R;
import com.salthai.a17376042_khb_finaltest.Bean.ContactsBean;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<ContactsBean> contactsList;
    Context context = MyApplication.getContext();
    TextView tv;


    static class ViewHolder extends RecyclerView.ViewHolder {
        //        添加contactView保存最外层布局的实例
        View contactView;
        AppCompatTextView contactsName;
        AppCompatTextView contactsNumber;

        public ViewHolder(View view) {
            super(view);
            contactView = view;
            contactsName = view.findViewById(R.id.name);
            contactsNumber = view.findViewById(R.id.number);
        }
    }

    public ContactsAdapter(List<ContactsBean> contactsList) {
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item,
                parent, false);

        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ContactsBean contactsBean = contactsList.get(position);
                Toast.makeText(v.getContext(), "打电话" + contactsBean.getName(),
                        Toast.LENGTH_SHORT).show();
                String number = contactsBean.getNumber();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contactsBean.getNumber()));
                context.startActivity(intent);

            }
        });
        return holder;
    }


    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactsBean contactsBean = contactsList.get(position);
        holder.contactsNumber.setText(contactsBean.getNumber());
        holder.contactsName.setText(contactsBean.getName());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}