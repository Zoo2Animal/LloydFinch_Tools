package com.lloydfinch.tools.thread;

/**
 * Name: SafeHandler
 * Author: lloydfinch
 * Function: UI的handler，安全的，用于从任何地方切换到UI线程使用
 * Date: 2020-03-09 19:50
 * Modify: lloydfinch 2020-03-09 19:50
 */
public class SafeHandler {

    /**
     * 开发者可以使用此标记来标记此Handler是否为safe的，如果是safe的，则会
     * 自动catch任何Throwable起到保护作用，否则会抛出异常
     */
    public static boolean SAFE = true;

    /**
     * warning!!!
     * 此Handler的生命周期跟Application同步，是static的，注意memory leak!!，及时removeCallback
     */
    private static UIHandler mHandler = new UIHandler();

    public static void post(Runnable runnable) {
        try {
            postDelay(runnable, 0);
        } catch (Throwable throwable) {
            if (SAFE) {
                throwable.printStackTrace();
            } else {
                throw throwable;
            }
        }
    }

    public static void postDelay(Runnable runnable, long delay) {
        try {
            mHandler.postDelay(runnable, delay);
        } catch (Throwable throwable) {
            if (SAFE) {
                throwable.printStackTrace();
            } else {
                throw throwable;
            }
        }
    }

    public static void remove(Runnable runnable) {
        mHandler.remove(runnable);
    }

    public static void remove(Runnable runnable, Object token) {
        mHandler.remove(runnable, token);

    }

    /**
     * 此方法不要轻易调用，因为Handler的生命周期是Application同步的，removeAll意味着移除一切
     */
    public static void removeAll() {
        mHandler.removeAll();
    }
}
