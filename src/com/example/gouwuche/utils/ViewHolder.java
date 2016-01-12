package com.example.gouwuche.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHolder {
	SparseArray<View> mViews;
	private int position;
	private View mConvertView;
	private Context context;
	public ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.context = context;
		this.position = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);

	}

	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.position = position;
			return holder;
		}

	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * \ 通过
	 * 
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	
	public ViewHolder setText(int viewId,String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
	public ViewHolder setImageResource(int viewId,int scouce){
		ImageView iv = getView(viewId);
		iv.setImageResource(scouce);
		return this;
	}
	
	public ViewHolder setTextAddListener(int viewId){
		final TextView tv = getView(viewId);
		
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mListener!=null) {
						mListener.onViewAddClick(tv, position);
					}
					
				}
			});
		
		
		return this;
	}
	
	public ViewHolder setTextSubListener(int viewId){
		final TextView tv = getView(viewId);
		
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener!=null) {
					mListener2.onViewSubClick(tv, position);
				}
				
			}
		});
		return this;
	}
	
	public int getPosition(){
		return position;
	}
	
	
	private onViewAddClickListener mListener;
	public interface onViewAddClickListener{
		void onViewAddClick(View v,int pos);
	}
	
	public void setOnViewAddClickListener(onViewAddClickListener mListener){
		this.mListener = mListener;
	}
	
	private onViewSubClickListener mListener2;
	public interface onViewSubClickListener{
		void onViewSubClick(View v,int pos);
	}
	
	public void setOnViewSubClickListener(onViewSubClickListener mListener2){
		this.mListener2 = mListener2;
	}

}
