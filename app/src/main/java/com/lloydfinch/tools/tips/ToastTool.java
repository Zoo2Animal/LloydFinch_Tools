package com.lloydfinch.tools.tips;

import android.content.Context;
import android.widget.Toast;

import com.lloydfinch.tools.thread.SafeHandler;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Name: ToastTool
 * Author: lloydfinch
 * Function: Toast工具
 * Date: 2020-03-09 19:40
 * Modify: lloydfinch 2020-03-09 19:40
 */
public class ToastTool {
    public static boolean DEBUGABLE = true;

    private static Context mContext;

    /**
     * 这里建议在Application中bind
     *
     * @param context
     */
    public static void bind(Context context) {
        mContext = context;
    }

    //<editor-fold desc="常规展示">
    public static void showShort(String msg) {
        SafeHandler.post(() -> {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        });
    }

    public static void showLong(String msg) {
        SafeHandler.post(() -> {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        });
    }
    //</editor-fold>

    //<editor-fold desc="debug展示">
    public static void debugShort(String msg) {
        SafeHandler.post(() -> {
            if (DEBUGABLE) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void debugLong(String msg) {
        SafeHandler.post(() -> {
            if (DEBUGABLE) {
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="排队展示，有的sb手机短时间内有多个Toast，就不会展示后续Toast">
    private Queue<String> queue = new LinkedList<>();
    public static long SHORT = 2000;
    public static long LONG = 3500;

    public void enqueueShort(String msg) {
        if (queue.contains(msg)) {
            return;
        }

        long delay = (queue.size()) * SHORT;
        queue.offer(msg);
        SafeHandler.postDelay(() -> {
            showShort(msg);
            SafeHandler.postDelay(() -> {
                queue.poll();
            }, SHORT);
        }, delay);
    }
    //</editor-fold>
}
