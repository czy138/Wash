package com.gem.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.gem.entity.Business;
import com.gem.hxwasha.BusinessListActivity;
import com.gem.hxwasha.R;
import com.gem.util.LocationUtil;
import com.gem.util.SingleRequestQueue;

public class IndexFragment extends Fragment implements OnClickListener {
	ListView lv;
	List<Business> bs;
	TextView tvLocation;
	RelativeLayout rlWash;
	RelativeLayout bagWash;
	RelativeLayout urgent;
	RelativeLayout goods;
	//��ҳͼƬ�ֲ�
	ViewPager vpIndex;
	List<View> views=new ArrayList<View>();
	
	Activity context;
	RequestQueue queue;
	
	SharedPreferences locationSave;
	
	LocationUtil locationUtil;
    double lat=0;
	double lng=0;
	String address=null;
	boolean isDefaultList=true;
	//ÿ�μ��� curPage+1
	int curPage=1;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context=activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_index_index, container, false);
		queue=SingleRequestQueue.getRequestQueue(context);
		locationSave =context.getApplicationContext().getSharedPreferences("Location",Context.MODE_PRIVATE);
		getDateFromShared();
		getList();
		return v;
	}
	//��SharedPreferencesȡ��λ��Ϣ
	public void getDateFromShared(){
		lat =Double.parseDouble(locationSave.getString("lat", "0"));
		lng =Double.parseDouble(locationSave.getString("lat", "0"));
		address=locationSave.getString("address", null);
	}
	public void initView(){
		//
		vpIndex=(ViewPager) getView().findViewById(R.id.index_index_viewpage);
		
		locationUtil=new LocationUtil(context, new MyLocationListener());
		//��λ
		tvLocation= (TextView) getView().findViewById(R.id.tv_index_location);
		lv=(ListView) getView().findViewById(R.id.lv_business);
		rlWash=(RelativeLayout) getView().findViewById(R.id.rl_index_fr_button_wash);
		bagWash=(RelativeLayout) getView().findViewById(R.id.rl_index_fr_button_bagwash);
		urgent=(RelativeLayout) getView().findViewById(R.id.rl_index_fr_button_urgent);
		goods=(RelativeLayout) getView().findViewById(R.id.rl_index_fr_button_goods);
		if(address!=null){
			tvLocation.setText(address);
		}
	}
	
	public void initEvent(){
		rlWash.setOnClickListener(this);
		bagWash.setOnClickListener(this);
		urgent.setOnClickListener(this);
		goods.setOnClickListener(this);
		tvLocation.setOnClickListener(this);

		View v1=View.inflate(getActivity(), R.layout.index_index_vp_item, null);
		View v2=View.inflate(getActivity(), R.layout.index_index_vp_item1, null);
		View v3=View.inflate(getActivity(), R.layout.index_index_vp_item2, null);
		
		views.add(v1);
		views.add(v2);
		views.add(v3);
		
		vpIndex.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return views.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				container.removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				container.addView(views.get(position));
				return views.get(position);
			}
			
		});
	}
	
	public List<Business> initData(){
		//��ѯSQLite
		//select businessId from favorBuisness where userId =? orderBy times limit 0,10 
		//���ݿ��ѯ���
		List<Business> b=null;
		return b;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
		initEvent();

	}
	
	//�������ȡ�̼��б�
	public void getList(){
//		String url = "http://"+Content.getIp()+":8080/HXXa/BusinessListServlet";
//		//String url ="http://"+Content.IP+":8080/HXXa/BusinessListServlet";
//		HttpUtils http = new HttpUtils();
//		RequestParams params = new RequestParams();
//		params.addHeader("name", "value");
//		if(isDefaultList){
//			params.addQueryStringParameter("from","indexDefault");
//			params.addQueryStringParameter("curPage", curPage+"");
//			
//		}else{
//			params.addQueryStringParameter("from","indexByLocation");
//			//��λ��ı�indexByLocation��ֵ����λ��Ϣ�����ֻ����أ�������̨����̨���涨λ��Ϣ
//			//�� from��ֵΪindexByLocationʱ������Ӧ��List
//		}
//		http.send(HttpMethod.GET,url,params, new RequestCallBack<String>() {
//
//			@Override
//			public void onFailure(HttpException arg0, String arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onSuccess(ResponseInfo<String> arg0) {
//				// TODO Auto-generated method stub
//				Type type =	new TypeToken<List<Business>>() {  
//                }.getType();
//				Gson gson = new Gson();
//				bs = gson.fromJson(arg0.result,type);
//				//����һ����ʾʱ�������û����ѽ϶���̼�
//				if(curPage==0){
//					List<Business> temp = new ArrayList<Business>();
//					List<Business> b=initData();
//					if(b!=null){
//						temp.addAll(b);
//						bs.removeAll(b);
//						temp.addAll(bs);
//						bs=temp;
//					}
//				}
//
//				lv.setAdapter(new BusinessListAdapter(bs,context,queue));
//				fixListViewHeight(lv);
//			}
//		});
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.tv_index_location){
			locationUtil.start();
		}else{
			Intent intent=null;
			Bundle bundle = new Bundle();
			bundle.putBoolean("isDefault", isDefaultList);
			switch(v.getId()){
			case R.id.rl_index_fr_button_wash:
				intent = new Intent(context,BusinessListActivity.class);
				bundle.putString("busiType", "wash");
				break;
			case R.id.rl_index_fr_button_bagwash:
				bundle.putString("busiType", "bagwash");
				break;
			case R.id.rl_index_fr_button_urgent:
				bundle.putString("busiType", "urgent");
				break;
			}
			intent.putExtras(bundle);
			startActivity(intent);
		}
			//��ת���̳�
			//Intent intent = new Intent(context,BusinessListActivity.class);
	} 
	
	 public void fixListViewHeight(ListView listView) {   
	        // ���û��������������������ListViewû��������ء�  
	        ListAdapter listAdapter = listView.getAdapter();  
	        int totalHeight = 0;   
	        if (listAdapter == null) {   
	            return;   
	        }   
	        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {     
	            View listViewItem = listAdapter.getView(index , null, listView);  
	            // ��������View �Ŀ��   
	            listViewItem.measure(0, 0);    
	            // ������������ĸ߶Ⱥ�
	            totalHeight += listViewItem.getMeasuredHeight();    
	        }   
	   
	        ViewGroup.LayoutParams params = listView.getLayoutParams();   
	        // listView.getDividerHeight()��ȡ�����ָ����ĸ߶�   
	        // params.height����ListView��ȫ��ʾ��Ҫ�ĸ߶�    
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
	        listView.setLayoutParams(params);   
	    }
	 

	 public void initHeader(){
		 	ScrollView sv =(ScrollView) getView().findViewById(R.id.scrollView1);
		 	
		 	sv.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return false;
				}
			});
	 }
	 
	 
	 public class MyLocationListener implements BDLocationListener {
	

			@Override
	        public void onReceiveLocation(BDLocation location) {
	            //Receive Location
	            lat = location.getLatitude();
	            lng = location.getLongitude();
	            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
	                address=location.getAddrStr();
	            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
	            	address=location.getAddrStr();
	            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
	            	Log.i("LocationError","���߶�λ");
	            } else if (location.getLocType() == BDLocation.TypeServerError) {
	            	Log.i("LocationError","��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
	            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
	                Log.i("LocationError","���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
	            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
	                Log.i("LocationError", "�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
	            }
	            tvLocation.setText(address); //�����ʾ��UI��
	            //��λ���д��sharePreferense
	            Editor edit =locationSave.edit();
	            edit.putString("lat", String.valueOf(lat));
	            edit.putString("lng", String.valueOf(lng));
	            edit.putString("address", address); 
	            edit.commit();
	            locationUtil.stop();
	        }
		}
	 
}
