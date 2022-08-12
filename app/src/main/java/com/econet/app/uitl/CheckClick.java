package com.econet.app.uitl;

/**
 * @author dai.jianhui
 */
public class CheckClick {

    private static final long CLICK_DELAY = 1500;
    private static long mOldClickTime;
    public static boolean isClickEvent() {
        long time = System.currentTimeMillis();
        if (time - mOldClickTime < CLICK_DELAY)
            return false;

        mOldClickTime = time;
        return true;
    }
}
