package bussinessModel;

/**
 * View的信息，目前用于存储历史的页面记录，返回时可以使用。<br>
 * 与model包的数据不同，这里的数据主要用于存储代码使用的业务Bean，而不是业务的数据
 * 
 * @author Administrator
 * 
 */
public class ViewInfoBean
{
	public String viewName;
	public Object value;

	public ViewInfoBean(String viewName, Object value)
	{
		this.value = value;
		this.viewName = viewName;
	}
}
