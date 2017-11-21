package comm.observer;

import view.IViewDataObserver;

public class GlobalHandlerAdapter
{
	public IViewDataObserver dataObserver;
	public int indKey = -1;
	public Object obj = null;

	public GlobalHandlerAdapter(IViewDataObserver paramIViewDataObserver, int paramInt)
	{
		this.dataObserver = paramIViewDataObserver;
		this.indKey = paramInt;
	}

	public GlobalHandlerAdapter(IViewDataObserver paramIViewDataObserver, int paramInt, Object paramObject)
	{
		this.dataObserver = paramIViewDataObserver;
		this.indKey = paramInt;
		this.obj = paramObject;
	}
}
