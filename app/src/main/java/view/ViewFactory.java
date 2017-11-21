package view;

import android.content.Context;

import comm.Consts;

/**
 * View工厂类，用于创建的界面View类
 * 
 * @author Administrator
 * 
 */
public class ViewFactory
{
	public static BaseView buildView(Context paramContext, String viewName, Object value)
	{
		BaseView baseView = null;
		if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_MAIN))
		{
			baseView = new MainView(paramContext);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_RESERVATION))
		{
			baseView = new ReservationView(paramContext, value);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_STORE))
		{
			baseView = new StoreView(paramContext, value);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_ABOUTME))
		{
			baseView = new AboutMeView(paramContext);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_EMPLOYEE))
		{
			baseView = new StoreEmployeeView(paramContext, value);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_LOGIN))
		{
			baseView = new LoginView(paramContext);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_REGEDIT))
		{
			baseView = new RegeditView(paramContext);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_TOP))
		{
			baseView = new TopView(paramContext, null, null);

		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_STOREBUSNIESS))
		{
			baseView = new StoreViewBusiness(paramContext, value);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_SHOWRECRUIT))
		{
			baseView = new LeftMainView(paramContext);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_PRODUCT))
		{
			baseView = new ShowProduct(paramContext);
		} else if (viewName.equalsIgnoreCase(Consts.VIEW_NAME_WELCOME))
		{
			baseView = new WelcomeView(paramContext);
		} else if(viewName.equalsIgnoreCase(Consts.VIEW_NAME_INFORMATION))
		{
			baseView = new InformationView(paramContext, value);
		}else if(viewName.equalsIgnoreCase(Consts.VIEW_NAME_POSITION))
		{
			baseView = new SeePositionView(paramContext);
		}else if(viewName.equalsIgnoreCase(Consts.VIEW_NAME_RELEASE))
		{
			baseView = new ReleasePositionView(paramContext);
		}else{
			// 默认加载首页面
			baseView = new MainView(paramContext);
		}
		return baseView;
	}
}
