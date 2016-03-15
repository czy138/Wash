package com.gem.hxwasha;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gem.util.Content;
import com.gem.util.ImageUtil;
import com.gem.util.ImageUtil.ImageCallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TestActivity extends Activity implements ImageCallBack{
	@ViewInject(R.id.iv_test)
	ImageView image;
	
	RequestQueue queue;
	ImageUtil imageUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		ViewUtils.inject(this);
		queue = Volley.newRequestQueue(this);
		
		imageUtil = new ImageUtil(this, queue, this);
	}
	public void showImage(View v){
		String url ="http://"+Content.getIp()+":8080/tomcat.png";
		imageUtil.getImage(url,image);
	}

	@Override
	public void showImagetoUI(Bitmap bitmap) {
		// TODO Auto-generated method stub
		image.setImageBitmap(bitmap);
	}
}
