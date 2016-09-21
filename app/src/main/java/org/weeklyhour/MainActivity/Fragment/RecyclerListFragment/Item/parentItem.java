package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.ViewHolder.parentViewHolder;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * parentItem 의 VO
 * parentItem 은 childItem을 가진다.
 */
public class parentItem extends RealmObject implements ParentListItem {

    @PrimaryKey
    public int id;

    public int progressBarColor;
    public String taskName;
    public int maxDay;
    public int currDay;

    @Ignore
    private Realm realm = Realm.getDefaultInstance();

    @Ignore
    private parentViewHolder.childOpenCheckCallback childOpenCheckCallback;
    public void callCloseChild(){
        childOpenCheckCallback.onChildOpenCheckCallback();
    }
    public void setOnChildOpenCheckCallback(parentViewHolder.childOpenCheckCallback callback){
        childOpenCheckCallback = callback;
    }

    public parentItem(){}


    //Expandable-RecyclerView 가 사용하기 위해 쓰는 Override 함수.
    @Override
    public List<?> getChildItemList() {
        return realm.where(childItem.class).equalTo("id",id).findAll();
    }

    //시작할때 열려있는지?
    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }



    public static void swap(parentItem left, parentItem right) throws CloneNotSupportedException {
        int dummy_progressBarColor;
        String dummy_taskName;
        int dummy_maxDay;
        int dummy_currDay;

        dummy_currDay = right.currDay;
        dummy_maxDay = right.maxDay;
        dummy_progressBarColor = right.progressBarColor;
        dummy_taskName = right.taskName;


        right.currDay = left.currDay;
        right.maxDay = left.maxDay;
        right.progressBarColor = left.progressBarColor;
        right.taskName = left.taskName;

        left.currDay = dummy_currDay;
        left.maxDay = dummy_maxDay;
        left.progressBarColor = dummy_progressBarColor;
        left.taskName = dummy_taskName;
    }

}
