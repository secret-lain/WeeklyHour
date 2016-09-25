package org.weeklyhour.MainActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.daimajia.androidviewhover.BlurLayout;

import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.adapter.setRealmResultClearCallback;
import org.weeklyhour.MainActivity.Fragment.SectionsPageAdapter;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

/*
MaintActivity
ㄴ 1. Appbar
    ㄴ 1-1. Toolbar
    ㄴ 1-2. TabLayout
ㄴ 2. ViewPager

1.Appbar // 커스텀 액션바. setSupportActionBar()를 통해 MainActivity의 Actionbar를 등록한다.
    상단은 toolbar(옵션메뉴를 지정하고, 타이틀명이 들어간다)
    하단은 프래그먼트의 탭을 표시하기 위한 TabLayout.

2. ViewPager // PageAdapter를 통해 Fragment와 Fragment별 PageTitle을 받는다.
        PageTitle은 TabLayout에게 전달한다.
        Fragment는 뿌려주고 좌우로 slide 가능하다.
* */
public class MainActivity extends AppCompatActivity{
    private SectionsPageAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private RealmConfiguration realmConfig;
    private setRealmResultClearCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_in_left);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //기본 ActivityName 타이틀바를 지우고 따로 TextView를 사용
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // FragmentAdapter
        mSectionsPagerAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // ViewPager란 수평으로 View를 좌/우 로 스크롤 할때 사용 할때 사용하는 클래스
        // FragmentAdapter를 추가한다.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //mViewPager.setOffscreenPageLimit(2);//좌우 2개까지의 Destroy하지않고 가지고있는다

        //TabLayout을 객체화하고, ViewPager를 등록한다
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //Realm DB를 사용하기 위한 기본 설정. Realm은 Singleton 패턴으로 동작한다.


        try{
            realmConfig = new RealmConfiguration.Builder(this.getApplicationContext()).build();
            Realm.setDefaultConfiguration(realmConfig);

            Realm realm = Realm.getDefaultInstance();
        } catch(RealmMigrationNeededException e){
            realmConfig = new RealmConfiguration.Builder(this.getApplicationContext()).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfig);
        }

        BlurLayout.setGlobalDefaultDuration(450);

    }

/*  ApplicationForCustomFont Application 클래스를 사용할 경우 붙인다.
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }*/
}
