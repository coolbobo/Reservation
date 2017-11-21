package view;

import service.ReservationService;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.owned.reservation.R;

import comm.AppContext;
import comm.Consts;
import comm.MainhomeActivity;
import comm.ViewManager;
import comm.observer.ObserverConsts;

/**
 * 
 */
public class LoginView extends BaseView
{

	private ReservationService aprser;
	/**
	 * mContext:TODO（上下文环境）
	 */
	private Context mContext;

	/**
	 * loginView:TODO（登录页面）
	 */
	private RelativeLayout loginView;

	/**
	 * loginName:TODO（用户名）
	 */
	private EditText loginName;

	/**
	 * loginPassword:TODO（登录密码）
	 */
	private EditText loginPassword;

	/**
	 * forgetPassword:TODO（忘记密码）
	 */
	private TextView forgetPassword;
	
	ProgressDialog  progressDialog ;

	/**
	 * login:TODO（登录按钮）
	 */
	private Button loginButton;

	/**
	 * myRegister:TODO（我要注册）
	 */
	private TextView myRegister;

	public LoginView(Context context)
	{
		this.mContext = context;
		progressDialog= new ProgressDialog(mContext);
		// 实例化UI控件
		this.findViews();
		/* 初始化顶部 */

	}

	/**
	 * findViews(实例化UI控件)
	 */
	private void findViews()
	{
		this.loginView = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.login, null);
		this.loginName = (EditText) this.loginView.findViewById(R.id.login_body_username);
		this.loginPassword = (EditText) this.loginView.findViewById(R.id.login_body_password);
		this.forgetPassword = (TextView) this.loginView.findViewById(R.id.login_body_forget);
		this.loginButton = (Button) this.loginView.findViewById(R.id.login_body_btn);
		this.myRegister = (TextView) this.loginView.findViewById(R.id.login_my_register);

		_initTop();
		setBackground();
		// 设置监听事件
		this.setListeners();

		AppContext.instance().appServ.regeditObserver(ObserverConsts.DATA_LOGIN_RESULT, this);
		
		AppContext.instance().appServ.regeditObserver(ObserverConsts.DATA_HISTORY_RESERVATION, this);
	}

	/**
	 * setListeners(设置监听事件)
	 */
	private void setListeners()
	{
		this.forgetPassword.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 忘记密码
				showToast("忘记密码...");
			}
		});
		/*登陆*/
		this.loginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				login();
			}
		});
		this.myRegister.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 我要注册
				MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_REGEDIT, null);
			}
		});
	}

	/**
	 * login(登录)
	 */
	private void login()
	{
		// 用户名
		String username = this.loginName.getText().toString();
		// 密码
		String password = this.loginPassword.getText().toString();
		if(loginVerify(username,password)){
			AppContext.instance().appServ.progressDialog=CustomProgressDialog.createDialog(mContext);
			AppContext.instance().appServ.progressDialog.setMessage("登陆中...");
			AppContext.instance().appServ.progressDialog.show();
			AppContext.instance().appServ.sendCmdLogin(username, password);
		}
	}

	/**
	 * loginVerify(登录验证)
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	private boolean loginVerify(String username, String password)
	{
		if ("".equalsIgnoreCase(username) || username.length() == 0)
		{
			showToast("用户名不能为空！");
			return false;
		} else if ("".equalsIgnoreCase(password) || password.length() == 0)
		{
			showToast("密码不能为空！");
			return false;
		}else{
			return true;
		}
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
		return this.loginView;
	}

	@Override
	public void viewDataChanged(int observerKey, Object value)
	{
		if (observerKey == ObserverConsts.DATA_LOGIN_RESULT)
		{
			// 登录成功，跳转到登录成功页面，界面给出提示信息
			if ((Integer) value == Consts.RSP_OK)
			{
				Toast.makeText(mContext, mContext.getText(R.string.msg_login_success), Toast.LENGTH_SHORT).show();
				
				/*登陆获取信息*/
				
				//AppContext.instance().appServ.asynGetLoveServerAfterLogin();
				AppContext.instance().appServ.asynGetDataFromServerAfterLogin();
			}
			// 登录失败，界面给出提示信息，不跳转界面。
			else
			{
				Toast.makeText(mContext, mContext.getText(R.string.msg_login_userpassword_invalid), Toast.LENGTH_SHORT)
						.show();
				clear();
			}
		}

		if(observerKey == ObserverConsts.DATA_HISTORY_RESERVATION){
			MainhomeActivity.mainHomeApp.loadWorkViewbyTag(Consts.VIEW_NAME_ABOUTME, null);
		
		}
	}

	private void _initTop()
	{
		TopView topView = (TopView) ViewManager.getInstance().getBaseView(mContext, Consts.VIEW_NAME_TOP, null);
		topView.refershReservationImg(false);
		topView.refershGoBack(true);
		/* 用户登陆 */
		topView.refershTopDescription(true, mContext.getResources().getString(R.string.top_login));
	
	}

	@Override
	public void unRegisterListenrService()
	{
		AppContext.instance().appServ.unRegeditObserver(ObserverConsts.DATA_LOGIN_RESULT, this);
		AppContext.instance().appServ.unRegeditObserver(ObserverConsts.DATA_HISTORY_RESERVATION, this);
	}

	@Override
	public void unSetBackground()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBackground()
	{
		loginView.setBackgroundResource(R.drawable.haircutmain);

	}
	
	public void clear(){
		this.loginName.setText("");
	
		this.loginPassword.setText("");
		this.loginName.requestFocus();
	}

}
