package conn;

/**
 * Http对外连接类，用于下发消息到后台服务。<br>
 * 可以获取到图片资源,CGI命令等<br>
 */
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil
{
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	// 连接字串前缀，用于下发连接到后台
	//public static String httpConnPre = "HTTP://192.168.1.100:8080/OEDSSHProject/";
	public static String httpConnPre = "HTTP://120.237.108.66:8080/OEDSSHProject/";
	static
	{
		client.setTimeout(11000); // 设置链接超时，如果不设置，默认为10s
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.get(httpConnPre.concat(urlString), res);
		System.out.println(httpConnPre.concat(urlString));
	}

	public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) // url里面带参数
	{
		
		client.get(httpConnPre.concat(urlString), params, res);
		
	}

	public static void get(String urlString, JsonHttpResponseHandler res) // 不带参数，获取json对象或者数组
	{
		client.get(httpConnPre.concat(urlString), res);
	}

	public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res) // 带参数，获取json对象或者数组
	{
		client.get(httpConnPre.concat(urlString), params, res);
	}

	public static void get(String uString, BinaryHttpResponseHandler bHandler) // 下载数据使用，会返回byte数据
	{
		client.get(httpConnPre.concat(uString), bHandler);
	}

	public static void post(String url,RequestParams params,AsyncHttpResponseHandler  res){
		
		client.post(httpConnPre.concat(url),params,res);
		
	}
	
	public static AsyncHttpClient getClient()
	{
		return client;
	}
	
	
}