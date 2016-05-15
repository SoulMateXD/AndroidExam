package com.zhangdesheng.androidexam.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MusicInformation extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnCompletionListener {

    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonStop;
    private TextView hint;
    private TextView singerName;
    private TextView songName;
    private ImageView albumPic;
    private TextView previousMusic;
    private TextView nextMusic;
    private TextView danquxunhuan;
    private SeekBar seekBar;

    private String music_pic;
    private String music_songname;
    private String music_singername;
    private String m4a;

    private ArrayList<Music> datas;
    private int[] playList;

    private int currentMusic;
    private int currentIndex;

    private MusicDB musicDB;

    private static final int SHOW_MESSAGE = 1;
    private static Handler handler;

    private boolean isPause;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_information);

        initView();

        musicDB = MusicDB.getInstance(MusicInformation.this);
        datas = musicDB.loadMusic();

        Intent intent = getIntent();
        playList = intent.getIntArrayExtra("playList");

        currentMusic = playList[0];
        currentIndex = 0;
        resetData();
        initMediaPlayer();
        isPause = false;
    }

    private void resetData() {
        Music music = datas.get(currentMusic);
        music_singername = music.getSingername();
        music_songname = music.getSongname();
        music_pic = music.getAlbumpic_big();
        m4a = music.getM4a();
    }

    private void initView() {
        buttonPlay = (Button) findViewById(R.id.play);
        buttonPause = (Button) findViewById(R.id.puase);
        buttonStop = (Button) findViewById(R.id.stop);
        hint = (TextView) findViewById(R.id.hint_text);
        singerName = (TextView) findViewById(R.id.music_info_singername);
        songName = (TextView) findViewById(R.id.music_info_songname);
        albumPic = (ImageView) findViewById(R.id.music_info_pic);
        previousMusic = (TextView)findViewById(R.id.music_info_previous);
        nextMusic = (TextView)findViewById(R.id.music_info_next);
        danquxunhuan = (TextView)findViewById(R.id.music_info_danquxunhuan);
        seekBar = (SeekBar)findViewById(R.id.music_info_seekbar);
        previousMusic.setOnClickListener(this);
        nextMusic.setOnClickListener(this);
        danquxunhuan.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);
        buttonPause.setEnabled(false);
        buttonStop.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser==true){
                   mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    Handler handler1 = new Handler();
    Runnable updateThread = new Runnable(){
        public void run() {
            //获得歌曲现在播放位置并设置成播放进度条的值
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            //每次延迟100毫秒再启动线程
            handler1.postDelayed(updateThread, 100);
        }
    };

    private void initMediaPlayer() {
        singerName.setText(music_singername);
        songName.setText(music_songname);
        getBitmap(music_pic);
        handler = new Handler() {

            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case SHOW_MESSAGE:
                        Bitmap bitmap = (Bitmap) message.obj;
                        albumPic.setImageBitmap(bitmap);
                        break;
                }
            }
        };

        try {


            mediaPlayer.setDataSource(m4a);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        seekBar.setMax(mediaPlayer.getDuration());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                mediaPlayer.start();
                hint.setText("开始播放音乐...");
                buttonPause.setText("暂停");
                buttonPlay.setEnabled(false);
                buttonPause.setEnabled(true);
                buttonStop.setEnabled(true);
                handler1.post(updateThread);
                break;
            case R.id.puase:
                if (!isPause) {
                    buttonPause.setText("继续");
                    mediaPlayer.pause();
                    buttonPlay.setEnabled(true);
                    isPause = true;
                    hint.setText("暂停播放音乐...");
                } else {
                    buttonPause.setText("暂停");
                    mediaPlayer.start();
                    hint.setText("继续播放音乐...");
                    buttonPlay.setEnabled(false);
                    isPause = false;
                }
                break;
            case R.id.stop:
                mediaPlayer.reset();
                initMediaPlayer();
                hint.setText("停止播放音乐...");
                buttonPause.setEnabled(false);
                buttonStop.setEnabled(false);
                buttonPlay.setEnabled(true);
                handler1.removeCallbacks(updateThread);
                break;
            case R.id.music_info_previous:
                previous();
                break;
            case R.id.music_info_next:
                next();
                break;
            case R.id.music_info_danquxunhuan:
                DanQuXunHuan();
                break;

        }
    }


    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }


    public static void getBitmap(final String path) {
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

    /*监听播放完成时的情况*/
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (playList.length >= 0) {
            next();
        } else {
            Toast.makeText(this, "列表为空", Toast.LENGTH_SHORT).show();
        }
    }

    //上一首
    private void previous() {
        if((currentIndex-1)>=0){
            currentIndex--;
            currentMusic = playList[currentIndex];
            resetData();

            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();
            initMediaPlayer();
            mediaPlayer.start();
            hint.setText("开始播放音乐...");
            buttonPause.setText("暂停");
            buttonPlay.setEnabled(false);
            buttonPause.setEnabled(true);
            buttonStop.setEnabled(true);
        }else{
            Toast.makeText(this, "当前已经是第一首歌曲了", Toast.LENGTH_SHORT).show();
        }
    }

    //下一首
    private void next() {
        if(currentIndex+1<playList.length){
            currentIndex++;
            currentMusic = playList[currentIndex];
            resetData();

            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();
            initMediaPlayer();
            mediaPlayer.start();
            hint.setText("开始播放音乐...");
            buttonPause.setText("暂停");
            buttonPlay.setEnabled(false);
            buttonPause.setEnabled(true);
            buttonStop.setEnabled(true);
        }else{
            Toast.makeText(this, "当前已经是最后一首歌曲了", Toast.LENGTH_SHORT).show();
        }
    }
        /* 额、、、好吧，这个单曲循环是假的，其实就是连续播100遍而已*/
    private void DanQuXunHuan(){
        playList = new int[100];
        for (int i =0; i<playList.length; i++){
            playList[i] = currentMusic;
        }
        currentIndex = 0;
        Toast.makeText(MusicInformation.this, "已将" + datas.get(currentIndex).getSongname() +
                "设为单曲循环", Toast.LENGTH_SHORT).show();

    }

}

