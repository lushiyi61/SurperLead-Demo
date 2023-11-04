package com.lushiyi.super_lead_demo

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.lushiyi.super_lead_demo.ble.BleDevice
import com.lushiyi.super_lead_demo.utils.UBluetooth

class MainActivity : AppCompatActivity() {

    companion object {
        var isInit = false
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isInit) {
            // 防止多次初始化
            init()
            isInit = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun init() {
        // 获取连接蓝牙权限
        val permission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
        // 初始化蓝牙工具类
        UBluetooth.init(getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager, permission)

        if (!UBluetooth.bluetoothManager.adapter.isEnabled) Toast.makeText(
            this,
            "请开启蓝牙，否则部分功能无法使用",
            Toast.LENGTH_LONG
        ).show()

        if (!permission) Toast.makeText(
            this,
            "请开启蓝牙权限，否则部分功能无法使用",
            Toast.LENGTH_LONG
        ).show()


        // 初始化蓝牙扫码枪
        BleDevice(
            this,
            "S23031300009",
            "6e400001-b5a3-f393-e0a9-e50e24dcca9e",
            "6e400003-b5a3-f393-e0a9-e50e24dcca9e",
            "00002902-0000-1000-8000-00805f9b34fb",
            doSomething = {  s ->
                try {
                    runOnUiThread {
                        // 呼叫react
                        val js = "window.onBarCodeEvent('${s}')"
//                        webView?.evaluateJavascript(
//                            js,
//                            ValueCallback<String>() { })
                    }
                } catch (e: Exception) {

                } finally {
                    return@BleDevice
                }
            }
        )
    }
}