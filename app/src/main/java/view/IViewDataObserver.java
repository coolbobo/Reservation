package view;

/**
 * View的接口，用于实现观察者
 * 
 * @author liuzhibin
 * 
 */
public interface IViewDataObserver
{

	public abstract void unRegisterListenrService();

	public abstract void viewDataChanged(int observerKey, Object value);

	/**
	 * 反注册设置的背景，避免图片过大造成OOM的错误
	 */
	public abstract void unSetBackground();

	/**
	 * 设置的背景，避免图片过大造成OOM的错误
	 */
	public abstract void setBackground();

}
