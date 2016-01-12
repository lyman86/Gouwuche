package com.ly.shopsystem.http;


import android.os.Message;

import com.example.gouwuche.bean.CustomBean;
import com.example.gouwuche.utils.JsonUtils;

public class HttpUpdateProFromCart extends BaseHttpLoad {
	
	private onUpdateFromCartListener mListener;

	public interface onUpdateFromCartListener {
		void UpdateSuccess(String success);

		void UpdateFailed(String failed);
	}

	public void setOnUpdateFromCartListener(onUpdateFromCartListener mListener) {
		this.mListener = mListener;
	}

	public HttpUpdateProFromCart(String url) {
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
				mListener.UpdateSuccess(result.info);
			} else {
				mListener.UpdateFailed(result.info);
			}
		}
	}

}
