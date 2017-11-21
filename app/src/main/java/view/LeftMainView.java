package view;

import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LeftMainView extends BaseView
{	
	/*上下文环境*/
	private Context context;
	private RelativeLayout mLayoutView;
	private ImageView  btnShowShop;
	private ImageView  btnWork;
	public LeftMainView(Context context)
	{
		this.context = context;
		_init();
		_initListeners();
	}
	private void _initListeners()
	{
		/*商品展示*/
		// TODO Auto-generated method stub
		btnShowShop.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				MainView.menu.toggle();
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_PRODUCT, null);
				AppContext.instance().appServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_PRODUCT,
						null));
			}
		});
		
		/**
		 * 加载工作
		 */
		btnWork.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				MainView.menu.toggle();
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_POSITION, null);
				AppContext.instance().appServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_POSITION,
						null));
				
			}
		});
	}
	/**
	 * 初始化UI
	 */
	private void _init()
	{
		mLayoutView = (RelativeLayout) LayoutInflater.from(this.context).inflate(R.layout.shopcase, null);	
		btnShowShop = (ImageView) mLayoutView.findViewById(R.id.show_shop);
		btnWork = (ImageView) mLayoutView.findViewById(R.id.show_work);
		_initTop();
		setBackground();
		
	}
	@Override
	public void unRegisterListenrService()
	{

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
		mLayoutView.setBackgroundResource(R.drawable.main_body);
		btnShowShop.setBackgroundResource(R.drawable.leftmain_shopselect);
		btnWork.setBackgroundResource(R.drawable.leftmain_workselect);
		
	}

	@Override
	public View getLayoutView(Object value)
	{
		// TODO Auto-generated method stub
		return mLayoutView;
	}
	
	
	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(context, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
	}
}
