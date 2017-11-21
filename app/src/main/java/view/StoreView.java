package view;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.StoreBean;
import service.ReservationService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.AppFlashConfig;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import comm.observer.ObserverConsts;

/**
 * 店家列表的首頁面，根据不同的业务，加载不同的店家。美容、足疗和理发
 * 
 * @author liuzhibin
 * 
 */
public class StoreView extends BaseView 
{

	private boolean isFlagfromStore = true;

	/* 店编号 */
	int storeID;

	private RelativeLayout mLayoutView;
	private Context mContext;

	private StoreBuniessListAdapter storeListAdapter;

	private ReservationService reservationServ;
	// 店家列表
	private ListView storeListView;
	private Spinner provinceSpinner;
	private Spinner citySpinner;
	private Spinner areaSpinner;

	/* 附近 */
	private ImageView iamgeView;
	
	AutoCompleteTextView  searchStore;

	/* 搜素 */
	private Button btn_search;

	/* 广告 */
	private ImageView adverlist;

	private ArrayList<StoreBean> storeList;
	
	private Set<String>  serachLists;
	
	private ArrayList<StoreBean> storeEmpList;

	/**
	 * 标识当前进入的界面，理发/美容/足疗 1:理发 2：足疗 3：美容
	 */
	private int bussinessFlag = Consts.NUMBER_ZERO;

	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		setBackground();
		return mLayoutView;
	}

	public StoreView(Context context, Object bussinessFlag)
	{
		mContext = context;
		// 当前的进入的业务标识
		this.bussinessFlag = (Integer) bussinessFlag;
		reservationServ = AppContext.instance().appServ;

		_init();
		
		_initListener();
		/**
		 * 根据店的id 加载所有员工
		 */
		
	}

	private void _init()
	{

		mLayoutView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.store, null);
		/* 省 */
		provinceSpinner = (Spinner) mLayoutView.findViewById(R.id.province_sp);

		citySpinner = (Spinner) mLayoutView.findViewById(R.id.city_sp);

		areaSpinner = (Spinner) mLayoutView.findViewById(R.id.area_sp);

		iamgeView = (ImageView) mLayoutView.findViewById(R.id.vicinity);
		searchStore= (AutoCompleteTextView) mLayoutView.findViewById(R.id.search_store);
		btn_search = (Button) mLayoutView.findViewById(R.id.btn_search);
		adverlist = (ImageView) mLayoutView.findViewById(R.id.advertiment_content);
		// 店家列表
		_initAreaData();

		

		storeListView = (ListView) mLayoutView.findViewById(R.id.store_all_list);
		storeListAdapter = new StoreBuniessListAdapter(mContext, this, isFlagfromStore);
		storeListAdapter.setData(reservationServ.storeList);
		storeListView.setAdapter(storeListAdapter);
		setBackground();
		_initTop();

		reservationServ.regeditObserver(ObserverConsts.DATA_STORE_PRAISE, this);
		reservationServ.regeditObserver(ObserverConsts.DATA_EMPLOYEE_LIST, this);
		_initAutoCompleted();

	}

	private void _initAutoCompleted()
	{
		AppFlashConfig  appflashCOnfig = new AppFlashConfig(mContext);
		Set<String> dataes =appflashCOnfig.getParam("serachLists");
		ArrayList<String> strings = new ArrayList<String>();
		if(dataes!=null){
			Iterator<String> iterator = dataes.iterator();
			while(iterator.hasNext()){
				strings.add(iterator.next());
			}

		}
		ArrayAdapter<String>  adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_dropdown_item_1line, strings);
		searchStore.setAdapter(adapter);
	
	
	}

	public void setBackground()
	{
		switch (bussinessFlag)
		{
		case Consts.DO_HAIRCUT:
			mLayoutView.setBackgroundResource(R.drawable.haircutmain);
			btn_search.setBackgroundResource(R.drawable.harcut_storeserach);

			break;

		case Consts.DO_FOOT:
			mLayoutView.setBackgroundResource(R.drawable.footer_main);
			btn_search.setBackgroundResource(R.drawable.footer_serach);
			break;

		case Consts.DO_COSMETOLOGY:
			mLayoutView.setBackgroundResource(R.drawable.hairdressing);
			btn_search.setBackgroundResource(R.drawable.stores_search);
			break;

		default:
			break;
		}
	}

	@Override
	public void unSetBackground()
	{
	}

	/**
	 * 初始化顶部的View
	 */
	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		// 填空值显示
		topView.refershTopDescription(true, "");
	}

	/**
	 * TODO:初始化位置信息，加载下拉的位置数据
	 */
	private void _initAreaData()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
				reservationServ.supportProvinceList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 加载省份列表
		provinceSpinner.setAdapter(adapter);
		// 将下拉置到当前定位到的省份
		provinceSpinner.setSelection(reservationServ.getPosByData(AppContext.instance().currentArea.provinceName,
				reservationServ.supportProvinceList));
		_DoProvinceChange(AppContext.instance().currentArea.provinceName);

		_DoCityChange(AppContext.instance().currentArea.cityName);
	}

	/**
	 * 省份发生变化时，加载不同的市。
	 * 
	 * @param provinceID
	 */
	private void _DoProvinceChange(String provinceName)
	{
		ArrayList<String> supportCityList = reservationServ.supportCityMap.get(provinceName);
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
				supportCityList);
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		citySpinner.setAdapter(cityAdapter);
		citySpinner.setSelection(reservationServ.getPosByData(AppContext.instance().currentArea.cityName,
				supportCityList));
	}

	/**
	 * 选择的市发生变化时，加载不同的区
	 * 
	 * @param cityID
	 */
	private void _DoCityChange(String cityName)
	{
		ArrayList<String> supportAreaList = reservationServ.supportAreaMap.get(cityName);
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
				supportAreaList);
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		areaSpinner.setAdapter(cityAdapter);
		areaSpinner.setSelection(reservationServ.getPosByData(AppContext.instance().currentArea.areaName,
				supportAreaList));
	}

	private void _initListener()
	{

		/* 进入详细页面 */
		storeListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3)
			{
				unSetBackground();
				/*加载员工所有数据*/
				reservationServ.progressDialog=CustomProgressDialog.createDialog(mContext);
				reservationServ.progressDialog.setMessage("正在加载中...");
				reservationServ.progressDialog.show();
				
				new Thread(new Runnable()
				{
					
					@Override
					public void run()
					{
						reservationServ.sendCmdGetEmployeesListByFlag(reservationServ.storeList.get(arg2).ID);
						
					}
				}).start();
				

			}

		});

		/* 省加载事件 */
		provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				// TODO Auto-generated method stub
				String proviceName = arg0.getItemAtPosition(arg2).toString();
				_DoProvinceChange(proviceName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		/* 城市加载 */
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				// TODO Auto-generated method stub
				String cityName = arg0.getItemAtPosition(arg2).toString();
				_DoCityChange(cityName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		/* 附近搜索 */
		iamgeView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

			}
		});

		
		/* 根据店名搜索 */
		btn_search.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				AppFlashConfig  appflashCOnfig = new AppFlashConfig(mContext);
				Set<String> dataes =appflashCOnfig.getParam("serachLists");
				if(dataes==null){
					dataes = new HashSet<>();
					dataes.add(searchStore.getText().toString());
				}
				Iterator<String> iterator = dataes.iterator();
				while(iterator.hasNext()){
					if(!searchStore.getText().toString().equals(iterator.next())){
						dataes.add(searchStore.getText().toString());
					}
				}
				appflashCOnfig.setParam("serachLists", dataes);
				String storeName =searchStore.getText().toString();
				storeEmpList = new ArrayList<StoreBean>();
				for (int i = 0; i < storeList.size(); i++)
				{
					StoreBean bean = reservationServ.storeList.get(i);
					
					for (int j = 0; j < storeName.length(); j++)
					{
						for (int z = 0; z <bean.name.length(); z++)
						{
							if(storeName.charAt(j)==bean.name.charAt(z)){
								storeEmpList.add(bean);
							}
						}
					}
				}
				storeListAdapter.setData(storeEmpList);
				storeListAdapter.notifyDataSetChanged();
				
			}

		});

	}

	@Override
	public void unRegisterListenrService()
	{
		reservationServ.unRegeditObserver(ObserverConsts.DATA_STORE_PRAISE, this);
		reservationServ.unRegeditObserver(ObserverConsts.DATA_EMPLOYEE_LIST, this);
	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		if (observerKey == ObserverConsts.DATA_STORE_PRAISE)
		{
			for (StoreBean storeBean : reservationServ.storeList)
			{
				if(storeBean.ID==(Integer)value){
					storeBean.praiseCount=storeBean.praiseCount+1;
					
				}
			}
			
			storeListAdapter.notifyDataSetChanged();
			
		}
		
		
		if(observerKey == ObserverConsts.DATA_EMPLOYEE_LIST){
			
			/*回调跳转界面*/
			MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_EMPLOYEE,(Integer)value);
			reservationServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_EMPLOYEE, null));
			
		}

	}

	public int getBussinessFlag()
	{
		return bussinessFlag;
	}




}
