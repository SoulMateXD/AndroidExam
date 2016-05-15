package com.zhangdesheng.androidexam.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.zhangdesheng.androidexam.Activity.MusicInformation;
import com.zhangdesheng.androidexam.Adapter.MusicListAdapter;
import com.zhangdesheng.androidexam.Adapter.MusicRecyclerAdapter;
import com.zhangdesheng.androidexam.R;
import com.zhangdesheng.androidexam.db.MusicDB;
import com.zhangdesheng.androidexam.model.Music;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/14.
 */
public class MyMusicListFragment extends Fragment {

    private MusicDB musicDB;

    private static final int SHOW_MESSAGE = 1;
    private static Handler handler;

    private ArrayList<Music> datas;

    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.my_music_list, null);

        musicDB = MusicDB.getInstance(getContext());

        datas = musicDB.loadMusic();
        Log.d("AAA", datas.size() + "000");

        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.my_music_list_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        MusicListAdapter adapter = new MusicListAdapter(datas);
        adapter.setOnItemClickListener(new MusicListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Music music) {
                musicDB.deleteMusic(music);
                Toast.makeText(getContext(), "歌曲 " + music.getSongname() + " 已从您的歌单移除",
                        Toast.LENGTH_SHORT ).show();
            }
        });
        recyclerView.setAdapter(adapter);

        final TextView refresh = (TextView)view.findViewById(R.id.my_music_list_refresh);
        TextView shunxu = (TextView)view.findViewById(R.id.shunxu_play);
        TextView liebiaoxunhuan = (TextView)view.findViewById(R.id.liebiaoxunhuan_play);
        TextView suiji = (TextView)view.findViewById(R.id.suiji_play);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas = musicDB.loadMusic();
                refreshDatas();

            }
        });

        shunxu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas = musicDB.loadMusic();
                int[] playList = new int[datas.size()];
                for (int i=0; i<datas.size(); i++){
                    playList[i] = i;
                }

                Intent intent = new Intent(getContext(), MusicInformation.class);
                intent.putExtra("playList", playList);
                startActivity(intent);
            }
        });

        /*这个列表循环。。。。。。其实就是将列表中的东西循环播放100次    0.0  别打我....*/
        liebiaoxunhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas = musicDB.loadMusic();
                int[] playList = new int[datas.size()*100];
                for (int j=0; j<100; j++ ) {
                    for (int i = 0; i < datas.size(); i++) {
                        playList[i + j*datas.size()] = i;
                    }
                }

                Intent intent = new Intent(getContext(), MusicInformation.class);
                intent.putExtra("playList", playList);
                startActivity(intent);

            }
        });

        suiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas = musicDB.loadMusic();
                int[] playList = new int[datas.size()];
                for (int i=0; i<datas.size(); i++){
                    playList[i] =(int)(Math.random()*100)%datas.size();
                }
                Intent intent = new Intent(getContext(), MusicInformation.class);
                intent.putExtra("playList", playList);
                startActivity(intent);

            }
        });


        return view;
    }

    private void refreshDatas() {
        MusicListAdapter adapter = new MusicListAdapter(datas);
        adapter.setOnItemClickListener(new MusicListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Music music) {
                musicDB.deleteMusic(music);
                Toast.makeText(getContext(), "歌曲 " + music.getSongname() + " 已从您的歌单移除",
                        Toast.LENGTH_SHORT ).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    public static void getBitmap(final String path)  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    Bitmap bitmap = null;
                    url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message = new Message();
                        message.what = SHOW_MESSAGE;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
