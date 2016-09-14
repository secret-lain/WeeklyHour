package org.weeklyhour.Fragment.RecyclerListFragment.testExpandable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import org.weeklyhour.Fragment.RecyclerListFragment.testExpandable.*;
import org.weeklyhour.Activity.R;

import java.util.List;

/**
 * Created by admin on 2016-09-14.
 */
public class expandableAdapter extends ExpandableRecyclerAdapter<parentViewHolder, childViewHolder> {
    private LayoutInflater mInflator;

    public expandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList){
        super(parentItemList);

        mInflator = LayoutInflater.from(context);
    }

    @Override
    public parentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentView = mInflator.inflate(R.layout.test_expandable_item, parentViewGroup, false);
        return new parentViewHolder(parentView);
    }

    @Override
    public childViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = mInflator.inflate(R.layout.test_expandable_childitem, childViewGroup, false);
        return new childViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(parentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        parentItem pItem = (parentItem) parentListItem;
        parentViewHolder.mCrimeTitleTextView.setText(pItem.text);
    }

    @Override
    public void onBindChildViewHolder(childViewHolder childViewHolder, int position, Object childListItem) {
        childItem cItem = (childItem) childListItem;
        childViewHolder.mCrimeDateText.setText(cItem.text);
    }

}
