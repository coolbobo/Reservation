package messageAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import model.AdvertisementBean;
import model.AreaBean;
import model.BaseRecordHisBean;
import model.EmployeesBean;
import model.LoginUesrBean;
import model.LoveBean;
import model.ReservationBean;
import model.StoreBean;
import model.StoreItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comm.AppContext;

/**
 * 消息适配层，用于适配需要发送消息的实体。 接收远端的消息，并解析成本地的内存或者Flash数据
 * 
 * @author Administrator
 * 
 */
public class MessageAdapter
{

	/**
	 * 填充消息体，用于下发参数给后台服务
	 * 
	 * @param command
	 * @param value
	 * @return
	 */
	public String getSendCMDMsg(String command, Object value)
	{
		String msgValue = "";
		/*点赞*/
		if (command.equalsIgnoreCase(CmdMessage.CMD_ADD_PRAISE))
		{
		/*充值记录*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_CASH_HIS))
		{
		/*收藏记录*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_LOVE_HIS))
		{
		/*预约记录*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_RESERVATION_HIS))
		{
        /*执行登陆*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_LOGIN))
		{
        /*注册*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_REGEDIT))
		{
		/*我要预约*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_RESERVATION))
		{
        /*收藏喜欢的发型师*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_LOVE_EMPLOYEES))
		{
        /*获取短信验证码*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_PHONE_CODE))
		{
        /*获取的同一省份的店家列表*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_STORE_INFO))
		{
        /*获取雇员列表*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_EMPLOYEES_INFO))
		{
        /*获取广告雇员列表*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_ADVER_INFO))
		{
        /*获取支持的省份*/
		} else if (command.equalsIgnoreCase(CmdMessage.CMD_GET_SUPPORT_PROVINCE))
		{
        /*获取支持的城市*/
		} 
		
		return msgValue;
	}

	/**
	 * 获取广告列表，转换到内部数据结构
	 * 
	 * @param resultInfo
	 * @return
	 */
	public ArrayList<AdvertisementBean> tranData2Advertisement(JSONArray resultInfo)
	{   
		ArrayList<AdvertisementBean>  advertisementBeanes = new ArrayList<AdvertisementBean>();
		if(resultInfo==null || resultInfo.length()<=0){
			return null;
		}
		 
		for (int i = 0; i < resultInfo.length(); i++)
		{
			AdvertisementBean  tempAdvertBean = new AdvertisementBean();
			try
			{
				JSONObject  jsonObject = resultInfo.getJSONObject(i);
				tempAdvertBean.setAdvDesc(jsonObject.getString(""));
			   
				/*有效期*/
				tempAdvertBean.setExpireDate(Date.valueOf(jsonObject.getString("")));
				
				tempAdvertBean.setFlag(jsonObject.getInt(""));
				tempAdvertBean.setObtainStroeID(jsonObject.getInt(""));
				tempAdvertBean.setSortNo(jsonObject.getInt(""));
				tempAdvertBean.setStoreProperty(jsonObject.getInt(""));
				advertisementBeanes.add(tempAdvertBean);
			} catch (JSONException e)
			{
				
				e.printStackTrace();
			}
		}
		return advertisementBeanes;
		
	}

	/**
	 * 获取店家数据，转换到内部的数据结构
	 * 
	 * @param resultInfo
	 * @return
	 */
	public ArrayList<StoreBean> tranData2Store(JSONArray resultInfo)
	{
		if(resultInfo == null || resultInfo.length()<=0){
			return null;
		}
		ArrayList<StoreBean>  storeBeanes = new ArrayList<StoreBean>(); 
		for (int i = 0; i < resultInfo.length(); i++)
		{
			StoreBean  tempStoreBean = new StoreBean();
			try
			{
				JSONObject  jsonObject = resultInfo.getJSONObject(i);
			    tempStoreBean.ID = jsonObject.getInt("id");
			    tempStoreBean.bussinessFlag = jsonObject.getInt("bussinessFlag");
			    //tempStoreBean.contracePhone = jsonObject.getString("phone");
			   // tempStoreBean.contractName = jsonObject.getString("");
			    tempStoreBean.description = jsonObject.getString("detailAddr");
			    //tempStoreBean.distance = jsonObject.getInt("");
			    //tempStoreBean.location = (AreaBean) jsonObject.get("");
			    tempStoreBean.name = jsonObject.getString("name");
			    tempStoreBean.saleProdcutDescription = jsonObject.getString("introDuction");
			    // tempStoreBean.sortNo = jsonObject.getInt("");
			    //tempStoreBean.starLevel = jsonObject.getInt("");
			    tempStoreBean.praiseCount = jsonObject.getInt("praiseCount");
			    tempStoreBean.tel = jsonObject.getString("phone");
			    tempStoreBean.starLevel = jsonObject.getInt("flag");
			    storeBeanes.add(tempStoreBean);
			} catch (JSONException e)
			{
				
				e.printStackTrace();
			}
		}
		return storeBeanes;
	}

