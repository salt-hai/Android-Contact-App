package com.salthai.a17376042_khb_finaltest.http;

import com.salthai.a17376042_khb_finaltest.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Connection;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sedHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url = new URL(address);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    InputStream in = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder respone = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        respone.append(line);
                    }
                    if (listener != null) {
                        listener.onFinish(respone.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendOKHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}


/*发起http请求
 * HttpUtil.sendOKHttpRequest("http://www.baidu.com",new okhttp3.Callback(){
 * @Override
 * public void onResponse(Call call,Response response)throws IOException {
 * 得到服务器返回内容
 * String responseData = response.body().string();
 * }
 *@Override
 * public void onFailure(Call call,IOException e){
 * 异常在此处理
 * }
 * });
 * */