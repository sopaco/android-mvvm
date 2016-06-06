package com.sopaco.libs.mvvm.bind.list;

import android.database.Cursor;

public interface ICursorParser<T> {
	T parseFromCursor(Cursor cursor) throws Exception;
}
