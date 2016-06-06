package com.sopaco.libs.mvvm.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopaco.libs.mvvm.Injector;
import com.sopaco.libs.mvvm.R;
import com.sopaco.libs.mvvm.VMBinder;
import com.sopaco.libs.mvvm.annotation.BindProperty;
import com.sopaco.libs.mvvm.annotation.FromId;
import com.sopaco.libs.mvvm.bind.list.LayoutPropertyMap;
import com.sopaco.libs.mvvm.sample.misc.CustomControl;

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class CustomControlBinding extends Fragment {

    @FromId(R.id.ctlCC1)
    @BindProperty(Path = "Data", Target = "DataContext")
    private View ctlCC1;
    @FromId(R.id.layoutRoot)
    private ViewGroup layoutRoot;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Result result = mockResult();
        View view = inflater.inflate(R.layout.page_sample_customcontrol, container, false);
        Injector.inject(this, view, true);
        VMBinder.getDefault().bind(this, result);

        CustomControl ctlCCAnother = new CustomControl(getActivity());
        layoutRoot.addView(ctlCCAnother);
        result = mockResult();
        result.Data.name = "this is a control created in runtime";
        result.Data.textColor = Color.RED;

        LayoutPropertyMap layoutPropMap = new LayoutPropertyMap();
        layoutPropMap.add(LayoutPropertyMap.ViewRootSelf, "Data", "DataContext");
        VMBinder.getDefault().bindFlatView(ctlCCAnother, result, layoutPropMap);
        return view;
    }

    private Result mockResult() {
        CustomControl.SampleDataType sampleData = new CustomControl.SampleDataType();
        sampleData.minHeight = 200;
        sampleData.name = "this is a control created in xml";
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
