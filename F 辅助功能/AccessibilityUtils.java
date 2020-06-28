package com.shijing.tobecomegod.utils;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.shijing.tobecomegod.application.MyApplication;

import java.io.File;
import java.util.List;

/**
 * Created by popfisher on 2017/7/11.
 * 使用本工具前需要先初始化{@link #updateEvent(AccessibilityService, AccessibilityEvent)}
 */

public class AccessibilityUtils {

    private static Context context = MyApplication.instance;
    private static AccessibilityEvent accessibilityEvent;
    private static AccessibilityService accessibilityService;

    private AccessibilityUtils() {
        throw new RuntimeException(getClass().getName() + " can not be new!");
    }

    /**
     * 初始化/更新 service&event
     */
    public static void updateEvent(AccessibilityService service, AccessibilityEvent event) {
        if (service != null && accessibilityService == null) {
            accessibilityService = service;
        }
        if (event != null) accessibilityEvent = event;
    }

    private static AccessibilityNodeInfo getRootNodeInfo() {
        if (Build.VERSION.SDK_INT >= 16) {
            // 建议使用getRootInActiveWindow，这样不依赖当前的事件类型
            return accessibilityService.getRootInActiveWindow();
            // 下面这个必须依赖当前的AccessibilityEvent
//            nodeInfo = curEvent.getSource();
        } else {
            return accessibilityEvent.getSource();
        }
    }

    /**
     * 根据Text搜索所有符合条件的节点, 模糊搜索方式
     */
    public static List<AccessibilityNodeInfo> findNodesByText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
           return nodeInfo.findAccessibilityNodeInfosByText(text);
        }
        return null;
    }

    /**
     * 根据View的ID搜索符合条件的节点,精确搜索方式;
     * 这个只适用于自己写的界面，因为ID可能重复
     * api要求18及以上
     * @param viewId 要加上包名,示例:com.google.example:id/cb_checkbox
     */
    public static List<AccessibilityNodeInfo> findNodesById(String viewId) {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
            if (Build.VERSION.SDK_INT >= 18) {
                return nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
            }
        }
        return null;
    }

    public static boolean clickByText(String text) {
        return performClick(findNodesByText(text));
    }

    /**
     * 根据View的ID搜索符合条件的节点,精确搜索方式;
     * 这个只适用于自己写的界面，因为ID可能重复
     * api要求18及以上
     * @param viewId
     * @return 是否点击成功
     */
    public static boolean clickById(String viewId) {
        return performClick(findNodesById(viewId));
    }

    /**
     * 模拟点击
     */
    private static boolean performClick(List<AccessibilityNodeInfo> nodeInfos) {
        if (nodeInfos != null && !nodeInfos.isEmpty()) {
            for (AccessibilityNodeInfo node : nodeInfos) {
                // 获得点击View的类型
                System.out.println("View类型：" + node.getClassName());
                // 进行模拟点击
                if (node.isEnabled()) {
                    return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
        return false;
    }

    /**
     * 模拟点击返回键
     */
    public static boolean clickBackKey() {
        return performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 模拟action所对应事件
     */
    private static boolean performGlobalAction(int action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return accessibilityService.performGlobalAction(action);
        }
        return false;
    }

    /**
     * 判断是否有辅助功能权限
     */
    public static boolean isAccessibilitySettingsOn(Class<? extends AccessibilityService> service) {

        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext()
                    .getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        String packageName = context.getPackageName();
        //原来是这么写的:getClass().getCanonicalName()
        String serviceStr = packageName + File.separator + service.getName();
        System.out.println("service:" + serviceStr);
        if (accessibilityEnabled == 1) {
            TextUtils.SimpleStringSplitter stringSplitter = new TextUtils.SimpleStringSplitter(':');

            String settingValue = Settings.Secure.getString(context.getApplicationContext()
                    .getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                stringSplitter.setString(settingValue);
                while (stringSplitter.hasNext()) {
                    String accessabilityService = stringSplitter.next();
                    System.out.println("accessabilityService:" + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(serviceStr)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 跳转到系统设置页面开启辅助功能
     */
    public static void openAccessibility(Context context){
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 获取打开辅助功能的Intent
     */
    public static Intent getAccessibilitySettingIntent() {
        // 一些垃圾品牌的手机可能不是这个Intent,无需适配
        return new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    }
}
