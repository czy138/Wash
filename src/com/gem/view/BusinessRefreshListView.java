package com.gem.view;

import com.gem.hxwasha.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class BusinessRefreshListView extends ListView implements OnScrollListener {

	int firstVisibleItem;
	private View header;
	private ImageView imageView;
	private ProgressBar progressBar;
	private TextView tvRefreshState;
	private TextView tvRefreshTime;
	private int headHeight;
	private float startY;
	private float moveY;
	
	private boolean ISDOWNSCROLL=false;
	
	
	OnPullListener listener;
	int move=0;
	
	public int headState;//ͷ��״̬��INIT,PREPAREREFERSHER ISREFERING��
	public final int INIT=0;//��ʼ״̬
	public  final int ISREFERING=1;//����ˢ��
	private View footView;
	private int footHeight;
	boolean loading=false;
	
	public BusinessRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initHeader(context);
		initFoot(context);
		setOnScrollListener(this);
	}

	public BusinessRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initHeader(context);
		initFoot(context);
		setOnScrollListener(this);
	}

	public BusinessRefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initHeader(context);
		initFoot(context);
		setOnScrollListener(this);
	}
	
	public void initHeader(Context context){
		header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_head, null);
		
		addHeaderView(header);
		header.measure(0, 0);
		headHeight=header.getMeasuredHeight();
		//����ͷ��
		header.setPadding(0, -headHeight, 0,0);
		//��ʼ��ͷ���ؼ�
		 imageView= (ImageView) header.findViewById(R.id.iv_refresher);
		 progressBar=(ProgressBar) header.findViewById(R.id.pb_refresher);
		 tvRefreshState=(TextView) header.findViewById(R.id.tv_refreshertext);
	     tvRefreshTime=(TextView) header.findViewById(R.id.tv_refreshtime);
	}
	public void initFoot(Context context){
		//xml->view
		footView=LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer, null);
		//���foot
		addFooterView(footView);
		//��ȡfoot�ĸ߶�
		footView.measure(0, 0);
		footHeight=footView.getMeasuredHeight();
		
		//����foot
		footView.setPadding(0, -footHeight, 0, 0);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev){
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			startY =ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			moveY =ev.getY();
			move =(int) (moveY-startY);
			if(headState!=ISREFERING){
				if(move>=0){
					tvRefreshState.setText("������λ");	
					progressBar.setVisibility(View.INVISIBLE);
					imageView.setVisibility(View.VISIBLE);
					header.setPadding(0, -headHeight+move, 0, 0);
				}
			}
				if(move<0){
					ISDOWNSCROLL=true;
				}
				
			break;
		case MotionEvent.ACTION_UP:
			if(headState!=ISREFERING){
				if(ev.getY()-startY<headHeight){
					header.setPadding(0, -headHeight, 0, 0);
				}else if(move>=headHeight){
					header.setPadding(0, 0, 0, 0);
					tvRefreshState.setText("����λ");	
					progressBar.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.INVISIBLE);
					headState=ISREFERING;
					Handler handler =new Handler();
					handler.postDelayed(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							header.setPadding(0, -headHeight, 0, 0);
							listener.onLocation();
							headState=INIT;
						}
						
					}, 1000);
					
				}
			}
			
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	public void setOnPullListener(OnPullListener listener){
		this.listener=listener;
	}
	
	public interface OnPullListener {
		void onLocation();
		void onLoad();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.i("RefreshListView","scrollState:"+scrollState);
		
		//���һ����¼�ɼ�&��������||���õ�==�����ز��� &��ǰ���ڼ���
		//
		if(!loading&&getLastVisiblePosition()==getCount()-1){
			//����״̬
			if(ISDOWNSCROLL&&(scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_TOUCH_SCROLL)){
				//foot��ʾ
				footView.setPadding(0, 0, 0, 0);
				loading=true;
				//����onpull����
				if(listener!=null){
					listener.onLoad();
				}
			}
		}
		
		//SCROLL_STATE_FLING;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		Log.i("RefreshListView","onscroll");
		this.firstVisibleItem=firstVisibleItem;
	}
	public void completePull(){
		footView.setPadding(0, -footHeight, 0, 0);
		loading=false;
	}
	
}
