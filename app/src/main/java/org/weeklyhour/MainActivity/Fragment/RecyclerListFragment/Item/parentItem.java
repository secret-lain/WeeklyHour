package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * parentItem 은 childItem을 가져야 하지만,
 * RealmResults<> 가 실시간 업데이트 되는점에 착안,
 * Expandable-RecyclerView가 Parent-Child item의 연결을 확인하려고 만든 getChildItemList에서
 * 같은 id의 childItem을 그냥 return 해버리는 방법으로 구현.
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

    public parentItem(){}
}
