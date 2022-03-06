package com.space.exchange.biz.net.utils;


/**
 * 
 * @Description: 防止过快点击造成多次事件
 * @date 下午3:03:13
 */
public class DoubleClickUtil {

	private static long lastClickTime;
	private static final int MIN_CLICK_DELAY_TIME = 2000;
	private static final int LONG_CLICK_DELAY_TIME = 1500;
	private static final int SHORT_CLICK_DELAY_TIME = 500;

	public static boolean isCommonClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime > MIN_CLICK_DELAY_TIME) {
			lastClickTime = time;
			return true;
		}else{
			return false;
		}
	}

	public static boolean isLongCommonClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime > LONG_CLICK_DELAY_TIME) {
			lastClickTime = time;
			return true;
		}else{
			return false;
		}
	}

	public static boolean isShortCommonClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime > SHORT_CLICK_DELAY_TIME) {
			lastClickTime = time;
			return true;
		}else{
			return false;
		}
	}
}
