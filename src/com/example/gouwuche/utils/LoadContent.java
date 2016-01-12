package com.example.gouwuche.utils;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gouwuche.R;
import com.example.gouwuche.adapter.ProCartListAdapter;
import com.example.gouwuche.adapter.ProCartListAdapter.onSelectListener;
import com.example.gouwuche.bean.ProCartListBean;
import com.ly.shopsystem.http.BaseHttpLoad;
import com.ly.shopsystem.http.HttpAddAndSubProToCart;
import com.ly.shopsystem.http.HttpAddAndSubProToCart.onAddProToCartListener;
import com.ly.shopsystem.http.HttpDeleteProFromCart;
import com.ly.shopsystem.http.HttpDeleteProFromCart.onDeleteFromCartListener;
import com.ly.shopsystem.http.HttpProCartList;
import com.ly.shopsystem.http.HttpProCartList.onProCartListListener;
import com.ly.shopsystem.http.HttpUpdateProFromCart;
import com.ly.shopsystem.http.HttpUpdateProFromCart.onUpdateFromCartListener;

public class LoadContent {
	private static String url;
	// private static MyDialog dialog;
	private static Context context;
	private onAddSuccessListener mAddListener;
	private onSubSuccessListener mSubListener;
	private static MyDialog dialog;

	/**
	 * 加好按钮的回调
	 * 
	 * @author Administrator
	 * 
	 */
	public interface onAddSuccessListener {
		void onAddSuccess(String success);
	}

	public void setOnAddSuccessListener(onAddSuccessListener mAddListener) {
		this.mAddListener = mAddListener;
	}

	/**
	 * 减号按钮的回调
	 * 
	 * @author Administrator
	 * 
	 */
	public interface onSubSuccessListener {
		void onSubSuccess(String success);
	}

	public void setOnSubSuccessListener(onSubSuccessListener mSubListener) {
		this.mSubListener = mSubListener;
	}

	public LoadContent(Context context, String url) {
		this.url = url;
		this.context = context;
	}

	public LoadContent() {
	}

	public void deleteProFromCart(final String dialogInfo,
			final ProCartListAdapter adapter, final int position) {
		dialog = new MyDialog(context, dialogInfo);
		dialog.createDialog();
		BaseHttpLoad mContent = new HttpDeleteProFromCart(url);
		mContent.start();
		((HttpDeleteProFromCart) mContent)
				.setOnDeleteFromCartListener(new onDeleteFromCartListener() {

					@Override
					public void deleteSuccess(String success) {
						adapter.removeItem(position);
						dialog.dismiss();
						Toast.makeText(context, success, Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void deleteFailed(String failed) {
						dialog.dismiss();
						Toast.makeText(context, failed, Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	public static void UpdateItem(final Activity activity,int number,String item){
		BaseHttpLoad mContent = new HttpUpdateProFromCart(
				StringUtils.getUpdateFromCartUrl(BaseUrl.EHSY_UPDATE_PRO_FROM_CART_URL, 
						"58e767f9fb72c44e68922d7033cca5e5", String.valueOf(number),item));
					
		mContent.start();
		((HttpUpdateProFromCart)mContent).setOnUpdateFromCartListener(new onUpdateFromCartListener() {
			
			@Override
			public void UpdateSuccess(String success) {
				System.out.println(success+"UpdateSuccess>>>>>>>");
			}
			
			@Override
			public void UpdateFailed(String failed) {
				Toast.makeText(context,failed,Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void showBuyProList(final Activity activity, final int layoutId,
			final String dialogInfo) {
		dialog = new MyDialog(activity, dialogInfo);
		dialog.createDialog();
		BaseHttpLoad mContent = new HttpProCartList(url);
		mContent.start();
		((HttpProCartList) mContent)
				.setOnProCartListListener(new onProCartListListener() {

					@Override
					public void loadSuccess(List<ProCartListBean> result) {
						initNum(result);
						dialog.dismiss();
						activity.setContentView(layoutId);
						ListView listView = (ListView) activity
								.findViewById(R.id.id_pro_cart_list_listView);

						ProCartListAdapter adapter = new ProCartListAdapter(
								activity, result, R.layout.item_pro_cart_list,
								activity);
						listView.setAdapter(adapter);
						initBottomView(activity, adapter);
					}

					@Override
					public void loadFailed(String failed) {
						dialog.dismiss();
						System.out.println(failed+"showBuyProList>>>>>>>>>>>");
						Toast.makeText(activity, failed, Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	private TextView alreadySelectTv;
	private TextView allPriceTv;

	protected void initBottomView(Activity activity, ProCartListAdapter adapter) {
		ImageView imageAllSelect = (ImageView) activity
				.findViewById(R.id.id_pro_cart_list_iv_unselect);
		alreadySelectTv = (TextView) activity
				.findViewById(R.id.id_pro_cart_list_tv_select_count);
		allPriceTv = (TextView) activity
				.findViewById(R.id.id_pro_cart_list_tv_all_price);
		TextView goOrderTv = (TextView) activity
				.findViewById(R.id.id_pro_cart_list_tv_go_order);
		initEvent(imageAllSelect, goOrderTv, adapter);
	}

	private boolean isSelect = false;

	private void initEvent(final ImageView imageAllSelect, TextView goOrderTv,
			final ProCartListAdapter adapter) {
		imageAllSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println(isSelect);
				if (isSelect) {
					adapter.unSelectAllPro();
					imageAllSelect.setImageResource(R.drawable.btn_circle_normal);
					allPriceTv.setText("总计："+ String.valueOf(adapter.getAllPrice(0)) + "￥");
					isSelect = false;
				} else {
					adapter.selectAllPro();
					DecimalFormat decimalFormat = new DecimalFormat("0.00");
					imageAllSelect.setImageResource(R.drawable.btn_circle_selected);
					allPriceTv.setText("总计："+ decimalFormat.format(adapter.getAllPrice(-1))+ "￥");
					isSelect = true;
				}
				
				alreadySelectTv.setText("已选商品：" + adapter.getSelectCount() + "");

			}
		});
		goOrderTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		adapter.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String allPrice) {
				allPriceTv.setText("总计：" + allPrice + "￥");
				alreadySelectTv.setText("已选商品：" + adapter.getSelectCount());
				System.out.println(adapter.getCount()+"  "+adapter.getSelectCount());
				if (adapter.getCount() == adapter.getSelectCount()) {
					
					imageAllSelect.setImageResource(R.drawable.btn_circle_selected);
					isSelect = true;
				} else {
					imageAllSelect.setImageResource(R.drawable.btn_circle_normal);
					isSelect = false;
				}

			}
		});

	}

	public static Integer nums[];

	private void initNum(List<ProCartListBean> result2) {
		nums = new Integer[result2.size()];
		for (int i = 0; i < result2.size(); i++) {
			nums[i] = Integer.parseInt(result2.get(i).number);
		}

	}

	/**
	 * 检测当的网络（WLAN、3G/2G）状态
	 * 
	 * @param context
	 *            Context
	 * @return true 表示网络可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}
}