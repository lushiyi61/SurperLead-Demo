package com.lushiyi.super_lead_demo.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


object UBluetooth {
    private val tag = ULogTag.getTag("UBluetooth")
    lateinit var bluetoothManager: BluetoothManager
    var permission: Boolean = false

    fun init(bluetoothManager: BluetoothManager, permission: Boolean) {
        this.bluetoothManager = bluetoothManager
        this.permission = permission
    }

    // 获取BT设备
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("MissingPermission")
    fun getBTDevice(name: String): BluetoothDevice? {
        Log.i(tag, "获取蓝牙状态：${bluetoothManager.adapter.isEnabled},蓝牙权限：${permission}")
        if (!bluetoothManager.adapter.isEnabled || !permission) {
            return null
        }

        val pairedDevices = bluetoothManager.adapter.bondedDevices
        Log.i(tag, "蓝牙设备数量：${pairedDevices.size}")
        if (pairedDevices.size > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (device in pairedDevices) {
//                Log.i(tag, "蓝牙设备：${device.alias}")
                if (device.name.contains(name) || device.alias?.contains(name) == true) {
                    return device
                }
            }
        }
        return null
    }

}