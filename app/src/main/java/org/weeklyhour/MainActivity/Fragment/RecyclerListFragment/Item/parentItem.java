package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016-09-13.
 * RecyclerListFragment
 */
public class parentItem implements ParentListItem {
    public int progressBarColor;
    public String taskName;
    public int maxDay;
    public int currDay;
    public List<childItem> mChildrenItem;

    public parentItem(String taskName, int maxDay, int progressBarColor, childItem childrenItem){
        this.progressBarColor = progressBarColor;
        this.taskName = taskName;
        this.maxDay = maxDay;
        mChildrenItem = new ArrayList<>(1);
        mChildrenItem.add(childrenItem);
        currDay = 0;
    }

    public void setMemo(String newMemoString){
        mChildrenItem.get(0).memo = newMemoString;
    }

    @Override
    public List<?> getChildItemList() {
        return mChildrenItem;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


}
