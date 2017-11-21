package comm;

import java.util.HashMap;

import view.BaseView;
import view.ViewFactory;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * View的管理功能
 * 
 * @author Administrator
 * 
 */
public class ViewManager
{

	private static ViewManager viewManager;
	private HashMap<String, BaseView> viewsMap = new HashMap<String, BaseView>();

	public static ViewManager getInstance()
	{
		try
		{
			if (viewManager == null)
			{
				viewManager = new ViewManager();
			}
			return viewManager;
		} finally
		{
		}
	}

	private void removeChildView(ViewGroup viewGroup, View paramView)
	{
		for (int i = 0; i < viewGroup.getChildCount(); ++i)
		{
			if ((viewGroup.getChildAt(i) instanceof ViewGroup))
			{
				removeChildView((ViewGroup) viewGroup.getChildAt(i), paramView);
			}

			if (viewGroup.getChildAt(i) == paramView)
			{
				viewGroup.removeView(paramView);
				return;
			}
		}
	}

	public void destroyManager()
	{
		this.viewsMap.clear();
		viewManager = null;
	}

	public void detachViewFromParent(View paramView)
	{
		View rootView = paramView.getRootView();
		if ((rootView == null) || (paramView == null))
		{
			return;
		}
		if (!(rootView instanceof ViewGroup))
		{
			return;
		}
		removeChildView((ViewGroup) rootView, paramView);
	}

	public BaseView getBaseView(Context paramContext, String paramString, Object value)
	{
		if ((paramString == null) || ("".equals(paramString.trim())))
		{
			return null;
		}
		
	
		if (viewsMap.get(paramString) != null)
		{
			return viewsMap.get(paramString);
		} else
		{
			BaseView baseView = ViewFactory.buildView(paramContext, paramString, value);
			viewsMap.put(paramString, baseView);
			return baseView;
		}
	}

	public BaseView getBaseView(Context paramContext, String paramString, boolean paramBoolean, Object value)
	{
		BaseView localBaseView = getBaseView(paramContext, paramString, value);
		if (paramBoolean)
		{
			detachViewFromParent(localBaseView.getLayoutView(value));
		}
		return localBaseView;
	}

	public BaseView getBaseView(String viewTag, Object value)
	{
		return getBaseView(null, viewTag, value);
	}

	public boolean isViewExist(String viewTag)
	{
		return viewsMap.get(viewTag) != null;
	}

	public void removeView(String viewTag)
	{
		this.viewsMap.remove(viewTag);
	}

}
