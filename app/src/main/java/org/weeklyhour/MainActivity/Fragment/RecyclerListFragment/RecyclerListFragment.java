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
import android.view.View;
import android.view.ViewGroup;

import org.weeklyhour.InsertItemActivity.newItemActivity;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.childItem;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.parentItem;
import org.weeklyhour.MainActivity.R;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

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
    private RealmResults<parentItem> parentItems;

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

        //LayoutManager set RecyclerView
        lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);

        //RealmResult set Adapter
        //Adapter set RecyclerView
        realm = Realm.getDefaultInstance();
        parentItems = realm.where(parentItem.class).findAll();
        adapter = new RecyclerViewAdapter(parentItems);
        recyclerView.setAdapter(adapter);

        //RealmResult add ChangeListener = callback when Data UPDATE
        parentItems.addChangeListener(new RealmChangeListener<RealmResults<parentItem>>() {
            @Override
            public void onChange(RealmResults<parentItem> element) {
                Log.d("Realm",  "" + parentItems.size());
            }
        });


        // bottom|right|end FloatingActionButton
        final FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setLongClickable(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 클릭시 netItemActivity로 이동.
                //requestCode 0 으로 newItemActivityIntent를 호출(MainActivity는 생존)

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
            realm.executeTransactionAsync(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    parentItem pItem = realm.createObject(parentItem.class);

                    //Primary key increment
                    pItem.id = realm.where(parentItem.class).max("id").intValue() + 1;
                    pItem.taskName = taskName;
                    pItem.maxDay = maxDay;
                    pItem.progressBarColor = color;
                }
            }, new Realm.Transaction.OnSuccess(){
                @Override
                public void onSuccess() {
                    //정상적으로 parentItem이 삽입된 경우
                    realm.executeTransactionAsync(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            childItem cItem = realm.createObject(childItem.class);
                            cItem.id = realm.where(childItem.class).max("id").intValue() + 1;
                            cItem.memo = memo;
                        }
                    }, new Realm.Transaction.OnSuccess(){
                        @Override
                        public void onSuccess() {
                            //둘다 정상적으로 삽입된 경우 adapter에 데이터의 변경사항을 알림
                            adapter.notifyParentItemInserted(parentItems.size() - 1);

                            Snackbar.make(getView(), "New Item Inserted!", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}
