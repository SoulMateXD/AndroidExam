package com.zhangdesheng.androidexam.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhangdesheng.androidexam.Activity.MusicInformation;
import com.zhangdesheng.androidexam.Adapter.MusicRecyclerAdapter;
import com.zhangdesheng.androidexam.R;
import com.zhangdesheng.androidexam.Utils.HttpCallbackListener;
import com.zhangdesheng.androidexam.Utils.HttpUtil;
import com.zhangdesheng.androidexam.db.MusicDB;
import com.zhangdesheng.androidexam.model.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/14.
 */
public class HotListFragment extends Fragment implements View.OnClickListener{

    private TextView hotlist3;
    private TextView hotlist5;
    private TextView hotlist6;
    private TextView hotlist16;
    private TextView hotlist17;
    private TextView hotlist18;
    private TextView hotlist19;
    private TextView hotlist23;
    private TextView hotlist26;


    private String url = "http://route.showapi.com/213-4";
    private String showapi_appid = "19045";
    private String showapi_sign = "36c76848e1764da59452587c46e4b70e";
    private String topid = "3";
    private String address = url + "?showapi_appid=" + showapi_appid + "&showapi_sign="+
            showapi_sign+"&topid="+topid ;

    private MusicDB musicDB;




    private ArrayList<Music> datalist = new ArrayList<Music>();

    RecyclerView recyclerView;

    MusicRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.hot_list, null);

        musicDB = MusicDB.getInstance(getContext());

        initView(view);


        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                parseJsonForHotList(response);
            }
            @Override
            public void onError(Exception e){ }
        });


        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.hotlist_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        MusicRecyclerAdapter adapter = new MusicRecyclerAdapter(datalist);
        adapter.setOnItemClickListener(new MusicRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Music music) {
                /*Intent intent = new Intent(getContext(), MusicInformation.class);
                intent.putExtra("music_pic", music.getAlbumpic_big());
                intent.putExtra("music_songname", music.getSongname());
                intent.putExtra("music_singername", music.getSingername());
                intent.putExtra("m4a", music.getUrl());
                startActivity(intent);*/

                musicDB.saveMusicInHot(music);
                Toast.makeText(getContext(), "歌曲 " + music.getSongname() + " 已保存至您的歌单",
                        Toast.LENGTH_SHORT ).show();
            }
        });
        recyclerView.setAdapter(adapter);


        return view;

    }

    private void initView(View view) {
        hotlist3 = (TextView)view.findViewById(R.id.hotlist_3);
        hotlist5 = (TextView)view.findViewById(R.id.hotlist_5);
        hotlist6 = (TextView)view.findViewById(R.id.hotlist_6);
        hotlist16 = (TextView)view.findViewById(R.id.hotlist_16);
        hotlist17 = (TextView)view.findViewById(R.id.hotlist_17);
        hotlist18 = (TextView)view.findViewById(R.id.hotlist_18);
        hotlist19 = (TextView)view.findViewById(R.id.hotlist_19);
        hotlist23 = (TextView)view.findViewById(R.id.hotlist_23);
        hotlist26 = (TextView)view.findViewById(R.id.hotlist_26);
        hotlist3.setOnClickListener(this);
        hotlist5.setOnClickListener(this);
        hotlist6.setOnClickListener(this);
        hotlist16.setOnClickListener(this);
        hotlist17.setOnClickListener(this);
        hotlist18.setOnClickListener(this);
        hotlist19.setOnClickListener(this);
        hotlist23.setOnClickListener(this);
        hotlist26.setOnClickListener(this);

    }

    private void parseJsonForHotList(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("showapi_res_body");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("pagebean");
            JSONArray jsonArray = jsonObject2.getJSONArray("songlist");
            String commentsResponse = jsonArray.toString();
            Gson gson = new Gson();
            ArrayList<Music> musics = gson.fromJson(commentsResponse,new TypeToken<ArrayList<Music>>(){}.getType());
            datalist = musics;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        resetTextColor();
        switch (v.getId()){
            case R.id.hotlist_3:
                address = getHotAddress("3");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist3.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_5:
                address = getHotAddress("5");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist5.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_6:
                address = getHotAddress("6");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist6.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_16:
                address = getHotAddress("16");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist16.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_17:
                address = getHotAddress("17");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist17.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_18:
                address = getHotAddress("18");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist18.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_19:
                address = getHotAddress("19");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist19.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_23:
                address = getHotAddress("23");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist23.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            case R.id.hotlist_26:
                address = getHotAddress("26");
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForHotList(response);
                    }
                    @Override
                    public void onError(Exception e){ }
                });
                hotlist26.setTextColor(Color.rgb(15,224,0));
                refreshData();
                break;
            default:
                break;
        }
    }

    public String getHotAddress(String i){
        String address = url + "?showapi_appid=" + showapi_appid + "&showapi_sign="+
                showapi_sign+"&topid="+i ;
        return address;
    }

    public void refreshData(){
        adapter = new MusicRecyclerAdapter(datalist);
        adapter.setOnItemClickListener(new MusicRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Music music) {
                /*Intent intent = new Intent(getContext(), MusicInformation.class);
                intent.putExtra("music_pic", music.getAlbumpic_big());
                intent.putExtra("music_songname", music.getSongname());
                intent.putExtra("music_singername", music.getSingername());
                intent.putExtra("m4a", music.getUrl());
                startActivity(intent);*/

                musicDB.saveMusicInHot(music);
                Toast.makeText(getContext(), "歌曲 " + music.getSongname() + " 已保存至您的歌单",
                        Toast.LENGTH_SHORT ).show();

            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void resetTextColor(){
        hotlist3.setTextColor(Color.rgb(98,98,98));
        hotlist5.setTextColor(Color.rgb(98,98,98));
        hotlist6.setTextColor(Color.rgb(98,98,98));
        hotlist16.setTextColor(Color.rgb(98,98,98));
        hotlist17.setTextColor(Color.rgb(98,98,98));
        hotlist18.setTextColor(Color.rgb(98,98,98));
        hotlist19.setTextColor(Color.rgb(98,98,98));
        hotlist23.setTextColor(Color.rgb(98,98,98));
        hotlist26.setTextColor(Color.rgb(98,98,98));
    }


}
