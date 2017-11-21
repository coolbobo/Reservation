package comm;

import android.app.FragmentManager;
import model.AreaBean;
import model.LoginUesrBean;
import service.ReservationService;
import comm.observer.GlobalHandler;

/**
 * 全局上下文，用于存储全局的上下文
 * 
 * @author coolbobo
 * 
 */

public class AppContext
{

	private static volatile AppContext instance = null;
	public GlobalHandler globalHandler;
	public ReservationService appServ = null;
	// 当前登录的用户名和密码信息，
	public LoginUesrBean userData = null;
	// 当前所在的位置
	public AreaBean currentArea = null;
	// 上一次登录所有的位置
	public AreaBean lastLoginArea = null;
	
	
	

	/**
	 * 单例初始化
	 * 
	 * @return
	 */
	public static AppContext instance()
	{
		try
		{
			if (instance == null)
			{
				instance = new AppContext();
				instance._init();
			}
			return instance;
		} finally
		{

		}
	}

	/**
	 * 初始�?
	 */
	private void _init()
	{
		_regeditService();
		// 初始化全局变量
		globalHandler = new GlobalHandler();
		_loadArea();
		
	}

	
	private void _loadArea()
	{
		// TODO Auto-generated method stub
		currentArea = new AreaBean();
		currentArea.id=1;
		currentArea.provinceName="广东省";
		currentArea.cityName="深圳市";
		currentArea.areaName="南山区";
	}

	/**
	 * 初始化各个服务类
	 */
	private void _regeditService()
	{
		appServ = new ReservationService();

	}
}
