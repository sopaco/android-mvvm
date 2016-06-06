package com.sopaco.libs.mvvm.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sopaco.libs.mvvm.Injector;
import com.sopaco.libs.mvvm.R;
import com.sopaco.libs.mvvm.VMBinder;
import com.sopaco.libs.mvvm.annotation.BindProperty;
import com.sopaco.libs.mvvm.annotation.FromId;
import com.sopaco.libs.mvvm.bind.BindingCommandType;
import com.sopaco.libs.mvvm.bind.CommandCallback;
import com.sopaco.libs.mvvm.bind.list.LayoutPropertyMap;
import com.sopaco.libs.mvvm.bind.list.MvvmArrayAdapter;
import com.sopaco.libs.mvvm.property.CommandMap;
import com.sopaco.libs.mvvm.sample.misc.DataType1;

import java.util.ArrayList;

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class MultiPropertyBinding extends Fragment {
    @FromId(R.id.tvTitle)
    @BindProperty("title")
    private TextView tvTitle;

    @FromId(R.id.tvDescription)
    @BindProperty("description")
    private TextView tvDescription;

    @FromId(R.id.ivPreview)
    @BindProperty("previewUrl")
    private ImageView ivPreview;

    @FromId(R.id.lvArrayList)
    private ListView lvArrayList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_sample_fragment_list, container, false);
        Injector.inject(this, view, true);
        DataType1 data = DataType1.mock();
        ArrayList<DataType1> arr = new ArrayList<DataType1>();
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());
        arr.add(DataType1.mock());

        VMBinder.getDefault().bind(this, data);
        LayoutPropertyMap propertyMap = new LayoutPropertyMap();
        propertyMap.add(R.id.tvTitle, "title");
        propertyMap.add(R.id.tvTitle, "titleTextColor", "TextColor");
        propertyMap.add(R.id.tvDescription, "description");
        propertyMap.add(R.id.tvDescription, "descriptionTextColor", "TextColor");
        propertyMap.add(R.id.ivPreview, "previewUrl");
        CommandMap cmdMap = new CommandMap();
        cmdMap.add(R.id.layoutRoot, BindingCommandType.OnClick, new CommandCallback<Object>() {

            @Override
            public void execute(View sender, Object argument) {
                Toast.makeText(getActivity(), ((DataType1) argument).title, Toast.LENGTH_SHORT).show();
            }
        });
        MvvmArrayAdapter<DataType1> mvvmArrayListAdapter = new MvvmArrayAdapter<DataType1>(getActivity(), arr, R.layout.layout_item, propertyMap, cmdMap);
        lvArrayList.setAdapter(mvvmArrayListAdapter);
        return view;
    }
}
