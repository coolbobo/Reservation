package model;

import java.util.Date;

public class AdvertisementBean
{
	private String advDesc;
	private int obtainStroeID; // 店家的ID
	private int flag; // 标识是否为公共广告区域
	private Date expireDate;
	private int sortNo;
	// 店家属性，用于设置店家的属于理发、足疗和美容
	private int storeProperty;

	public String getAdvDesc()
	{
		return advDesc;
	}

	public int getStoreProperty()
	{
		return storeProperty;
	}

	public void setStoreProperty(int storeProperty)
	{
		this.storeProperty = storeProperty;
	}

	public void setObtainStroeID(int obtainStroeID)
	{
		this.obtainStroeID = obtainStroeID;
	}

	public void setFlag(int flag)
	{
		this.flag = flag;
	}

	public void setSortNo(int sortNo)
	{
		this.sortNo = sortNo;
	}

	public void setAdvDesc(String advDesc)
	{
		this.advDesc = advDesc;
	}

	public Integer getObtainStroeID()
	{
		return obtainStroeID;
	}

	public void setObtainStroeID(Integer obtainStroeID)
	{
		this.obtainStroeID = obtainStroeID;
	}

	public Integer getFlag()
	{
		return flag;
	}

	public void setFlag(Integer flag)
	{
		this.flag = flag;
	}

	public Date getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(Date expireDate)
	{
		this.expireDate = expireDate;
	}

	public Integer getSortNo()
	{
		return sortNo;
	}

	public void setSortNo(Integer sortNo)
	{
		this.sortNo = sortNo;
	}

}
