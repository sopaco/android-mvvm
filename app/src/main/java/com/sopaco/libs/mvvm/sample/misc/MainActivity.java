package com.sopaco.libs.mvvm.sample.misc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anderfans.common.AppBase;
import com.sopaco.libs.mvvm.Injector;
import com.sopaco.libs.mvvm.R;
import com.sopaco.libs.mvvm.annotation.FromId;
import com.sopaco.libs.mvvm.bind.BindingCommandType;
import com.sopaco.libs.mvvm.bind.CommandCallback;
import com.sopaco.libs.mvvm.bind.list.LayoutPropertyMap;
import com.sopaco.libs.mvvm.bind.list.MvvmArrayAdapter;
import com.sopaco.libs.mvvm.property.CommandMap;
import com.sopaco.libs.mvvm.sample.CustomControlBinding;
import com.sopaco.libs.mvvm.sample.FlatViewBinding;
import com.sopaco.libs.mvvm.sample.ImagePropertyBinding;
import com.sopaco.libs.mvvm.sample.ListBindingFragment;
import com.sopaco.libs.mvvm.sample.MultiPropertyBinding;
import com.sopaco.libs.mvvm.sample.SimpleInnerBinding;

import java.util.ArrayList;


public class MainActivity extends Activity {

    @FromId(R.id.lvList)
    private ListView lvList;
    @FromId(R.id.areaFragment)
    private ViewGroup areaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBase.setContext(getApplication());
        setContentView(R.layout.activity_main);
        Injector.inject(this);

        ArrayList<SampleItem> sampleDataSource = new ArrayList<SampleItem>();
        sampleDataSource.add(SampleItem.create("列表绑定", ListBindingFragment.class));
        sampleDataSource.add(SampleItem.create("直接view绑定", FlatViewBinding.class));
        sampleDataSource.add(SampleItem.create("注解绑定", SimpleInnerBinding.class));
        sampleDataSource.add(SampleItem.create("多属性绑定", MultiPropertyBinding.class));
        sampleDataSource.add(SampleItem.create("自定义控件属性", CustomControlBinding.class));
        sampleDataSource.add(SampleItem.create("图片属性绑定", ImagePropertyBinding.class));
//        sampleDataSource.add(SampleItem.create("性能数据评估", PerformanceReporter.class));
        LayoutPropertyMap layoutPropMap = new LayoutPropertyMap();
        layoutPropMap.add(R.id.tvName, "name");

        CommandMap cmdMap = new CommandMap();
        cmdMap.add(R.id.layoutRoot, BindingCommandType.OnClick, new CommandCallback<SampleItem>() {
            @Override
            public void execute(View sender, SampleItem argument) {
                navigateToSample(argument.sampleTargetClazz);
            }
        });

        MvvmArrayAdapter<SampleItem> dataAdapter = new MvvmArrayAdapter<SampleItem>(this, sampleDataSource, R.layout.item_simple, layoutPropMap, cmdMap);
        lvList.setAdapter(dataAdapter);
    }

    private void navigateToSample(Class<? extends Fragment> sampleTargetClazz) {
        Intent intent = new Intent(this, GenericActivity.class);
        intent.putExtra("target_fragment_clazz", sampleTargetClazz);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class SampleItem {
        public static SampleItem create(String str, Class<? extends Fragment> clazz) {
            SampleItem si = new SampleItem();
            si.name = str;
            si.sampleTargetClazz = clazz;
            return si;
        }
        public String name;
        public Class<? extends Fragment> sampleTargetClazz;
    }
}
