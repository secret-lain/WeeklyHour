package org.weeklyhour;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by admin on 2016-09-24.
 */

public class ApplicationForCustomFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/MiSaeng.ttf"));
    }
}
