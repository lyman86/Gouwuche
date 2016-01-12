package com.example.gouwuche.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.gouwuche.adapter.ProCartListAdapter;

public class MyAlertDialog {
	
	private Context context;
	private String url;
	private ProCartListAdapter adapter;
	private int position;
	public MyAlertDialog(Context context,String url,ProCartListAdapter adapter,int position) {
		this.context = context;
		this.url = url;
		this.position = position;
		this.adapter = adapter;
	}

	public void showDialogOne(String message,String title,int icon){
		 AlertDialog.Builder builder = new Builder(context);
		 builder.setMessage(message);
		 builder.setTitle(title);
		 builder.setIcon(icon);
		 builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LoadContent loadContent = new LoadContent(context, url);
				loadContent.deleteProFromCart("删除中",adapter,position);
				dialog.dismiss();
			}
		});
		 builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		 
		 builder.create().show();
	}
	

}
