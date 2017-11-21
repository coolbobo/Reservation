package view;

import android.app.Dialog;
import android.content.Context;

/**
 * 对话框的抽象類
 * @author Administrator
 *
 */
public class AbsDlg extends Dialog
{

	public AbsDlg(Context paramContext)
	{
		super(paramContext);
		setCanceledOnTouchOutside(false);
		_init();
	}

	private void _init()
	{
		
		
	}

	public AbsDlg(Context paramContext, int paramInt)
	{
		super(paramContext, paramInt);
		setCanceledOnTouchOutside(false);
	}
	

}
