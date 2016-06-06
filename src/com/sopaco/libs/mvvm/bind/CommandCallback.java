package com.sopaco.libs.mvvm.bind;

import android.view.View;

public interface CommandCallback<T> {

    void execute(View sender, T argument);
}
