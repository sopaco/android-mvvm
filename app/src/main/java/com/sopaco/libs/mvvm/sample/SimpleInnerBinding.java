package com.sopaco.libs.mvvm.sample;

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

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class SimpleInnerBinding extends Fragment {

    @FromId(R.id.ivHead)
    @BindProperty("HeadObject")
    private View ivHead;
    @FromId(R.id.tvName)
    @BindProperty("name")
    private View tvName;
    @FromId(R.id.tvDescription)
    @BindProperty("Description")
    private View tvDescription;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_sample_innerbinding, container, false);
        Injector.inject(this, view, true);
        VMBinder.getDefault().bind(this, new Data());
        return view;
    }

    static class Data {
        public String name = "Ballen Speed Runner";
        public Object HeadObject = R.mipmap.ic_launcher;
        public String getDescription() {
            return "是独立开发建设的房间数量大幅降低江苏大丰；； 京东方；是的肌肤接收；看巅峰时代； 发射点发射点发士大夫大师傅似的犯得上的；方式；地方发士大夫是；开发商肯定是的";
        }
    }
}
