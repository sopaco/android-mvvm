package com.sopaco.libs.mvvm.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopaco.libs.mvvm.VMBinder;
import com.sopaco.libs.mvvm.bind.list.LayoutPropertyMap;
import com.sopaco.libs.mvvm.sample.misc.CustomControl;

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class FlatViewBinding extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CustomControl view = new CustomControl(getActivity());
        Result result = mockResult();

        LayoutPropertyMap layoutPropMap = new LayoutPropertyMap();
        layoutPropMap.add(LayoutPropertyMap.ViewRootSelf, "Data", "DataContext");
        VMBinder.getDefault().bindFlatView(view, result, layoutPropMap);
        return view;
    }

    private Result mockResult() {
        CustomControl.SampleDataType sampleData = new CustomControl.SampleDataType();
        sampleData.minHeight = 200;
        sampleData.name = "bind to flat view";
        sampleData.backgroundColor = Color.DKGRAY;
        sampleData.textColor = Color.GREEN;
        Result result = new Result();
        result.Data = sampleData;
        return result;
    }

    static class Result {
        public CustomControl.SampleDataType Data;
    }
}
