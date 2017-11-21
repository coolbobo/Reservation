package comm;

import android.content.Context;
import android.widget.LinearLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * 类 名 称：CustomGallery 类 描 述：自定义画廊控件 创 建 人：lyh
 * 
 */
public class CustomGallery extends LinearLayout
{

	/* 适配器 */
	private BaseAdapter adapter;
	/* 适配器的条目选中事件 */
	private OnItemSelectedListener onItemSelectedListener;

	/* 构造函数 */
	public CustomGallery(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setOrientation(HORIZONTAL);// 设定布局为水平方向
	}

	/* 适配器的set方法 */
	public void setAdapter(BaseAdapter adapter)
	{
		this.adapter = adapter;
		// 给画廊加入视图
		for (int i = 0; i < adapter.getCount(); i++)
		{
			View view = adapter.getView(i, null, null);
			// 设置单个视图在画廊里的尺寸
			this.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		}
	}

	/* 适配器的条目选中事件的set方法 */
	public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener)
	{
		this.onItemSelectedListener = onItemSelectedListener;
	}

	/**
	 * 设置条目选中事件
	 * 
	 * @param position
	 *            选中的条目下标
	 */
	public void setItemSelected(int position)
	{
		View view = this.adapter.getView(position, null, null);// 获得选中的视图
		this.onItemSelectedListener.onItemSelected(null, view, position, adapter.getItemId(position));
	}

}
