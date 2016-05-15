package com.zhangdesheng.androidexam.model;

/**
 * Created by Administrator on 2016/5/14.
 */
public class Music {

    private int id;
    private int albumid;
    private String albumname;
    private String albumpic_big;
    private String albumpic_small;
    private String downUrl;
    private String m4a;
    private int singerid;
    private String singername;

    public int getSongid() {
        return songid;
    }

    public int getSingerid() {
        return singerid;
    }

    public int getAlbumid() {
        return albumid;
    }

    private int songid;
    private String songname;
    private String url;

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    private String albummid;

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Music() {
        albumid = 0;
        albumname = "";
        albumpic_big = "";
        albumpic_small = "";
        downUrl = "";
        m4a = "";
        singerid = 0;
        singername = "";
        songid = 0;
        songname = "";
        url = "";
        albummid = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getAlbumpic_big() {
        return albumpic_big;
    }

    public void setAlbumpic_big(String albumpic_big) {
        this.albumpic_big = albumpic_big;
    }

    public String getAlbumpic_small() {
        return albumpic_small;
    }

    public void setAlbumpic_small(String albumpic_sma) {
        this.albumpic_small = albumpic_sma;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getM4a() {
        return m4a;
    }

    public void setM4a(String m4a) {
        this.m4a = m4a;
    }


    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }
}
