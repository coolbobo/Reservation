package view;

import service.ReservationService;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;
import com.slidingmenu.lib.SlidingMenu;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import comm.observer.ObserverConsts;

/**
 * 主界面，用于加载主界面
 * 
 * @author Administrator
 * 
 */
public class MainView extends BaseView
{

	private Context mContext;

	private RelativeLayout mLayoutView;
	/* 剪发 */
	private Button doHairCut;
	/* 浴足 */
	private Button doFoot;
	/* 美容 */
	private Button doCosmetology;

	public static SlidingMenu menu = null;

	private ReservationService reservationServ;

	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		setBackground();
		return mLayoutView;
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public MainView(Context context)
	{

		this.mContext = context;
		reservationServ = AppContext.instance().appServ;
	
		_init();
		
	}

	
	


	@Override
	public void setBackground()
	{
		mLayoutView.setBackgroundResource(R.drawable.main_body);
		doHairCut.setBackgroundResource(R.drawable.main_haircutselect);
		doFoot.setBackgroundResource(R.drawable.main_footerselect);
		doCosmetology.setBackgroundResource(R.drawable.main_reveravtionselect);
	}

	@Override
	public void unSetBackground()
	{

	}

	private void _initTop()
	{
		
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(true);
		topView.refershGoBack(false);
	}

	/**
	 * 初始化加载数据
	 */
	private void _init()
	{
		// 初始化赋值layout布局
		mLayoutView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.main, null);
		
		doHairCut = (Button) mLayoutView.findViewById(R.id.need_harcut);
		doCosmetology = (Button) mLayoutView.findViewById(R.id.need_Cosmetology);
		doFoot = (Button) mLayoutView.findViewById(R.id.need_foot);
		menu = new SlidingMenu(mContext);
		reservationServ.regeditObserver(ObserverConsts.DATA_STORE_LIST,this);
		_initMemu();
		_initTop();
		_initListener();
		setBackground();

	}

	private void _initMemu()
	{
			menu.setMode(SlidingMenu.LEFT);
	        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        menu.setShadowWidthRes(R.dimen.shadow_width);
	        menu.setShadowDrawable(R.drawable.shadow);
	        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	        menu.setBehindWidth(400);
	        menu.setFadeDegree(0.35f);
	        menu.attachToActivity(MainhomeActivity.mainHomeApp, SlidingMenu.SLIDING_WINDOW);
	        LeftMainView leftView = (LeftMainView) ViewManager.getInstance().getBaseView(MainhomeActivity.mainHomeApp,Consts.VIEW_NAME_SHOWRECRUIT, null);
	        menu.setMenu(leftView.getLayoutView(null));
		
	}

	/**
	 * 监听事件
	 */
	private void _initListener()
	{
		/**
		 * 我要理发
		 */
		doHairCut.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				/**
				 * 初始化数据
				 */
				reservationServ.progressDialog=CustomProgressDialog.createDialog(mContext);
				reservationServ.progressDialog.setMessage("正在加载中...");
				reservationServ.progressDialog.show();
				new Thread(new Runnable()
				{
					
					@Override
					public void run()
					{
						reservationServ.sendCmdGetStoreList(AppContext.instance().currentArea.id, Consts.DO_HAIRCUT);
						
					}
				}).start();
				
			}
		});
		
		
		doCosmetology.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				/**
				 * 初始化数据
				 */
				reservationServ.progressDialog=CustomProgressDialog.createDialog(mContext);
				reservationServ.progressDialog.setMessage("正在加载中...");
				reservationServ.progressDialog.show();
				
				new Thread(new Runnable()
				{
					
					@Override
					public void run()
					{
						reservationServ.sendCmdGetStoreList(AppContext.instance().currentArea.id, Consts.DO_COSMETOLOGY);
						
					}
				}).start();
				

			}
		});
		
		
		
		
		
		
		
		
		
		/**
		 * 足浴
		 */
		doFoot.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				/**
				 * 初始化数据
				 */
				reservationServ.progressDialog=CustomProgressDialog.createDialog(mContext);
				reservationServ.progressDialog.setMessage("正在加载中...");
				reservationServ.progressDialog.show();
				
				new Thread(new Runnable()
				{
					
					@Override
					public void run()
					{
						reservationServ.sendCmdGetStoreList(AppContext.instance().currentArea.id, Consts.DO_FOOT);
						
					}
				}).start();
				
			}
		});

		

	}

	@Override
	public void unRegisterListenrService()
	{
		// TODO Auto-generated method stub
		reservationServ.unRegeditObserver(ObserverConsts.DATA_STORE_LIST,this);
		

	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		if(observerKey == ObserverConsts.DATA_STORE_LIST){
			MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_STOREBUSNIESS,(Integer)value);
			
			reservationServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_STOREBUSNIESS,
					(Integer)value));
		}

	}

	public SlidingMenu getMenu()
	{
		return menu;
	}

	public void setMenu(SlidingMenu menu)
	{
		this.menu = menu;
	}
	
	
	

}
