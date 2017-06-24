package net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import entity.ActItem;
import entity.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by DELL on 2017/4/19.
 */

public class NetTools {
    public String getListJson(int startPage, int pageSize, String pageType) {
        HttpURLConnection connection = null;
        String url = "http://actplus.sysuactivity.com/api/actlist"+"?start="+startPage+"&pageSize="+pageSize+"&actType="+pageType;
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
    public List<ActItem> getList(final int startPage, final int pageSize, final String pageType) {
        List<ActItem> data = new ArrayList<>();
        String shit = getListJson(startPage, pageSize, pageType);
        try {
            JSONArray jsonArray = new JSONArray(shit);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                //get detail
                ActItem item = new ActItem(temp.getString("actName"), temp.getString("actTime"), temp.getInt("actId"),temp.getString("actLoc"), temp.getString("posterName"));
                //get poster image,这里是旧的方法，加载图片到bitmap里头，但是太慢了，改用glide。
                //item.SetImage(getImage("poster", temp.getString("posterName")));
                data.add(item);
            }
        } catch (Exception e) {
            Log.i("jsonArray part", "error");
        }
        return data;
    }
    //此方法太慢，改用glide
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
    public Map<String, Object> getActOriginDetail(int actId) {
        String url = "http://actplus.sysuactivity.com/api/actmeta/?actId="+actId;
        HttpURLConnection connection = null;
        String response = "";
        Map<String, Object> item = new HashMap<>();
        try {
            connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            }
        } catch (Exception e) {
            Log.e("getActOriginDetail:", e.toString());
        }
        try {
            JSONObject detail_data = new JSONObject(response);
            //get detail
            item.put("actName", detail_data.getString("actName"));
            item.put("actTime", detail_data.getString("actTime"));
            item.put("actLoc", detail_data.getString("actLoc"));
            item.put("actPub", detail_data.getString("actPub"));
            item.put("posterName", detail_data.getString("posterName"));
        } catch (Exception e) {
            Log.i("jsonArray part", "error");
        }
        return item;
    }
    public Map<String, Object> getActDetail(int actId) {
        String url = "http://actplus.sysuactivity.com/api/actdetail/?actId=" + actId;
        HttpURLConnection connection = null;
        String response = "";
        Map<String, Object> item = new HashMap<>();
        try {
            connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            }
        } catch (Exception e) {
            Log.e("getActOriginDetail:", e.toString());
        }
        try {
            JSONObject detail_data = new JSONObject(response);
            //get detail
            item.put("qrName", detail_data.getString("qrName"));
            item.put("actFor", detail_data.getString("actFor"));
            item.put("actDetail", detail_data.getString("actDetail"));
            item.put("actJoin", detail_data.getString("actJoin"));
        } catch (Exception e) {
            Log.i("jsonArray part", "error");
        }
        return item;
    }
    public List<Map<String, String>> getGroupList(final int startPage,final int pageSize) {
        List<Map<String, String>> data = new ArrayList<>();
        String url = "http://actplus.sysuactivity.com/api/group/groupList?start="+startPage+"&pageSize="+pageSize;
        HttpURLConnection connection = null;
        String response = "";
        //get response
        try {
            connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            }
        }catch (Exception e) {
            Log.e("getGroupList:", e.toString());
        }
        //解析response
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                //get detail
                Map<String, String> item = new HashMap<>();
                item.put("groupId", temp.getString("groupId"));
                item.put("contact", temp.getString("contact"));
                item.put("title", temp.getString("title"));
                item.put("sponsorId", temp.getString("sponsorId"));
                //强行获取头像、用户名
                //解析时间
                //SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                //Date date = format.parse(temp.getString("pubTime"));
                //item.put("pubTime", date.toString());
                item.put("pubTime", temp.getString("pubTime"));
                item.put("mainText", temp.getString("mainText"));
                item.put("actType", temp.getString("actType"));
                data.add(item);
            }
        } catch (Exception e) {
            Log.i("get group List json:", "error");
        }
        return data;
    }
    //使用cookie获得userinfo
    public UserInfo getUserInfo(String cookie) {
        HttpURLConnection connection = null;
        String url = "http://actplus.sysuactivity.com/api/user/userInfo";
        String response = "";
        try {
            connection = (HttpURLConnection)(new URL(url.toString())).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestProperty("Cookie", cookie);
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        try {
            JSONObject userJson = new JSONObject(response);
            UserInfo userInfo = new UserInfo(userJson.getString("nickname"), userJson.getString("headImg"), userJson.getString("userId"));
            return userInfo;
        } catch (JSONException e) {
            Log.e("userJson:", e.toString());
        }
        return null;
    }
    //使用cookie获得user发布的组队信息
    public List<String> getUserGroup(String cookie) {
        HttpURLConnection connection = null;
        String url = "http://actplus.sysuactivity.com/api/user/myTeam";
        String response = "";
        try {
            connection = (HttpURLConnection)(new URL(url.toString())).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestProperty("Cookie", cookie);
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        try {
            JSONArray groupsJson = new JSONArray(response);
            List<String> groups = new ArrayList<>();
            for (int i = 0; i < groupsJson.length(); i++) {
                JSONObject temp = groupsJson.getJSONObject(i);
                String group = temp.getString("title");
                groups.add(group);
            }
            return groups;
        } catch (JSONException e) {
            Log.e("userJson:", e.toString());
        }
        return null;
    }
}
