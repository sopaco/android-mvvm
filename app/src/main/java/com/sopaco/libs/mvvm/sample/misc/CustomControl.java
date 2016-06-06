package com.sopaco.libs.mvvm.sample.misc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class CustomControl extends TextView {

    public static class SampleDataType {
        public String name;
        public int textColor;
        public int backgroundColor;
        public int minHeight;
    }

    private SampleDataType data;

    public CustomControl(Context context) {
        super(context);
    }

    public CustomControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDataContext(SampleDataType data) {
        this.data = data;
        setMinHeight(data.minHeight);
        setText(data.name);
        setTextColor(data.textColor);
        setBackgroundColor(data.backgroundColor);
    }
}
