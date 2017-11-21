package view;

import java.util.ArrayList;

import model.BaseRecordHisBean;
import model.ReservationBean;
import cn.sharesdk.framework.ShareSDK;

import cn.sharesdk.onekeyshare.OnekeyShare;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import comm.observer.ObserverConsts;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutMeView extends BaseView
{
	/* 上下文环境 */
	private Context mContext;

	/* 主区域 */
	private RelativeLayout mLayoutView;

	/* 预约 */
	private LinearLayout aboutReva;

	/* 收藏 */
	private LinearLayout aboutLove;

	/* 预约图标 */
	private ImageView aboutRevaView;

	/* 收藏图标 */
	private ImageView aboutLoveView;

	/* 充值图标 */
	private ImageView aboutCashView;
	
	private AboutAdapter  aboutAdatper;

	/* 充值 */
	private LinearLayout aboutCash;

	/* 呈现界面 */
	private ListView listViewShow;

	

	public AboutMeView(Context context)
	{
		this.mContext = context;
		aboutAdatper = new AboutAdapter(mContext);
		_init();
		_inListeners();
	
	}

	private void _init()
	{
		mLayoutView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.about_me, null);

		aboutReva = (LinearLayout) mLayoutView.findViewById(R.id.about_reva);

		aboutLove = (LinearLayout) mLayoutView.findViewById(R.id.about_love);

		aboutCash = (LinearLayout) mLayoutView.findViewById(R.id.about_cash);

		aboutRevaView = (ImageView) mLayoutView.findViewById(R.id.about_reva_icon);

		aboutLoveView = (ImageView) mLayoutView.findViewById(R.id.about_love_icon);

		aboutCashView = (ImageView) mLayoutView.findViewById(R.id.about_cash_icon);
		
		listViewShow = (ListView) mLayoutView.findViewById(R.id.about_show);
		
		_initTop();
		setBackground();
		/*默认选中我的预约*/
		aboutReva.setBackgroundResource(R.drawable.about_imagecash);
		aboutRevaView.setBackgroundResource(R.drawable.myabout_revationfocus);
		
	  
		aboutAdatper.setData(AppContext.instance().appServ.getRecordHisData(Consts.ABOUT_TAB_RESERVATION_HIS));
		listViewShow.setAdapter(aboutAdatper);
		
		AppContext.instance().appServ.regeditObserver(ObserverConsts.DATA_HISTORY_CASH, this);
		//AppContext.instance().appServ.regeditObserver(ObserverConsts.DATA_HISTORY_LOVE, this);
	
		AppContext.instance().appServ.asynGetCashfterLogin();
	
	}

	private void _inListeners()
	{
		/* 预约历史记录 */
		aboutReva.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				aboutAdatper.setData(AppContext.instance().appServ.getRecordHisData(Consts.ABOUT_TAB_RESERVATION_HIS));
				aboutAdatper.notifyDataSetChanged();
				aboutLove.setBackgroundResource(0);
				aboutCash.setBackgroundResource(0);
				aboutLoveView.setBackgroundResource(R.drawable.myabout_shoucang);
				aboutCashView.setBackgroundResource(R.drawable.myabout_cashfocus);
				v.setBackgroundResource(R.drawable.about_imagecash);
				aboutRevaView.setBackgroundResource(R.drawable.myabout_revationfocus);
				

			}
		});

		/* 收藏记录 */
		aboutLove.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
				aboutAdatper.setData(AppContext.instance().appServ.getRecordHisData(Consts.ABOUT_TAB_LOVE));
				aboutAdatper.notifyDataSetChanged();
				aboutCash.setBackgroundResource(0);
				aboutReva.setBackgroundResource(0);
				aboutRevaView.setBackgroundResource(R.drawable.myabout_revationfocus);
				aboutCashView.setBackgroundResource(R.drawable.myabout_cashfocus);
				v.setBackgroundResource(R.drawable.about_imagecash);
				aboutLoveView.setBackgroundResource(R.drawable.myabout_shoucang);
				
				
			}
		});

		/* 充值记录 */
		aboutCash.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				aboutAdatper.setData(AppContext.instance().appServ.getRecordHisData(Consts.ABOUT_TAB_CASH_HIS));
				aboutAdatper.notifyDataSetChanged();
				
				aboutLoveView.setBackgroundResource(R.drawable.myabout_shoucang);
				aboutLove.setBackgroundResource(0);
				aboutReva.setBackgroundResource(0);
				aboutRevaView.setBackgroundResource(R.drawable.myabout_revationfocus);
				v.setBackgroundResource(R.drawable.about_imagecash);
				aboutCashView.setBackgroundResource(R.drawable.myabout_cashfocus);
			}
		});
	}

	@Override
	public void unRegisterListenrService()
	{
		AppContext.instance().appServ.unRegeditObserver(ObserverConsts.DATA_HISTORY_CASH, this);
		//AppContext.instance().appServ.unRegeditObserver(ObserverConsts.DATA_HISTORY_LOVE, this);

	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		if (observerKey == ObserverConsts.DATA_RESERVATION)
		{
			ReservationBean bean = (ReservationBean) value;
			// TODO:刷新到显示界面的数据列表中
		}
		
		if(observerKey == ObserverConsts.DATA_HISTORY_CASH){
			
			
		}

	}

	@Override
	public void unSetBackground()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBackground()
	{
		mLayoutView.setBackgroundResource(R.drawable.haircutmain);
		aboutRevaView.setBackgroundResource(R.drawable.myabout_revation);
		aboutLoveView.setBackgroundResource(R.drawable.myabout_shoufocus);
		aboutCashView.setBackgroundResource(R.drawable.myabout_cash);
	}

	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		return mLayoutView;
	}

	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		topView.refershTopDescription(true, mContext.getResources().getString(R.string.top_person));
	}

	class AboutAdapter extends BaseAdapter
	{
		private ArrayList<BaseRecordHisBean> baseRecoreHisbean;
		
		private Context  mContext;
		
		public AboutAdapter(Context context){
			this.mContext =context;
		}
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return baseRecoreHisbean.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}
		
		public void setData(ArrayList<BaseRecordHisBean> baseRecorehisBean){
			this.baseRecoreHisbean = baseRecorehisBean;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			BaseRecordHisBean  baseBean= baseRecoreHisbean.get(position);
			ViewHolder  viewHolder = new ViewHolder();
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.about_me_list, null);
				viewHolder.relativeLayout  = (RelativeLayout) convertView.findViewById(R.id.personage_layout);
				viewHolder.userDot = (ImageView) convertView.findViewById(R.id.user_dot);
				viewHolder.userShare = (ImageView) convertView.findViewById(R.id.user_share);
			
				viewHolder.textContent = (TextView) convertView.findViewById(R.id.personage_item_text);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			viewHolder.userDot.setImageResource(R.drawable.about_icon);
			viewHolder.textContent.setText(baseBean.value);
			viewHolder.userShare.setImageResource(R.drawable.share);
			viewHolder.userShare.setTag(baseBean.value);
			viewHolder.userShare.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
				showShare(false, null, v.getTag().toString());
				}
			});
			return convertView;
		}

	}
	
	final class ViewHolder{
		/*视图容器*/
		RelativeLayout  relativeLayout;
		
		ImageView userDot;
		
		TextView  textContent;
		
		ImageView  userShare;
		
	}
	
	
	/***
	 * 
	 * showShare(分享的)
	 * 
	 * @param silent
	 * @param platform
	 */
	public void showShare(boolean silent, String platform, String content)
	{
		// 初始化分享
		ShareSDK.initSDK(MainhomeActivity.mainHomeApp, "211d6383a4d7");
		String url = Consts.ADDRESS + "?id=";

		final OnekeyShare result = new OnekeyShare();
		result.setNotification(R.drawable.ic_launcher, mContext.getString(R.string.app_name));
		result.setAddress(url);
		result.setTitle(content);
		result.setTitleUrl(url);
		result.setText(content + url);
		result.setUrl(url);
		result.setComment(url);
		result.setSite(mContext.getString(R.string.app_name));
		result.setSiteUrl("http://jinjizi.net");
		result.setSilent(silent);
		if (platform != null)
		{
			result.setPlatform(platform);
		}
		result.show(MainhomeActivity.mainHomeApp);
	}
}
