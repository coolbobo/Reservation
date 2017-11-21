package view;

import android.app.FragmentManager;
import android.view.View;

public abstract class BaseView implements IViewDataObserver
{
	
	public abstract View getLayoutView(Object value);

	public void updateLayoutView()
	{
	}

	public void updateLayoutView(int paramInt)
	{
	}

	public View getLayoutView()
	{
		// TODO Auto-generated method stub
		return null;
	}


}