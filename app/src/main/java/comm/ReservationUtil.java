package comm;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 服务的工具类，用于公共方法在这儿
 * 
 * @author Administrator
 * 
 */
public class ReservationUtil
{

	/**
	 * 获取当前的手机号码，不定能获取得到。
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context)
	{
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}
}
