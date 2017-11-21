package service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import messageAdapter.CmdMessage;
import messageAdapter.MessageAdapter;
import model.AdvertisementBean;
import model.AreaBean;
import model.BaseRecordHisBean;
import model.EmployeesBean;
import model.LoginUesrBean;
import model.ProductBean;
import model.ReservationBean;
import model.StoreBean;
import model.StoreItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import view.CustomProgressDialog;
import view.IViewDataObserver;
import view.RevationServiceView;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import bussinessModel.ReservationBussinessBean;
import bussinessModel.ViewInfoBean;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import comm.AppContext;
import comm.Consts;
import comm.GPSLocation;
import comm.observer.GlobalHandler;
import comm.observer.GlobalHandlerAdapter;
import comm.observer.ObserverConsts;
import conn.HttpUtil;

/**
 * 业务服务类，用于服务各个界面
 * 
 * @author Administrator
 * 
 */
public class ReservationService
{

	private Context mContext;

	// 充值记录
	public ArrayList<BaseRecordHisBean> cashHisList = null;

	// 预约记录
	public ArrayList<BaseRecordHisBean> reservationHisList = null;

	// 收藏记录
	public ArrayList<BaseRecordHisBean> loveHistList = null;

	// 店家列表
	public ArrayList<StoreBean> storeList = null;
	
	public ArrayList<StoreItemBean>  storeItemBean; 

	// 浏览历史记录
	private LinkedList<ViewInfoBean> historyViewRouteList = null;

	// 广告列表
	public ArrayList<AdvertisementBean> adverList = null;

	// 店員列表
	public ArrayList<EmployeesBean> employeesList = null;

	/* 产品列表 */
	public ArrayList<ProductBean> productesList = null;

	// 消息接收和发送适配器
	public final MessageAdapter messageAdapter = new MessageAdapter();

	// 观察者列表
	private HashMap<Integer, ArrayList<IViewDataObserver>> ObservedList = null;
	// 支持的区域列表
	private ArrayList<AreaBean> areaBeans = null;
	
	public ArrayList<String> supportProvinceList = null;
	public HashMap<String, ArrayList<String>> supportCityMap = null;
	public HashMap<String, ArrayList<String>> supportAreaMap = null;

	public String mValue;
	
	public CustomProgressDialog progressDialog = null; 

	/**
	 * 构造函数
	 */
	public ReservationService()
	{
		// 初始化数据容器对象，对于装载内存数据
		cashHisList = new ArrayList<BaseRecordHisBean>();
		reservationHisList = new ArrayList<BaseRecordHisBean>();
		loveHistList = new ArrayList<BaseRecordHisBean>();
		storeList = new ArrayList<StoreBean>();
		adverList = new ArrayList<AdvertisementBean>();
		employeesList = new ArrayList<EmployeesBean>();
		historyViewRouteList = new LinkedList<ViewInfoBean>();
		ObservedList = new HashMap<Integer, ArrayList<IViewDataObserver>>();
		productesList = new ArrayList<ProductBean>();
		areaBeans = new ArrayList<AreaBean>();
		supportProvinceList = new ArrayList<String>();
		supportCityMap = new HashMap<String, ArrayList<String>>();
		supportAreaMap = new HashMap<String, ArrayList<String>>();
		storeItemBean = new ArrayList<StoreItemBean>();
		// TODO:加载测试数据，后续正式上线会将数据删除。
		_initTestData();

	}

	/**
	 * 注册观察者
	 * 
	 * @param keyValue
	 * @param view
	 */
	public void regeditObserver(int keyValue, IViewDataObserver view)
	{
		ArrayList<IViewDataObserver> observedList = ObservedList.get(keyValue);
		if (observedList == null)
		{
			observedList = new ArrayList<IViewDataObserver>();
			ObservedList.put(keyValue, observedList);
		}
		if (observedList.contains(view))
		{
			return;
		}
		observedList.add(view);
	}

