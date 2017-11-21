package comm;

import java.util.Set;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 整个程序的APP的配置文件,将数据存储在Flash空间中<br>
 * 
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class AppFlashConfig
{
	public static final String CONFIG_LOGIN_NAME = "loginName";
	public static final String CONFIG_LOGIN_PASSWORD = "loginPwd";
	public static final String CONFIG_LAST_AREA_PROVINCE = "areaProvince";
	public static final String CONFIG_LAST_AREA_CITY = "areaCity";
	public static final String CONFIG_LAST_AREA_AREA = "areaArea";

	private Context mContext;

	private SharedPreferences settings;

	public AppFlashConfig(Context context)
	{
		this.mContext = context;
		this.settings = this.mContext.getSharedPreferences("paramsxml", 0);
	}

	public boolean getParamBooleanByKey(String paramString)
	{
		return this.settings.getBoolean(paramString, false);
	}

	public float getParamFloatByKey(String paramString)
	{
		return this.settings.getFloat(paramString, 0.0F);
	}

	public Integer getParamIntByKey(String paramString)
	{
		return Integer.valueOf(this.settings.getInt(paramString, 0));
	}

	public Integer getParamIntByKey(String paramString, int paramInt)
	{
		return Integer.valueOf(this.settings.getInt(paramString, paramInt));
	}

	public String getParamStringByKey(String paramString)
	{
		return this.settings.getString(paramString, "");
	}

	public void removeParam(String paramString)
	{
		this.settings.edit().remove(paramString).commit();
	}

	public void setParam(String paramString, float paramFloat)
	{
		this.settings.edit().putFloat(paramString, paramFloat).commit();
	}

	public void setParam(String paramString, int paramInt)
	{
		this.settings.edit().putInt(paramString, paramInt).commit();
	}

	public void setParam(String paramString1, String paramString2)
	{
		this.settings.edit().putString(paramString1, paramString2).commit();
	}

	public void setParam(String paramString, boolean paramBoolean)
	{
		this.settings.edit().putBoolean(paramString, paramBoolean).commit();
		
	}
	
	public void setParam(String paramString, Set<String> paramString2)
	{
		this.settings.edit().putStringSet(paramString, paramString2).commit();
	}
	
	public Set<String> getParam(String param )
	{
		return this.settings.getStringSet(param, null);
	}
	
  
	
	

}
