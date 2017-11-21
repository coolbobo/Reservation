package comm;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

/**
 * GPS定位模塊，用于定位到具體的位置，找到附近的理發店/足療/美容
 * 
 * @author Administrator
 * 
 */
public class GPSLocation
{

	private Context mContext;

	// 经度
	private Double latitude = null;

	// 纬度
	private Double longitude = null;

	public GPSLocation(Context context)
	{
		mContext = context;
	}

	public void openGPSSettings()
	{
		LocationManager alm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
		{

			return;
		}

		Toast.makeText(mContext, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		// startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面

	}

	private void getLocation()
	{
		// 获取位置管理服务
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) mContext.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
		Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		// 获取经纬度
		locationManager.requestLocationUpdates(provider, 2000, 5, locationListener);
		// 根据经度或者纬度，加载显示其所有的城市

	}

	private final LocationListener locationListener = new LocationListener()
	{
		public void onLocationChanged(Location location)
		{
			if (location != null)
			{
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}

		}

		public void onProviderDisabled(String provider)
		{
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider)
		{
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			// TODO Auto-generated method stub

		}
	};

	/**
	 * 根据区域定位到的经纬度，获取到对应的城市<br>
	 * 调用百度地图进行搜索获取
	 * 
	 * @return
	 */
	public String getAreaFromLocation()
	{
		getLocation();
		// TODO:根据经度和纬度，获取到地理位置
		return null;
	}

}
