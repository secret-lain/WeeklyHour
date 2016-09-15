package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * parentItem 의 VO
 * parentItem 은 childItem을 가진다.
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

        //오픈소스라서 List<>를 받아야 하지만 여러개의 childItem이 필요하지 않음.
        mChildrenItem = new ArrayList<>(1);
        mChildrenItem.add(childrenItem);
        currDay = 0;
    }

    //편집시 setMemo -> notifyDataChange to Adpater
    public void setMemo(String newMemoString){
        mChildrenItem.get(0).memo = newMemoString;
    }
    public String getMemo(){return mChildrenItem.get(0).memo;}

    @Override
    public List<?> getChildItemList() {
        return mChildrenItem;
    }

    //시작할때 열려있는지?
    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


}
