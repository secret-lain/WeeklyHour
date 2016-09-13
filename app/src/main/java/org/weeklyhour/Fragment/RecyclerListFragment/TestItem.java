package org.weeklyhour.Fragment.RecyclerListFragment;

import android.graphics.Color;

/**
 * Created by admin on 2016-09-13.
 */
public class testItem {
    int progressBarColor;
    public String taskName;
    public int maxDay;

    public testItem(String taskName, int maxDay, int progressBarColor){
        this.progressBarColor = progressBarColor;
        this.taskName = taskName;
        this.maxDay = maxDay;
    }
}
