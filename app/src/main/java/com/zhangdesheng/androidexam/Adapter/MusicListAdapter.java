package com.zhangdesheng.androidexam.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangdesheng.androidexam.R;
import com.zhangdesheng.androidexam.model.Music;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangdesheng.androidexam.R;
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
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>
        implements View.OnClickListener{

    /*这里。。。为了换一个TextView的内容，由于是写死了，我又很懒。。就直接复制了一份，和MusicRecyclerAdapter
    * 是一样的代码*/
    private ArrayList<Music> datalist;
    private static final int SHOW_MESSAGE = 1;
    private static Handler handler;

    public MusicListAdapter(ArrayList<Music> datalist){
        this.datalist = datalist;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Music music);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Music music = datalist.get(position);
        holder.songName.setText(music.getSongname());
        holder.singerName.setText(music.getSingername());
        holder.itemView.setTag(music);
        String picUrl = music.getAlbumpic_small();
        getBitmap(picUrl);
        handler = new Handler(){

            @Override
            public void handleMessage(Message message){
                switch (message.what){
                    case SHOW_MESSAGE:
                        Bitmap bitmap = (Bitmap)message.obj;
                        holder.albumpic.setImageBitmap(bitmap);
                        break;
                }
            }
        };

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(Music) v.getTag());
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumpic;
        TextView playImg;
        TextView singerName;
        TextView songName;

        public ViewHolder(View view){
            super(view);
            albumpic = (ImageView)view.findViewById(R.id.music_list_item_img);
            songName = (TextView)view.findViewById(R.id.music_list_item_song_name);
            singerName = (TextView)view.findViewById(R.id.music_list_item_singer_name);
            playImg = (TextView)view.findViewById(R.id.music_list_item_delete);
        }
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
