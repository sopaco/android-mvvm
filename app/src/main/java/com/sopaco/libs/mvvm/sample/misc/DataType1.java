package com.sopaco.libs.mvvm.sample.misc;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class DataType1 {
    private static final Random seed = new Random();

    public String title;
    private String description;
    private String previewUrl;
    public int titleTextColor = Color.RED;
    public int descriptionTextColor = Color.BLUE;

    public static DataType1 mock() {
        DataType1 ret = new DataType1();
        ret.title = "title..." + seed.nextInt(500);
        ret.description = "this is description";
        ret.previewUrl = "http://cn.bing.com/s/me/MeFrameworkSpriteV2.png";
        return ret;
    }
}
