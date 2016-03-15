package com.gem.adapter;

import java.util.List;


import com.android.volley.RequestQueue;
import com.gem.entity.Business;
import com.gem.hxwasha.R;
import com.gem.util.Content;
import com.gem.util.ImageUtil;
import com.gem.util.ImageUtil.ImageCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BusinessListAdapter extends BaseAdapter implements ImageCallBack {
	List<Business> bs;
	Context context;
	ImageUtil imageUtil;
	public BusinessListAdapter(List<Business> bs,Context context,RequestQueue queue){
		this.bs=bs;
		this.context=context;
		imageUtil=new ImageUtil(context, queue, this);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		if(convertView!=null){
			holder=(ViewHolder) convertView.getTag();
		}else{
			convertView =LayoutInflater.from(context).inflate(R.layout.item_business_list,null);
			holder.ratingBar=(RatingBar) convertView.findViewById(R.id.ratingBar1);
			holder.img=(ImageView) convertView.findViewById(R.id.iv_image);
			holder.tvShopName=(TextView) convertView.findViewById(R.id.tv_shopname);
			holder.tvBagWash=(TextView) convertView.findViewById(R.id.tv_bagwash_text);
			holder.tvUrgent =(TextView) convertView.findViewById(R.id.tv_urgent_text);
			holder.tvMoney=(TextView) convertView.findViewById(R.id.tv_money);
			convertView.setTag(holder); 
		}
		holder.tvShopName.setText(bs.get(position).getShopName());
		String url ="http://"+Content.getIp()+":8080/"+bs.get(position).getImgesUrl();
		imageUtil.getImage(url,holder.img);	
		return convertView;
	}

	
	
class ViewHolder{
	ImageView img;
	RatingBar ratingBar;
	
	TextView tvShopName;
	TextView tvBagWash;
	TextView tvUrgent;
	TextView tvMoney;
}



@Override
public void showImagetoUI(Bitmap bitmap) {
	// TODO Auto-generated method stub
	
}


}
