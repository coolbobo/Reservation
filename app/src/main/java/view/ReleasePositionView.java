package view;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.NetworkManager;
import comm.ViewManager;

/**
 * 界面顶层的View，用于返回和显示用户信息
 * 
 * @author liuzhibin
 * 
 */
public class ReleasePositionView extends BaseView
{

	private Context mContext;

	private RelativeLayout mLayoutView;
	
	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		return mLayoutView;
	}

	/**
	 * 构�?函数
	 * 
	 * @param context
	 */
	public ReleasePositionView(Context context)
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
		mLayoutView = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.position_release, null);
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
		

	}
	
	
	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
	}


	

}
