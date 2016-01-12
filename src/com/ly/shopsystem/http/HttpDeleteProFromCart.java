package com.ly.shopsystem.http;


import android.os.Message;

import com.example.gouwuche.bean.CustomBean;
import com.example.gouwuche.utils.JsonUtils;

public class HttpDeleteProFromCart extends BaseHttpLoad {
	
	private onDeleteFromCartListener mListener;

	public interface onDeleteFromCartListener {
		void deleteSuccess(String success);

		void deleteFailed(String failed);
	}

	public void setOnDeleteFromCartListener(onDeleteFromCartListener mListener) {
		this.mListener = mListener;
	}

	public HttpDeleteProFromCart(String url) {
		super(url);
	}

	@Override
	public void doIt() {
		myThread = new Thread() {
			@Override
			public void run() {
				try {

					CustomBean result = JsonUtils.getCustomBean(url);
					Message message = Message.obtain();
					message.obj = result;
					mHandler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

	}

	@Override
	public void handleMyMessage(Message msg) {
		CustomBean result = (CustomBean) msg.obj;
		if (mListener != null) {
			if (result.code.equals("success")) {
				mListener.deleteSuccess(result.info);
				System.out.println("loadSuccess");
			} else {
				mListener.deleteFailed(result.info);
				System.out.println("loadFailed");
			}
		}
	}

}
