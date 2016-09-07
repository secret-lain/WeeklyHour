package org.weeklyhour.Fragment.RecyclerListFragment;


import android.content.res.Resources;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerListFragment extends Fragment {
    ArrayList<Item> dummyItems;


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
        dummyItems.add(new Item("1", "a"));
        dummyItems.add(new Item("2", "b"));
        dummyItems.add(new Item("3", "c"));
        dummyItems.add(new Item("4", "d"));

        View layout = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        RecyclerView RecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(lm);

        RecyclerView.Adapter adapter = new RecyclerViewAdapter(dummyItems);
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
