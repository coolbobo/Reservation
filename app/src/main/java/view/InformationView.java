 package view;
import java.util.List;

import com.owned.reservation.R;

import comm.Consts;
import comm.ViewManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 咨询view
 * @author Administrator
 *
 */
public class InformationView extends BaseView
{
	/*上下文环境*/
	private Context mContext;
	/*外层*/
	private RelativeLayout  mLayoutVIew;
	
	/*展示信息*/
	private ListView informationList;
	
	private InformationAdapter adapter;
	
	private int businessFlag = Consts.NUMBER_ZERO;
	
	public InformationView(Context context,Object value){
			this.mContext = context;
			businessFlag = (int) value;
			_init();
			_initListeners();
	}
	/*初始化*/
	private void _init()
	{
		mLayoutVIew = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.information, null);
		informationList =  (ListView) mLayoutVIew.findViewById(R.id.information_all);
		_initTop();
		
		/*初始化数据*/
		InformationAdapter  adapter = new InformationAdapter(mContext);
		informationList.setAdapter(adapter);
	}
    
	/*TOP*/
	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		/* 用户登陆 */
		topView.refershTopDescription(true, mContext.getResources().getString(R.string.hairdreeing_zixun));
		
	}
	private void _initListeners()
	{
		/*选中时间*/
		informationList.setOnItemSelectedListener(new OnItemSelectedListener()
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
		mLayoutVIew.setBackgroundResource(R.drawable.haircutmain);
		informationList.setBackgroundColor(Color.WHITE);
	}

	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		setBackground();
		return mLayoutVIew;
	}
	
}   
	/*适配器*/
	class InformationAdapter extends BaseAdapter{
		private Context  context;
		public InformationAdapter(Context mContext){
			this.context = mContext;
		}
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return 8; 
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder  viewHolder = new ViewHolder();
			if(convertView==null){
				convertView =LayoutInflater.from(context).inflate(R.layout.information_item, null);
				viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.information_relative);
				viewHolder.imageview = (ImageView) convertView.findViewById(R.id.information_image);
				viewHolder.textTitle = (TextView) convertView.findViewById(R.id.information_title);
				viewHolder.textContent = (TextView) convertView.findViewById(R.id.information_content);
				convertView.setTag(viewHolder);
			}else{
				viewHolder =(ViewHolder) convertView.getTag();
			}
			   viewHolder.imageview.setBackgroundResource(R.drawable.zixun_information);
			   viewHolder.textTitle.setText(context.getResources().getString(R.string.hairdreeing_zixun));
			   viewHolder.textTitle.setText(context.getResources().getString(R.string.hairdreeing_content));
			
			
			return convertView;
		}
		
	}
	
	final class ViewHolder{
			
			/*视图容器*/
			RelativeLayout  relativeLayout;
			/*图片*/
			ImageView  imageview;
			
			/*标题*/
			TextView   textTitle;
			
			/*内容*/
			TextView   textContent;
			
		}
	