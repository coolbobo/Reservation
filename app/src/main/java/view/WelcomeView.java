package view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
public class WelcomeView extends BaseView
{
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what)
			{
			case 1:
				/*释放首页资源*/
				ViewManager.getInstance().destroyManager();
				MainhomeActivity.mainHomeApp.showTopView();
				BaseView baseView = ViewManager.getInstance().getBaseView(mContext,Consts.VIEW_NAME_TOP,null);
				MainhomeActivity.mainHomeApp.getTopAreaLayout().addView(baseView.getLayoutView(null));
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_MAIN,null);
				AppContext.instance().appServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_MAIN, null));
				
				//网络判断
			    boolean result = NetworkManager.instance(mContext).isNewWork();
			    if(!result){
			    	Toast.makeText(mContext,"网络连接失败，请连接网络",Toast.LENGTH_SHORT).show();
			    }
			    
			    
			    
				
				break;
			default:
				break;
			}
			
		};
	};
	private Context mContext;

	private LinearLayout mLayoutView;
    
	/*图片*/
	private ImageView welcomeApp;

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
	public WelcomeView(Context context)
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
		mLayoutView = (LinearLayout) LayoutInflater.from(this.mContext).inflate(R.layout.welcome, null);
		welcomeApp = (ImageView) mLayoutView.findViewById(R.id.welcome_app);
		welcomeApp.setBackgroundResource(R.drawable.welcome_app);
		welcomeApp.setScaleType(ScaleType.FIT_XY);
		_initTop();
		initshowTime();
		
	}



	/*显示时间*/
	private void initshowTime()
	{
		Timer  timer = new Timer();
		timer.schedule(new TimerTask()
		{
			
			@Override
			public void run()
			{
				Message message = Message.obtain();
				message.what=1;
				handler.sendMessage(message);
				// TODO Auto-generated method stub
				
			}
		}, 3000);
		
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

	}
	
	
	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		
		
	}
	

}
