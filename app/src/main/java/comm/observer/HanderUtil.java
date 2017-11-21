package comm.observer;

import comm.AppContext;
import view.IViewDataObserver;
import android.os.Message;

/**
 * 处理Handler,用于注册监听者和观察者的桥梁.将数据从后台发向前台界面
 * 
 * @author coolbobo
 * 
 */
public class HanderUtil
{
	public static void sendUIMsg(IViewDataObserver paramIViewDataObserver, int paramInt)
	{
		Message localMessage = Message.obtain();
		localMessage.obj = new GlobalHandlerAdapter(paramIViewDataObserver, paramInt, null);
		AppContext.instance().globalHandler.sendMessage(localMessage);
	}

	public static void sendUIMsg(IViewDataObserver paramIViewDataObserver, int paramInt, Object paramObject)
	{
		Message localMessage = Message.obtain();
		localMessage.obj = new GlobalHandlerAdapter(paramIViewDataObserver, paramInt, paramObject);
		AppContext.instance().globalHandler.sendMessage(localMessage);
	}
}
