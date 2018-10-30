package com.wei.mypic.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtil {
    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();//
        windowManager.getDefaultDisplay().getMetrics(outMetrics);//
        return outMetrics.heightPixels;
    }
}
