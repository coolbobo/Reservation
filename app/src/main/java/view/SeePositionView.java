package view;

import service.ReservationService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;

/**
 * 界面顶层的View，用于返回和显示用户信息
 * 
 * @author liuzhibin
 * 
 */
public class SeePositionView extends BaseView
{
	private ReservationService  revationService;
	/* 上下文环境 */
	private Context mContext;

	/* 视图容器 */
	private RelativeLayout mLayoutView;

	/* 区域 */
	private Spinner spinnerArea;

	/* 职位 */
	private Spinner spinnerJob;

	/* 薪资 */
	private Spinner spinnerSalary;

	/* 筛选 */
	private Button btnFilter;

	/* 工作显示 */
	private ListView jobShow;

	private PositionAdater positionAdater;

	private TextView allCity;

	private TextView position;

	private TextView sure;

	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		return mLayoutView;
	}

	/**
	 * 构�?函数
	 * 
	 * @param context
	 */
	public SeePositionView(Context context)
	{
		this.mContext = context;
		revationService = AppContext.instance().appServ;
		findViews();
		initData();
		setListeners();

	}

	/**
	 * 初始化加载数据
	 */
	private void findViews()
	{

		// 初始化赋值layout布局
		mLayoutView = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.position_can, null);
		spinnerArea = (Spinner) mLayoutView.findViewById(R.id.position_area);
		spinnerJob = (Spinner) mLayoutView.findViewById(R.id.position_job);
		spinnerSalary = (Spinner) mLayoutView.findViewById(R.id.position_salary);
		btnFilter = (Button) mLayoutView.findViewById(R.id.position_filter);
		allCity = (TextView) mLayoutView.findViewById(R.id.position_all);
		position = (TextView) mLayoutView.findViewById(R.id.position_fujin);
		sure = (TextView) mLayoutView.findViewById(R.id.position_sure);
		jobShow = (ListView) mLayoutView.findViewById(R.id.position_show);
	}

	private void setListeners()
	{
		/* 区域 */
		spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});

		/* 职位 */
		spinnerJob.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});

		/* 筛选 */
		btnFilter.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

			}
		});

		/* 薪资 */
		spinnerSalary.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});

		/* 所有区域 */
		allCity.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_RELEASE, null);
				/*加入历史记录*/
				revationService.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_RELEASE, null));
			  
			}
		});
		/* 附近 */
		position.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

			}
		});

		/* 放心企业 */
		sure.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	private void initData()
	{
		jobShow.setAdapter(new PositionAdater(mContext));

	}

	@Override
	public void unRegisterListenrService()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 针对后台数据的变化，刷新界面的显示
	 */
	@Override
	public void viewDataChanged(int observerKey, Object value)
	{

	}

	@Override
	public void unSetBackground()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBackground()
	{

	}

	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
	}

	public class PositionAdater extends BaseAdapter
	{
		private  Context mContext;
		public PositionAdater(Context context)
		{
			this.mContext = context;
		}
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return 10;
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder  viewHolder = new ViewHolder();
			if(convertView==null)
			{
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.position_show, null);
				viewHolder.positionItemBg= (RelativeLayout) convertView.findViewById(R.id.position_itembg);
				viewHolder.btnXing = (ImageView) convertView.findViewById(R.id.btn_xing);
				viewHolder.positionName = (TextView) convertView.findViewById(R.id.position_name);
				viewHolder.positionSalary=(TextView) convertView.findViewById(R.id.position_salary);
				viewHolder.positionCount = (TextView) convertView.findViewById(R.id.positionCount);
				viewHolder.positionSuffer = (TextView) convertView.findViewById(R.id.positionSuffer);
				viewHolder.positionEducation = (TextView) convertView.findViewById(R.id.positionEducation);
				viewHolder.companyName = (TextView) convertView.findViewById(R.id.company);
				viewHolder.workArea = (TextView) convertView.findViewById(R.id.position_address);
				viewHolder.workAddress = (TextView) convertView.findViewById(R.id.position_area);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

	}
    
	/*视图容器*/
	final class ViewHolder
	{
		/*背景*/
		RelativeLayout  positionItemBg;
		
		/*推荐*/
		ImageView   btnXing;
		
		/*职位名字*/
		TextView  positionName;
		
		/*工资*/
		TextView  positionSalary;
		
		/*招聘人数*/
		TextView  positionCount;
		
		/*工作经验*/
		TextView positionSuffer;
		
		/*学历*/
		TextView  positionEducation;
		/*公司名字*/
		TextView companyName;
		
		/*工作地址*/
		TextView workArea;
		
		/*工作地址*/
		TextView workAddress;
		
	}

}
