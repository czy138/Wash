package com.gem.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import libcore.io.DiskLruCache;
import libcore.io.DiskLruCache.Editor;
import libcore.io.DiskLruCache.Snapshot;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.lidroid.xutils.BitmapUtils;

public class ImageUtil {
	

	Context context;
	RequestQueue queue;
	ImageCallBack imageCallBack;
	LruCache<String, Bitmap> lruCache; 
	DiskLruCache diskCache;
	public ImageUtil(Context context, RequestQueue queue,
			ImageCallBack imageCallBack) {
		super();
		this.context = context;
		this.queue = queue;
		this.imageCallBack = imageCallBack;
		initLruCache();
		initDiskCache();
	}
	
	public void getImage(String url,ImageView v){
		Bitmap bitmap = getBitmapFromLruCache(url);
		Bitmap bitmapDisk = getBitmapFromDiskLruCache(url);
//		if(bitmap!=null){
//			v.setImageBitmap(bitmap);
//		}else if(bitmapDisk!=null){    
//			v.setImageBitmap(bitmapDisk);
//		}else{
//			getImageFromNet(url,v);
//		}
		getImageFromNet(url,v);
	}
	
	//下载图片
	public void getImageFromNet(final String url,final ImageView view){
//		ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
//			
//			@Override
//			public void onResponse(Bitmap response) {
//				// TODO Auto-generated method stub
//				view.setImageBitmap(response);
//				putBitmapToLruCache(url,response);
//				putBitmapToDiskCache(url, response);
//			}
//		}, 0, 0, Config.ARGB_8888, new Response.ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
//			}
//		}){
//
//			@Override
//			protected Response<Bitmap> parseNetworkResponse(
//					NetworkResponse response) {
//				// TODO Auto-generated method stub
//				Log.i("ThreadTest", "volley所在线程"+Thread.currentThread().getId()+"");
//				return super.parseNetworkResponse(response);
//			}
//			
//		};
//		queue.add(request);
		BitmapUtils utils = new BitmapUtils(context);  
	 
		utils.display(view, url);  
	}
	
	//回调接口
	public interface ImageCallBack {
		void showImagetoUI(Bitmap bitmap);
	}
	
	//LRU缓存
	public void  initLruCache(){
		long memory = Runtime.getRuntime().maxMemory();
		lruCache= new LruCache<String,Bitmap>((int) (memory/8)){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getByteCount();
			}
			
		};
	}
	
	public Bitmap getBitmapFromLruCache(String urlStr){
		return lruCache.get(urlStr);
	}
	
	public void putBitmapToLruCache(String urlStr,Bitmap bitmap){
		lruCache.put(urlStr, bitmap);
	}
	
	//硬盘缓存
	public void initDiskCache(){
		try {
			 diskCache=DiskLruCache.open(getDirectory(), 1, 1, 10 * 1024 * 1024);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获取硬盘缓存地址
	private File getDirectory() {
		String path;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
			      ||!Environment.isExternalStorageRemovable()){
			path = context.getExternalCacheDir().getPath();
		}else{
			path = context.getCacheDir().getPath(); 
		}
		return new File(path+File.separator+"bitmap");
	}
	
	public void putBitmapToDiskCache(String url,Bitmap bitmap){
		try {
			String key = hashKeyForDisk(url);
			Editor edit = diskCache.edit(key);
			if(edit!=null){
				OutputStream os = edit.newOutputStream(0);
				int b=0;
				if(bitmap!=null){
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
					InputStream is = new ByteArrayInputStream(baos.toByteArray());
					while((b=is.read())!=-1){
						os.write(b);
					}
					os.flush();
					edit.commit();
				}else{
					edit.abort();
				}
				diskCache.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Bitmap getBitmapFromDiskLruCache(String url){
		try {
			String key = hashKeyForDisk(url);
			Snapshot ss=diskCache.get(key);
			if(ss!=null)
			{
				return BitmapFactory.decodeStream(ss.getInputStream(0));
			}else{
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String hashKeyForDisk(String key) {
		String cacheKey;
		try {
		     final MessageDigest mDigest = MessageDigest.getInstance("MD5");
		     mDigest.update(key.getBytes());
		     cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
		     cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	
	
	
	
}
