package org.weeklyhour.MainActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import org.weeklyhour.MainActivity.Fragment.SectionsPageAdapter;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/*
탭이 있는 메인액티비티이다.

mSectionsPagerAdapter 프래그먼트 매니징을 한다. 최초 선언시 입력한 tabCount만큼 반환한다.
mViewPager는 수평으로 드래그했을때의 처리를 돕는다.

* */
public class MainActivity extends AppCompatActivity{
    private SectionsPageAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FragmentAdapter
        mSectionsPagerAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // ViewPager란 수평으로 View를 좌/우 로 스크롤 할때 사용 할때 사용하는 클래스
        // FragmentAdapter를 추가한다.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);//좌우 2개까지의 Destroy하지않고 가지고있는다


        //TabLayout을 객체화하고, ViewPager를 등록한다
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        realmConfig = new RealmConfiguration.Builder(this.getApplicationContext()).build();
        //Realm.deleteRealm(realmConfig);
        Realm.setDefaultConfiguration(realmConfig);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // menu_main.xml을 inflate해서 사용한다. 이는 현재 Settings 라는 스트링이, 최대 카운트 100이
        // 정의되어 있다.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
