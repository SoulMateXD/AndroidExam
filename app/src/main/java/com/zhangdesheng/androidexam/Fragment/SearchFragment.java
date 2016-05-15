package com.zhangdesheng.androidexam.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Created by Administrator on 2016/5/14.
 */
public class SearchFragment extends Fragment implements View.OnClickListener{

    private String address;
    private String keyword;
    private int currentPage = 1;

    private String url = "http://route.showapi.com/213-1";
    private String showapi_appid = "19045";
    private String showapi_sign = "36c76848e1764da59452587c46e4b70e";

    private ArrayList<Music> datalist = new ArrayList<Music>();

    private RecyclerView recyclerView;
    private TextView previousPage;
    private TextView nextPage;
    private TextView textCurrentPage;
    private ImageView imageView;
    private EditText mySearchText;

    private MusicDB musicDB;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.search_fragment, null);

        musicDB = MusicDB.getInstance(getContext());

        initView(view);


        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                parseJsonForSearch(response);
            }
            @Override
            public void onError(Exception e){ }
        });
        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.search_frag_recyclerview);
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
                intent.putExtra("m4a", music.getM4a());
                startActivity(intent);*/

                musicDB.saveMusicInSearch(music);
                Toast.makeText(getContext(), "歌曲 " + music.getSongname() + " 已保存至您的歌单",
                        Toast.LENGTH_SHORT ).show();
            }
        });
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void initView(View view) {
        mySearchText = (EditText)view.findViewById(R.id.search_frag_search_text);
        imageView = (ImageView)view.findViewById(R.id.search_frag_launch_search);
        previousPage = (TextView)view.findViewById(R.id.search_frag_previous_page);
        nextPage = (TextView)view.findViewById(R.id.search_frag_next_page);
        textCurrentPage = (TextView)view.findViewById(R.id.search_frag_currentpage);

        imageView.setOnClickListener(this);
        previousPage.setOnClickListener(this);
        nextPage.setOnClickListener(this);
    }

    public String getSearchAddress(String keyword, int page){
        String address = url + "?" + "keyword="+ keyword + "&page="+ page +"&showapi_appid=" + showapi_appid + "&showapi_sign="+
                showapi_sign ;
        return address;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_frag_launch_search:
                keyword = mySearchText.getText().toString();
                currentPage = 1;
                address = getSearchAddress(keyword, currentPage);
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForSearch(response);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
                textCurrentPage.setText(currentPage+"");
                refreshData();
                break;
            case R.id.search_frag_previous_page:
                if (currentPage == 1) {
                    Toast.makeText(getContext(), "亲~到顶啦！", Toast.LENGTH_SHORT).show();
                }else {
                    currentPage = currentPage-1;
                    address = getSearchAddress(keyword, currentPage);
                    HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            parseJsonForSearch(response);
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
                    refreshData();
                }
                textCurrentPage.setText(currentPage+"");
                break;
            case R.id.search_frag_next_page:
                currentPage = currentPage+1;
                address = getSearchAddress(keyword, currentPage);
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJsonForSearch(response);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
                refreshData();
                textCurrentPage.setText(currentPage+"");
                break;


        }
    }

    private void parseJsonForSearch(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("showapi_res_body");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("pagebean");
            JSONArray jsonArray = jsonObject2.getJSONArray("contentlist");
            String commentsResponse = jsonArray.toString();
            Gson gson = new Gson();
            ArrayList<Music> musics = gson.fromJson(commentsResponse,new TypeToken<ArrayList<Music>>(){}.getType());
            datalist = musics;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void refreshData(){
        MusicRecyclerAdapter adapter = new MusicRecyclerAdapter(datalist);
        adapter.setOnItemClickListener(new MusicRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Music music) {
               /* Intent intent = new Intent(getContext(), MusicInformation.class);
                intent.putExtra("music_pic", music.getAlbumpic_big());
                intent.putExtra("music_songname", music.getSongname());
                intent.putExtra("music_singername", music.getSingername());
                intent.putExtra("m4a", music.getM4a());
                startActivity(intent);*/

                musicDB.saveMusicInSearch(music);
                Toast.makeText(getContext(), "歌曲 " + music.getSongname() + " 已保存至您的歌单",
                        Toast.LENGTH_SHORT ).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
