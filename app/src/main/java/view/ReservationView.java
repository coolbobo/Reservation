package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import service.ReservationService;
import model.ReservationBean;
import model.StoreBean;
import model.StoreItemBean;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import bussinessModel.ReservationBussinessBean;
import bussinessModel.ViewInfoBean;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import comm.observer.ObserverConsts;

/**
 * 预约对话框，点击预约界面
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
public class ReservationView extends BaseView
{
    /*预约*/
	private ReservationService arpService;

	private Context mContext;
	
	/*业务编码*/
	private int businessFlag =Consts.NUMBER_ZERO;

	/* 员工编号 */
	private Integer employeeId;

	private RelativeLayout mLayoutView;

	/* 小闹钟图片 */
	private ImageView revervationImage;

	/* 预约text */
	private TextView revervationText;

	/* 布局text */
	private RelativeLayout revertionLayout;

	/* 消费套餐类别 */
	private Spinner consumerCategorySpinner;
	/* 时间 */
	private TextView consumerDate;
	
	private String timeValue;
	
	private Integer storeItemId;
	
	/*时间选择器*/
	private DatePickerDialog pickter;
	
	private int year;
	
	private int month;
	
	private int day;
	
	private ArrayList<StoreItemBean>  storeItemBeans;

	/* 时间段 */
	private Spinner consumerDatetimeSpinner;

	/* 电话号码 */
	private TextView consumerTurephone;

	private Button btnRevervation;

	private Button btnRrevervationCacle;
	
	private TextView empName;
	
	private TextView empPosition;

	private StoreBean store;
	
	private RelativeLayout relativeLayout;


	public ReservationView(Context context, Object value)
	{
		this.mContext = context;
		ReservationBussinessBean bean = (ReservationBussinessBean) value;
		employeeId = bean.employeeID;
		store = bean.store;
		arpService = AppContext.instance().appServ;
		storeItemBeans =arpService.storeItemBean;
		_init();
		_initListeners();
		
	}

	/* 初始化 */
	private void _init()
	{
		mLayoutView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.reservation, null);
		relativeLayout = (RelativeLayout) mLayoutView.findViewById(R.id.reservation_dialog_layout);
		revervationImage = (ImageView) mLayoutView.findViewById(R.id.revervation_image);
		empName = (TextView) mLayoutView.findViewById(R.id.store_detail_address);
		empPosition = (TextView) mLayoutView.findViewById(R.id.store_detail_contract);
		revervationText = (TextView) mLayoutView.findViewById(R.id.revervation_text);
	
		revertionLayout = (RelativeLayout) mLayoutView.findViewById(R.id.revertion_layout);
		consumerCategorySpinner = (Spinner) mLayoutView.findViewById(R.id.consumer_category);
		consumerDatetimeSpinner = (Spinner) mLayoutView.findViewById(R.id.consumer_datetime);
		consumerDate = (TextView) mLayoutView.findViewById(R.id.consumer_date);
		consumerTurephone = (TextView) mLayoutView.findViewById(R.id.consumer_turephone);
		btnRevervation = (Button) mLayoutView.findViewById(R.id.consumer_btnrevation);
		btnRrevervationCacle = (Button) mLayoutView.findViewById(R.id.consumer_btncancle);
		
		_initTop();
		_initDate();
		setBackground();
		_initListData();
		arpService.regeditObserver(ObserverConsts.DATA_RESERVATION, this);
	}
    
	/**
	 * 初始化时间
	 */
	private void _initDate()
	{
		
		empName.setText(store.name);
		
		empPosition.setText(store.description);
		
		/*获取当前日历*/
		Calendar  calendar = Calendar.getInstance(Locale.CHINA);
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		consumerDate.setText(year+"-"+(month+1)+"-"+day);
		
	}

	/**
	 * 初始化下拉列表的数据
	 */
	private void _initListData()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
				AppContext.instance().appServ.getItemBean(storeItemBeans));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		consumerCategorySpinner.setAdapter(adapter);
		 if(storeItemBeans!=null && storeItemBeans.size()>0){
			 storeItemId=storeItemBeans.get(0).ID;
		    }
		
		
		ArrayAdapter<CharSequence> reservationTimeAdapter = ArrayAdapter.createFromResource(mContext,
				R.array.reservationtime, android.R.layout.simple_spinner_item);
		reservationTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		consumerDatetimeSpinner.setAdapter(reservationTimeAdapter);
		if(storeItemBeans!=null  && storeItemBeans.size()>0){
			timeValue=consumerDatetimeSpinner.getItemAtPosition(0).toString();
		    }
		

	}

	/* 事件处理 */
	private void _initListeners()
	{
		// 设置时间
		consumerDate.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				pickter = new DatePickerDialog(ReservationView.this.mContext, new DatePickerDialog.OnDateSetListener(){
				
				
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
					{
						ReservationView.this.year = year;
						ReservationView.this.month = monthOfYear;
						ReservationView.this.day = dayOfMonth;
						consumerDate.setText(year+"-"+(month+1)+"-"+day);
					}
				}, year, month, day);
				
				pickter.show();
				
				
			}
		});
		
		
		
		/*預約*/
		btnRevervation.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				    if(arpService.isLoginSys()){
				    	final ReservationBean reservation =  new ReservationBean();
						reservation.setReservationDate(consumerDate.getText().toString()+timeValue);
						reservation.setConsumeID(storeItemId);
						reservation.setEmployeeID(employeeId);
						arpService.progressDialog=CustomProgressDialog.createDialog(mContext);
						arpService.progressDialog.setMessage("预约中...");
						arpService.progressDialog.show();
						new Thread(new Runnable()
						{
							
							@Override
							public void run()
							{
								arpService.sendCmdDoReservation(reservation);
								
							}
						}).start();
						
				    }else{
				    	
				    	MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_LOGIN, null);
				    	arpService
						.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_LOGIN, null));
				    }
					
				
				
				
			}
		});
		
		
		/*取消*/
		btnRrevervationCacle.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				/*清空所有数据*/
			}
		});
		
		
		consumerDatetimeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				timeValue = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		
		consumerCategorySpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				storeItemId =storeItemBeans.get(position).ID;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void unRegisterListenrService()
	{
		arpService.unRegeditObserver(ObserverConsts.DATA_RESERVATION, this);

	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		if(observerKey==ObserverConsts.DATA_RESERVATION){
			
			if((Integer)value  == Consts.RSP_OK)
			{
				/*预约成功*/
				Toast.makeText(mContext, mContext.getResources().getString(R.string.msg_reservation_success), Toast.LENGTH_SHORT).show();
				
				/*此时需要将我要预约变为预约成功*/
				
				
			}else{
				/*失败*/
				Toast.makeText(mContext, mContext.getResources().getString(R.string.msg_reservation_failed), Toast.LENGTH_SHORT).show();
			}
			
		}
	}

	@Override
	public void unSetBackground()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBackground()
	{
		
	
		if(store.bussinessFlag==Consts.DO_HAIRCUT){
			relativeLayout.setBackgroundResource(R.drawable.haircutmain);
			revertionLayout.setBackgroundResource(R.drawable.haircut_employee);
		}else if(store.bussinessFlag==Consts.DO_FOOT){
			relativeLayout.setBackgroundResource(R.drawable.footer_main);
			revertionLayout.setBackgroundResource(R.drawable.footer_employee);
		}else if(store.bussinessFlag==Consts.DO_COSMETOLOGY){
			relativeLayout.setBackgroundResource(R.drawable.hairdressing);
			revertionLayout.setBackgroundResource(R.drawable.hardression_person);
		}
	}

	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		setBackground();
		return mLayoutView;
	}

	/* 顶部 */
	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		// 填空值显示
		topView.refershTopDescription(true, "技师资料");

	}
	
	
	/***
	 * 
	 * showShare(分享的)
	 * 
	 * @param silent
	 * @param platform
	 */
	public void showShare(boolean silent, String platform, String content)
	{
		// 初始化分享
		ShareSDK.initSDK(MainhomeActivity.mainHomeApp, "211d6383a4d7");
		String url = Consts.ADDRESS + "?id=";

		final OnekeyShare result = new OnekeyShare();
		result.setNotification(R.drawable.ic_launcher, mContext.getString(R.string.app_name));
		result.setAddress(url);
		result.setTitle(content);
		result.setTitleUrl(url);
		result.setText(content + url);
		result.setUrl(url);
		result.setComment(url);
		result.setSite(mContext.getString(R.string.app_name));
		result.setSiteUrl("http://jinjizi.net");
		result.setSilent(silent);
		if (platform != null)
		{
			result.setPlatform(platform);
		}
		result.show(MainhomeActivity.mainHomeApp);
	}

}
