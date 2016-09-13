package org.weeklyhour.Fragment.RecyclerListFragment;


import android.content.res.Resources;
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

import org.weeklyhour.Activity.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerListFragment extends Fragment {
    ArrayList<testItem> dummyItems;


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
        dummyItems = new ArrayList<>();
        Random rnd = new Random();
        dummyItems.add(new testItem("a", 5, Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))));
        dummyItems.add(new testItem("B", 8, Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))));
        dummyItems.add(new testItem("c", 10, Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))));
        dummyItems.add(new testItem("D", 250, Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))));

        View layout = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        RecyclerView RecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(lm);

        RecyclerView.Adapter adapter = new RecyclerViewAdapterTest(dummyItems);
        RecyclerView.setAdapter(adapter);

        //구석에 있는 플러스버튼이다.
        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 별기능이 있진 않고 그냥 잠깐 액션한다. 스낵바 잠깐 뿅
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return layout;


    }

}
