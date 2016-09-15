package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.weeklyhour.InsertItemActivity.newItemActivity;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.childItem;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.parentItem;
import org.weeklyhour.MainActivity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerListFragment extends Fragment {
    List<parentItem> dummyParentItems;
    private RecyclerViewAdapter adapter;

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
        dummyParentItems = new ArrayList<>();
        Random rnd = new Random();
        dummyParentItems.add(new parentItem("a",rnd.nextInt(256), Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256)), new childItem("memo1")));
        dummyParentItems.add(new parentItem("b",rnd.nextInt(256), Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256)), new childItem("memo2")));


        View layout = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        RecyclerView RecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(lm);

        adapter = new RecyclerViewAdapter(dummyParentItems);
        RecyclerView.setAdapter(adapter);

        //구석에 있는 플러스버튼이다.
        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
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

        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //from newItemActivity, parentItem Data Inserted
        if(resultCode == 1203){
            String taskName = data.getStringExtra("taskName");
            String memo = data.getStringExtra("memo");
            int maxDay = data.getIntExtra("maxDay", 1);
            int color = data.getIntExtra("color", 0);


            dummyParentItems.add(new parentItem(taskName, maxDay, color, new childItem(memo)));
            adapter.notifyParentItemInserted(dummyParentItems.size() - 1);

            Snackbar.make(getView(), "New Item Inserted!", Snackbar.LENGTH_LONG).show();
        }
    }

}
