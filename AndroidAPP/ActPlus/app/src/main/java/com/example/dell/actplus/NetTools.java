package com.example.dell.actplus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;

import static android.content.ContentValues.TAG;

/**
 * Created by DELL on 2017/4/19.
 */

public class NetTools {
    public String getListJson(int startPage, int pageSize, String pageType) {
        HttpURLConnection connection = null;
        String url = "http://actplus.sysuactivity.com/api/actlist"+"?start="+startPage+"&pageSize="+pageSize+"&pageType="+pageType;
        try {
            Log.i("getListJson", "start connecting");
            connection = (HttpURLConnection) ((new URL(url.toString())).openConnection());
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            InputStream in = connection.getInputStream();
            //get json string
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                String response = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                return response;
            }
            else {
                connection.disconnect();
                return null;
            }
        } catch(Exception e) {
            Log.i(TAG, "getListJson: " + e.toString());
        } finally {
            if (connection != null) connection.disconnect();
        }
        return null;
    }
    public List<ActItem> getList(final int startPage,final int pageSize,final String pageType) {
        List<ActItem> data = new ArrayList<>();
        String shit = getListJson(startPage, pageSize, pageType);
        try {
            JSONArray jsonArray = new JSONArray(shit);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                //get detail
                ActItem item = new ActItem(temp.getString("actName"), temp.getString("actTime"), temp.getInt("actId"),temp.getString("actLoc"));
                //get poster image
                item.SetImage(getImage("poster", temp.getString("posterName")));
                data.add(item);
            }
        } catch (Exception e) {
            Log.i("jsonArray part", "error");
        }
        return data;
    }
    public Bitmap getImage(String imageType, String imageName) {
        //获取方式 ： GET
        //图片地址 ：http://actplus.sysuactivity.com/imgBase/{imageType}/{imageName}
        String url = "http://actplus.sysuactivity.com/imgBase/" + imageType + "/"+ imageName;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) (new URL(url.toString()).openConnection());
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            if (connection.getResponseCode() == 200) {
                //inputstream to bitmap
                InputStream in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.i("getBitmap part", "error");
        } finally {
            if (connection != null)
            connection.disconnect();
        }
        return null;
    }
}
