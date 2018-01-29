package com.runtai.newdexintong.module.home.jpush.command;

import android.content.Context;

public abstract class JPushCommand {
	
	public abstract void execute(Context context, String json, String title);
}