	/**
	 * 通知观察者，刷新界面
	 * 
	 * @param paramInteger
	 * @param paramObject
	 */
	public void noticeViewDataChange(Integer keyValue, Object value)
	{
		ArrayList<IViewDataObserver> observedList = ObservedList.get(keyValue);
		if (observedList == null)
		{
			return;
		}
		GlobalHandler handler = AppContext.instance().globalHandler;
		Iterator<IViewDataObserver> observedIterator = observedList.iterator();
		while (observedIterator.hasNext())
		{
			IViewDataObserver observerView = (IViewDataObserver) observedIterator.next();
			if (observerView == null)
			{
				continue;
			}
			GlobalHandlerAdapter globalHandlerAdapter = new GlobalHandlerAdapter(observerView, keyValue.intValue(),
					value);
			Message observerMsg = Message.obtain();
			observerMsg.obj = globalHandlerAdapter;
			handler.sendMessage(observerMsg);
		}
	}

	/**
	 * 反注册观察者
	 * 
	 * @param keyValue
	 * @param view
	 */
	public void unRegeditObserver(int keyValue, IViewDataObserver view)
	{
		ArrayList<IViewDataObserver> noticeList = (ArrayList<IViewDataObserver>) ObservedList.get(keyValue);
		if (noticeList != null)
		{
			noticeList.remove(view);
		}
	}

