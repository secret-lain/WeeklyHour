package org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.Item;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 2016-09-23.
 */

public class item extends RealmObject {
    @PrimaryKey
    public int id;

    public String taskName;
    public int progressColor;
    public int maxHour;
    public int currHour;
}
