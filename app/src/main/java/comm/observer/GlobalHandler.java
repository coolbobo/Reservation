package comm.observer;

import android.os.Handler;
import android.os.Message;

public class GlobalHandler extends Handler
{
	public static final int ASYN_HANDLER = 2;
	public static final int SERVICE_HANDLER = 1;
	protected static final String TAG = "GlobalHandler";

	public void handleMessage(Message paramMessage)
	{
		GlobalHandlerAdapter globalHandlerAdapter = (GlobalHandlerAdapter) paramMessage.obj;
		try
		{
			globalHandlerAdapter.dataObserver.viewDataChanged(globalHandlerAdapter.indKey, globalHandlerAdapter.obj);
			return;
		} catch (Throwable localThrowable)
		{
			// TODO:
		} finally
		{
			paramMessage.obj = null;
		}
	}
}
