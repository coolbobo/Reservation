package comm;

import java.util.ArrayList;

import view.BaseView;

import com.owned.reservation.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class GuiderActivity extends Activity implements GestureDetector.OnGestureListener
{
    
	
	
	private GestureDetector gestureDetector = null; 
	
	/*root*/
	private FrameLayout  mFrameLayout;
	
	/*图片的集合*/
	private ArrayList<ImageView>  imges ;
	
	/*viewFlipper*/
	private ViewFlipper  viewFlipper;
	 private int[] imgs = { R.drawable.img1, R.drawable.img2,  
             R.drawable.img3, R.drawable.img4, R.drawable.img5 };  
	
	/*5张切换图片*/
	private ImageView welcome_img1;
	private ImageView welcome_img2;
	private ImageView welcome_img3;
	private ImageView welcome_img4;
	private ImageView welcome_img5;
	
	
	/*跳转进入app首页*/
	private Button btn_inApp;

	/**
	 * 初始化UI
	 * @param mcontext
	 */
    


    
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
		_initView();
		_initListeners();
		
	}
	private void _initView()
	{
		viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);
		welcome_img1 = (ImageView) findViewById(R.id.iv_1);
		welcome_img2 = (ImageView) findViewById(R.id.iv_2);
		welcome_img3 = (ImageView) findViewById(R.id.iv_3);
		welcome_img4 = (ImageView) findViewById(R.id.iv_4);
		welcome_img5 = (ImageView) findViewById(R.id.iv_5);
		
		btn_inApp =  (Button) findViewById(R.id.revation_in);
		imges = new ArrayList<ImageView>();
		gestureDetector  = new GestureDetector(this);
		loadImageViews();
	
	}

	

	private void loadImageViews()
	{
		imges.add(welcome_img1);
		imges.add(welcome_img2);
		imges.add(welcome_img3);
		imges.add(welcome_img4);
		imges.add(welcome_img5);
		/*初始化视图*/
		for (ImageView imageView : imges)
		{
			imageView.setBackgroundResource(R.drawable.dotselected);
		}
		
		isHide();
		 for (int i = 0; i < imgs.length; i++) {          // 添加图片源   
	            ImageView iv = new ImageView(this);  
	            iv.setImageResource(imgs[i]);  
	            iv.setScaleType(ImageView.ScaleType.FIT_XY);  
	            // imageView 视图
	            viewFlipper.addView(iv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
	        }  
		   imges.get(0).setEnabled(false);
		   imges.get(0).setBackgroundResource(R.drawable.dotunfoucs);
	       /*自动播放*/
	       viewFlipper.setAutoStart(true);         // 设置自动播放功能（点击事件，前自动播放）   
	       /*三秒*/
	       viewFlipper.setFlipInterval(3000);  
	        if(viewFlipper.isAutoStart() && !viewFlipper.isFlipping()){  
	            /*启动*/
	        	viewFlipper.startFlipping();  
	        } 
	       
		
	}

    

	/**
     * 监听
     */
	private void _initListeners()
	{
		/*跳转到app首页*/
		btn_inApp.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent();
				intent.setClass(GuiderActivity.this, MainhomeActivity.class);
				startActivity(intent);
				finish();
			}
		});	
	}
	
	/*改变浮点的选中*/
	private void dotChange(int index){
        for (int i = 0; i < imges.size(); i++) {
           if (i == index) {
        	   imges.get(i).setEnabled(false);
        	   imges.get(i).setBackgroundResource(R.drawable.dotunfoucs);
           }else {
        	   imges.get(i).setEnabled(true);
        	   imges.get(i).setBackgroundResource(R.drawable.dotselected);
           }	
        }
     }
	
	/**
	 * 是否显示
	 */
	private void isShow(){
		 /*隐藏所有滚动*/
    	for (ImageView tempImageView : imges)
		{
			tempImageView.setVisibility(View.INVISIBLE);
		}
    	btn_inApp.setVisibility(View.VISIBLE);
	}
	
	private void isHide(){
		for (ImageView tempImageView : imges)
		{
			tempImageView.setVisibility(View.VISIBLE);
		}
    	btn_inApp.setVisibility(View.INVISIBLE);
        
		
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		
		return false;
	}

	/**
	 * why?
	 * @param event
	 * @return
	 */
    public boolean onTouchEvent(MotionEvent event) {  
        viewFlipper.stopFlipping(); //点击事件后，停止自动播放   
        viewFlipper.setAutoStart(false);      
        return gestureDetector.onTouchEvent(event);         // 注册手势事件   
    }  
    
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		if (e2.getX() - e1.getX() > 120) {           
        	// 从左向右滑动（左进右出）   
            Animation rInAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_in);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）   
            Animation rOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）   
            viewFlipper.setInAnimation(rInAnim);  
            viewFlipper.setOutAnimation(rOutAnim);  
            View viewfirst = viewFlipper.getChildAt(0);
            View viewLast = viewFlipper.getChildAt(imgs.length-1);
            View currentView = viewFlipper.getCurrentView();
            if(viewLast == currentView){
            	isShow();
            }
            isHide();
            if (viewfirst == currentView) {
            	
            } 
            else {
               viewFlipper.showPrevious();  
               dotChange(viewFlipper.getDisplayedChild());
            }
            
            return true;  
            
        } else if (e2.getX() - e1.getX() < -120) {        // 从右向左滑动（右进左出）   
            Animation lInAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_in);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）   
            Animation lOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_out);     // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）   
 
            viewFlipper.setInAnimation(lInAnim);  
            viewFlipper.setOutAnimation(lOutAnim);  
            
            View view = viewFlipper.getChildAt(imgs.length-1);
            View view1 = viewFlipper.getCurrentView();
            if (view == view1) {
                 isShow();
            } else {
                isHide();
               viewFlipper.showNext();   
               dotChange(viewFlipper.getDisplayedChild());
            }
            return true;  
        }  
        return true;  
	}



	@Override
	public void onLongPress(MotionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
