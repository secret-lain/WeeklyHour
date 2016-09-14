package org.weeklyhour.Fragment.RecyclerListFragment.testExpandable;


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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class testExpandableRecyclerListFragment extends Fragment {
    ArrayList<parentItem> dummyParentItems;
    ArrayList<childItem> dummyChildItems;

    private RecyclerView.Adapter adapter;

       public testExpandableRecyclerListFragment() {
        // Required empty public constructor
    }

    public static testExpandableRecyclerListFragment newInstance() {
        testExpandableRecyclerListFragment fragment = new testExpandableRecyclerListFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dummyParentItems = new ArrayList<>();

        List<childItem> childrenList = new ArrayList<>();
        childrenList.add(new childItem("a"));
        childrenList.add(new childItem("a2"));
        dummyParentItems.add(new parentItem("A", childrenList));

        dummyParentItems.add(new parentItem("B", childrenList));

        View layout = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        RecyclerView RecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(lm);

        adapter = new expandableAdapter(getContext() ,dummyParentItems);
        RecyclerView.setAdapter(adapter);

        //구석에 있는 플러스버튼이다.
        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setLongClickable(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 별기능이 있진 않고 그냥 잠깐 액션한다. 스낵바 잠깐 뿅
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Intent newItemActivityIntent = new Intent(getContext(), newItemActivity.class);
                //startActivityForResult(newItemActivityIntent, 0);
                //requestCode 0 으로 newItemActivityIntent를 호출(MainActivity는 생존)
            }
        });

        return layout;
    }
}
