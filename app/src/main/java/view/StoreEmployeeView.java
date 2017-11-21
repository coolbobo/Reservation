package view;

/**
 * 详细的店家信息，主要显示雇员信息
 */
import java.util.ArrayList;

import model.EmployeesBean;
import model.StoreBean;
import service.ReservationService;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import bussinessModel.ReservationBussinessBean;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import comm.observer.ObserverConsts;

public class StoreEmployeeView extends BaseView
{
	private LinearLayout mLayoutView;

	private int storeID;
	private Context mContext;

	/* 业务编码 */
	private int businessFlag = Consts.NUMBER_ZERO;

	private ReservationService reservationServ;

	private EmployeeListAdapter employeeListAdapter;

	// 雇员列表
	private ListView employeeListView;

	private TextView storeNameView;
	private TextView storeAddrView;
	private TextView storeContractView;
	private TextView storeDescriptionView;
	private StoreBean storeInfo = null;
	
	
	

	private ArrayList<EmployeesBean> employeeList;

	@Override
	public View getLayoutView(Object value)
	{
		storeInfo = AppContext.instance().appServ.getStoreBeanByID((int)value);
		businessFlag = storeInfo.bussinessFlag;
		_initTop();
		setBackground();
		return mLayoutView;
	}


	public StoreEmployeeView(Context context, Object value)
	{
		this.mContext = context;
		this.storeID = (Integer) value;
		reservationServ = AppContext.instance().appServ;
		_init();
		_initListener();
		
	}

	private void _init()
	{
		mLayoutView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.store_detail, null);
		employeeListView = (ListView) mLayoutView.findViewById(R.id.store_employees_detail_list);
		storeNameView = (TextView) mLayoutView.findViewById(R.id.store_name_desc);
		storeAddrView = (TextView) mLayoutView.findViewById(R.id.store_detail_address);
		storeContractView = (TextView) mLayoutView.findViewById(R.id.store_detail_contract);
		storeDescriptionView = (TextView) mLayoutView.findViewById(R.id.store_description);
		
		storeInfo = AppContext.instance().appServ.getStoreBeanByID(storeID);
		businessFlag = storeInfo.bussinessFlag;
		setBackground();
		_initStoreInfoData();
		//reservationServ.progressDialog=ProgressDialog.show(mContext, "请稍等", "努力的加载中...");
		employeeList = reservationServ.employeesList;
	
		
		// 店家列表
		employeeListAdapter = new EmployeeListAdapter();

		employeeListAdapter.setData(employeeList);
		employeeListView.setAdapter(employeeListAdapter);

