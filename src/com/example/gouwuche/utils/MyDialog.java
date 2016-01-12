package com.example.gouwuche.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.gouwuche.R;



public class MyDialog extends Dialog{
	private String info;
	private Context context;
	private TextView tvDialog;
	public MyDialog(Context context,String info) {
		super(context);
		this.info = info;
		this.context = context;
	}
	
	public void createDialog(){
		LayoutInflater inflater = LayoutInflater.from(context);
		View rootView = inflater.inflate(R.layout.dialog, null);
		initView(rootView);
		setContentView(rootView);
		setTitle("消息");
		tvDialog.setText(info);
		show();
	}

	private void initView(View rootView) {
			tvDialog = (TextView) rootView.findViewById(R.id.id_dialog_info);
	}
	
	
	

}
