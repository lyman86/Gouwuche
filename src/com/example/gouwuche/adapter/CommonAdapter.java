package com.example.gouwuche.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.gouwuche.R;
import com.example.gouwuche.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public abstract class CommonAdapter<T> extends BaseAdapter {

	protected List<T> list;
	protected LayoutInflater commonInflater;
	protected Context context;
	private int layoutId;
	public int position;
	protected DisplayImageOptions baseOptions = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.default2)
	.bitmapConfig(Bitmap.Config.ARGB_8888)
	.showImageForEmptyUri(R.drawable.default2)
	.showImageOnFail(R.drawable.default2).cacheInMemory(true)
	.cacheOnDisk(true).displayer(new FadeInBitmapDisplayer(2000))
	.build();

	public CommonAdapter(Context context, List<T> list, int layoutId) {
		this.context = context;
		this.list = list;
		this.layoutId = layoutId;
	}
	
	public CommonAdapter() {}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.position = position;
		ViewHolder viewHolder = ViewHolder.get(context, convertView, parent,
				layoutId, position);
		
		convert(viewHolder, getItem(position));
		
		return viewHolder.getConvertView();
	}

	public abstract void convert(ViewHolder viewHolder, T t);

}
