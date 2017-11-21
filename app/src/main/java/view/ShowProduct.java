package view;

import java.util.ArrayList;

import model.ProductBean;
import service.ReservationService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;





import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.ViewManager;


/**
 * 产品展示
 */
public class ShowProduct extends BaseView
{
	private ReservationService aprser;
	/* 上下文环境 */
	private Context mContext;

	private RelativeLayout mLayoutView;

	/* listView */
	private ListView listView;

	/* 搜索 */
	private Button btnSearch;

	private ProductAdapter productAdapter;

	/* 业务选择 */
	private Spinner busnessFlag;

	/* 搜索框 */
	private AutoCompleteTextView autoCompleteSearch;

	public ShowProduct(Context context)
	{
		this.mContext = context;
		// 实例化UI控件
		aprser = AppContext.instance().appServ;
		this.findViews();
		initViews();
		/* 初始化顶部 */
		setListeners();
	}

	/**
	 * findViews(实例化UI控件)
	 */
	private void findViews()
	{
		mLayoutView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.product, null);
		listView = (ListView) mLayoutView.findViewById(R.id.product_list);
		btnSearch = (Button) mLayoutView.findViewById(R.id.btn_product);
		autoCompleteSearch = (AutoCompleteTextView) mLayoutView.findViewById(R.id.product_search);
		busnessFlag = (Spinner) mLayoutView.findViewById(R.id.productCategory);
		_initTop();
	}

	/**
	 * 初始化数据
	 */
	private void initViews()
	{
		// TODO Auto-generated method stub
		ProductAdapter productAdapter = new ProductAdapter(this.mContext);
		//productAdapter.setAdapter(null);
		listView.setAdapter(productAdapter);
	}

	/**
	 * setListeners(设置监听事件)
	 */
	private void setListeners()
	{
		
		/*商品选中事件*/
		listView.setOnItemSelectedListener(new OnItemSelectedListener()
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
		
		/*商品搜索*/
		btnSearch.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		
		/*下拉选中事件*/
		autoCompleteSearch.setOnItemSelectedListener(new OnItemSelectedListener()
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
		// TODO Auto-generated method stub

	}

	@Override
	public View getLayoutView(Object value)
	{
		// TODO Auto-generated method stub
		_initTop();
		return mLayoutView;
	}

	public class ProductAdapter extends BaseAdapter
	{
		private ArrayList<ProductBean> productes;
		
		private Context  mContext;
		public ProductAdapter(Context context)
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
			if (convertView == null)
			{
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.product_item, null);
				viewHolder.mRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.shop_item_layout);
				viewHolder.imageView = (ImageView) convertView.findViewById(R.id.shop_image);
				viewHolder.productName = (TextView) convertView.findViewById(R.id.shop_product_name);
				viewHolder.price = (TextView) convertView.findViewById(R.id.shop_product_price);
				viewHolder.detail = (TextView) convertView.findViewById(R.id.shop_product_description);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		public void setAdapter(ArrayList<ProductBean> productes)
		{
			this.productes = productes;
		}

	}

	final class ViewHolder
	{
		/*视图容器*/
		 RelativeLayout  mRelativeLayout;
		
		/*imageView*/
		 ImageView imageView;
		 
		 TextView productName;
		 
		 TextView detail;
		 
		 TextView  price;
		
	}
	
	
	public void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		/* 用户登陆 */
		//topView.refershTopDescription(true, mContext.getResources().getString(R.string.top_login));
	}

}
