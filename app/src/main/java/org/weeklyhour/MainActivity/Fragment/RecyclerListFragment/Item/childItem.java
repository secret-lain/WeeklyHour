package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 2016-09-14.
 */
public class childItem extends RealmObject{

    @PrimaryKey
    public int id;

    public String memo;

    public childItem(){}
    public childItem(String memo){
        this.memo = memo;
    }
}
