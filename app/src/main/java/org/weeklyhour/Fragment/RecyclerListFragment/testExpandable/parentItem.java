package org.weeklyhour.Fragment.RecyclerListFragment.testExpandable;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by admin on 2016-09-14.
 */
public class parentItem implements ParentListItem {
    public String text;
    public List<? extends childItem> mChildrenList;

    public parentItem(String text, List<? extends childItem> ChildrenList){
        this.text = text;
        mChildrenList = ChildrenList;
    }

    @Override
    public List<? extends childItem> getChildItemList() {
        return mChildrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public Object get(int position){
        return mChildrenList.get(position);
    }
}
