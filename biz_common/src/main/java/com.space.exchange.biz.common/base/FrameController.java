package com.space.exchange.biz.common.base;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Handler;

/**
 * The class <code>FrameController</code>
 * <p>
 * FrameController控制器,要想使用此框架,Activty必须实现.
 *
 * @author Tonghu Lei
 * @version 1.0
 */
public interface FrameController {

	
	/**
	 * replace  一个Fragment
	 * 
	 * @param clz
	 * @param args
	 * @return
	 */
	boolean loadFragment(Class<?> clz, Bundle args);

	/**
	 * 返回上一层的Fragment
	 */
	void backFragment();

	/**
	 * 返回上几层的Fragment
	 * 
	 * @param count
	 */
	void backFragment(int count);
	/**
	 * 返回上几层的Fragment
	 * @param
	 * @param
	 */
	void backFragment(Bundle bundle);

	/**
	 * 设置滑动菜单,滑动模式 设置值为(TOUCHMODE_NONE,SlidingMenu.TOUCHMODE_MARGIN,SlidingMenu,
	 * TOUCHMODE_FULLSCREEN)
	 * 
	 * @param mode
	 * @return
	 */
	boolean setSlidingMenuTouchModeAbove(int mode);

	/**
	 * 得到当前Handler
	 * 
	 * @return
	 */
	Handler getHandler();

	/**
	 * 取得ContentResolver
	 * 
	 * @return
	 */
	ContentResolver getContentResolver();
	
	/**
	 * 清空该Fragment之前栈里面
	 * @param clz
	 */
	void clearFragment(Class<?> clz);

}
