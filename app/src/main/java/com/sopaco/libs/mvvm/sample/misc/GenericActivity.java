package com.sopaco.libs.mvvm.sample.misc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sopaco.libs.mvvm.R;

/**
 * Created by meng.jiang on 2015/5/6.
 */
public class GenericActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Class<? extends Fragment> fragmentClazz = (Class<? extends Fragment>) getIntent().getSerializableExtra("target_fragment_clazz");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = null;
        try {
            fragment = fragmentClazz.newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("fragment create failure, consider whether a empty constructor provided..." + fragmentClazz.getName(), ex);
        }
        transaction.add(R.id.areaFragment, fragment);
        transaction.commit();
    }
}
