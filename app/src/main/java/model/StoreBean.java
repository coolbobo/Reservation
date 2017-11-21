package model;

import java.util.ArrayList;

/**
 * 所有的店家信息
 * 
 * @author Administrator
 * 
 */
public class StoreBean
{
	public String name;
	public String description;
	public String tel;
	public int sortNo;
	public String contractName;
	public String contracePhone;
	public AreaBean location;
	public int ID;
	public int bussinessFlag; // 业务标识，标识美容、足疗、美发
	public String saleProdcutDescription;
	public int distance; // 店家距离当前登录用户的距离 ，用于显示排序，将最近的显示在最上方列表。
	public int starLevel;
	public int praiseCount;
	public ArrayList<String> ConsumerPackList;



	/**
	 * 根据店家的信用，识别出具体的星级图片
	 * 
	 * @return
	 */
	public int getStarImage()
	{
		return -1;

	}

}
