package org.weeklyhour.Fragment.RecyclerListFragment;

/**
 * Created by admin on 2016-09-13.
 * RecyclerListFragment
 */
public class parentitem {
    int progressBarColor;
    public String taskName;
    public int maxDay;

    public parentitem(String taskName, int maxDay, int progressBarColor){
        this.progressBarColor = progressBarColor;
        this.taskName = taskName;
        this.maxDay = maxDay;
    }
}