	/**
	 * 根据值，获取下拉列表的位置
	 * 
	 * @param dataValue
	 * @param arrayData
	 * @return
	 */
	public int getPosByData(String dataValue, ArrayList<String> arrayData)
	{
		if (arrayData == null || arrayData.size() == 0)
		{
			return -1;
		}
		String compareValue = null;
		for (int i = 0; i < arrayData.size(); i++)
		{
			compareValue = arrayData.get(i);

			if (dataValue.equalsIgnoreCase(compareValue))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * 为店家点赞，将数据传递到后台
	 * 
	 * @param storeID
	 */
	public void sendCmdAddStorePraise(final int storeID)
	{
		String msg = CmdMessage.CMD_ADD_PRAISE;
		RequestParams  params = new RequestParams();
		params.put("storeId",String.valueOf(storeID));
		
		// 下发赞命令给服务端，更新到数据库后台中
		HttpUtil.get(msg,params, new AsyncHttpResponseHandler()
		{

			public void onSuccess(String arg0)
			{
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						// 点赞成功，本地手工将赞的数据增加1.
						noticeViewDataChange(ObserverConsts.DATA_STORE_PRAISE, storeID);

					}else{
						/*点赞失败*/
						// 点赞成功，本地手工将赞的数据增加1.
						noticeViewDataChange(ObserverConsts.DATA_STORE_PRAISE,Consts.RSP_FAULT);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});

	}
	
	
	
	
	
	/**
	 * 为发型师点赞
	 * 
	 * @param storeID
	 */
	public void sendCmdAddEmpPraise(final int employeeID)
	{
		String msg = CmdMessage.CMD_ADDEMP_PRAISE;
		RequestParams  params = new RequestParams();
		params.put("employeeId",String.valueOf(employeeID));
		
		// 下发赞命令给服务端，更新到数据库后台中
		HttpUtil.get(msg,params, new AsyncHttpResponseHandler()
		{

			public void onSuccess(String arg0)
			{
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						// 点赞成功，本地手工将赞的数据增加1.
						noticeViewDataChange(ObserverConsts.DATA_EMP_PRAISE, employeeID);

					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});

	}
	
	
	
	

	/**
	 * 获取支持的省份，数据在登录系统时从后台获取(AreanBeans)
	 * 
	 * @return
	 */
	public void sendCmdGetSupportProvince()
	{
		String message = CmdMessage.CMD_GET_SUPPORT_PROVINCE;
		HttpUtil.get(message, new AsyncHttpResponseHandler()
		{

			@Override
			public void onFailure(Throwable error, String content)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinish()
			{
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(String content)
			{
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(content.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						areaBeans =messageAdapter.tranData2SupportProvince(resultObj.getJSONArray("dataString"));
							
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

				
			}
				
	
		});
	}
    
	/**
	 * 获取支持的省份区域
	 */
	public void getSupportProvince(){
		   if(areaBeans!=null){
			   messageAdapter.getSupportProvices(areaBeans);
		   }	
	}
	
	/**
	 * 获取支持业务的市，数据登录时从后台获取，使用时，只需要过滤。
	 * 
	 * @param provinceID
	 * @return
	 */
	public void getSupportCity()
	{
		
	
	}

	/**
	 * 只要支持的市，所有的区都会支持的。
	 * 
	 * @param cityName
	 * @return
	 */
	public void getSupportArea()
	{
		
	}

	/**
	 * 点击赞事件
	 * 
	 * @param employeeID
	 *//*
	public int sendCmdDoPraiseEmployee(int employeeID)
	{
		HttpUtil.get(CmdMessage.CMD_GET_SUPPORT_AREA, new AsyncHttpResponseHandler()
		{
			public void onSuccess(String arg0)
			{ // 成功后返回一个JSONArray数据

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});
		return -1;
	}*/

	/**
	 * 获取短信注册验证码
	 * 
	 * @return
	 */
	public String sendCmdGetPhoneCode()
	{
		mValue = null;
		HttpUtil.get(CmdMessage.CMD_GET_PHONE_CODE, new AsyncHttpResponseHandler()
		{
			public void onSuccess(String arg0)
			{
				mValue = arg0;

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});

		return mValue;
	}

	/**
	 * 注册用户名
	 * 
	 * @param name
	 * @param phoneCode
	 */
	public int sendCmdRegeditUser(String name, String phoneCode)
	{
		int result = -1;
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("phoneCode", phoneCode);
		// 下发赞命令给服务端，更新到数据库后台中
		HttpUtil.get(CmdMessage.CMD_REGEDIT, params, new AsyncHttpResponseHandler()
		{
			public void onSuccess(String arg0)
			{
			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};
		});
		return result;
	}

	public void sendCmdLogin(String userName, String pwd)
	{
		String msg = CmdMessage.CMD_LOGIN;
		RequestParams params = new RequestParams();
		params.put("loginName",userName);
		params.put("passWord",pwd);
		// 下发赞命令给服务端，更新到数据库后台中
		HttpUtil.post(msg,params, new AsyncHttpResponseHandler()
		{

			public void onSuccess(String arg0)
			{
				progressDialog.dismiss();
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						AppContext.instance().userData=messageAdapter.tranDataLoginBean(resultObj.getJSONObject("dataString"));
						// 登录成功，跳转到相应的页面
						
						noticeViewDataChange(ObserverConsts.DATA_LOGIN_RESULT, Consts.RSP_OK);
						
					} else
					{
						// 登录失败，跳转到相应的页面
						noticeViewDataChange(ObserverConsts.DATA_LOGIN_RESULT, Consts.RSP_FAULT);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});
	}

	public ArrayList<String> getItemBean(ArrayList<StoreItemBean> itembeas){
		ArrayList<String>  itemStrings = new ArrayList<String>();
		for (int i = 0; i < itembeas.size(); i++)
		{
			itemStrings.add(itembeas.get(i).description);
		}
		return itemStrings;
	}
	
	
	/**
	 * 用户收藏，点击事件
	 * 
	 * @param employeeID
	 */
	public void sendCmdDoLoveEmployee(final int employeeID)
	{
		String msg = CmdMessage.CMD_LOVE_EMPLOYEES;
		RequestParams params = new RequestParams();
		
		params.put("employeeId", String.valueOf(employeeID));
		// 下发赞命令给服务端，更新到数据库后台中
		HttpUtil.get(msg,params, new AsyncHttpResponseHandler()
		{

			public void onSuccess(String arg0)
			{
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						// 收藏成功，跳转到相应的页面
						noticeViewDataChange(ObserverConsts.DATA_LOVE_EMPLOYEE, employeeID);

					}else{
						// 收藏失败
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});

	}
	
	/**
	 * 根据店id 查询消费套餐列表
	 * 
	 * @param employeeID
	 */
	public void sendGetStoreItem(final ReservationBussinessBean bean)
	{
		
		String msg = CmdMessage.CMD_GET_ITEM_INFO;
		RequestParams params = new RequestParams();
		params.put("storeId", String.valueOf(bean.store.ID));
		// 下发赞命令给服务端，更新到数据库后台中
		HttpUtil.get(msg,params, new AsyncHttpResponseHandler()
		{
           
			public void onSuccess(String arg0)
			{
				JSONObject resultObj = null;
				progressDialog.dismiss();
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						storeItemBean = messageAdapter.testStoreItemBean(resultObj.getJSONArray("dataString"));
						/*通知主线程跟新数据*/
					    noticeViewDataChange(ObserverConsts.DATA_ITEM,bean);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			}

		});

	}
	
	
	
	
	
	

	/**
	 * 根据StoreID获取Store的Bean信息
	 * 
	 * @param storeID
	 * @return
	 */
	public StoreBean getStoreBeanByID(int storeID)
	{

		if (storeList == null || storeList.size() == 0)
		{
			return null;
		}
		int i = -1;
		for (i = 0; i < storeList.size(); i++)
		{
			if (storeList.get(i).ID == storeID)
			{
				return storeList.get(i);
			}
		}
		return null;
	}
	
	public StoreBean getStoreBeanByID(ArrayList<StoreBean> storeList, int storeID)
	{
		if (storeList == null || storeList.size() == 0)
		{
			return null;
		}
		int i = -1;
		for (i = 0; i < storeList.size(); i++)
		{
			if (storeList.get(i).ID == storeID)
			{
				return storeList.get(i);
			}
		}
		return null;
	}

	/**
	 * 根据员工id获取EmployeeBean的Bean信息
	 * 
	 * @param employeeId
	 * @return
	 */
	public EmployeesBean getEmployeeBeanByID(int employeeId)
	{
		if (employeesList == null || employeesList.size() == 0)
		{
			return null;
		}
		int i = -1;
		for (i = 0; i < storeList.size(); i++)
		{
			if (employeesList.get(i).ID == employeeId)
			{
				return employeesList.get(i);
			}
		}
		return null;
	}

	/**
	 * 根据员工id获取EmployeeBean的Bean信息
	 * 
	 * @param employeeId
	 * @return
	 */
	public EmployeesBean getEmployeeBeanByID(ArrayList<EmployeesBean> employeesList, int employeeId)
	{
		if (employeesList == null || employeesList.size() == 0)
		{
			return null;
		}
		int i = -1;
		for (i = 0; i < employeesList.size(); i++)
		{
			if (employeesList.get(i).ID == employeeId)
			{
				return employeesList.get(i);
			}
		}
		return null;
	}

	/**
	 * 获取上一个浏览过的View,用于返回到指下的View。
	 * 
	 * @return
	 */
	public ViewInfoBean getLastViewInfo()
	{
		// 如果没有之前的历史记录，且只有一个页面，就不需要点返回。
		if (historyViewRouteList == null || historyViewRouteList.size() < 2)
		{
			return null;

		}
		return historyViewRouteList.get(historyViewRouteList.size() - 2);
	}

	/**
	 * 添加View到列表中，如果已经返回到首页面，那么将之前的View去掉。
	 * 
	 * @param view
	 */
	public void addView2HistoryRouteList(ViewInfoBean viewBean)
	{
		// 如果返回到首页，那么在其前面的所有列表清空，避免View一直重复记录。
		if (viewBean.viewName.equalsIgnoreCase(Consts.VIEW_NAME_MAIN))
		{
			historyViewRouteList.clear();

		}
		historyViewRouteList.add(viewBean);
	}

	/**
	 * 清除掉队列最尾的View
	 */
	public void removeLastViewFromRoute()
	{
		historyViewRouteList.removeLast();
	}

	/**
	 * 获取当前的页面
	 * 
	 * @return
	 */
	public ViewInfoBean getCurrentViewIfo()
	{
		if (historyViewRouteList == null || historyViewRouteList.size() < 2)
		{

			return null;

		}
		return historyViewRouteList.get(historyViewRouteList.size() - 1);
	}

	/**
	 * 根据业务标识不同，加载不同的店家数据,店家的数据依赖于距离/排名进行显示排序
	 * 
	 * @param bussinessFlag
	 * @return
	 */
	/*public ArrayList<StoreBean> getStoreListByFlag(int bussinessFlag)
	{
		ArrayList<StoreBean> bussinessStoreList = new ArrayList<StoreBean>();
		for (StoreBean storeInfo : storeList)
		{
			if (storeInfo.bussinessFlag == bussinessFlag)
			{
				bussinessStoreList.add(storeInfo);
			}
		}
		progressDialog.dismiss();
		return bussinessStoreList;
	}
*/
	/**
	 * 根据StroeID获取到当前的店员列表
	 * 
	 * @param storeID
	 * @return
	 */
	public ArrayList<EmployeesBean> getEmployeeListByStoreID(int storeID)
	{
		ArrayList<EmployeesBean> employeeListes = new ArrayList<EmployeesBean>();
		for (EmployeesBean employeesBean : employeesList)
		{
			if(employeesBean.obtainStroeID==storeID){
				employeeListes.add(employeesBean);
			}
		}
		
		return employeeListes;
	}

	/**
	 * 从服务端获取店家数据，只加载本省的数据
	 */
	public void sendCmdGetStoreList(Integer areaId,final Integer buniessFlag)
	{
		String message = CmdMessage.CMD_GET_STORE_INFO;
		RequestParams params = new RequestParams();  
	    params.put("areaId", String.valueOf(areaId)); 
	    params.put("bunessFlag", String.valueOf(buniessFlag)); 
	    // 设置请求的参数名和参数值   
		Log.i("liu",message);
		HttpUtil.get(message,params, new AsyncHttpResponseHandler()
		{

			@Override
			public void onFinish()
			{
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(String content)
			{
				progressDialog.dismiss();
				Log.i("liu", content);
				JSONObject resltObj = null;
				try
				{
					resltObj = new JSONObject(new String(content.getBytes(),"UTF-8"));
					if(resltObj.getInt("result")==Consts.RSP_OK){
						
						storeList = messageAdapter.tranData2Store(resltObj.getJSONArray("dataString"));
						// 通知刷新界面数据
						noticeViewDataChange(ObserverConsts.DATA_STORE_LIST, buniessFlag);
					}else{
					
					}
				} catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable error)
			{
				// TODO Auto-generated method stub
				
			}
			
		});

	}

	/**
	 * 返回公共区域的广告信息
	 * 
	 * @return
	 */
	public void sendCmdGetPublicAdverList()
	{
		String message =CmdMessage.CMD_GET_ADVER_INFO;
		HttpUtil.get(message, new AsyncHttpResponseHandler()
		{
			public void onSuccess(String arg0)
			{ // 成功后返回一个JSONArray数据
				JSONObject resltObj = null;
				try
				{
					resltObj = new JSONObject(new String(arg0.getBytes(),"UTF-8"));
					if(resltObj.getInt("result")==Consts.RSP_OK){
						adverList = messageAdapter.tranData2Advertisement(resltObj.getJSONArray("dataString"));
					}
				} catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};
		});
	}
    
	/**
	 * 根据店id 查询所有的员工
	 * @param storeID
	 */
	public void sendCmdGetEmployeesListByFlag(final int storeId)
	{
		String msg = CmdMessage.CMD_GET_EMPLOYEES_INFO;
		RequestParams params = new RequestParams();  
	    params.put("storeId", String.valueOf(storeId)); 
		HttpUtil.get(msg,params, new AsyncHttpResponseHandler()
		{
			@Override
			public void onFinish()
			{
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(String content)
			{
				progressDialog.dismiss();
				Log.i("liu",content);
				
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(content.getBytes(), "utf-8"));
					/*如果获取员工成功*/
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						// 将数据转换成内存数据
						employeesList=messageAdapter.tranData2Employees(resultObj.getJSONArray("dataString"));
						noticeViewDataChange(ObserverConsts.DATA_EMPLOYEE_LIST,storeId);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

				
			}

			@Override
			public void onFailure(Throwable error)
			{
				// TODO Auto-generated method stub
				
			}
		});

	}

	/**
	 * 获取当前收藏的历史记录
	 */
	public void sendCmdGetLoveHisList()
	{
		String msg = CmdMessage.CMD_GET_LOVE_HIS;
		HttpUtil.get(msg, new AsyncHttpResponseHandler()
		{

			public void onSuccess(String arg0)
			{
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						// 将数据转换成内存数据
						loveHistList =messageAdapter.tranData2Love(resultObj.getJSONArray("dataString"));
						// 通知刷新界面数据
						noticeViewDataChange(ObserverConsts.DATA_HISTORY_LOVE, null);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});

	}

	/**
	 * 获取充值的记录
	 * 
	 * @return
	 */
	public void sendCmdGetCompensateHisList()
	{
		String msg = CmdMessage.CMD_GET_CASH_HIS;
		HttpUtil.get(msg, new AsyncHttpResponseHandler()
		{

			public void onSuccess(String arg0)
			{
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						// 将数据转换成内存数据
						cashHisList=messageAdapter.tranData2Cash(resultObj.getJSONArray("dataString"));
						// 通知刷新界面数据
						noticeViewDataChange(ObserverConsts.DATA_HISTORY_CASH, null);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});
	}

	/**
	 * 获取预约记录
	 * 
	 * @return
	 */
	public void sendCmdGetReservationHisList()
	{
		String msg = CmdMessage.CMD_GET_RESERVATION_HIS;
		HttpUtil.get(msg, new AsyncHttpResponseHandler()
		{

			public void onSuccess(String arg0)
			{
				JSONObject resultObj = null;
				try
				{
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						// 将数据转换成内存数据
						reservationHisList=messageAdapter.tranData2Reservation(resultObj.getJSONArray("dataString"));
						// 通知刷新界面数据
						noticeViewDataChange(ObserverConsts.DATA_HISTORY_RESERVATION, null);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{
					
					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});

	}

	/**
	 * 执行预约，调用Http接口，返回预约结果
	 * 
	 * @return
	 */
	public void sendCmdDoReservation(final ReservationBean reservation)
	{

		String msg = CmdMessage.CMD_RESERVATION;
			
		RequestParams  params = new RequestParams();
		params.put("timeBetween", reservation.getReservationDate().toString());
		params.put("storeItemId",reservation.getConsumeID().toString());
		params.put("employeeId", reservation.getEmployeID().toString());
		// 下发赞命令给服务端，更新到数据库后台中
		HttpUtil.get(msg,params, new AsyncHttpResponseHandler()
		{
			public void onSuccess(String arg0)
			{
				
				progressDialog.dismiss();
				JSONObject resultObj = null;
				try
				{
					
					resultObj = new JSONObject(new String(arg0.getBytes(), "utf-8"));
					if (resultObj.getInt("result") == Consts.RSP_OK)
					{
						noticeViewDataChange(ObserverConsts.DATA_RESERVATION,  Consts.RSP_OK);

					}else{
						noticeViewDataChange(ObserverConsts.DATA_RESERVATION,  Consts.RSP_FAULT);
					}
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} catch (JSONException e)
				{

					e.printStackTrace();
				}

			};

			public void onFailure(Throwable arg0)
			{
			};

			public void onFinish()
			{
			};

		});

	}

	/**
	 * 判断当前是否有用户登录
	 * 
	 * @return
	 */
	public boolean isLoginSys()
	{
		return AppContext.instance().userData != null;
	}

	/**
	 * 根据当前选择的类型，加载不同的历史数据，充值、预约、收藏
	 * 
	 * @param selectedFlag
	 * @return
	 */
	public ArrayList<BaseRecordHisBean> getRecordHisData(int selectedFlag)
	{
		ArrayList<BaseRecordHisBean> resultBean = null;
		if (selectedFlag == Consts.ABOUT_TAB_CASH_HIS)
		{
			resultBean = cashHisList;
		} else if (selectedFlag == Consts.ABOUT_TAB_LOVE)
		{
			resultBean = loveHistList;
		} else if (selectedFlag == Consts.ABOUT_TAB_RESERVATION_HIS)
		{
			resultBean = reservationHisList;
		}

		return resultBean;
	}

	/**
	 * 从GPS上获取位置信息,使用异步的方法<br>
	 * 如果长时间没有获取到GPS信息，那么就使用上次的定位结果或者用户选择的结果。
	 */
	public void getAreaDataFromGPS()
	{
		String threadName = "getAreaDataFromGPS";
		final GPSLocation gpsLocation = new GPSLocation(mContext);
		gpsLocation.openGPSSettings();
		Thread getGpsDataThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				gpsLocation.getAreaFromLocation();
			}
		}, threadName);
		getGpsDataThread.start();
	}

	/**
	 * 登录前，异步从后台获取一些公共的数据<br>
	 * 1. 支持的区域<br>
	 * 2. 各个理发店的信息，雇员信息<br>
	 * 3. 公共的广告信息
	 */
	public void asynGetDataFromServerBeforeLogin()
	{
		String threadName = "asynGetDataFromServerBeforeLogin";
		Thread beforeLoginThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{

				sendCmdGetPublicAdverList();
				//sendCmdGetStoreList(AppContext.instance().currentArea.id);
				sendCmdGetSupportProvince();
			}
		}, threadName);
		beforeLoginThread.start();
	}

	/**
	 * 登录后，从后台获取与用户相关的数据<br>
	 * 1. 个人充值记录、预约记录和收藏记录
	 * 
	 */
	public void asynGetDataFromServerAfterLogin()
	{
		String threadName = "asynGetDataFromServerAfterLogin";
		Thread afterLoginThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				sendCmdGetReservationHisList();
			}
		}, threadName);
		afterLoginThread.start();

	}
	
	
	public void asynGetCashfterLogin()
	{
		String threadName = "asynGetCashfterLogin";
		Thread afterLoginThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				sendCmdGetCompensateHisList();
		
			}
		}, threadName);
		afterLoginThread.start();

	}
	
	
	
	public void asynGetLoveServerAfterLogin()
	{
		String threadName = "asynGetLoveServerAfterLogin";
		Thread afterLoginThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				sendCmdGetLoveHisList();
			}
		}, threadName);
		afterLoginThread.start();

	}

	private void _initTestData()
	{
		/*int k = 1;
		// 初始化加载店家列表
		ArrayList<String> consumList = new ArrayList<String>();
		consumList.add("洗煎吹套餐20美元");
		consumList.add("染套餐200美元");
		consumList.add("按摩套餐20美元");
		for (int i = 1; i < 7; i++)
		{
			StoreBean store = new StoreBean();
			store.name = "科苑沙宣旗舰店";
			store.description = "李龙，欢迎光临本店";
			store.contracePhone = "400-1234567";
			store.saleProdcutDescription = "本店销售长生不老药，欢迎选购";
			store.bussinessFlag = Consts.DO_HAIRCUT;
			store.starLevel = i;
			store.ID = k;
			store.praiseCount = k;
			store.ConsumerPackList = consumList;

			storeList.add(store);
			k++;

		}
	
		// 初始化加载店家列表
		for (int i = 1; i < 7; i++)
		{
			StoreBean store = new StoreBean();
			store.name = "科苑星星旗舰店";
			store.description = "柳智斌，欢迎光临本店";
			store.bussinessFlag = Consts.DO_FOOT;
			store.starLevel = i;
			store.ID = k;
			store.praiseCount = k;
			store.ConsumerPackList = consumList;

			storeList.add(store);
			k++;

		}
	
		// 初始化加载店家列表
		for (int i = 1; i < 7; i++)
		{
			StoreBean store = new StoreBean();
			store.name = "南山科苑旗舰店";
			store.description = "刘海龙，欢迎光临本店";
			store.bussinessFlag = Consts.DO_COSMETOLOGY;
			store.starLevel = i;
			store.ID = k;
			store.praiseCount = k;
			store.ConsumerPackList = consumList;

			storeList.add(store);
			k++;

		}

		// 初始化加载店家列表
		k = 1;
		for (int i = 1; i < 7; i++)
		{
			EmployeesBean employeeBean = new EmployeesBean();
			employeeBean.name = "罗龙昌";
			employeeBean.goodat = "擅长洗剪吹";
			employeeBean.Praise = 200;
			employeeBean.ID = k;
			employeeBean.obtainStroeID = 1;
			employeesList.add(employeeBean);
			k++;

		}
		
		
		k = 1;
		for (int i = 1; i < 7; i++)
		{
			EmployeesBean employeeBean = new EmployeesBean();
			employeeBean.name = "李龙";
			employeeBean.goodat = "擅长洗剪吹";
			employeeBean.Praise = 200;
			employeeBean.ID = k;
			employeeBean.obtainStroeID =7;
			employeesList.add(employeeBean);
			k++;

		}
		k = 1;
		for (int i = 1; i < 7; i++)
		{
			EmployeesBean employeeBean = new EmployeesBean();
			employeeBean.name = "刘海龙";
			employeeBean.goodat = "擅长洗剪吹";
			employeeBean.Praise = 200;
			employeeBean.ID = k;
			employeeBean.obtainStroeID =13;
			employeesList.add(employeeBean);
			k++;

		}
		
		
		
		k = 1;
		for (int i = 0; i < 7; i++)
		{
			ProductBean productBean = new ProductBean();
			productBean.storeName = "南山后海旗舰店";
			productBean.product_description = "擅长洗剪吹";
			productBean.product_price = 123;
			productBean.product_StoreID = 1;
			productBean.product_NO = k;
			productesList.add(productBean);
			k++;
		}
*/
		String[] items = new String[] { "广东省" };
		String[] guang_city = new String[] { "广州市", "深圳市", "惠州市", "东莞市" };
		String[] shenzhen_area = new String[] { "福田区", "罗湖区", "南山区", "盐田区", "宝安区", "龙岗区" };
		String[] guang_area = new String[] { "越秀", "海珠", "荔湾", "天河", "白云", "黄埔", "花都", "番禺", "萝岗", "南沙" };
		String[] hui_area = new String[] { "惠城区", "惠阳区", "大亚湾经济技术开发区", "仲恺高新技术产业开发区", "博罗县", "惠东县", "龙门县" };
		String[] dong_area = new String[] { "虎门", "长安", "沙田", "洪梅", "中堂", "麻涌", "道窑", "塘厦", "谢岗", "南城区", "东城区", "北城区",
				"万江" };
		/* 加载省份 */
		for (int i = 0; i < items.length; i++)
		{
			supportProvinceList.add(items[i]);
		}

		/* 加载城市 */
		ArrayList<String> cities = new ArrayList<String>();
		for (int i = 0; i < guang_city.length; i++)
		{
			cities.add(guang_city[i]);
		}
		supportCityMap.put(items[0], cities);

		/* 加载各个区 */
		ArrayList<String> areas = new ArrayList<String>();
		for (int i = 0; i < shenzhen_area.length; i++)
		{
			areas.add(shenzhen_area[i]);

		}

		supportAreaMap.put(guang_city[1], areas);

		areas = new ArrayList<String>();
		for (int i = 0; i < guang_area.length; i++)
		{
			areas.add(guang_area[i]);

		}

		supportAreaMap.put(guang_city[0], areas);

		areas = new ArrayList<String>();
		for (int i = 0; i < hui_area.length; i++)
		{
			areas.add(hui_area[i]);

		}

		supportAreaMap.put(guang_city[2], areas);

		areas = new ArrayList<String>();
		for (int i = 0; i < dong_area.length; i++)
		{
			areas.add(dong_area[i]);

		}

		supportAreaMap.put(guang_city[3], areas);

	}
}
