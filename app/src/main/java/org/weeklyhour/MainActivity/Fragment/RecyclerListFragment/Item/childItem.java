package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * childItem.
 * List형태로 parentItem에 종속된다.
 * List형태인 이유는 Expandable-RecyclerView 오픈소스가 그렇게 만들어졌다.
 * Realm을 사용하기 때문에 parentItem은 RealmResults<childItem> 형식으로 반환한다.
 */
public class childItem extends RealmObject{
    @PrimaryKey
    public int id;

    public String memo;

    public childItem(){}

    public static void swap(childItem left, childItem right){
        String dummy_memo;

        dummy_memo = right.memo;

        right.memo = left.memo;
        left.memo = dummy_memo;
    }
}
