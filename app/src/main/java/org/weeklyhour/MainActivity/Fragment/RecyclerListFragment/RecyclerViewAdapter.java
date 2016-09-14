package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.childItem;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item.parentItem;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.ViewHolder.childViewHolder;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.ViewHolder.parentViewHolder;
import org.weeklyhour.MainActivity.R;

import java.util.List;
import java.util.Random;

/**
 * 어댑터는 RecyclerView에게 데이터를 전달해주는 역할을 한다.
 * 최초 선언시 RecyclerView에서 사용될 ArrayList<parentItem> 을 갖는다.
 * Created by admin on 2016-09-07.
 */
public class RecyclerViewAdapter extends ExpandableRecyclerAdapter<parentViewHolder, childViewHolder> {
    private static final Random rnd = new Random();
    private List<? extends ParentListItem> mParentItems;

    public RecyclerViewAdapter(@NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mParentItems = parentItemList;
    }

    @Override
    public parentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentView = LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.fragment_recycler_parent_item, parentViewGroup, false);
        return new parentViewHolder(parentView);
    }

    @Override
    public childViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = LayoutInflater.from(childViewGroup.getContext())
                .inflate(R.layout.fragment_recycler_child_item, childViewGroup, false);
        return new childViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(final parentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        parentItem pItem = (parentItem)parentListItem;
        
        parentViewHolder.day.setText(pItem.currDay + " / " + pItem.maxDay);
        parentViewHolder.taskName.setText(pItem.taskName);

        //배경은 흰색으로 고정
        parentViewHolder.progressbar.setProgressBackgroundColor(Color.parseColor("#FFFFFF"));

        //Adapter에 적용된 ArrayList에서 순차적으로 꺼내와서 실제 객체에 적용
        parentViewHolder.progressbar.setProgressColor(pItem.progressBarColor);
        parentViewHolder.progressbar.setMax(pItem.maxDay);
        parentViewHolder.progressbar.setProgress(rnd.nextInt(pItem.maxDay + 1));

        //toggleImage의 경우 protected method때문에 new ParentViewHolder에서 정의됨
    }

    @Override
    public void onBindChildViewHolder(childViewHolder childViewHolder, int position, Object childListItem) {
        childViewHolder.memo.setText(((childItem)childListItem).memo);

    }
}
