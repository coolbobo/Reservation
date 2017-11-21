package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import service.ReservationService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.CustomGallery;
import comm.MainhomeActivity;
import comm.ViewManager;
import comm.observer.ObserverConsts;

/**
 * 店家列表的首頁面，根据不同的业务，加载不同的店家。美容、足疗和理发
 * 
 * @author liuzhibin
 * 
 */
public class StoreViewBusiness extends BaseView
{
	/* 标识是否从这个视图跳转 */
	private boolean isFlagfromBusiness = false;
	/* 店编号 */
	private int storeID;

	/* 外层滚动条 */
	private ScrollView scrollView;

	private LinearLayout mLayoutView;
	private Context mContext;
	/* 搜索按钮 */
	private Button btn_serach;

	/* 省 */
	private Spinner provinceSpinner;

	private Spinner citySpinner;

	private Spinner areaSpinner;
	/* 滚动视图 */
	private HorizontalScrollView horizontalScrollView;
	/* 自定义画廊 */
	private CustomGallery customGallery;
	/* 广告图片适配器 */
	private ImageAdapter imageAdapter;
	/* 切换imgaeView */
	private List<ImageView> textViews;
	/* 屏幕宽度 */
	private int widthScreen;
	/* 屏幕高度 */
	private int heightScreen;
	/* 当前选中的条目 */
	private int selectedItemsId = 0;

	/* 在滑动事件中按下时的X坐标 */
	private int downX;

	/* 在滑动事件中松开时的X坐标 */
	private int upX;

	/* 在滑动事件中滑动超过多少标准就换上一张或下一张 */
	private int norm = 0;

	private ImageView textScroll_01;

	private ImageView textScroll_02;

	private ImageView textScroll_03;
	
	
	/*资讯image*/
	private ImageView zixunImage;
	
	/*资讯right*/
	private LinearLayout zixunRight;
	
	/*消费者服务*/
	RelativeLayout  quit;

	/* 更多 */
	private Button btn_choose_shop_more;

	private StoreBuniessListAdapter storeListAdapter;

	private ReservationService reservationServ;

