package view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;

/**
 * 界面顶层的View，用于返回和显示用户信息
 * 
 * @author liuzhibin
 * 
 */
public class TopView extends BaseView
{

	private Context mContext;

	private RelativeLayout mLayoutView;

	/* 返回 */
	private ImageView gobackBtn;

	/* 关于我 */
	private ImageView aboutMeBtn;

	/* yuewo */
	private TextView appDescirption;

	/* 页面标题展示 */
	private TextView topDescription;

	/* yuewo */
	private ImageView reservationImage;

	private RelativeLayout reservationLayout;
	private RelativeLayout goBackLayout;
	private RelativeLayout aboutMelayout;

	@Override
	public View getLayoutView(Object value)
	{
		setBackground();
		return mLayoutView;
	}

	/**
	 * 构�?函数
	 * 
	 * @param context
	 */
	public TopView(Context context, String viewName, Object value)
	{
		this.mContext = context;
		_init();
	
	}

	/**
	 * 初始化加载数据
	 */
	private void _init()
	{
		// 初始化赋值layout布局
		mLayoutView = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.top, null);
		reservationImage = (ImageView) mLayoutView.findViewById(R.id.top_reservation_img);
		appDescirption = (TextView) mLayoutView.findViewById(R.id.app_description);
		reservationLayout = (RelativeLayout) mLayoutView.findViewById(R.id.top_reservation_layout);

		gobackBtn = (ImageView) mLayoutView.findViewById(R.id.go_back_img);
		topDescription = (TextView) mLayoutView.findViewById(R.id.top_description);
		goBackLayout = (RelativeLayout) mLayoutView.findViewById(R.id.top_back_layout);

		aboutMeBtn = (ImageView) mLayoutView.findViewById(R.id.top_about_me);
		aboutMelayout = (RelativeLayout) mLayoutView.findViewById(R.id.top_aboutme_layout);

		setBackground();
		_initListener();
	}

	/**
	 * 界面上返回图标隐藏或者显示
	 * 
	 * @param isShow
	 */
	public void refershReservationImg(boolean isShow)
	{
		if (isShow)
		{
			reservationLayout.setVisibility(View.VISIBLE);
		} else
		{
			reservationLayout.setVisibility(View.GONE);
		}

	}

	/**
	 * 刷新顶部的字体和背景
	 * 
	 * @param bussinessFlag
	 */
	public void refreshTextColorAndBackground(int bussinessFlag)
	{
		if (bussinessFlag == Consts.DO_FOOT)
		{
			mLayoutView.setBackgroundResource(R.drawable.footer_top);
		} else if (bussinessFlag == Consts.DO_HAIRCUT)
		{
			mLayoutView.setBackgroundResource(R.drawable.haircut_top);
		} else if (bussinessFlag == Consts.DO_COSMETOLOGY)
		{
			mLayoutView.setBackgroundResource(R.drawable.hairdressing);
		}
	}

	/**
	 * 界面上返回图标隐藏或者显示
	 * 
	 * @param isShow
	 */
	public void refershTopDescription(boolean isShow, String showContent)
	{
		if (showContent != null)
		{
			topDescription.setText(showContent);
		}
		if (isShow)
		{
			topDescription.setVisibility(View.VISIBLE);

		} else
		{
			topDescription.setVisibility(View.GONE);
		}

	}

	/**
	 * 刷新返回按钮状态
	 * 
	 * @param isShow
	 */
	public void refershGoBack(boolean isShow)
	{
		if (isShow)
		{
			goBackLayout.setVisibility(View.VISIBLE);
		} else
		{
			goBackLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 监听事件
	 */
	private void _initListener()
	{
		/* 返回上一级目录 */
		gobackBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				goBack();

			}
		});

		/* 关于我界面 */
		aboutMeBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				/*ViewInfoBean currBean = AppContext.instance().appServ.getCurrentViewIfo();
				BaseView baseView = ViewManager.getInstance().getBaseView(mContext, currBean.viewName, currBean.value);
				baseView.unSetBackground();*/
				/**
				 * 判断当前是否登陆
				 */
				if(AppContext.instance().appServ.isLoginSys()){
					/*加载个人中心*/
					MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_ABOUTME, null);
					AppContext.instance().appServ
							.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_ABOUTME, null));
				}else{
					MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_LOGIN, null);
					AppContext.instance().appServ
					.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_LOGIN, null));
				}
				

			}
		});
		
		
		
		reservationImage.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				MainView.menu.toggle();
			}
		});
		

	}

	/**
	 * 界面点击返回事件
	 */
	public void goBack()
	{
		ViewInfoBean currBean = AppContext.instance().appServ.getCurrentViewIfo();
		BaseView baseView = ViewManager.getInstance().getBaseView(mContext, currBean.viewName, currBean.value);
		if(ViewManager.getInstance().isViewExist(currBean.viewName)){
			ViewManager.getInstance().removeView(currBean.viewName);
		}
		baseView.unSetBackground();
		ViewInfoBean viewBean = AppContext.instance().appServ.getLastViewInfo();
		if (viewBean == null || viewBean.viewName == null)
		{
			return;
		}
	
		MainhomeActivity.mainHomeApp.loadWorkViewbyTag(viewBean.viewName, viewBean.value);
		/* 删除最后一个历史记录 */
		AppContext.instance().appServ.removeLastViewFromRoute();
	}

	@Override
	public void unRegisterListenrService()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 针对后台数据的变化，刷新界面的显示
	 */
	@Override
	public void viewDataChanged(int observerKey, Object value)
	{

	}

	@Override
	public void unSetBackground()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void setBackground()
	{
		mLayoutView.setBackgroundResource(R.drawable.hairdressing);
		gobackBtn.setBackgroundResource(R.drawable.top_goback);

	}

}
