package com.project.xiaoji.life;

import android.util.Log;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class APIManager {
    public static final boolean isLog=true;
    public static final String BASEURL="http://api.mglife.me/";
    public static void log(String str){
        if (isLog){
            Log.i("xiaoji", str);
        }
    }
}
