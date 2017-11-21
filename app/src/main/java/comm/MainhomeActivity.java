package comm;

import service.ReservationService;
import view.BaseView;
import view.CustomProgressDialog;
import view.TopView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import com.owned.reservation.R;

/**
 * 系统主界面管理类，用于加载各个界面View
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class MainhomeActivity extends Activity
{

	
	/* 计时器 */
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			isExit = false;
		}

	};
	/* 是否退出 */
	private boolean isExit = false;
	private RelativeLayout workAreaLayout;
	private RelativeLayout topAreaLayout;
	public static MainhomeActivity mainHomeApp;
	private ReservationService  reservationServ;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainhome);
		findViews();
		/*验证是否首次进入用 */
		isFirstInAPp();
		// 默认加载首页面
		// loadWorkViewbyTag(Consts.VIEW_NAME_MAIN, null);

		_init();
	}

	private void findViews()
	{
		reservationServ = AppContext.instance().appServ;
		mainHomeApp = this;
		workAreaLayout = (RelativeLayout) findViewById(R.id.work_area);
		topAreaLayout = (RelativeLayout) findViewById(R.id.top);
	}

	private void isFirstInAPp()
	{
		AppFlashConfig appflashConfig = new AppFlashConfig(mainHomeApp);
		Integer integer = appflashConfig.getParamIntByKey("paramsxml");
		/* 导航界面 */
		if (integer == null || integer == 0)
		{
			appflashConfig.setParam("paramsxml", 1);
			Intent intent = new Intent();
			intent.setClass(MainhomeActivity.mainHomeApp, GuiderActivity.class);
			startActivity(intent);

		} else
		{
			hideTopView();
			loadWorkViewbyTag(Consts.VIEW_NAME_WELCOME, null);
		}

	}

	/**
	 * 初始化全局数据
	 */
	private void _init()
	{
		// 获取后台数据，第一次登录时获取。
		AppContext.instance().appServ.asynGetDataFromServerBeforeLogin();
	}

	/**
	 * 加载对应的界面到主界面
	 * 
	 * @param viewTag
	 */
	public void loadWorkViewbyTag(String viewTag, Object value)
	{
		removeCurrentWorkView();
		BaseView baseView = ViewManager.getInstance().getBaseView(this, viewTag, value);
		workAreaLayout.addView(baseView.getLayoutView(value));
	}

	/**
	 * 清空之前的View界面
	 */
	public void removeCurrentWorkView()
	{
		workAreaLayout.removeAllViews();
	}

	/**
	 * 点击硬按键返回，直接弹出退出。
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			exitApp();
			return true;
		} else
		{
			return super.onKeyDown(keyCode, event);
		}
	}

	public void exitApp()
	{
		if (!isExit)
		{
			isExit = true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessageDelayed(0, 2000);
		} else
		{
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}

	}

	@Override
	protected void onDestroy()
	{
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}
	
	/**
	 * 显示顶部
	 */
	public void showTopView(){
		topAreaLayout.setVisibility(View.VISIBLE);
		
	}
	/**
	 * 隐藏顶部
	 */
	public void hideTopView(){
		topAreaLayout.setVisibility(View.GONE);
	}

	public RelativeLayout getTopAreaLayout()
	{
		return topAreaLayout;
	}

	

	
	
}
