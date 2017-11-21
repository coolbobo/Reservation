package view;

import com.owned.reservation.R;

import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 项目名称：OEDReservation <br>
 * 类 名 称：Luo_RegisterView <br>
 * 类 描 述：注册页面 <br>
 * 创 建 人：LuoLongChang<br>
 * 创建时间：2014年6月10日 上午11:58:03 <br>
 * Copyright (c) 罗龙昌-版权所有
 */
public class RegeditView extends BaseView
{

	/**
	 * mContext:TODO（上下文环境）
	 */
	private Context mContext;

	/**
	 * registerView:TODO（注册页面）
	 */
	private RelativeLayout registerView;

	/**
	 * registerPhone:TODO（手机号码）
	 */
	private EditText registerPhone;

	/**
	 * verificationButton:TODO（获取验证码）
	 */
	private Button verificationButton;

	/**
	 * registerVerification:TODO（验证码）
	 */
	private EditText registerVerification;

	/**
	 * registerButton:TODO（注册按钮）
	 */
	private Button registerButton;

	/**
	 * myLogin:TODO（我要登录）
	 */
	private TextView myLogin;

	public RegeditView(Context context)
	{
		this.mContext = context;
		this.findViews();

	}

	/**
	 * findViews(实例化UI控件)
	 */
	private void findViews()
	{
		this.registerView = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.regedit, null);
		this.registerPhone = (EditText) this.registerView.findViewById(R.id.register_body_phone);
		this.verificationButton = (Button) this.registerView.findViewById(R.id.register_body_verification_btn);
		this.registerVerification = (EditText) this.registerView.findViewById(R.id.register_body_verification);
		this.registerButton = (Button) this.registerView.findViewById(R.id.register_body_btn);
		this.myLogin = (TextView) this.registerView.findViewById(R.id.register_my_login);
		_initTop();
		setBackground();
		
		// 设置监听事件
		this.setListeners();
	}
	/**
	 * setListeners(设置监听事件)
	 */
	private void setListeners()
	{
		this.verificationButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 获取验证码
				showToast("获取验证码...");
			}
		});
		this.registerButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 注册按钮
				showToast("注册按钮...");
				// 注册
				register();
			}
		});
		this.myLogin.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 我要登录
				showToast("我要登录...");
			}
		});
	}

	/**
	 * register(注册)
	 */
	private void register()
	{
		// 手机号码
		String phone = this.registerPhone.getText().toString();
		// 验证码
		String verification = this.registerVerification.getText().toString();
		// 注册验证
		this.registerVerify(phone, verification);
	}

	private void registerVerify(String phone, String verification)
	{
		if ("".equalsIgnoreCase(phone) || phone.length() == 0)
		{
			showToast("手机号码不能为空！");
			return;
		} else if ("".equalsIgnoreCase(verification) || verification.length() == 0)
		{
			showToast("验证码不能为空！");
			return;
		}
		// 显示结果
		showToast("注册：手机号码 " + phone + "，验证码 " + verification + "。");
	}

	/**
	 * showToast(弹出提示)
	 * 
	 * @param result
	 *            显示字符串
	 */
	public void showToast(String result)
	{
		Toast.makeText(this.mContext, result, Toast.LENGTH_SHORT).show();
	}

	@Override
	public View getLayoutView(Object value)
	{
		_initTop();
		return this.registerView;
	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{

	}

	@Override
	public void unRegisterListenrService()
	{

	}

	@Override
	public void unSetBackground()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBackground()
	{
		
		registerView.setBackgroundResource(R.drawable.haircutmain);
	}
	
	
	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		/*用户登陆*/
		topView.refershTopDescription(true, mContext.getResources().getString(R.string.top_rediget));
	}
}
