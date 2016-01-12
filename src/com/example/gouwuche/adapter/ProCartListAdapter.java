package com.example.gouwuche.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gouwuche.R;
import com.example.gouwuche.bean.ProCartListBean;
import com.example.gouwuche.utils.BaseUrl;
import com.example.gouwuche.utils.LoadContent;
import com.example.gouwuche.utils.MyAlertDialog;
import com.example.gouwuche.utils.StringUtils;
import com.example.gouwuche.utils.ViewHolder;
import com.example.gouwuche.utils.ViewHolder.onViewAddClickListener;
import com.example.gouwuche.utils.ViewHolder.onViewSubClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ProCartListAdapter extends CommonAdapter<ProCartListBean> {
	private Activity mActivity;
	//每个商品数量的集合
	private List<Integer> numbers;
	//删除单个商品的链接
	private String delteUrl;
	//被选中商品 的数量
	private int selectCount = 0;
	
	ProCartListBean bean;
	private List<Boolean>isNumbersChanged;

	private onSelectListener mListener;
	private DecimalFormat decimalFormat;
	
	private boolean isClick = false;
	
	//商品的总价格
	private float all = 0.0f;
	private long secondTime;
	
	
	private final int ADD = 1;

	public interface onSelectListener {
		void onSelect(String allPrice);
	}

	public void setOnSelectListener(onSelectListener mListener) {
		this.mListener = mListener;
	}

	public ProCartListAdapter(Context context, List<ProCartListBean> list,
			int layoutId, Activity activity) {
		super(context, list, layoutId);
		mActivity = activity;
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
		//初始化每个商品的数量
		numbers = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			numbers.add(Integer.parseInt(list.get(i).number));
		}
		
		List<String> subTotals = new ArrayList<>();
		List<Boolean> isSelects = new ArrayList<>();
		bean = new ProCartListBean();
		isNumbersChanged = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			subTotals.add(String.valueOf(Float.parseFloat(list.get(i).price)* Integer.parseInt(list.get(i).number)));
			isNumbersChanged.add(false);
			isSelects.add(false);
		}
		bean.setSubTotals(subTotals);
		bean.setIsSelects(isSelects);
		decimalFormat = new DecimalFormat("0.00");
		new MyThread().start();
		
	}

	public ProCartListAdapter() {
	}

	
	@Override
	public void convert(final ViewHolder viewHolder, final ProCartListBean t) {
		String subTotal = decimalFormat.format(Float.parseFloat(t.price)* numbers.get(viewHolder.getPosition()));
		viewHolder
				.setText(R.id.tv_title, t.title)
				.setText(R.id.tv_price, "批发价：" + t.price + "￥")
				.setText(R.id.tv_pinpai, "品牌：" + t.brand)
				.setText(R.id.tv_item, "订货号：" + t.item)
				.setText(R.id.tv_xinghao, "型号：" + t.model)
				.setText(R.id.tv_pro_num,String.valueOf(numbers.get(viewHolder.getPosition())))
				.setText(R.id.tv_item_price, "小计: " + subTotal);
		
			    viewHolder.setTextAddListener(R.id.tv_add_price)
			    .setOnViewAddClickListener(new onViewAddClickListener() {

				@Override
				public void onViewAddClick(View v, final int pos) {
					if (LoadContent.isNetworkAvailable(context)) {
						addOrSubLogic(1,viewHolder,t,pos);
						setUpdate();
					}else{
						Toast.makeText(context, "网络问题", Toast.LENGTH_SHORT).show();
					}
					
					
				
				}
			});

	viewHolder.setTextSubListener(R.id.tv_sub_price)
			.setOnViewSubClickListener(new onViewSubClickListener() {

				@Override
				public void onViewSubClick(View v, final int pos) {
					if (numbers.get(pos) <= 1) {
						viewHolder.getView(R.id.tv_sub_price).setClickable(false);
					} else {
								if (LoadContent.isNetworkAvailable(context)) {
									addOrSubLogic(0,viewHolder,t,pos);
									setUpdate();
								}else{
									Toast.makeText(context, "网络问题", Toast.LENGTH_SHORT).show();
								}

								
					}

				}
			});
	
		ImageView imageView = viewHolder.getView(R.id.iv_goods);
		ImageLoader.getInstance().displayImage(BaseUrl.EHSY_BASE_URL + t.thumb,imageView, baseOptions);

		ImageView imageBt = viewHolder.getView(R.id.id_shopping_cart_iv_delete);
		imageBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				delteUrl = StringUtils.getDeleteFromCartUrl(BaseUrl.EHSY_DELETE_PRO_FROM_CART_URL,"58e767f9fb72c44e68922d7033cca5e5", t.item);
				MyAlertDialog alertDialog = new MyAlertDialog(context,
						delteUrl, ProCartListAdapter.this, viewHolder.getPosition());
				alertDialog.showDialogOne("确定删除该商品吗？", "提示", -1);
			}
		});
		
		final CheckBox checkBox = viewHolder.getView(R.id.cb_select);
		final List<Boolean>isSelects = bean.getIsSelects();
		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 String allPrice;
				 String subTotle = bean.getSubTotals().get(viewHolder.getPosition());
				 
				if (isSelects.get(viewHolder.getPosition())) {
					all -= Float.valueOf(subTotle);
					allPrice = decimalFormat.format(all);
					all = Float.valueOf(allPrice);
					isSelects.set(viewHolder.getPosition(), false);
					selectCount--;
					checkBox.setChecked(false);
				}else{
					all += Float.valueOf(subTotle);
					allPrice = decimalFormat.format(all);
					all = Float.valueOf(allPrice);
					isSelects.set(viewHolder.getPosition(), true);			
					selectCount++;
					checkBox.setChecked(true);	
				}
				if (mListener != null) {
					mListener.onSelect(allPrice);
				}
				
			}
		});

		if (isSelects.get(viewHolder.getPosition())) {
			checkBox.setChecked(true);

		} else {
			checkBox.setChecked(false);
		}
	}
	
	protected void setUpdate() {
		isClick = true;
	    secondTime = System.currentTimeMillis();
	}
	
	/**
	 * 加减逻辑
	 * @param type 1是加，其他数字都是减
	 * @param viewHolder
	 * @param t
	 * @param pos
	 */
	public void addOrSubLogic(int type,final ViewHolder viewHolder, final ProCartListBean t,final int pos){
		int num = numbers.get(pos);	
		isNumbersChanged(pos);
		//点击加号的时候
		if (type==ADD) {
			++num;
			if (bean.getIsSelects().get(pos)) {

				if (mListener != null) {
					all += Float.valueOf(t.price);
					mListener.onSelect(decimalFormat.format(all));
				}
		}
			viewHolder.getView(R.id.tv_sub_price).setClickable(true);
			//点击减号的时候
			}else{
			--num;
			if (bean.getIsSelects().get(pos)) {

				if (mListener != null) {
					all -= Float.valueOf(t.price);
					mListener.onSelect(decimalFormat.format(all));
				}
			}
		}
		numbers.set(pos, num);
		String subTotal = decimalFormat.format((Float.parseFloat(t.price) * numbers.get(pos)));
		viewHolder.setText(R.id.tv_pro_num,String.valueOf(numbers.get(pos)));
		viewHolder.setText(R.id.tv_item_price,"小计: " + subTotal);
		bean.getSubTotals().set(pos, subTotal);
	}
	
	/**
	 *设置number值已经改变
	 * @param num2
	 * @param pos
	 */
	private void isNumbersChanged(int pos) {
		isNumbersChanged.set(pos, true);
	}

	/**
	 * list删除选项
	 */
	public void removeItem(int pos) {
		list.remove(pos);
		if (bean.getIsSelects().get(pos)) {
			all -= Float.valueOf(bean.getSubTotals().get(pos));
			selectCount--;
		}
		bean.getSubTotals().remove(pos);
		bean.getIsSelects().remove(pos);
		String allPrice = decimalFormat.format(all);
		numbers.remove(pos);
		if (mListener != null) {
			mListener.onSelect(allPrice);
		}

		notifyDataSetChanged();
	}
	/**
	 * 创建对外方法选中所有商品
	 */
	public void selectAllPro() {
		selectCount = list.size();
		System.out.println(selectCount+"  selectCount"); 
		for (int i = 0; i < list.size(); i++) {
			if (bean.getIsSelects().get(i) == false) {
				bean.getIsSelects().set(i, true);
				all += (Float.valueOf(bean.getSubTotals().get(i)));

			}

		}

		notifyDataSetChanged();
	}
	/**
	 * 创建对外方法取消=选中所有商品
	 */
	public void unSelectAllPro() {
		selectCount = 0;
		for (int i = 0; i < list.size(); i++) {
			bean.getIsSelects().set(i, false);
		}
		all = 0.00f;
		notifyDataSetChanged();
	}
	/**
	 * 获取选中商品的数量
	 * @return
	 */
	public int getSelectCount() {
		return selectCount;
	}
	/**
	 * 获取所有商品的价格
	 * @param count
	 * @return
	 */
	public float getAllPrice(int count) {
		List<String>subTotals = bean.getSubTotals();
		float allPrice = 0.0f;
		if (count != 0) {
			for (int i = 0; i < subTotals.size(); i++) {
				allPrice += (Float.valueOf(subTotals.get(i)));
			}
		}
		return allPrice;
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {

				mTime = (long) msg.obj;
				
				if (mTime - secondTime>1000&&isClick) {
					for (int i = 0; i < isNumbersChanged.size(); i++) {
						if (isNumbersChanged.get(i)) {
							LoadContent.UpdateItem(mActivity, numbers.get(i),list.get(i).item);
							isNumbersChanged.set(i, false);
						}
					}
					
					isClick = false;
				}

			
		};
	};
	private long mTime = 0;
	/**
	 * 创建一个线程时刻判断当点击停止时候所获得的时间和线程里的时间做比较，若后者和前者的差值大于1秒，则向副服务器提交值，跟新购物车
	 * @author Administrator
	 *
	 */
	class MyThread extends Thread{
		
		@Override
		public void run() {
			while(true){
				try {
					mTime = System.currentTimeMillis();
					Thread.sleep(1);
					Message msg= Message.obtain();
					msg.obj = mTime;
					mHandler.sendMessage(msg);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
