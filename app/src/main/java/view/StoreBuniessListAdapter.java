package view;

import java.util.ArrayList;

import model.StoreBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bussinessModel.ViewInfoBean;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;

/**
 * 界面店家列表加载
 * 
 * @author Administrator
 * 
 */
public class StoreBuniessListAdapter extends BaseAdapter
{

	private Context mContext;
	private ArrayList<StoreBean> storeList = new ArrayList<StoreBean>();
	private LayoutInflater mInflater;
	private StoreBean storeInfo;
	private BaseView parentView;
	private boolean fromWhere;

	public StoreBuniessListAdapter(Context context, BaseView parentView, boolean fromwhere)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		
		this.parentView = parentView;
		this.fromWhere = fromwhere;
	}

	@Override
	public int getCount()
	{
		/* true store_business 跳转 */
		if (!fromWhere)
		{
			return Consts.NUMBER_FOUR;
		} else
		{
			return storeList.size();
		}

	}

	@Override
	public Object getItem(int arg0)
	{
		return storeList.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		return arg0;
	}

	public void setData(ArrayList<StoreBean> storeList)
	{
		this.storeList = storeList;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		StoreListViewHolder storeViewHolder = null;
		storeInfo = storeList.get(arg0);
		if (arg1 == null)
		{
			storeViewHolder = new StoreListViewHolder();
			if (!fromWhere)
			{
				arg1 = mInflater.inflate(R.layout.store_busniess_item, null);
			} else
			{
				arg1 = mInflater.inflate(R.layout.store_item, null);
			}
			storeViewHolder.storeImage = (ImageView) arg1.findViewById(R.id.store_image);
			storeViewHolder.relativeLayout = (RelativeLayout) arg1.findViewById(R.id.store_item_layout);
			storeViewHolder.storeStarLevel1 = (ImageView) arg1.findViewById(R.id.store_star_level1);
			storeViewHolder.storeStarLevel2 = (ImageView) arg1.findViewById(R.id.store_star_level2);
			storeViewHolder.storeStarLevel3 = (ImageView) arg1.findViewById(R.id.store_star_level3);
			storeViewHolder.storeStarLevel4 = (ImageView) arg1.findViewById(R.id.store_star_level4);
			storeViewHolder.storeStarLevel5 = (ImageView) arg1.findViewById(R.id.store_star_level5);
			storeViewHolder.storeName = (TextView) arg1.findViewById(R.id.store_name);
			storeViewHolder.storeAddress = (TextView) arg1.findViewById(R.id.store_address);
			storeViewHolder.storePraise = (ImageView) arg1.findViewById(R.id.store_Praise);
			storeViewHolder.storePraiseCount = (TextView) arg1.findViewById(R.id.store_PraiseCount);
			storeViewHolder.storePraise.setTag(storeInfo.ID);
			arg1.setTag(storeViewHolder);

		} else
		{
			storeViewHolder = (StoreListViewHolder) arg1.getTag();

		}
		if (!fromWhere)
		{
			if (((StoreViewBusiness) parentView).getBussinessFlag() == Consts.DO_COSMETOLOGY)
			{
				storeViewHolder.relativeLayout.setBackgroundResource(R.drawable.haridressing_list);
			} else if (((StoreViewBusiness) parentView).getBussinessFlag() == Consts.DO_FOOT)
			{
				storeViewHolder.relativeLayout.setBackgroundResource(R.drawable.footer_storelist);
			} else
			{
				storeViewHolder.relativeLayout.setBackgroundResource(R.drawable.haricut_storelist);
			}
		} else
		{
			if (((StoreView) parentView).getBussinessFlag() == Consts.DO_COSMETOLOGY)
			{
				storeViewHolder.relativeLayout.setBackgroundResource(R.drawable.haridressing_list);
			} else if (((StoreView) parentView).getBussinessFlag() == Consts.DO_FOOT)
			{
				storeViewHolder.relativeLayout.setBackgroundResource(R.drawable.footer_storelist);
			} else
			{
				storeViewHolder.relativeLayout.setBackgroundResource(R.drawable.haricut_storelist);
			}
		}
		

		storeViewHolder.storeName.setText(storeInfo.name);
		storeViewHolder.storeAddress.setText(storeInfo.description);
		// storeViewHolder.storeImage.setImageResource(R.drawable.haircut01);
		storeViewHolder.storeImage.setTag(storeInfo.ID);
		storeViewHolder.storePraiseCount.setText(String.valueOf(storeInfo.praiseCount));
		storeViewHolder.storeStarLevel2.setVisibility(View.VISIBLE);
		// TODO:替代掉魔鬼数字
		if (storeInfo.starLevel == 1)
		{
			storeViewHolder.storeStarLevel2.setVisibility(View.GONE);
			storeViewHolder.storeStarLevel3.setVisibility(View.GONE);
			storeViewHolder.storeStarLevel4.setVisibility(View.GONE);
			storeViewHolder.storeStarLevel5.setVisibility(View.GONE);

		} else if (storeInfo.starLevel == 2)
		{
			storeViewHolder.storeStarLevel3.setVisibility(View.GONE);
			storeViewHolder.storeStarLevel4.setVisibility(View.GONE);
			storeViewHolder.storeStarLevel5.setVisibility(View.GONE);

		} else if (storeInfo.starLevel == 3)
		{
			storeViewHolder.storeStarLevel4.setVisibility(View.GONE);
			storeViewHolder.storeStarLevel5.setVisibility(View.GONE);

		} else if (storeInfo.starLevel == 4)
		{
			storeViewHolder.storeStarLevel5.setVisibility(View.GONE);

		}
		// 用户点击赞事件
		storeViewHolder.storePraise.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int storeID = (Integer) v.getTag();
				AppContext.instance().appServ.sendCmdAddStorePraise(storeID);
			}
		});

		// 点击图片，加载店面界面
		storeViewHolder.storeImage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				/* Tag传递数据 */

				int storeID = (Integer) v.getTag();
				parentView.unSetBackground();
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_EMPLOYEE, storeID);
				AppContext.instance().appServ.addView2HistoryRouteList(new ViewInfoBean(Consts.VIEW_NAME_EMPLOYEE,
						storeID));
			}
		});

		return arg1;
	}

	
}

/* 视图容器 */
class StoreListViewHolder
{
	RelativeLayout relativeLayout;
	ImageView storeImage;
	ImageView storeStarLevel1;
	ImageView storeStarLevel2;
	ImageView storeStarLevel3;
	ImageView storeStarLevel4;
	ImageView storeStarLevel5;
	TextView storeName;
	TextView storeAddress;
	ImageView storePraise;
	TextView storePraiseCount;

}
