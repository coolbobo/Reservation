package comm;

import java.util.ArrayList;

/**
 * 区域资源包，用于加载所有的省市和区
 * @author Administrator
 *
 */
public class AreaResuource
{

	public ArrayList<String> allProvinceList;
	
	
	public AreaResuource()
	{
		_initAllProvince();
	}
	
	private void _initAllProvince()
	{
		allProvinceList.add("广东");
		
	}
}
