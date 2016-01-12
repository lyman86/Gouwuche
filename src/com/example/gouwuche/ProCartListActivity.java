package com.example.gouwuche;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.gouwuche.utils.BaseUrl;
import com.example.gouwuche.utils.LoadContent;
import com.example.gouwuche.utils.StringUtils;

public class ProCartListActivity extends Activity {
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String realUrl = StringUtils.getProCarListUrl(
				BaseUrl.EHSY_ADD_PROCART_LIST_URL,"58e767f9fb72c44e68922d7033cca5e5");
		LoadContent loadContent = new LoadContent(this, realUrl);
		loadContent
				.showBuyProList(this, R.layout.activity_pro_cart_list, "加载中");
	}
}