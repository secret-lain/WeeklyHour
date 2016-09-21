package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.weeklyhour.InsertItemActivity.newItemActivity;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.childItem;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.parentItem;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.helper.OnStartDragListener;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.helper.SimpleItemTouchHelperCallback;
import org.weeklyhour.MainActivity.R;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerListFragment extends Fragment implements OnStartDragListener {
    private RecyclerViewAdapter adapter;

    private Realm realm;
    private RealmList<parentItem> parentItems;

    private ItemTouchHelper mItemTouchHelper;

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

        RecyclerView RecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(lm);



        realm = Realm.getDefaultInstance();
        parentItems = new RealmList<>();
        RealmResults<parentItem> rResult = realm.where(parentItem.class).findAll();
        parentItems.addAll(rResult.subList(0, rResult.size()));

        adapter = new RecyclerViewAdapter(parentItems, this);
        RecyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(RecyclerView);

        //구석에 있는 플러스버튼이다.
        final FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setRippleColor(Color.parseColor("#0000FF"));
        fab.setLongClickable(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 별기능이 있진 않고 그냥 잠깐 액션한다. 스낵바 잠깐 뿅
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                Intent newItemActivityIntent = new Intent(getContext(), newItemActivity.class);
                startActivityForResult(newItemActivityIntent, 0);
                //requestCode 0 으로 newItemActivityIntent를 호출(MainActivity는 생존)
            }
        });

        RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
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
                        ((Thread)mRunnable).interrupt();
                        handler.removeCallbacks(mRunnable);
                    }
                    handler.postDelayed(mRunnable, 5000);
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

        //from newItemActivity, parentItem Data Inserted
        if(resultCode == 1203){
            final String taskName = data.getStringExtra("taskName");
            final int maxDay = data.getIntExtra("maxDay", 1);
            final int color = data.getIntExtra("color", 0);

            final String memo = data.getStringExtra("memo");

            realm.executeTransactionAsync(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    parentItem pItem = realm.createObject(parentItem.class);
                    pItem.id = realm.where(parentItem.class).max("id").intValue() + 1;
                    pItem.taskName = taskName;
                    pItem.maxDay = maxDay;
                    pItem.progressBarColor = color;
                }
            }, new Realm.Transaction.OnSuccess(){
                @Override
                public void onSuccess() {
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
                            adapter.notifyParentItemInserted(parentItems.size() - 1);

                            Snackbar.make(getView(), "New Item Inserted!", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
