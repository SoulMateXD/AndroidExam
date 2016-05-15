package com.zhangdesheng.androidexam.Utils;

import android.util.Log;

import com.zhangdesheng.androidexam.model.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/2.
 */
public class HttpUtil {


    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }

                    if (listener != null){
                        listener.onFinish(response.toString());
                    }

                } catch (IOException e) {
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


/*          不好意思，技术太差了，自己写的json解析不能解析那些参数不全的item（有的有9个属性，
            有的只有4 、5个属性），解析上面用的是Gson
    public static ArrayList<Music> parseJsonGetDatas(String response){
        JSONObject jsonObject = null;
        ArrayList<Music> datas = new ArrayList<>();
        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("showapi_res_body");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("pagebean");
            JSONArray jsonArray = jsonObject2.getJSONArray("songlist");
            JSONObject currentObject = null;
            for (int i=0; i<jsonArray.length(); i++){
                currentObject = (JSONObject) jsonArray.get(i);
                Music music = new Music();
                try {
                    currentObject.getInt("albumid");
                }catch (JSONException e ){
                    i++;
                    currentObject = (JSONObject)jsonArray.get(i);
                }
                if (currentObject.getInt("albumid")!=0){
                    music.setAlbumid(currentObject.getInt("albumid"));
                }
                if (!currentObject.getString("albummid").equals("")){
                    music.setAlbummid(currentObject.getString("albummid"));
                }
                if (!currentObject.getString("albumpic_big").equals("")){
                    music.setAlbumpic_big(currentObject.getString("albumpic_big"));
                }
                if (!currentObject.getString("albumpic_small").equals("")){
                    music.setAlbumpic_small(currentObject.getString("albumpic_small"));
                }
                if (!currentObject.getString("downUrl").equals("")){
                    music.setDownUrl(currentObject.getString("downUrl"));
                }
                if (currentObject.getInt("singerid")!=0){
                    music.setSingerid(currentObject.getInt("singerid"));
                }
                if (!currentObject.getString("singername").equals("")){
                    music.setSingername(currentObject.getString("singername"));
                }
                if (currentObject.getInt("songid")!=0){
                    music.setSongid(currentObject.getInt("songid"));
                }
                if (!currentObject.getString("songname").equals("")){
                    music.setSongname(currentObject.getString("songname"));
                }
                if (!currentObject.getString("url").equals("")){
                    music.setUrl(currentObject.getString("url"));
                }
                datas.add(music);
                Log.d("Text", music.getAlbumpic_small().toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return datas;

    }*/
}
