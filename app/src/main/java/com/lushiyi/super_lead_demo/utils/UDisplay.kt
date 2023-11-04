package com.lushiyi.super_lead_demo.utils

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Window
import android.view.WindowManager

class UDisplay {

    companion object {

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        /**
         * 获取屏幕高度
         */
        fun getScreenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        /**
         * 获取屏幕分辨率
         */
        fun getScreenRatio(context: Context): String {
            return getScreenWidth(context).toString() + "X" + getScreenHeight(context).toString()
        }

        /**
         * 扩展使用刘海屏
         */
        fun useSpecialScreen(window: Window) {
            //允许window 的内容可以上移到刘海屏状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Log.i("","适配刘海屏")
                val lp = window.attributes
                lp.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                window.attributes = lp
            }
        }


        /**
         * dp转px
         */
//        fun dip2px(dipValue: Float): Int {
//            val scale = appContext.resources.displayMetrics.density
//            return (dipValue * scale + 0.5f).toInt()
//        }

        /**
         * px转dp
         */
//        fun px2dip(pxValue: Float): Int {
//            val scale = appContext.resources.displayMetrics.density
//            return (pxValue / scale + 0.5f).toInt()
//        }
    }
}