	/* 人气发型师 */
	private HorizontalScrollView haird_resser_list;
	private Handler handler = new Handler()
	{

		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			// 口令验证,判断是否是指定的线程传入
			if (msg.what == 0)
			{

				customGallery.setItemSelected(selectedItemsId);// 图片滚动
			}

		}

	};

	// 店家列表
	private ListView storeListView;

	private RelativeLayout person_faxing;

	private RelativeLayout store_choose_tui;

	private RelativeLayout person_sure;

	/**
	 * 标识当前进入的界面，理发/美容/足疗 1:理发 2：足疗 3：美容
	 */
	private int bussinessFlag = 0;

	@Override
	public View getLayoutView(Object value)
	{
		this.bussinessFlag = (Integer) value;
		_initTop();
		setBackground();
		return scrollView;
	}

	public StoreViewBusiness(Context context, Object bussinessFlag)
	{
		mContext = context;
		// 当前的进入的业务标识
		this.bussinessFlag = (Integer) bussinessFlag;
		reservationServ = AppContext.instance().appServ;
		_initTop();
		_init();
		_initListener();
	}

	private void _init()
	{
		scrollView = (ScrollView) LayoutInflater.from(mContext).inflate(R.layout.store_business, null);
		mLayoutView = (LinearLayout) scrollView.findViewById(R.id.store);
		storeListView = (ListView) scrollView.findViewById(R.id.store_list);
		horizontalScrollView = (HorizontalScrollView) scrollView.findViewById(R.id.horizonScrollView);
		customGallery = (CustomGallery) scrollView.findViewById(R.id.adverlists);
		textScroll_01 = (ImageView) scrollView.findViewById(R.id.adver_gundong1);
		textScroll_02 = (ImageView) scrollView.findViewById(R.id.adver_gundong2);
		textScroll_03 = (ImageView) scrollView.findViewById(R.id.adver_gundong3);
		btn_serach = (Button) scrollView.findViewById(R.id.btn_searchstore);
		zixunImage = (ImageView) mLayoutView.findViewById(R.id.zixun_image);
		zixunRight = (LinearLayout) mLayoutView.findViewById(R.id.zixun_right);
		quit = (RelativeLayout) mLayoutView.findViewById(R.id.service);
		provinceSpinner = (Spinner) scrollView.findViewById(R.id.business_province_sp);
		citySpinner = (Spinner) scrollView.findViewById(R.id.business_city_sp);
		areaSpinner = (Spinner) scrollView.findViewById(R.id.business_area_sp);
		person_faxing = (RelativeLayout) scrollView.findViewById(R.id.person_faxing);
		store_choose_tui = (RelativeLayout) scrollView.findViewById(R.id.store_choose_tui);
		person_sure = (RelativeLayout) scrollView.findViewById(R.id.person_sure);
		loadTextViews();
		haird_resser_list = (HorizontalScrollView) scrollView.findViewById(R.id.haird_resser_list);
		btn_choose_shop_more = (Button) scrollView.findViewById(R.id.choose_shop_more);
		setBackground();
		setHard_resser_List();
		setScreens();
		customGallery.setAdapter(new ImageAdapter());
		/* 定时调动滚动的图片 */
		setSchedule();
		
	
		
		
		
		// 店家列表
		storeListAdapter = new StoreBuniessListAdapter(mContext, this, isFlagfromBusiness);
		storeListAdapter.setData(reservationServ.storeList);
		storeListView.setAdapter(storeListAdapter);
		reservationServ.regeditObserver(ObserverConsts.DATA_EMPLOYEE_LIST, this);
		
		_initAreaData();

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

	@Override
	public void setBackground()
	{
		switch (bussinessFlag)
		{
		case Consts.DO_HAIRCUT:
			mLayoutView.setBackgroundResource(R.drawable.haircutmain);
			btn_serach.setBackgroundResource(R.drawable.haircut_serach);
			store_choose_tui.setBackgroundResource(R.drawable.haircut_tui);
			person_faxing.setBackgroundResource(R.drawable.haircut_power);
			person_sure.setBackgroundResource(R.drawable.haircut_zixun);
			break;

		case Consts.DO_FOOT:
			mLayoutView.setBackgroundResource(R.drawable.footer_main);
			btn_serach.setBackgroundResource(R.drawable.footer_serach);
			store_choose_tui.setBackgroundResource(R.drawable.footer_tui);
			person_faxing.setBackgroundResource(R.drawable.footer_power);
			person_sure.setBackgroundResource(R.drawable.footer_zixun);
			break;

		case Consts.DO_COSMETOLOGY:
			mLayoutView.setBackgroundResource(R.drawable.hairdressing);
			btn_serach.setBackgroundResource(R.drawable.stores_search);
			store_choose_tui.setBackgroundResource(R.drawable.haridressing_tui);
			person_faxing.setBackgroundResource(R.drawable.hairdressing_recommend);
			person_sure.setBackgroundResource(R.drawable.hairdreesing_zixun);
			break;

		default:

			break;
		}
	}

	@Override
	public void unSetBackground()
	{
	}

	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		topView.refreshTextColorAndBackground(bussinessFlag);

		if (this.bussinessFlag == Consts.DO_HAIRCUT)
		{
			topView.refershTopDescription(true, mContext.getResources().getString(R.string.top_haricut));

		} else if (this.bussinessFlag == Consts.DO_FOOT)
		{
			topView.refershTopDescription(true, mContext.getResources().getString(R.string.top_foot));

		} else if (this.bussinessFlag == Consts.DO_COSMETOLOGY)
		{
			topView.refershTopDescription(true, mContext.getResources().getString(R.string.top_cosmetology));
		}
	}

	private void setHard_resser_List()
	{
		int[] recourse = new int[] { R.drawable.hairstylistemp01, R.drawable.hairstylistemp02,
				R.drawable.hairstylistemp01, R.drawable.hairstylistemp02, R.drawable.hairstylistemp01,
				R.drawable.hairstylistemp02, R.drawable.hairstylistemp01, R.drawable.hairstylistemp02,
				R.drawable.hairstylistemp01, R.drawable.hairstylistemp02 };
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);

		for ( int i = 0; i < recourse.length; i++)
		{
			LinearLayout linLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.hairstylist, null);
			ImageView imageView = (ImageView) linLayout.findViewById(R.id.hairstylistItem);
			TextView textView1 = (TextView) linLayout.findViewById(R.id.hairsty_name);
			TextView textView2 = (TextView) linLayout.findViewById(R.id.hairsty_postion);
			TextView textView3 = (TextView) linLayout.findViewById(R.id.hairsty_empNO);
			imageView.setImageResource(recourse[i]);
			imageView.setTag(i);
			textView1.setText("李天佑");
			textView2.setText("张无忌");
			textView3.setText("朱海");
			/**
			 * 点击事件
			 */
			imageView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Toast.makeText(mContext,v.getTag().toString(), Toast.LENGTH_SHORT).show();

				}
			});
			linearLayout.addView(linLayout);
		}
		haird_resser_list.addView(linearLayout);

	}

	private void setSchedule()
	{
		Timer timer = new Timer();// 定时器
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				selectedItemsId++;// 选中的条目下标自增
				// 当下标走到最后一张图片了,从0再来
				if (selectedItemsId >= textViews.size())
				{
					selectedItemsId = 0;
				}
				Message message = Message.obtain();
				message.what = 0;
				handler.sendMessage(message);
			}
		}, 5000, 5000);// 第一个5000是延时多少毫秒再执行、第二个5000是间隔多少毫秒执行一次

	}

	private void setScreens()
	{
		DisplayMetrics metric = new DisplayMetrics();
		MainhomeActivity.mainHomeApp.getWindowManager().getDefaultDisplay().getMetrics(metric);
		widthScreen = metric.widthPixels;
		heightScreen = metric.heightPixels;
	}

	private void loadTextViews()
	{
		// TODO Auto-generated method stub
		textViews = new ArrayList<ImageView>();
		textViews.add(textScroll_01);
		textViews.add(textScroll_02);
		textViews.add(textScroll_03);
	}

	private void _initListener()
	{
		/* 自定义画廊选中事件 */
		customGallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				selectedItemsId = position % textViews.size();
				showgunDong(selectedItemsId);
				horizontalScrollView.scrollTo(selectedItemsId * widthScreen, 0);// 根据选中条目下标进行显示图片

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		/* 滚动监听事件 */
		horizontalScrollView.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View arg0, MotionEvent event)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					downX = (int) event.getX();// 获得按下时的X坐标
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					upX = (int) event.getX();// 获得松开时的X坐标
					norm = widthScreen / 5;// 标准为屏幕宽度的一半
					if (upX - downX > norm)
					{// 从左往右拖超过一半屏幕,转到上一张
						selectedItemsId--;// 选中的条目下标自减
						// 当下标已经是第一张图片了,只能是0,这里可以让它转到最后一张
						if (selectedItemsId < 0)
						{
							selectedItemsId = 0;
						}
					} else if (downX - upX > -norm)
					{
						// 从右往左拖超过一半屏幕,转到下一张
						selectedItemsId++;// 选中的条目下标自增
						// 当下标已经是最后一张图片了,只能是最后一张,这里可以让它转到第一张
						if (selectedItemsId >= textViews.size())
						{
							selectedItemsId = textViews.size() - 1;
						}
					}
					customGallery.setItemSelected(selectedItemsId);
					return true;
				}
				return false;
			}
		});

		/* 进入详细页面 */
		storeListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3)
			{
				unSetBackground();
				/*查询所有员工列表*/
				/*加载员工所有数据*/
				reservationServ.progressDialog=CustomProgressDialog.createDialog(mContext);
				reservationServ.progressDialog.setMessage("正在加载中...");
				reservationServ.progressDialog.show();
				
				new Thread(new Runnable()
				{
					
					@Override
					public void run()
					{
						reservationServ.sendCmdGetEmployeesListByFlag(reservationServ.storeList.get(position).ID);
						
					}
				}).start();
				
			}

		});

		/* 加载所有店的显示 */
		btn_choose_shop_more.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				
				
				unSetBackground();
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_STORE, bussinessFlag);
				reservationServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_STORE, bussinessFlag));

			}
		});

		/* 搜索 */
		btn_serach.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_LOGIN, null);
			}
		});

		for (int i = 0; i < textViews.size(); i++)
		{
			ImageView imageView = textViews.get(i);
			imageView.setTag(i);
			imageView.setOnClickListener(new OnClickListener()
			{

				/* 显示第i 张图片 */
				@Override
				public void onClick(View v)
				{
					showgunDong(selectedItemsId);
					selectedItemsId = (int) v.getTag();
					customGallery.setItemSelected(selectedItemsId);

				}
			});
			
			
			/*省市级联*/
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
			
			
			/*跳转到资讯界面*/
			zixunImage.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_INFORMATION,bussinessFlag);
					reservationServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_INFORMATION, bussinessFlag));
				}
			});
			
			/*跳转到资讯界面*/
			zixunRight.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_INFORMATION,bussinessFlag);
					reservationServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_INFORMATION, bussinessFlag));
				}
			});
			
			
			/*跳转到服务界面*/
			quit.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					
				}
			});
			
			
		}

	}

	@Override
	public void unRegisterListenrService()
	{
		// TODO Auto-generated method stub
		reservationServ.unRegeditObserver(ObserverConsts.DATA_EMPLOYEE_LIST, this);
	}

	/* ImageView */
	public void showgunDong(int position)
	{

		for (int i = 0; i < textViews.size(); i++)
		{

			textViews.get(i).setBackgroundResource(R.drawable.dotselected);
		}
		// 预防数组越界
		if (position >= textViews.size())
		{
			position = textViews.size() - 1;
		} else if (position < 0)
		{
			position = 0;
		}

		textViews.get(position).setBackgroundResource(R.drawable.dotunfoucs);
	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		if(observerKey==ObserverConsts.DATA_EMPLOYEE_LIST){
			MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_EMPLOYEE, (Integer)value);
			reservationServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_EMPLOYEE, (Integer)value));
		}
		
	}

	/* 图片适配器 */
	public class ImageAdapter extends BaseAdapter
	{
		private int[] images = new int[] { R.drawable.hairdressing_advert01, R.drawable.gallery3, R.drawable.gallery5 };

		public int getCount()
		{
			return this.images.length;// 返回图片数组长度
		}

		public Object getItem(int position)
		{
			return position;
		}

		public long getItemId(int position)
		{
			return position;// 返回条目下标
		}

		public View getView(final int position, View convertView, ViewGroup parent)
		{
			LinearLayout layout;// 布局文件,单张图片的布局
			if (convertView == null)
			{

				layout = new LinearLayout(mContext);// 首次加载是空的

			} else
			{
				layout = (LinearLayout) convertView;// 加载完后滑动图片时不用再new
			}
			layout.setLayoutParams(new LayoutParams(widthScreen, LayoutParams.WRAP_CONTENT));// 设定布局文件的大小
			ImageView imageView = new ImageView(mContext);// 图片控件
			imageView.setLayoutParams(new LayoutParams(widthScreen, LayoutParams.MATCH_PARENT));
			imageView.setImageResource(images[position]);
			layout.addView(imageView);// 将图片视图放入父容器中
			imageView.setScaleType(ScaleType.FIT_XY);

			/* 跳转到响应的广告界面 */
			imageView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{

				}
			});
			return layout;
		}

	}

	public int getBussinessFlag()
	{
		return bussinessFlag;
	}

}