	/**
	 * 获取店家员工列表
	 * 
	 * @param resultInfo
	 * @return
	 */
	public ArrayList<EmployeesBean> tranData2Employees(JSONArray jsonArray)
	{
		
		ArrayList<EmployeesBean>  employeesBeanes = new ArrayList<EmployeesBean>(); 
		for (int i = 0; i < jsonArray.length(); i++)
		{
			EmployeesBean  tempEmployeeBean = new EmployeesBean();
			try
			{
				JSONObject  jsonObject = jsonArray.getJSONObject(i);
			    tempEmployeeBean.ID = jsonObject.getInt("id");
			    tempEmployeeBean.goodat = jsonObject.getString("introduction");
			    tempEmployeeBean.name = jsonObject.getString("name");
			    tempEmployeeBean.obtainStroeID = jsonObject.getInt("obtainStroeID");
			    tempEmployeeBean.Praise = jsonObject.getInt("praiseCount");
			    employeesBeanes.add(tempEmployeeBean);
			} catch (JSONException e)
			{
				
				e.printStackTrace();
			}
		}
		return employeesBeanes;
		
	}

	/**
	 * 获取充值记录，转换到内部数据结构
	 * 
	 * @param resultInfo
	 * @return
	 */
	public ArrayList<BaseRecordHisBean> tranData2Cash(JSONArray resultInfo)
	{
		if(resultInfo == null || resultInfo.length()<=0){
			return null;
		}
		ArrayList<BaseRecordHisBean>  baseRecordBeanes = new ArrayList<BaseRecordHisBean>(); 
		for (int i = 0; i < resultInfo.length(); i++)
		{
			BaseRecordHisBean  tempBaseRecordBean = new BaseRecordHisBean();

				
				try
				{
					JSONObject item = resultInfo.getJSONObject(i);
					
					StringBuffer  buffer = new StringBuffer();
					buffer.append(AppContext.instance().userData.loginName);
					buffer.append(",在");
					buffer.append(item.get("createDate").toString());
					buffer.append("充值");
					buffer.append(item.getDouble("cash"));
					tempBaseRecordBean.value=buffer.toString();
					
					baseRecordBeanes.add(tempBaseRecordBean);
					
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
		}
		return baseRecordBeanes;
	}

	/**
	 * 获取预约记录，转换到充值记录中。
	 * 
	 * @param resultInfo
	 * @return
	 */
	public ArrayList<BaseRecordHisBean> tranData2Reservation(JSONArray resultInfo)
	{
		if(resultInfo == null || resultInfo.length()<=0){
			return null;
		}
		ArrayList<BaseRecordHisBean>  ReservationBeanes = new ArrayList<BaseRecordHisBean>(); 
		for (int i = 0; i < resultInfo.length(); i++)
		{
			
			try
			{
				BaseRecordHisBean  tempReservationBean = new BaseRecordHisBean();
				JSONObject  jsonObject = resultInfo.getJSONObject(i);
				StringBuffer buffer = new StringBuffer();
				buffer.append(AppContext.instance().userData.loginName);
				buffer.append(",在");
				buffer.append(jsonObject.getString("timeBetween").toString());
				buffer.append("预约");
				int empId =jsonObject.getInt("employeeId");
				for ( EmployeesBean employeeId: AppContext.instance().appServ.employeesList)
				{
					if(empId==employeeId.ID){
						buffer.append(employeeId.name);
					}
				}
				buffer.append("服务");
				tempReservationBean.value=buffer.toString();
				ReservationBeanes.add(tempReservationBean);
			} catch (JSONException e)
			{
				
				e.printStackTrace();
			}
		}
		return ReservationBeanes;
	
	}

	/**
	 * 将返回的消息结果，转换成收藏记录。
	 * 
	 * @param resultInfo
	 * @return
	 */
	public ArrayList<BaseRecordHisBean> tranData2Love(JSONArray resultInfo)
	{
		if(resultInfo == null || resultInfo.length()<=0){
			return null;
		}
		ArrayList<BaseRecordHisBean>  loveBeanes = new ArrayList<BaseRecordHisBean>(); 
		for (int i = 0; i < resultInfo.length(); i++)
		{
			try
			{
				BaseRecordHisBean  tempBaseRecordBean = new BaseRecordHisBean();
				JSONObject  item = resultInfo.getJSONObject(i);
				StringBuffer  buffer = new StringBuffer();
				buffer.append(AppContext.instance().userData.loginName);
				buffer.append(",在");
				buffer.append(item.get("createDate").toString());
				buffer.append("收藏");
				int empId =item.getInt("employeeID");
				for ( EmployeesBean employeeId: AppContext.instance().appServ.employeesList)
				{
					if(empId==employeeId.ID){
						buffer.append(employeeId.name);
					}
				}
				tempBaseRecordBean.value=buffer.toString();
				loveBeanes.add(tempBaseRecordBean);
			} catch (JSONException e)
			{
				
				e.printStackTrace();
			}
		}
		return loveBeanes;
	}

	/**
	 * 将返回的消息结果，转换成短信验证码。
	 * 
	 * @param resultInfo
	 * @return
	 */
	public String tranData2PhoneCode(String resultInfo)
	{
		if(resultInfo== null){
			return null;
		}
		return resultInfo;
	}

	/**
	 * 获取整个区域
	 * 
	 * @param resultInfo
	 * @return
	 */
	public ArrayList<AreaBean> tranData2SupportProvince(JSONArray resultInfo)
	{
		ArrayList<AreaBean>  areaBeans = new ArrayList<AreaBean>();
		if(resultInfo== null || resultInfo.length()<=0 ){
			return null;
		}
		for (int i = 0; i < resultInfo.length(); i++)
		{
			try
			{
				AreaBean temp = new AreaBean();
				JSONObject  josnObject = resultInfo.getJSONObject(i);
				temp.provinceName=josnObject.getString("province");
				temp.cityName = josnObject.getString("city");
				temp.areaName = josnObject.getString("area");
				areaBeans.add(temp);
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return areaBeans;
	}
   public ArrayList<String>  getSupportProvices(ArrayList<AreaBean> areaBeans){
	   ArrayList<String> provinceSpinner = new ArrayList<String>();   
	   for (int i = 0; i < areaBeans.size(); i++)
	   {
		   if(provinceSpinner.size()>0){
			   if(provinceSpinner.get(i).equals(areaBeans.get(i).provinceName)){
				   
			   }
		   }
		   provinceSpinner.add(areaBeans.get(i).provinceName);
			
	   }
	   return provinceSpinner;
   }
	/**
	 * 目前已经开放市
	 * 
	 * @param resultInfo
	 * @param provinceName
	 * @return
	 */
	public HashMap<String, ArrayList<String>> tranData2SupportCity(ArrayList<AreaBean> areaBeans)
	{
		HashMap<String, ArrayList<String>> citySpinner = new HashMap<String, ArrayList<String>>();
		
		return citySpinner;
	}

	/**
	 * 解析后台获取到的支持的区域，理论上来说，所有的区域都需要支持。
	 * 
	 * @param resultInfo
	 * @param cityName
	 * @return
	 */
	public HashMap<String, ArrayList<String>> tranData2SupportArea(ArrayList<AreaBean> areaBeans)
	{
		HashMap<String, ArrayList<String>> AreaSpinner = new HashMap<String, ArrayList<String>>();

		return AreaSpinner;
	}
	
	
	/**
	 * 消费套餐项目
	 * @param jsonArray
	 * @return
	 */
	public ArrayList<StoreItemBean>  testStoreItemBean(JSONArray resultInfo){
		ArrayList<StoreItemBean>  storeItemBeans = new ArrayList<StoreItemBean>();
		for (int i = 0; i < resultInfo.length(); i++)
		{
			try
			{
				JSONObject temp  =resultInfo.getJSONObject(i);
				StoreItemBean  itemBean = new StoreItemBean();
				itemBean.ID = temp.getInt("id");
				itemBean.description = temp.getString("itemDescription");
				storeItemBeans.add(itemBean);
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return storeItemBeans;
	}

	/**
	 * 获取当前登陆信息
	 * @param jsonObject
	 * @return
	 */
	public LoginUesrBean tranDataLoginBean(JSONObject  jsonObject){
		LoginUesrBean loginUserBean = new LoginUesrBean();
		try
		{
			loginUserBean.ID = jsonObject.getInt("id");
			loginUserBean.loginName=jsonObject.getString("loginName");
			loginUserBean.aliasName = jsonObject.getString("aliasName");
			loginUserBean.loginPwd = jsonObject.getString("passWord");
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginUserBean;
	}
}
