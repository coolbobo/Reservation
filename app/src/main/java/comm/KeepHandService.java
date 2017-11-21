package comm;

import java.util.Timer;
import java.util.TimerTask;

import service.ReservationService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 与外部Http接口定时器
 * 
 * @author coolbobo
 * 
 */
public class KeepHandService extends Service
{

	/*
	 * 业务定时器，定时从后台获取推送的消息数据
	 */
	private Timer bussinessTimer;

	private ReservationService appServ = AppContext.instance().appServ;

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	public void onStart(Intent paramIntent, int paramInt)
	{
		stopTimer();
		startBussinessTimer();
	}

	/**
	 * 启动定时器
	 */
	private void startBussinessTimer()
	{
		bussinessTimer = new Timer("keepHandService.businessTimer");
		bussinessTimer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				// 定时获取邮箱数据，获取后台给前台的推送消息
				// appServ.asynGetDataFromServerBeforeLogin();

			}
		}, 0);
	}

	/**
	 * 停止定时器
	 */
	private void stopTimer()
	{
		if (bussinessTimer != null)
		{
			bussinessTimer.cancel();
			bussinessTimer.purge();
			bussinessTimer = null;
		}
	}

}
