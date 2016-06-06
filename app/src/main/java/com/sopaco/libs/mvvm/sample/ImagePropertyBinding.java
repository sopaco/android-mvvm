package com.sopaco.libs.mvvm.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sopaco.libs.mvvm.Injector;
import com.sopaco.libs.mvvm.R;
import com.sopaco.libs.mvvm.VMBinder;
import com.sopaco.libs.mvvm.annotation.BindProperty;
import com.sopaco.libs.mvvm.annotation.FromId;

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class ImagePropertyBinding extends Fragment {

    @FromId(R.id.ivImage1)
    @BindProperty("ImageObject1")
    private ImageView ivImage1;
    @FromId(R.id.ivImage2)
    @BindProperty("ImageObject2")
    private ImageView ivImage2;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_sample_imagepropertybinding, container, false);
        Injector.inject(this, view, true);

        Data data = new Data();
        VMBinder.getDefault().bind(this, data);
        return view;
    }

    static class Data {
        public Object getImageObject1() {
            return "http://img.ithome.com/newsuploadfiles/2015/12/20151209_155325_306.png";
        }

        public Object getImageObject2() {
            return R.mipmap.ic_launcher;
        }
    }
}
