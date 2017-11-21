package view;

import com.owned.reservation.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 消费保障view
 * @author Administrator
 *
 */
public class RevationServiceView extends BaseView
{   
	/*上下文参数*/
	private Context mContext;
	
	/*上下*/
	RelativeLayout  mLayoutView;
	
	public RevationServiceView(Context context){
		this.mContext = context;
		_init();
		_initListeners();
	}
	private void _init()
	{
		mLayoutView =(RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.revation_service, null);
		
	}
	
	private void _initListeners()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unRegisterListenrService()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unSetBackground()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBackground()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getLayoutView(Object value)
	{
		// TODO Auto-generated method stub
		return mLayoutView;
	}

}
