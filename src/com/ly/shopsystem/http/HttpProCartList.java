package com.ly.shopsystem.http;

import java.util.List;

import android.os.Message;

import com.example.gouwuche.bean.ProCartListBean;
import com.example.gouwuche.utils.JsonUtils;

public class HttpProCartList extends BaseHttpLoad {

	private onProCartListListener mListener;

	public interface onProCartListListener {
		void loadSuccess(List<ProCartListBean> result);

		void loadFailed(String failed);
	}

	public void setOnProCartListListener(onProCartListListener mListener) {
		this.mListener = mListener;
	}
	
	public HttpProCartList(String url) {
		super(url);
	}

	
	
	@Override
	public void doIt() {
		myThread = new Thread() {
			@Override
			public void run() {
				try {
					List<ProCartListBean> result = JsonUtils.getProListJson(url);
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
		List<ProCartListBean> result = (List<ProCartListBean>) msg.obj;
		//System.out.println(result.contentid+"  HttpLoadContentPro  handleMyMessage");
		if (mListener != null) {
			if (result.size()==1) {
				mListener.loadFailed(result.get(0).info);
				System.out.println(result.size()+"   >>>mListener.loadFailed(result.get(0).info)");
			}else{
				System.out.println(result.size()+"   >>>mListener.loadSuccess(result)");
				mListener.loadSuccess(result);
			}
			
		}

	}

}
