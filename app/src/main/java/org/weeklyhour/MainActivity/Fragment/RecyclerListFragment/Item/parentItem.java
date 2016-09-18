package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
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
    public RealmList<childItem> mChildrenItem;

    public parentItem(){
    }

    public parentItem(String taskName, int maxDay, int progressBarColor, childItem childrenItem){
        this.progressBarColor = progressBarColor;
        this.taskName = taskName;
        this.maxDay = maxDay;

        currDay = 0;
    }

    //편집시 setMemo -> notifyDataChange to Adpater
    public void setMemo(String newMemoString){
        mChildrenItem.get(0).memo = newMemoString;
    }
    public String getMemo(){return mChildrenItem.get(0).memo;}


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


}
