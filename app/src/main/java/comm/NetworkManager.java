package comm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络
 * @author Administrator
 *
 */
public class NetworkManager
{
	private Context mContext;
	private static NetworkManager instance;
	/**
	 * 单例初始化
	 * 
	 * @return
	 */
	public static NetworkManager instance(Context context)
	{
		try
		{
			if (instance == null)
			{
				instance = new NetworkManager();
				instance.mContext = context;
			}
			return instance;
		} finally
		{

		}
	}
	/**
	 * 判断是否有网络
	 * @return
	 */
	public boolean isNewWork(){
		ConnectivityManager  manager = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
		NetworkInfo  info = manager.getActiveNetworkInfo();
		if(info!=null && info.isAvailable())
		{
			return true;
		}
		return false;
	}
}
