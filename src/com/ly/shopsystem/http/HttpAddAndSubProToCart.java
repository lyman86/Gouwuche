package com.ly.shopsystem.http;


import com.example.gouwuche.bean.CustomBean;
import com.example.gouwuche.utils.JsonUtils;



import android.os.Message;

public class HttpAddAndSubProToCart extends BaseHttpLoad {
	
	private onAddProToCartListener mListener;

	public interface onAddProToCartListener {
		void addOrSubSuccess(String success);

		void addOrSubFailed(String failed);
	}

	public void setOnAddProToCartListener(onAddProToCartListener mListener) {
		this.mListener = mListener;
	}

	public HttpAddAndSubProToCart(String url) {
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
				mListener.addOrSubSuccess(result.info);
			} else {
				mListener.addOrSubFailed(result.info);
			}
		}

	}

}
