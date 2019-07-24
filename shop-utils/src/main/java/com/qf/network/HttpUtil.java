package com.qf.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/13 14:02
 */
public class HttpUtil {
    public static String sendGet(String urlStr) {

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ) {
            //获取URL请求对象
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);

            //发送请求到指定服务器
            connection.connect();

            //获取服务器返回的结果
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024 * 10];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }

            byte[] buffer = baos.toByteArray();
            return new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        sendGet("http://localhost:8083/item/createhtml?gid=16");
    }
}