		reservationServ.regeditObserver(ObserverConsts.DATA_LOVE_EMPLOYEE, this);
		reservationServ.regeditObserver(ObserverConsts.DATA_EMP_PRAISE,this);
		reservationServ.regeditObserver(ObserverConsts.DATA_ITEM, this);
		reservationServ.regeditObserver(ObserverConsts.DATA_RESERVATION, this);
		reservationServ.regeditObserver(ObserverConsts.DATA_EMPLOYEE_LIST, this);
	}

	@Override
	public void setBackground()
	{
		switch (businessFlag)
		{
		case Consts.DO_HAIRCUT:
			mLayoutView.setBackgroundResource(R.drawable.haircutmain);
			break;
		case Consts.DO_FOOT:
			mLayoutView.setBackgroundResource(R.drawable.footer_main);
			break;
		case Consts.DO_COSMETOLOGY:
			mLayoutView.setBackgroundResource(R.drawable.hairdressing);
			break;
		default:
			break;
		}

	}

	public void unSetBackground()
	{
		/*反注册
		 * store_detail_bg.setBackgroundResource(0);
		 * store_detail_layout.setBackgroundResource(0);
		 * 
		 * System.gc();
		 */
	}

	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		// 填空值显示
		topView.refershTopDescription(true, storeInfo.name);
	}

	private void _initStoreInfoData()
	{
		storeNameView.setText(storeInfo.name);
		storeAddrView.setText(storeInfo.description);
		storeContractView.setText(storeInfo.tel);
		storeDescriptionView.setText(storeInfo.saleProdcutDescription);
	}

	private void _initListener()
	{
		
	}

	/**
	 * 雇员列表加载
	 * 
	 * @author Administrator
	 * 
	 */
	
	private class EmployeeListAdapter extends BaseAdapter
	{
		private ArrayList<EmployeesBean> employeeList = new ArrayList<EmployeesBean>();
		private LayoutInflater mInflater = LayoutInflater.from(StoreEmployeeView.this.mContext);
		private EmployeesBean employeeItem = null;

		@Override
		public int getCount()
		{
			return employeeList.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			return employeeList.get(arg0);
		}

		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		public void setData(ArrayList<EmployeesBean> employeeList)
		{
			this.employeeList = employeeList;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2)
		{
			employeeViewHolder employeeViewHolder = new employeeViewHolder();
			employeeItem = employeeList.get(arg0);
			int employeeID = -1;
			if (arg1 == null)
			{
				arg1 = this.mInflater.inflate(R.layout.employee_item, null);
				employeeViewHolder.relativeLayout = (RelativeLayout) arg1.findViewById(R.id.employee_item_layout);
				employeeViewHolder.employeeImage = (ImageView) arg1.findViewById(R.id.employee_image);
				employeeViewHolder.employeeName = (TextView) arg1.findViewById(R.id.employee_name);
				employeeViewHolder.employeeGoodat = (TextView) arg1.findViewById(R.id.employee_goodat);
				employeeViewHolder.employee_priase = (ImageView) arg1.findViewById(R.id.employee_priase);
				employeeViewHolder.employee_PraiseCount = (TextView) arg1.findViewById(R.id.employee_PraiseCount);
				employeeViewHolder.employee_stowCount = (TextView) arg1.findViewById(R.id.employee_stowCount);
				employeeViewHolder.employee_stow = (ImageView) arg1.findViewById(R.id.employee_stow);
				employeeViewHolder.employee_reservation_btn = (Button) arg1.findViewById(R.id.employee_reservation_btn);
				arg1.setTag(employeeViewHolder);
			} else
			{
				employeeViewHolder = (employeeViewHolder) arg1.getTag();
			}
			switch (businessFlag)
			{
			case Consts.DO_HAIRCUT:
				employeeViewHolder.relativeLayout.setBackgroundResource(R.drawable.haircut_employee);
				employeeViewHolder.employee_reservation_btn.setBackgroundResource(R.drawable.haircut_reversation);
				
				break;

			case Consts.DO_FOOT:
				employeeViewHolder.relativeLayout.setBackgroundResource(R.drawable.footer_employee);
				employeeViewHolder.employee_reservation_btn.setBackgroundResource(R.drawable.footer_reversation);
				break;
			case Consts.DO_COSMETOLOGY:
				employeeViewHolder.relativeLayout.setBackgroundResource(R.drawable.hardression_person);
				employeeViewHolder.employee_reservation_btn.setBackgroundResource(R.drawable.btn_revation);
				break;

			default:
				break;
			}

			employeeViewHolder.employeeName.setText(employeeItem.name);
			employeeViewHolder.employeeGoodat.setText(employeeItem.goodat);
			employeeViewHolder.employee_stowCount.setText("(".concat(String.valueOf(employeeItem.loveCount))
					.concat(")"));
			// employeeViewHolder.employeeImage.setImageResource(R.drawable.shop_emplyee_header01);

			employeeID = employeeItem.ID;
			employeeViewHolder.employee_priase.setTag(employeeID);
			employeeViewHolder.employee_PraiseCount.setText(String.valueOf(employeeItem.Praise));
			employeeViewHolder.employee_stow.setTag(employeeID);
			employeeViewHolder.employee_reservation_btn.setTag(employeeID);
			// 用户点击赞事件
			employeeViewHolder.employee_priase.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					int employeeID = (Integer) v.getTag();
					reservationServ.sendCmdAddEmpPraise(employeeID);

				}
			});

			// 用戶收藏
			employeeViewHolder.employee_stow.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					int employeeID = (Integer) v.getTag();
					AppContext.instance().appServ.sendCmdDoLoveEmployee(employeeID);
				}
			});

			// 我要预约
			employeeViewHolder.employee_reservation_btn.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{

					int employeeID = (Integer) v.getTag();
					unSetBackground();
					ReservationBussinessBean bean = new ReservationBussinessBean();
					bean.employeeID = employeeID;
					bean.store = storeInfo;
					reservationServ.progressDialog=CustomProgressDialog.createDialog(mContext);
					reservationServ.progressDialog.setMessage("正在加载中...");
					reservationServ.progressDialog.show();
					reservationServ.sendGetStoreItem(bean);
				}
			});

			return arg1;
		}
	}

	class employeeViewHolder
	{
		
		RelativeLayout relativeLayout;
		ImageView employeeImage;
		/* 点赞 */
		ImageView employee_priase;
		/* 姓名 */
		TextView employeeName;
		/* 擅长 */
		TextView employeeGoodat;
		/* 赞数量 */
		TextView employee_PraiseCount;
		/* 收藏数量 */
		TextView employee_stowCount;
		/* 加入收藏 */
		ImageView employee_stow;
		/* 预约 */
		Button employee_reservation_btn;
	}

	@Override
	public void unRegisterListenrService()
	{
		reservationServ.unRegeditObserver(ObserverConsts.DATA_LOVE_EMPLOYEE, this);
		reservationServ.unRegeditObserver(ObserverConsts.DATA_EMP_PRAISE, this);
		reservationServ.unRegeditObserver(ObserverConsts.DATA_ITEM, this);
		reservationServ.unRegeditObserver(ObserverConsts.DATA_EMPLOYEE_LIST, this);
	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		if (observerKey == ObserverConsts.DATA_LOVE_EMPLOYEE)
		{
			// 刷新手机上收藏的次数，类似点赞的刷新
			EmployeesBean employeeBean = reservationServ.getEmployeeBeanByID(this.employeeList, (Integer) value);
			employeeBean.loveCount = employeeBean.loveCount + 1;
			employeeListAdapter.notifyDataSetChanged();

		}
		
		if(observerKey==ObserverConsts.DATA_EMP_PRAISE)
		{
			for (EmployeesBean employeeBean : reservationServ.employeesList)
			{
				if(employeeBean.ID==(Integer)value)
				{
					employeeBean.Praise=employeeBean.Praise+1;
				}
			}
			
			employeeListAdapter.notifyDataSetChanged();
		}
		
		
		
		if(observerKey==ObserverConsts.DATA_ITEM){
			MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_RESERVATION, value);
			reservationServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_RESERVATION, value));
		}
		
		
		
		if(observerKey==ObserverConsts.DATA_RESERVATION){
			
			if((Integer)value  == Consts.RSP_OK)
			{
				
			}
			
		}
		
		
		if(observerKey ==ObserverConsts.DATA_EMPLOYEE_LIST){
			this.employeeList=reservationServ.employeesList;
			employeeListAdapter.setData(this.employeeList);
			employeeListAdapter.notifyDataSetChanged();
			
		}

	}

}
