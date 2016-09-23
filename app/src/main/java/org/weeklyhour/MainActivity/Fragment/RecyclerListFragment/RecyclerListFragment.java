package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.weeklyhour.InsertItemActivity.newItemActivity;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.item;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.adapter.RecyclerViewAdapter;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.adapter.setRealmResultClearCallback;
import org.weeklyhour.MainActivity.R;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * RecyclerView
 * ㄴ LayoutManager = Linear, Grid 등 RecyclerView의 레이아웃을 결정
 * ㄴ RecyclerViewAdapter = CustomAdapter, 데이터를 표현해주고, ViewHolder 에 정보결합
 *
 * Realm 을 통해 저장된 DB의 정보를 가져오고, RealmResult<>로 가져온 결과를 Adapter에 연결.
 * */
public class RecyclerListFragment extends Fragment {
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private RecyclerView recyclerView;

    private Realm realm;
    private RealmResults<item> parentItems;
    public setRealmResultClearCallback callback;

    public RecyclerListFragment() {
        // Required empty public constructor
    }

    public static RecyclerListFragment newInstance() {
        RecyclerListFragment fragment = new RecyclerListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);


        //LayoutManager set RecyclerView
        lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);

        //RealmResult set Adapter
        //Adapter set RecyclerView
        realm = Realm.getDefaultInstance();
        parentItems = realm.where(item.class).findAll();
        adapter = new RecyclerViewAdapter(parentItems);
        recyclerView.setAdapter(adapter);

        //RealmResult add ChangeListener = callback when Data UPDATE
        parentItems.addChangeListener(new RealmChangeListener<RealmResults<item>>() {
            @Override
            public void onChange(RealmResults<item> element) {
                Log.d("Realm", "RecyclerListFragment::parentItems changed, size: " + element.size());
                adapter.notifyDataSetChanged();
            }
        });

        //set Item Animation
        recyclerView.setItemAnimator(new FadeInAnimator());

        // bottom|right|end FloatingActionButton
        final FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setLongClickable(false);
        fab.setOnClickListener(new View.OnClickListener() {
            //버튼 클릭시 netItemActivity로 이동.
            //requestCode 0 으로 newItemActivityIntent를 호출(MainActivity는 생존)
            @Override
            public void onClick(View view) {

                Intent newItemActivityIntent = new Intent(getContext(), newItemActivity.class);
                startActivityForResult(newItemActivityIntent, 0);
            }
        });

        /*
        * RecyclerView ScrollListener
        * Scroll 중일때, 플로팅버튼.hide()
        * Scroll 중이 아닐 때, 5초 후 show()
        * 5초 Delay 중에 Scroll 할 경우, 다시 5초로 갱신
        * */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            Handler handler = new Handler();
            final Runnable mRunnable = new Thread() {
                @Override
                public void run() {
                    fab.show();
                }
            };
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE){
                    if(((Thread)mRunnable).isInterrupted() == false){
                        //이미 동작중일 경우,
                        //다중으로 쓰레드가 동작하여 postDelay가 중복되는 것을 방지하기 위함
                        ((Thread)mRunnable).interrupt();
                        handler.removeCallbacks(mRunnable);
                    }

                    handler.postDelayed(mRunnable, 5000); // 5초후 show();
                }
                else
                    fab.hide();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        callback = adapter;
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //from newItemActivity, new Realm Object Insert(parentItem)
        if(resultCode == 1203){
            //parentItem
            final String taskName = data.getStringExtra("taskName");
            final int maxDay = data.getIntExtra("maxDay", 1);
            final int color = data.getIntExtra("color", 0);

            //childItem
            final String memo = data.getStringExtra("memo");

            //DB Insert
            adapter.addItem(taskName, maxDay, color);
            //adapter.notifyParentItemInserted(parentItems.size() - 1);
            Snackbar.make(getView(), "New Item Inserted!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            callback.setDataInit();
            Snackbar.make(getView(), "Item All Cleared.", Snackbar.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
