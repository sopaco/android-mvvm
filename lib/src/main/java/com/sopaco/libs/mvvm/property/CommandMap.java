package com.sopaco.libs.mvvm.property;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import com.anderfans.common.log.LogRoot;
import com.sopaco.libs.mvvm.bind.BindingCommandType;
import com.sopaco.libs.mvvm.bind.CommandCallback;

@SuppressLint("UseSparseArrays")
public class CommandMap {
	private Map<Integer, Map<BindingCommandType, CommandCallback>> map;
	
	public CommandMap() {
		map = new HashMap<Integer, Map<BindingCommandType,CommandCallback>>();
	}
	
	public void add(int resId, BindingCommandType cmdType, CommandCallback callback) {
		if(!map.containsKey(resId)) {
			map.put(resId, new HashMap<BindingCommandType, CommandCallback>());
		}
		map.get(resId).put(cmdType, callback);
	}
	
	public CommandCallback findCommand(int resId, BindingCommandType cmdType) {
		Map<BindingCommandType, CommandCallback> cmdMap = map.get(resId);
		if(cmdMap == null) {
			return null;
		}
		return cmdMap.get(cmdType);
	}

	public void attachTo(final View itemView, final Object itemData) {
		Iterator<Integer> resIdsIter = map.keySet().iterator();
		while(resIdsIter.hasNext()) {
			Integer resId = resIdsIter.next();
			final View view = itemView.findViewById(resId);
			if(view == null) {
				continue;
			}
			Map<BindingCommandType, CommandCallback> commands = map.get(resId);
			Set<Entry<BindingCommandType, CommandCallback>> pairs = commands.entrySet();
			for(final Entry<BindingCommandType, CommandCallback> pair : pairs) {
				BindingCommandType cmdType = pair.getKey();
				if(cmdType == BindingCommandType.OnClick) {
                    view.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
                            pair.getValue().execute(itemView, itemData);
						}
					});
				} else if(cmdType == BindingCommandType.OnLongClick) {
                    view.setOnLongClickListener(new OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							pair.getValue().execute(itemView, itemData);
							return true;
						}
					});
				} else if(cmdType == BindingCommandType.Custom) {
					LogRoot.debug("custom command not supported..." + itemData.toString());
				}
			}
		}
	}
}
