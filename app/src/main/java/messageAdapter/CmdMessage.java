package messageAdapter;

/**
 * 发送命令，给服务端
 * 
 * @author Administrator
 * 
 */
public class CmdMessage
{

	// 对喜欢的商家点赞
	public static String CMD_ADD_PRAISE = "storeinfo_addStorePraise?";
	// 执行登录
	public static String CMD_LOGIN = "service_oedLogin";
	// 注册
	public static String CMD_REGEDIT = "DoRegedit";
	// 执行预约
	public static String CMD_RESERVATION = "reservation_addHResveration?";
	// 收藏喜欢的发型师
	public static String CMD_LOVE_EMPLOYEES = "loveEmp_addLoveEmployees?";
	// 获取预约记录
	public static String CMD_GET_RESERVATION_HIS = "reservation_findAllHResveration";
	// 获取收藏记录

	public static String CMD_GET_LOVE_HIS = "loveEmp_findAllLoveEmployees";

	public static String CMD_GET_CASH_HIS = "cash_queryAllCashUser";

	// 获取短信验证码
	public static String CMD_GET_PHONE_CODE = "GetPhoneCode";
	

	// 获取的同一省份的店家列表
	public static String CMD_GET_STORE_INFO = "storeinfo_queryAllStoreInfoes?";
	
	//
	// 获取雇员列表
	public static String CMD_GET_EMPLOYEES_INFO = "storeEmp_findAllEmployees";
	//获取广告列表
	
	//http://192.168.1.110:8080/OEDSSHProject/
	public static String CMD_GET_ADVER_INFO = "adver_findAllAdvertisements";
	// 获取支持的省份

	
	//http://192.168.1.110:8080/OEDSSHProject/parea
	public static String CMD_GET_SUPPORT_PROVINCE = "parea";

	/*// 获取支持的城市
	public static String CMD_GET_SUPPORT_CITY = "GetSupportCity";
	// 获取支持的区域
	public static String CMD_GET_SUPPORT_AREA = "GetSupportArea";*/
	/*对员工点赞*/
	public static String CMD_ADDEMP_PRAISE ="storeEmp_addEmployeePraise?";
	
	public static String CMD_GET_ITEM_INFO ="item_findAllStoreImtes?";
}
