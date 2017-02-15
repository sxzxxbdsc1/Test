package com.zhy.demo.mynews.utils;

import android.bluetooth.BluetoothAdapter;


/**
 * Created Time: 2016/9/14.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：开启蓝牙功能
 */
public class BluetoothUtils {
    static BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    public static boolean startBlueTooth() {
        if (!btAdapter.isEnabled()) {
            boolean enable = btAdapter.enable();
            if (enable) {
                return true;
            } else {
//                Utils.showToast(BaseApplication.getApplication(), "请务必允许开启蓝牙!!!");
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 断开之后重新连接标签
     *
     * @return
     */
    public static int isBlueToothOpen() {
        if (!btAdapter.isEnabled()) {
            boolean enable = btAdapter.enable();
            if (enable) {
                return 1;
            } else {
//                Utils.showToast(BaseApplication.getApplication(), "请务必允许开启蓝牙!!!");
                return 2;
            }
        } else {
            return 3;//已经开启的
        }
    }
}
