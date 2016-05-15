package com.zhangdesheng.androidexam.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhangdesheng.androidexam.Fragment.HotListFragment;
import com.zhangdesheng.androidexam.Fragment.MyMusicListFragment;
import com.zhangdesheng.androidexam.Fragment.SearchFragment;
import com.zhangdesheng.androidexam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/14.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentAdapter;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    private TextView TabHotList;
    private TextView TabSearch;
    private TextView TabMusicList;

    private HotListFragment hotListFragment;
    private MyMusicListFragment myMusicListFragment;
    private SearchFragment searchFragment;

    private Color whiteColor = new Color();
    private Color redColor = new Color();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        initView();

        fragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount()
            {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return fragmentList.get(arg0);
            }
        };

        viewPager.setAdapter(fragmentAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                resetTabColor();
                switch (position)
                {
                    case 0:
                        TabHotList.setTextColor(Color.rgb(255,78,72));
                        break;
                    case 1:
                        TabSearch.setTextColor(Color.rgb(255,78,72));
                        break;
                    case 2:
                        TabMusicList.setTextColor(Color.rgb(255,78,72));
                        break;
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

    }

    private void resetTabColor() {
        TabHotList.setTextColor(Color.rgb(255,255,255));
        TabMusicList.setTextColor(Color.rgb(255,255,255));
        TabSearch.setTextColor(Color.rgb(255,255,255));
    }

    protected void initView(){
        TabHotList = (TextView)findViewById(R.id.main_hotlist_tab);
        TabMusicList = (TextView)findViewById(R.id.main_musiclist_tab);
        TabSearch = (TextView)findViewById(R.id.main_search_tab);

        TabHotList.setOnClickListener(this);
        TabMusicList.setOnClickListener(this);
        TabSearch.setOnClickListener(this);

        hotListFragment = new HotListFragment();
        myMusicListFragment = new MyMusicListFragment();
        searchFragment = new SearchFragment();
        fragmentList.add(hotListFragment);
        fragmentList.add(searchFragment);
        fragmentList.add(myMusicListFragment);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.main_hotlist_tab:
                viewPager.setCurrentItem(0);
                break;
            case R.id.main_search_tab:
                viewPager.setCurrentItem(1);
                break;
            case R.id.main_musiclist_tab:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
}
